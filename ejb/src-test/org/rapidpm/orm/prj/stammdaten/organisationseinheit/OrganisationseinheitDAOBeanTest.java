/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.organisationseinheit;

import org.rapidpm.orm.BaseDAOBeanTest;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 04.01.12
 * Time: 11:41
 */
public class OrganisationseinheitDAOBeanTest extends BaseDAOBeanTest<OrganisationseinheitDAOBean> {
    public OrganisationseinheitDAOBeanTest() {
        super(OrganisationseinheitDAOBean.class, DaoFactoryBean.class);
    }

    @Test
    public void testConnectAdresse() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectAdresse("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectWebDomain() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectWebDomain("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectKommunikationsServiceUID() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectKommunikationsServiceUID("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectGesellschaftsform() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectGesellschaftsform("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectBrancheAssoc() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectBrancheAssoc("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectAusbildungseinheit() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectAusbildungseinheit("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectTaetigkeitsfeldAssoc() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectTaetigkeitsfeldAssoc("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectVerwaltungseinheit() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectVerwaltungseinheit("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectPerson() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectPerson("sessionID", -1L, 1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectWirtschaftseinheit() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final OrganisationseinheitDAOBean.OrganisationseinheitResult result = daoBean.connectWirtschaftseinheit("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }
}
