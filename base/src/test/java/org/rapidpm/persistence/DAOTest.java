package org.rapidpm.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.BaseTest;
import org.rapidpm.Constants;

import static org.junit.Assert.assertEquals;

/**
 * DAO Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/16/2010</pre>
 */
public class DAOTest extends BaseTest {

//    protected EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);
//    protected EntityManager entityManager = emf.createEntityManager();
    protected DaoFactory daoFactory = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);


    @Before
    public void setUp() throws Exception {
//        daoFactory.setEntityManager(entityManager);
    }

    @After
    public void tearDown() throws Exception {
//        emf.close();
        daoFactory.getEntityManager().close();
    }

    @Test
    public void testDummy() throws Exception {
        assertEquals(true, true);
    }

}
