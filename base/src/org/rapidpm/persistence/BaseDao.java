/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;
import org.jboss.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 13.03.2010
 *        Time: 22:27:47
 */

@SuppressWarnings("EjbInterceptorInspection")
public class BaseDAO<K extends Number, E> implements Serializable {
    private static final Logger logger = Logger.getLogger(BaseDAO.class);

//    @Inject()
//    private LogFactory loggerFactory

//    public BaseDAO(final String persistenceUnitName) {
//        final EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
//        this.em = emf.createEntityManager();
//    }

    protected BaseDAO(final AuditReader auditReader, final EntityManager em, final Class<E> entityClass) {
        this.auditReader = auditReader;
        this.entityManager = em;
        this.entityClass = entityClass;
    }


    protected BaseDAO(final EntityManager entityManager, final Class<E> entityClass) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

    //    private EntityManager em = null;
    private AuditReader auditReader = null;
    protected Class<E> entityClass;
    protected EntityManager entityManager = null;
    private final EntityUtils entityUtils = new EntityUtils();


    public void setAuditReader(AuditReader auditReader) {
        this.auditReader = auditReader;
    }

//    private final EntityUtils entityUtils = new EntityUtils(); // darf nicht static sein!

    public void flush() {
        if (entityManager != null) {
            entityManager.flush();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("EM is null not flushing");
            }
        }
    }

//    /**
//     * Erzeuge einen eigenen EntityManagerFactory - persistenceUnit
//     */
//    public BaseDAO() {
//        super();
//    }

    public void setEm(final EntityManager em) {
        this.entityManager = em;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public AuditReader getAuditReader() {
        return AuditReaderFactory.get(getEntityManager());
    }

    public EntityUtils getEntityUtils() {
        return entityUtils;
    }


    public <T> void removeTX(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("removeTX entity: " + entity);
        }
        if (entity == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Entity war null....");
            }
        } else {
            final Class<? extends Object> aClass = entity.getClass();
            final Entity annotation = aClass.getAnnotation(Entity.class);
            if (annotation == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("Obj ist keine Entity.." + entity);
                }
            } else {
                simpleRemoveTX(entity);
            }
        }
    }

    public <T> void remove(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("remove entity: " + entity);
        }
        if (entity == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Entity war null....");
            }
        } else {
            final Class<? extends Object> aClass = entity.getClass();
            final Entity annotation = aClass.getAnnotation(Entity.class);
            if (annotation == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("Obj ist keine Entity..");
                }
            } else {
                getEntityManager().remove(entity);
            }
        }
    }


    public <T> void saveOrUpdate(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("saveOrUpdateTX entity " + entity);
        }
        if (entity == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Entity war null....");
            }
        } else {
            final Long oid = entityUtils.getOIDFromEntity(entity);
            if (oid == null || oid == -1L) {
                getEntityManager().persist(entity);
            } else {
                getEntityManager().merge(entity);
            }
        }
    }

    public <T> void saveOrUpdateTX(final T entity) {
        Integer i = null;
        i++;
        if (logger.isInfoEnabled()) {
            logger.info("saveOrUpdateTX entity " + entity);
        }
        if (entity == null) {
            if (logger.isInfoEnabled()) {
                logger.info("Entity war null....");
            }
        } else {
            final Long oid = entityUtils.getOIDFromEntity(entity);
            if (oid == null || oid == -1L) {
                simplePersistTX(entity);
            } else {
                simpleMergeTX(entity);
            }
        }
    }


    private <T> void simpleRemoveTX(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("simpleRemove entity : " + entity);
        }
        new SimpleRemoveTransaction<T>().remove(entity);
    }

    private <T> void simplePersistTX(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("simplePersist entity : " + entity);
        }
        new SimplePersistTransaction<T>().persist(entity);
    }

    private <T> void simpleMergeTX(final T entity) {
        if (logger.isInfoEnabled()) {
            logger.info("simpleMerge entity : " + entity);
        }
        new SimpleMergeTransaction<T>().persist(entity);
    }

    private class SimpleRemoveTransaction<T> {
        public void remove(final T entity) {
            new Transaction() {

                public void doTask() {
                    final EntityManager entityManager = getEntityManager();
                    entityManager.remove(entity);
                    //                    entityManager.refresh(entity);
                }
            }.execute();
        }
    }

    private class SimplePersistTransaction<T> {
        public void persist(final T entity) {
            new Transaction() {

                public void doTask() {
                    final EntityManager entityManager = getEntityManager();
                    entityManager.persist(entity);
                    //                    entityManager.refresh(entity);
                }
            }.execute();
        }
    }

    private class SimpleMergeTransaction<T> {
        public void persist(final T entity) {
            new Transaction() {

                public void doTask() {
                    final EntityManager entityManager = getEntityManager();
                    entityManager.merge(entity);
                    //                    entityManager.refresh(entity);
                }
            }.execute();
        }
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public abstract class Transaction {
        private final EntityTransaction transaction = entityManager.getTransaction();

        public abstract void doTask();

        public void execute() {
            try {
                transaction.begin();
                doTask();
                if (transaction.isActive()) {
                    transaction.commit();
                } else {
                    logger.warn("tx nicht mehr active.. ");
                }

            } catch (Exception e) {
                logger.error(e);
//                System.out.println("e = " + e);
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                } else {
                }
            }
        }
    }


    public static class EntityUtils<T> {

        public <T> Long getOIDFromEntity(final T entity) {
            Long oid = -1L;
            final Class<?> aClass = entity.getClass();
            try {
                final Method method = aClass.getDeclaredMethod("getId");
                oid = (Long) method.invoke(entity);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error(e);
            }
            return oid;
        }

        public <T> boolean containsOID(final List<T> entityList, final Long oid) {
            for (final T entity : entityList) {
                final Long oidFromEntity = getOIDFromEntity(entity);
                if (oid.equals(oidFromEntity)) {
                    return true;
                }
            }
            return false;
        }

        public <T> List<Long> getOIDs(final List<T> objs) {
            final List<Long> oids = new ArrayList<>();
            for (final T t : objs) {
                final Long oidFromEntity = getOIDFromEntity(t);
                if (oidFromEntity.equals(-1L)) {
                    //Fehler
                } else {
                    oids.add(oidFromEntity);
                }
            }
            return oids;
        }
    }


