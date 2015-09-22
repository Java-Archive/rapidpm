/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 13.03.2010
 * Time: 22:27:47
 */

public abstract class DAO<K extends Number, E> implements Serializable {
  private static final Logger logger = Logger.getLogger(DAO.class);
  protected Class<E> entityClass;
  protected OrientGraph orientDB = null;
  protected EntityUtils<E> entityUtils;
  protected DAO(final OrientGraph orientDB, final Class<E> entityClass) {
    this.orientDB = orientDB;
    this.entityClass = entityClass;
    this.entityUtils = new EntityUtils<>(entityClass);
  }

  public void setOrientDB(final OrientGraph orientDB) {
    this.orientDB = orientDB;
  }

  public OrientGraph getEntityManager() {
    return orientDB;
  }


  /**
   * Liefert die Entität passend zum PKey
   *
   * @param rid PKey der Entität
   *
   * @return Die Entität oder null falls nichts gefunden wurde.
   */
  public E findByID(final String rid, boolean loadFull) {
    final Vertex vertex = orientDB.getVertex(rid);
    final E entity = entityUtils.convertVertexToEntity(vertex);
    if (loadFull) {
      try {
        loadFull(entity);
      } catch (InvalidKeyException e) {
        logger.error(e.getMessage());
      } catch (NotYetImplementedException e) {
        logger.error(e.getCause());
      }

    }
    return entity;
  }

  protected abstract E loadFull(final E entity) throws InvalidKeyException, NotYetImplementedException;

