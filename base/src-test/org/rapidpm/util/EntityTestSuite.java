/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.util;

import org.rapidpm.TestConfig;
import org.rapidpm.lang.PackageClassLoader;
import org.rapidpm.persistence.EntityFactory;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 06.10.11
 * Time: 15:20
 */
public class EntityTestSuite {
    private static final Logger logger = Logger.getLogger(EntityTestSuite.class);
    public static final int TEST_COUNT = 1;

    protected final EntityManagerFactory emf = Persistence.createEntityManagerFactory("baseormJDBCDev");
    protected EntityManager entityManager = createEntityManager();

    private final Deque<ClassId> cleanStack = new LinkedList<ClassId>();
    private final List<ErrorObject> errorList = new ArrayList<ErrorObject>();

    protected EntityManager createEntityManager() {
        entityManager = emf.createEntityManager();
        return entityManager;
    }

    protected EntityTransaction createTransaction() {
        return entityManager.getTransaction();
//        return createEntityManager().getTransaction();
    }

    public void testEntityFactory(final EntityFactory<?> entityFactory) {
        if (logger.isDebugEnabled()) {
            logger.debug("==== saveOrUpdate ====");
        }
        for (int i = 0; i < TEST_COUNT; i++) {
            final Object obj = entityFactory.createRandomEntity();
            final EntityManager em = createEntityManager();
            recursiveSave(obj);
            em.close();
            if (logger.isDebugEnabled()) {
                logger.debug("================");
            }
        }
    }

    @Test
    public void testOne() throws Exception {
//        BasicConfigurator.configure(); // REFAC log4j config
//        testEntityFactory(new PersonTestFactory());
    }

    @Test
    public void testAll() throws Exception {
//        BasicConfigurator.configure(); // REFAC log4j config

        final PackageClassLoader pcl = new PackageClassLoader();
        final List<Class> classes = pcl.getClasses("de.RapidPM");
        for (final Class aClass : classes) {
            if (aClass.getSuperclass() == EntityFactory.class) { // REFAC abgeleitet von EntityTestFactory
                if (logger.isDebugEnabled()) {
                    logger.debug("Teste " + aClass);
                }
                final EntityFactory<?> entityFactory = (EntityFactory<?>) aClass.newInstance();
                testEntityFactory(entityFactory);
            }
        }
    }

    private void save(final Object obj) {
        if (obj == null) {
            return;
        }

        final EntityTransaction transaction = createTransaction();
        transaction.begin();
        entityManager.persist(obj);
        try {
            transaction.commit();
            cleanStack.push(new ClassId(obj.getClass(), getOIDFromEntity(obj)));
            if (logger.isDebugEnabled()) {
                logger.debug("saveOrUpdate: " + obj);
            }
        } catch (RollbackException e) {
            errorList.add(new ErrorObject(obj, e));
            logger.error(e);
        }
    }

    private void remove(final Object obj) {
        if (obj == null) {
            return;
        }

        final Class<?> objClass = obj.getClass();
        final Long entityId = getOIDFromEntity(obj);
        final Object entity = entityManager.find(objClass, entityId);
        if (entity != null) {
            if (logger.isInfoEnabled()) {
                logger.info("remove: " + obj);
            }
            final EntityTransaction transaction = createTransaction();
            transaction.begin();
            entityManager.remove(entity);
            try {
                transaction.commit();
                cleanStack.remove(new ClassId(objClass, entityId));
            } catch (RollbackException e) {
                errorList.add(new ErrorObject(obj, e));
                logger.error(e);
            }
        } else {
            // Entity wurde nicht gefunden
        }
    }

    private void recursiveSave(final Object obj) {
        if (obj == null) {
            return;
        }

        for (final Field field : obj.getClass().getDeclaredFields()) {
            final Class<?> fieldType = field.getType();
            field.setAccessible(true);
            final Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error(e);
                return;
            }
            if (fieldType.isAnnotationPresent(Entity.class)) {
                recursiveSave(value);
            } else if (value instanceof Iterable) {
                final Iterable it = (Iterable) value;
                for (final Object o : it) {
                    if (o != null && o.getClass().isAnnotationPresent(Entity.class)) {
                        recursiveSave(o);
                    }
                }
            }
        }

        save(obj);
    }

    private void recursiveRemove(final Object obj) {
        if (obj == null) {
            return;
        }

        remove(obj);

        for (final Field field : obj.getClass().getDeclaredFields()) {
            final Class<?> fieldType = field.getType();
            field.setAccessible(true);
            final Object value;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                logger.error(e);
                return;
            }
            if (fieldType.isAnnotationPresent(Entity.class)) {
                recursiveRemove(value);
            } else if (value instanceof Iterable) {
                final Iterable it = (Iterable) value;
                for (final Object o : it) {
                    if (o != null && o.getClass().isAnnotationPresent(Entity.class)) {
                        recursiveRemove(o);
                    }
                }
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        if (TestConfig.isAutoCleanUp()) {
            cleanUp();
        }
        emf.close();
    }

    public void cleanUp() {
        if (logger.isInfoEnabled()) {
            logger.info("EntityTestSuite.cleanUp()");
        }
        while (!cleanStack.isEmpty()) {
            final ClassId classId = cleanStack.pop();
            final EntityManager em = createEntityManager();
            final Object entity = entityManager.find(classId.getClazz(), classId.getId());
            remove(entity);
            em.close();
        }
    }

    private static <T> Long getOIDFromEntity(final T entity) {
        Long oid = -1L;
        final Class<?> aClass = entity.getClass();
        try {
            final Method method = aClass.getDeclaredMethod("getId");
            oid = (Long) method.invoke(entity);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (NoSuchMethodException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        }
        return oid;
    }

    private static class ClassId {
        private final Class<?> clazz;
        private final Long id;

        private ClassId(final Class<?> clazz, final Long id) {
            this.clazz = clazz;
            this.id = id;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public Long getId() {
            return id;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final ClassId classId = (ClassId) o;

            if (!clazz.equals(classId.clazz)) return false;
            if (!id.equals(classId.id)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + id.hashCode();
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("ClassId");
            sb.append("{clazz=").append(clazz);
            sb.append(", id=").append(id);
            sb.append('}');
            return sb.toString();
        }
    }

    private static class ErrorObject {
        private final Object object;
        private final Throwable error;

        private ErrorObject(final Object object, final Throwable error) {
            this.object = object;
            this.error = error;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final ErrorObject that = (ErrorObject) o;

            if (!error.equals(that.error)) return false;
            if (!object.equals(that.object)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = object.hashCode();
            result = 31 * result + error.hashCode();
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("ErrorObject");
            sb.append("{object=").append(object);
            sb.append(", error=").append(error);
            sb.append('}');
            return sb.toString();
        }
    }

}