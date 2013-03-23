package org.rapidpm;

import org.rapidpm.persistence.DaoFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p/>
 * User: svenruppert
 * Date: 13.11.12
 * Time: 10:46
 * <p/>
 * Version:
 */
public class CreateDatabase {

    public static void main(String[] args) {
//        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("integration-test");
//        final EntityManager entityManager = emf.createEntityManager();
        final DaoFactory daoFactoryFactory = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);
//        daoFactory.setEntityManager(entityManager);
        final EntityManager entityManager = daoFactoryFactory.getEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        entityManager.flush();
        entityManager.close();
//        emf.close();



    }

}
