/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence;

import com.google.common.base.Joiner;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.gremlin.Tokens;
import org.apache.log4j.Logger;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 13.03.2010
 * Time: 22:27:47
 */

public class DAO<K extends Number, E> implements Serializable {
    private static final Logger logger = Logger.getLogger(DAO.class);

    protected DAO(final OrientGraph orientDB, final Class<E> entityClass) {
        this.orientDB = orientDB;
        this.entityClass = entityClass;
        this.entityUtils = new EntityUtils<>(entityClass);
    }

    protected Class<E> entityClass;
    protected OrientGraph orientDB = null;
    protected  EntityUtils<E> entityUtils;


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
     * @return Die Entität oder null falls nichts gefunden wurde.
     */
    public E findByID(final String rid) {
            final Vertex vertex = orientDB.getVertex(rid);
            return entityUtils.convertVertexToEntity(vertex);
    }

    public List<E> findAll(){
        try {
            final Iterable<Vertex> entityVertices = orientDB.getVerticesOfClass(entityClass.getSimpleName());
            return entityUtils.convertVerticesToEntities(entityVertices);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
