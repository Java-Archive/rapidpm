package org.rapidpm.persistence.security;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 5:09:55 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.DAOTest;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerGruppeDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BenutzerGruppeDAOTest extends DAOTest {

    @Test
    public void testLoadBenutzerGruppeByName() throws Exception {
        final BenutzerGruppeDAO dao = daoFactory.getBenutzerGruppeDAO();
        final BenutzerGruppe gruppe = dao.loadBenutzerGruppeByName("user");
        assertNotNull(gruppe);
        assertEquals(gruppe.getGruppenname(), "user");
    }
}