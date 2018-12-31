package org.rapidpm.persistence.prj.bewegungsdaten;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 21, 2010
 * Time: 9:10:13 AM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationDAOTest extends BaseDAOTest {


    @BeforeEach
    public void prepareData(){
        //TODO Daten erzeugen f die Tests
//        final EntityTransaction transaction = entityManager.getTransaction();

        final Registration r = new Registration();
//        r.set
//        entityManager.persist();

//        transaction.commit();
    }



    @Test
    public void testCheckIfLoginIsAvailable() throws Exception {
        final RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        final boolean b = registrationDAO.checkIfLoginIsAvailable("sven.ruppert", "NeoScioPortal");
        assertFalse(b);
    }

    @Test
    public void testLoadAllForWebappAndAproval() throws Exception {
        final RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        final List<Registration> registrations = registrationDAO.loadAllRegistrationForWebAppAndAproval("KIO Oberberg_APP");
        assertNotNull(registrations);
        assertFalse(registrations.isEmpty());
        for (final Registration registration : registrations) {
            final RegistrationStatus registrationStatus = registration.getRegistrationStatus();
            assertNotNull(registrationStatus);
            final String status = registrationStatus.getStatus();
            assertEquals("Zur_Prüfung", status);
        }

    }

    @Test
    public void testLoadAllNewRegistration() throws Exception {
        final RegistrationDAO registrationDAO = daoFactory.getRegistrationDAO();
        final List<Registration> registrations = registrationDAO.loadAllRegistrationForAproval();
        assertNotNull(registrations);
        assertFalse(registrations.isEmpty());
        for (final Registration registration : registrations) {
            final RegistrationStatus registrationStatus = registration.getRegistrationStatus();
            assertNotNull(registrationStatus);
            final String status = registrationStatus.getStatus();
            assertEquals("Zur_Prüfung", status);
        }
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //To change body of created methods use File | Settings | File Templates.
    }
}
