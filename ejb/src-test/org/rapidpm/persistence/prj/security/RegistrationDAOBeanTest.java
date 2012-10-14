/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.security;

import org.junit.Test;
import org.rapidpm.persistence.prj.DAOBeanTest;
import org.rapidpm.persistence.security.RegistrationDAOBean;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 15.12.11
 * Time: 12:02
 */
public class RegistrationDAOBeanTest extends DAOBeanTest<RegistrationDAOBean> {
    public RegistrationDAOBeanTest() {
        super(RegistrationDAOBean.class);
    }

    @Test
    public void testSaveOrUpdateTX() throws Exception {
        final RegistrationDAOBean.FlatRegistration registration = new RegistrationDAOBean.FlatRegistration();
        registration.setLogin("max.mustermann");
        registration.setVorname("Max");
        registration.setNachname("Mustermann");
        registration.setUnternehmen("RapidPM - www.rapidpm.org");
        registration.setPosition("Chef");
        registration.setEmail("max.mustermann@neoscio.de");
        registration.setPlz("58097");
        registration.setOrt("Iserlohn");
        registration.setLaendercode("DE");
        registration.setVorwahl("02371");
        registration.setNummer("123");
        registration.setDurchwahl("45");
        registration.setTitel("");
        registration.setStrasse("Galmeistraße");
        registration.setHausnr("23");
        registration.setRegistrationStatusOID(daoFactory.getRegistrationStatusDAO().loadRegistrationStatus_Zur_Pruefung().getId());
        registration.setTaetigkeitsfeldOID(daoFactory.getTaetigkeitsfeldDAO().loadTaetigkeitsfeldNichtEingeordnet().getId());
        registration.setAnredeOID(daoFactory.getAnredeDAO().loadAnredeHerr().getId());
        registration.setMandantengruppeOID(daoFactory.getMandantengruppeDAO().loadMandantengruppe("NeoScioPortal").getId());
        registration.setBenutzerWebapplikationOID(daoFactory.getBenutzerWebapplikationDAO().loadBenutzerWebapplikation("NeoScioPortal_App").getId());

        daoFactory.getRegistrationDAO().new Transaction() {
            @Override
            public void doTask() {
                final RegistrationDAOBean.RegistrationResult result = daoBean.saveOrUpdateTX("sessionID", -1L, registration);
                assertTrue(result.getValid());
                printLog(result);
            }
        }.execute();
    }
}
