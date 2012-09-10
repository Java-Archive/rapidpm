/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.kommunikation;

import org.junit.Test;
import org.rapidpm.orm.prj.BaseDAOTest;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 10.11.11
 * Time: 15:45
 */
public class KommunikationsServiceUIDDAOTest extends BaseDAOTest {
    @Test
    public void testLoadServiceUIDsForOrganisationseinheit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactoryFactory.getKommunikationServiceUIDDAO();
        final List<KommunikationsServiceUID> kommunikationsServiceUIDs = dao.loadServiceUIDsForOrganisationseinheit(1L);
        assertFalse(kommunikationsServiceUIDs.isEmpty());
    }

    @Test
    public void testIsEmailAlreadyRegisteredSystemweit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactoryFactory.getKommunikationServiceUIDDAO();
        assertTrue(dao.isEmailRegistered("sven.ruppert@neoscio.de"));
        assertFalse(dao.isEmailRegistered("max.mustermann@test.de"));
    }

    @Test
    public void testIsEmailAlreadyRegisteredMandatenweit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactoryFactory.getKommunikationServiceUIDDAO();
        assertTrue(dao.isEmailRegistered("sven.ruppert@neoscio.de", "NeoScioPortal"));
        assertFalse(dao.isEmailRegistered("sven.ruppert@neoscio.de", "Hochschulsuchmaschine"));
        assertFalse(dao.isEmailRegistered("max.mustermann@test.de", 16L));
    }
}
