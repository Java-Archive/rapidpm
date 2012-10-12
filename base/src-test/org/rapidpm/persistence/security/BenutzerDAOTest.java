package org.rapidpm.persistence.security;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 4:36:01 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.DAOTest;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BenutzerDAOTest extends DAOTest {

    @Test
    public void testFindbyID() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer byID = dao.findByID(-1L);
        assertNotNull(byID);
        final Benutzer nullObj = dao.findByID(-2L);
        assertNull(nullObj);

    }

    @Test
    public void testLoadBenutzer001() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("sven.ruppert", "RapidPMPortal");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "sven.ruppert");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "RapidPMPortal");

    }

    @Test
    public void testLoadBenutzer002a() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("sven.ruppert", "neodiener", "RapidPMPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "sven.ruppert");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "RapidPMPortal");
    }

    @Test
    public void testLoadBenutzer002b() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("", "neodiener", "RapidPMPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_RapidPMPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "RapidPMPortal");
    }

    @Test
    public void testLoadBenutzerByMandantenGruppe001() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", true);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", false);
        assertNotNull(visibleUser);
        assertFalse(visibleUser.isEmpty());
        for (final Benutzer benutzer : visibleUser) {
            assertFalse(benutzer.getHidden());
        }


    }

    @Test
    public void testLoadBenutzerByMandantenGruppe002a() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", true, true);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
            assertTrue(benutzer.getActive());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", false, true);
        assertNotNull(visibleUser);
        assertFalse(visibleUser.isEmpty());
        for (final Benutzer benutzer : visibleUser) {
            assertFalse(benutzer.getHidden());
            assertTrue(benutzer.getActive());
        }
    }

    @Test
    public void testLoadBenutzerByMandantenGruppe002b() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", true, false);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
            assertFalse(benutzer.getActive());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("RapidPMPortal", false, false);
        assertNotNull(visibleUser);
        assertFalse(visibleUser.isEmpty());
        for (final Benutzer benutzer : visibleUser) {
            assertFalse(benutzer.getHidden());
            assertFalse(benutzer.getActive());
        }


    }

    @Test
    public void testAktiveBenutzerByMandantenGruppe001() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzerList = dao.aktiveBenutzerByMandantenGruppe("RapidPMPortal", true);
        assertNotNull(benutzerList);
        assertFalse(benutzerList.isEmpty());
        for (final Benutzer benutzer : benutzerList) {
            assertTrue(benutzer.getActive());
        }
    }

    @Test
    public void testAktiveBenutzerByMandantenGruppe002() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzerList = dao.aktiveBenutzerByMandantenGruppe("RapidPMPortal", false);
        assertNotNull(benutzerList);
        assertFalse(benutzerList.isEmpty());
        for (final Benutzer benutzer : benutzerList) {
            assertFalse(benutzer.getActive());
        }
    }

    @Test
    public void testLoadBenutzerInclSystemBenutzer() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("Anonymous_RapidPMPortal", "anonymous", "RapidPMPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_RapidPMPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "RapidPMPortal");
    }

    @Test
    public void testLoadAnonymousBenutzer() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadAnonymousBenutzer("RapidPMPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_RapidPMPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "RapidPMPortal");
    }

    @Test
    public void testCheckIfBenutzerLoginIsAvailable() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        assertTrue(dao.checkIfBenutzerLoginIsAvailable("adadgadgagagfaewt", "RapidPMPortal"));
        assertFalse(dao.checkIfBenutzerLoginIsAvailable("sven.ruppert", "RapidPMPortal"));
    }

    @Test
    public void testLoadBenutzerByEmail() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();

//        assertEquals(1, dao.loadBenutzerByEmail("sven.ruppert@rapidpm.org").size());
//        assertTrue(dao.loadBenutzerByEmail("max.mustermann@test.de").isEmpty());

        assertNotNull(dao.loadBenutzerByEmail("sven.ruppert@rapidpm.org", 16L));
        assertNull(dao.loadBenutzerByEmail("sven.ruppert@rapidpm.org", 1L));

        assertNotNull(dao.loadBenutzerByEmail("sven.ruppert@rapidpm.org", "RapidPMPortal"));
        assertNull(dao.loadBenutzerByEmail("sven.ruppert@rapidpm.org", "RapidPMTest"));
    }
}
