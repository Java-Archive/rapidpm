package org.rapidpm.persistence.security;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 4:36:01 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.BaseDAOTest;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BenutzerDAOTest extends BaseDAOTest {

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
        final Benutzer benutzer = dao.loadBenutzer("sven.ruppert", "NeoScioPortal");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "sven.ruppert");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "NeoScioPortal");

    }

    @Test
    public void testLoadBenutzer002a() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("sven.ruppert", "neodiener", "NeoScioPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "sven.ruppert");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "NeoScioPortal");
    }

    @Test
    public void testLoadBenutzer002b() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("", "neodiener", "NeoScioPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_NeoScioPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "NeoScioPortal");
    }

    @Test
    public void testLoadBenutzerByMandantenGruppe001() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", true);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", false);
        assertNotNull(visibleUser);
        assertFalse(visibleUser.isEmpty());
        for (final Benutzer benutzer : visibleUser) {
            assertFalse(benutzer.getHidden());
        }


    }

    @Test
    public void testLoadBenutzerByMandantenGruppe002a() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", true, true);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
            assertTrue(benutzer.getActive());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", false, true);
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
        final List<Benutzer> hiddenUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", true, false);
        assertNotNull(hiddenUser);
        assertFalse(hiddenUser.isEmpty());
        for (final Benutzer benutzer : hiddenUser) {
            assertTrue(benutzer.getHidden());
            assertFalse(benutzer.getActive());
        }

        final List<Benutzer> visibleUser = dao.loadBenutzerByMandantenGruppe("NeoScioPortal", false, false);
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
        final List<Benutzer> benutzerList = dao.aktiveBenutzerByMandantenGruppe("NeoScioPortal", true);
        assertNotNull(benutzerList);
        assertFalse(benutzerList.isEmpty());
        for (final Benutzer benutzer : benutzerList) {
            assertTrue(benutzer.getActive());
        }
    }

    @Test
    public void testAktiveBenutzerByMandantenGruppe002() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final List<Benutzer> benutzerList = dao.aktiveBenutzerByMandantenGruppe("NeoScioPortal", false);
        assertNotNull(benutzerList);
        assertFalse(benutzerList.isEmpty());
        for (final Benutzer benutzer : benutzerList) {
            assertFalse(benutzer.getActive());
        }
    }

    @Test
    public void testLoadBenutzerInclSystemBenutzer() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadBenutzer("Anonymous_NeoScioPortal", "anonymous", "NeoScioPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_NeoScioPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "NeoScioPortal");
    }

    @Test
    public void testLoadAnonymousBenutzer() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        final Benutzer benutzer = dao.loadAnonymousBenutzer("NeoScioPortal_App");
        assertNotNull(benutzer);
        assertEquals(benutzer.getLogin(), "Anonymous_NeoScioPortal");
        final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
        assertNotNull(mandantengruppe);
        assertEquals(mandantengruppe.getMandantengruppe(), "NeoScioPortal");
    }

    @Test
    public void testCheckIfBenutzerLoginIsAvailable() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();
        assertTrue(dao.checkIfBenutzerLoginIsAvailable("adadgadgagagfaewt", "NeoScioPortal"));
        assertFalse(dao.checkIfBenutzerLoginIsAvailable("sven.ruppert", "NeoScioPortal"));
    }

    @Test
    public void testLoadBenutzerByEmail() throws Exception {
        final BenutzerDAO dao = daoFactory.getBenutzerDAO();

//        assertEquals(1, dao.loadBenutzerByEmail("sven.ruppert@neoscio.de").size());
//        assertTrue(dao.loadBenutzerByEmail("max.mustermann@test.de").isEmpty());

        assertNotNull(dao.loadBenutzerByEmail("sven.ruppert@neoscio.de", 16L));
        assertNull(dao.loadBenutzerByEmail("sven.ruppert@neoscio.de", 1L));

        assertNotNull(dao.loadBenutzerByEmail("sven.ruppert@neoscio.de", "NeoScioPortal"));
        assertNull(dao.loadBenutzerByEmail("sven.ruppert@neoscio.de", "NeoScioTest"));
    }
}
