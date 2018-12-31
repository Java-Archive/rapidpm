package org.rapidpm.persistence.prj;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.01.12
 * Time: 21:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseDAOTest {

    protected  DaoFactory daoFactory = DaoFactorySingelton.getInstance();


    @BeforeEach
    public void setUp() throws Exception {
//        daoFactory.setEntityManager(entityManager);
    }

    @AfterEach
    public void tearDown() throws Exception {
//        daoFactory.getEntityManager().close();
    }

    @Test
    public void testDummy() throws Exception {
        assertEquals(true, true);
    }

}
