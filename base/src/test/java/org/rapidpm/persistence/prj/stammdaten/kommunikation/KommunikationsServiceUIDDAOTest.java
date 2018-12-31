/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 10.11.11
 * Time: 15:45
 */
public class KommunikationsServiceUIDDAOTest extends BaseDAOTest {
    @Test
    public void testLoadServiceUIDsForOrganisationseinheit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactory.getKommunikationServiceUIDDAO();
        final List<KommunikationsServiceUID> kommunikationsServiceUIDs = dao.loadServiceUIDsForOrganisationseinheit(1L);
        assertFalse(kommunikationsServiceUIDs.isEmpty());
    }

    @Test
    public void testIsEmailAlreadyRegisteredSystemweit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactory.getKommunikationServiceUIDDAO();
        assertTrue(dao.isEmailRegistered("sven.ruppert@neoscio.de"));
        assertFalse(dao.isEmailRegistered("max.mustermann@test.de"));
    }

    @Test
    public void testIsEmailAlreadyRegisteredMandatenweit() throws Exception {
        final KommunikationsServiceUIDDAO dao = daoFactory.getKommunikationServiceUIDDAO();
        assertTrue(dao.isEmailRegistered("sven.ruppert@neoscio.de", "NeoScioPortal"));
        assertFalse(dao.isEmailRegistered("sven.ruppert@neoscio.de", "Hochschulsuchmaschine"));
        assertFalse(dao.isEmailRegistered("max.mustermann@test.de", 16L));
    }
}
