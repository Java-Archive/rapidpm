package org.rapidpm.orm.rohdaten;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 21, 2010
 * Time: 5:12:19 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.BaseDAOTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OntologieDAOTest extends BaseDAOTest {

    @Test
    public void testLoadOntologie() throws Exception {
        final OntologieDAO dao = daoFactory.getOntologieDAO();
        final Ontologie ontologie = dao.loadOntologie("KIO Oberberg", "TestGesuch");
        assertNotNull(ontologie);
        assertEquals(ontologie.getSymbolischerName(), "TestGesuch");
        assertEquals(ontologie.getMandantengruppe().getMandantengruppe(), "KIO Oberberg");

    }
}
