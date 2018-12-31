package org.rapidpm.persistence.rohdaten;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 21, 2010
 * Time: 5:12:19 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.DAOTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OntologieDAOTest extends DAOTest {

    @Test
    public void testLoadOntologie() throws Exception {
        final OntologieDAO dao = daoFactory.getOntologieDAO();
        final Ontologie ontologie = dao.loadOntologie("KIO Oberberg", "TestGesuch");
        assertNotNull(ontologie);
        assertEquals(ontologie.getSymbolischerName(), "TestGesuch");
        assertEquals(ontologie.getMandantengruppe().getMandantengruppe(), "KIO Oberberg");

    }
}