  public List<E> findAll() {
    try {
      final Iterable<Vertex> entityVertices = orientDB.getVerticesOfClass(entityClass.getSimpleName());
      return entityUtils.convertVerticesToEntities(entityVertices);
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  public E createEntityFlat(final E entity) {
    final OrientVertex[] v = new OrientVertex[1];
    new OrientDBTransactionExecutor(orientDB) {
      @Override
      public void doSpecificDBWork() {
        v[0] = orientDB.addVertex("class:" + entityClass.getSimpleName(), entityUtils.convertEntityToKeyValueMap(entity));
      }
    }.execute();
    return entityUtils.convertVertexToEntity(v[0]);
  }

  public abstract E createEntityFull(final E entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException;

  public void addEdgeFromVertexToVertex(final Vertex fromVertex, final String edgeLabel, final Vertex toVertex) {
    new OrientDBTransactionExecutor(orientDB) {
      @Override
      public void doSpecificDBWork() {
        fromVertex.addEdge(edgeLabel, toVertex);
      }
    }.execute();
  }

  public void deleteByEntity(final E entity, final boolean deleteFull) {
    final Map<String, Object> entityFieldMap = checkAndConvertEntityBeforeDeletion(entity);
    if (entityFieldMap == null) {
      return;
    }
    deleteByID(entityFieldMap.get("id").toString(), deleteFull);
  }

  private Map<String, Object> checkAndConvertEntityBeforeDeletion(E entity) {
    final Map<String, Object> entityFieldMap = entityUtils.convertEntityToKeyValueMap(entity);
    if (!entityFieldMap.containsKey("id") || entityFieldMap.get("id").toString().equals("")) {
      logger.error("couldn't delete transient entity of class " + entity.getClass().getSimpleName());
      return null;
    }
    return entityFieldMap;
  }

  public void deleteByID(final String id, final boolean deleteFull) {
    if (id == null || id.equals("")) {
      logger.error("tried to delete entity without id");
      return;
    }
    new OrientDBTransactionExecutor(orientDB) {
      @Override
      public void doSpecificDBWork() {
        if (deleteFull) {
          deleteByIDFull(id);
        } else {
          deleteByIDFlat(id);
        }
      }
    }.execute();
  }

  public void deleteByIDFull(final String id) {

  }

  /*
  *   DON'T USE THIS FROM OUTSIDE
  */
  protected void deleteByIDFlat(final String id) {
    final String deleteCommand = "DELETE VERTEX " + id;
    orientDB.getRawGraph().command(new OCommandSQL(deleteCommand)).execute();

  }

  public void updateByEntity(final E entity, final boolean full) {
    if (full) {
      updateByEntityFull(entity);
    } else {
      updateByEntityFlat(entity);
    }
  }

  protected void updateByEntityFull(final E entity) {

  }


  protected void updateByEntityFlat(final E entity) {
    new OrientDBTransactionExecutor(orientDB) {
      @Override
      public void doSpecificDBWork() {
        final Map<String, Object> fieldValueMap = entityUtils.convertEntityToKeyValueMap(entity);
        final String id = fieldValueMap.get("id").toString();
        fieldValueMap.remove("id");
        String updateCommand = "UPDATE " + entity.getClass().getSimpleName() + " SET ? WHERE @rid = '" + id + "'";
        updateCommand = updateCommand.replace("?", createFieldUpdateString(fieldValueMap));
        orientDB.getRawGraph().command(new OCommandSQL(updateCommand)).execute();
      }
    }.execute();
  }

  private String createFieldUpdateString(Map<String, Object> fieldValueMap) {
    final StringBuilder sb = new StringBuilder();
    for (final String fieldName : fieldValueMap.keySet()) {
      String value;
      final Object valueObject = fieldValueMap.get(fieldName);
      if (valueObject instanceof String) {
        value = "'" + valueObject.toString() + "'";
      } else if (valueObject instanceof Date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = formatter.format(valueObject);
      } else {
        value = valueObject.toString();
      }
      sb.append(fieldName + " = " + value);
      sb.append(", ");
    }
    return sb.substring(0, sb.length() - 2);
  }

  protected OrientVertex removeEdgesOfKindFromVertex(final Direction direction, final String edgeLabel, final Vertex vertex) {
    new OrientDBTransactionExecutor(orientDB) {
      @Override
      public void doSpecificDBWork() {
        Iterable<Edge> edges = vertex.getEdges(direction, edgeLabel);
        for (final Edge oneEdge : edges) {
          OrientVertex vertex1 = (OrientVertex) oneEdge.getVertex(Direction.IN);
          OrientVertex vertex2 = (OrientVertex) oneEdge.getVertex(Direction.OUT);
          vertex1.reload();
          vertex2.reload();
          oneEdge.remove();
        }
      }
    }.execute();
    final OrientVertex updatedVertex = orientDB.getVertex(vertex.getId());
    updatedVertex.reload();
    return updatedVertex;

  }


//    //TODO die Abfrage fehlt noch..
//    public static class RevisionEntity<E> {
//        private E entity;
//        private int revision;
//        private int revisionType;
//
//        public RevisionEntity() {
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("RevisionEntity");
//            sb.append("{entity=").append(entity);
//            sb.append(", revision=").append(revision);
//            sb.append(", revisionType=").append(revisionType);
//            sb.append('}');
//            return sb.toString();
//        }
//
//        public E getEntity() {
//            return entity;
//        }
//
//        public void setEntity(final E entity) {
//            this.entity = entity;
//        }
//
//        public int getRevision() {
//            return revision;
//        }
//
//        public void setRevision(final int revision) {
//            this.revision = revision;
//        }
//
//        public int getRevisionType() {
//            return revisionType;
//        }
//
//        public void setRevisionType(final int revisionType) {
//            this.revisionType = revisionType;
//        }
//    }


//    public List<E> loadWithOIDList(final List<Long> oids) {
//        final List<E> entityliste = new ArrayList<>();
//        if (oids == null || oids.isEmpty()) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("OID-Liste war leer bzw. null..");
//            }
//        } else {
//            String oidtxt = "";
//            for (int i = 0; i < oids.size(); i++) {
//                oidtxt = oidtxt + oids.get(i) + "";
//                if (i == (oids.size() - 1)) {
//                    //
//                } else {
//                    oidtxt = oidtxt + " , ";
//                }
//            }
//
//            final TypedQuery<E> query = orientDB.createQuery("from " + entityClass.getName() + " e where e.id in (" + oidtxt + ")", entityClass);
//            final List<E> resultList = query.getResultList();
//            if (resultList.isEmpty()) {
//
//            } else {
//                entityliste.addAll(resultList);
//            }
//        }
//        return entityliste;
//    }
//
//    @Deprecated
//    public List<E> loadWithOIDList(final Set<Long> oids) {
//        final List<Long> oidlist = new ArrayList<>();
//        oidlist.addAll(oids);
//        return loadWithOIDList(oidlist);
//    }
//
//    public Set<E> loadWithOIDSet(final Set<Long> oids) {
//        final Set<E> entitySet = new HashSet<>();
//        if (oids == null || oids.isEmpty()) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("OID-Set war leer bzw. null..");
//            }
//        } else {
//            final String oidtxt = Joiner.on(',').skipNulls().join(oids);
//            final TypedQuery<E> query = orientDB.createQuery("from " + entityClass.getName() + " e where e.id in (" + oidtxt + ")", entityClass);
//            final List<E> resultList = query.getResultList();
//            entitySet.addAll(resultList);
//        }
//        return entitySet;
//    }
//
//
//    /**
//     * @param entity
//     * @return true wenn alles ok ist.
//     */
//    public boolean refresh(final E entity) {
//        final boolean result = false;
//        orientDB.refresh(entity);  //TODO einstellbar machen
//        return true;
//    }

  public Iterable<Vertex> loadAllEntities() {
    final Iterable<Vertex> entityList = orientDB.getVerticesOfClass(entityClass.getSimpleName());
    return entityList;
  }

//    public E getSingleResultOrNull(final TypedQuery<E> typedQuery) {
//        try {
//            final E singleResult = typedQuery.getSingleResult();
//            return singleResult;
//        } catch (Exception e) {
//            logger.info(e);
//            return null;
//                try {
//                    return entityClass.newInstance();
//                } catch (InstantiationException e1) {
//                    logger.error(e1);
//                    return null;
//                } catch (IllegalAccessException e1) {
//                    logger.error(e1);
//                    return null;
//                }
//        }
//    }


//    public void connectEntity(final E entity, final List<E> entityList) {
//        final Long oid = entityUtils.getOIDFromEntity(entity);
//        if (oid == null || oid < 0L) {
//            throw new IllegalStateException("Die Entität " + entityClass.getSimpleName() + " besitzt keine gültige OID: " + oid);
//        }
//        if (entityUtils.containsOID(entityList, oid)) {
//            throw new AlreadyConnectedException("Die Entität " + entityClass.getSimpleName() + " mit der OID " + oid + " ist bereits verknüpft");
//        }
//        entityList.add(entity);
//    }
//
//    public E connectEntity(final Long entityOID, final List<E> entityList) {
//        final E entity = findByID(entityOID);
//        if (entity == null) {
//            throw new IllegalArgumentException("Es existiert keine Entität " + entityClass.getSimpleName() + " mit der OID " + entityOID);
//        }
//        connectEntity(entity, entityList);
//        return entity;
//    }


//    } //BAseDAO
}
