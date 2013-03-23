package org.rapidpm.persistence.prj;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.01.12
 * Time: 21:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;

import static org.junit.Assert.assertEquals;

public class BaseDAOTest {

    protected DaoFactory daoFactory = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);


    @Before
    public void setUp() throws Exception {
//        daoFactory.setEntityManager(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.getEntityManager().close();
    }

    @Test
    public void testDummy() throws Exception {
        assertEquals(true, true);
    }

}
