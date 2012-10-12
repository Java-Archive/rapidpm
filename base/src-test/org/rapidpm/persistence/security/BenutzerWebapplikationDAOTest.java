package org.rapidpm.persistence.security;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 5:11:22 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.DAOTest;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.BenutzerWebapplikationDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BenutzerWebapplikationDAOTest extends DAOTest {

    @Test
    public void testLoadBenutzerWebapplikation() throws Exception {
        final BenutzerWebapplikationDAO dao = daoFactory.getBenutzerWebapplikationDAO();
        final BenutzerWebapplikation webapplikation = dao.loadBenutzerWebapplikation("KIO Oberberg_App");
        assertNotNull(webapplikation);
        assertEquals(webapplikation.getWebappName(), "KIO Oberberg_App");
    }
}