package org.rapidpm.orm;

import org.rapidpm.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

/**
 * BaseDAO Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/16/2010</pre>
 */
public class BaseDAOTest extends BaseTest {

    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("baseormJDBCbeta");
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
