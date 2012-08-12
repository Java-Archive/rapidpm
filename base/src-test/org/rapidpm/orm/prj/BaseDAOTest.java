package org.rapidpm.orm.prj;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.01.12
 * Time: 21:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.orm.DaoFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class BaseDAOTest {

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("baseormNeoMetaJDBCbeta");
    protected EntityManager entityManager = emf.createEntityManager();
    protected DaoFactory daoFactory = new DaoFactory();


    @Before
    public void setUp() throws Exception {
        daoFactory.setEm(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        emf.close();
    }

    @Test
    public void testDummy() throws Exception {
        assertEquals(true, true);
    }

}