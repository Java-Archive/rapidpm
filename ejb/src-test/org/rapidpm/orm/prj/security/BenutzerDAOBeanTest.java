/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.security;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;
import org.rapidpm.orm.DaoFactoryBean;
import org.rapidpm.orm.DaoFactoryBeanTest;
import org.rapidpm.orm.security.BenutzerDAOBean;
import org.rapidpm.orm.system.security.Benutzer;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 15.12.11
 * Time: 14:00
 */
public class BenutzerDAOBeanTest extends DaoFactoryBeanTest<BenutzerDAOBean> {


    public BenutzerDAOBeanTest() {
        super(BenutzerDAOBean.class, DaoFactoryBean.class);
    }

    private Benutzer getTestBenutzer() {
        return daoFactory.getBenutzerDAO().findByID(1L);
    }

    @Test
    public void testChangePasswd() throws Exception {
        BasicConfigurator.configure();

        final Benutzer benutzer = getTestBenutzer();
        daoFactory.getBenutzerDAO().new Transaction() {
            @Override
            public void doTask() {
                final BenutzerDAOBean.BenutzerResult result = daoBean.changePasswd("sessionID", -1L, benutzer.getId(), "test");
                DaoFactoryBeanTest.printLog(result);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testChangeLogin() throws Exception {
        BasicConfigurator.configure();

        final Benutzer benutzer = getTestBenutzer();
        daoFactory.getBenutzerDAO().new Transaction() {
            @Override
            public void doTask() {
                final BenutzerDAOBean.BenutzerResult result = daoBean.changeLogin("sessionID", -1L, benutzer.getId(), "test.login");
                DaoFactoryBeanTest.printLog(result);
                assertTrue(result.getValid());
            }
        }.execute();
    }
}