//    public static abstract class BaseDAO<K extends Number, E extends Object> {
//        private static final Logger logger = Logger.getLogger(BaseDAO.class);
//        /**
//         * Die Aktion ist in einer Transaktion gekapselt.
//         * Liefert true zurück wenn der Forgang erfolgreich absolviert wurde.
//         *
//         * @param entity die zu löschende Entität
//         * @return true wenn alles OK ist sonst false
//         */
//        public boolean remove(final E entity) {
//            if (entity == null) {
//                return false;
//            }
//            entityManager.remove(entity);
//            return true;
//        }


    //    /**
    //     * Liefert die Entität passend zum PKey
    //     *
    //     * @param id PKey der Entität
    //     *
    //     * @return Die Entität oder null falls nichts gefunden wurde.
    //     */
    public E findByID(final Long id) {
        final TypedQuery<E> query = entityManager.createQuery("from " + entityClass.getName() + " e where e.id=:id", entityClass);
        final TypedQuery<E> typedQuery = query.setParameter("id", id);
        return getSingleResultOrNull(typedQuery);
    }

    public List<E> loadAllRevisionsFor(final Long oid) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        final AuditQueryCreator queryCreator = reader.createQuery();
        final AuditQuery auditQuery = queryCreator.forRevisionsOfEntity(entityClass, true, false).add(AuditEntity.id().eq(oid));
        return auditQuery.getResultList();
    }

    //TODO die Abfrage fehlt noch..
    public static class RevisionEntity<E> {
        private E entity;
        private int revision;
        private int revisionType;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("RevisionEntity");
            sb.append("{entity=").append(entity);
            sb.append(", revision=").append(revision);
            sb.append(", revisionType=").append(revisionType);
            sb.append('}');
            return sb.toString();
        }

        public E getEntity() {
            return entity;
        }

        public void setEntity(final E entity) {
            this.entity = entity;
        }

        public int getRevision() {
            return revision;
        }

        public void setRevision(final int revision) {
            this.revision = revision;
        }

        public int getRevisionType() {
            return revisionType;
        }

        public void setRevisionType(final int revisionType) {
            this.revisionType = revisionType;
        }
    }


    public List<E> loadWithOIDList(final List<Long> oids) {
        final List<E> entityliste = new ArrayList<>();
        if (oids == null || oids.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("OID-Liste war leer bzw. null..");
            }
        } else {
            String oidtxt = "";
            for (int i = 0; i < oids.size(); i++) {
                oidtxt = oidtxt + oids.get(i) + "";
                if (i == (oids.size() - 1)) {
                    //
                } else {
                    oidtxt = oidtxt + " , ";
                }
            }

            final TypedQuery<E> query = entityManager.createQuery("from " + entityClass.getName() + " e where e.id in (" + oidtxt + ")", entityClass);
            final List<E> resultList = query.getResultList();
            if (resultList.isEmpty()) {

            } else {
                entityliste.addAll(resultList);
            }
        }
        return entityliste;
    }

    public List<E> loadWithOIDList(final Set<Long> oids) {
        final List<Long> oidlist = new ArrayList<>();
        oidlist.addAll(oids);
        return loadWithOIDList(oidlist);
    }


    /**
     * @param entity
     * @return true wenn alles ok ist.
     */
    public boolean refresh(final E entity) {
        final boolean result = false;
        entityManager.refresh(entity);  //TODO einstellbar machen
        return true;
    }


    public List<E> loadAllEntities() {
        final List<E> entityList = entityManager.createQuery("from " + entityClass.getName(), entityClass).getResultList();
        return entityList;
    }

    public E getSingleResultOrNull(final TypedQuery<E> typedQuery) {
        try {
            final E singleResult = typedQuery.getSingleResult();
            return singleResult;
        } catch (Exception e) {
            logger.info(e);
            return null;
//                try {
//                    return entityClass.newInstance();
//                } catch (InstantiationException e1) {
//                    logger.error(e1);
//                    return null;
//                } catch (IllegalAccessException e1) {
//                    logger.error(e1);
//                    return null;
//                }
        }
    }


    public void connectEntity(final E entity, final List<E> entityList) {
        final Long oid = entityUtils.getOIDFromEntity(entity);
        if (oid == null || oid < 0L) {
            throw new IllegalStateException("Die Entität " + entityClass.getSimpleName() + " besitzt keine gültige OID: " + oid);
        }
        if (entityUtils.containsOID(entityList, oid)) {
            throw new AlreadyConnectedException("Die Entität " + entityClass.getSimpleName() + " mit der OID " + oid + " ist bereits verknüpft");
        }
        entityList.add(entity);
    }

    public E connectEntity(final Long entityOID, final List<E> entityList) {
        final E entity = findByID(entityOID);
        if (entity == null) {
            throw new IllegalArgumentException("Es existiert keine Entität " + entityClass.getSimpleName() + " mit der OID " + entityOID);
        }
        connectEntity(entity, entityList);
        return entity;
    }


    @SuppressWarnings("ClassWithoutNoArgConstructor")
    public static class AlreadyConnectedException extends IllegalStateException {
        public AlreadyConnectedException(final String s) {
            super(s);
        }
    }
//    } //BAseDAO
}
