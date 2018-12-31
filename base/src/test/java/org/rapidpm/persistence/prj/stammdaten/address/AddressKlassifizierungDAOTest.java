package org.rapidpm.persistence.prj.stammdaten.address;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 3:29:36 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressKlassifizierungDAOTest extends BaseDAOTest {

    @Test
    public void testLoadAddressAdressKlassifizierung() throws Exception {
        final AddressKlassifizierungDAO dao = daoFactory.getAddressKlassifizierungDAO();
        final AdressKlassifizierung klassifizierung = dao.loadAdressKlassifizierung("privat");
        assertNotNull(klassifizierung);
        assertEquals("privat", klassifizierung.getKlassifizierung());
    }

    @Test
    public void testLoadKlassifizierungPrivat() throws Exception {
        final AddressKlassifizierungDAO dao = daoFactory.getAddressKlassifizierungDAO();
        final AdressKlassifizierung klassifizierung = dao.loadKlassifizierungPrivat();
        assertNotNull(klassifizierung);
        assertEquals("privat", klassifizierung.getKlassifizierung());
    }

    @Test
    public void testLoadKlassifizierungBeruflich() throws Exception {
        final AddressKlassifizierungDAO dao = daoFactory.getAddressKlassifizierungDAO();
        final AdressKlassifizierung klassifizierung = dao.loadKlassifizierungBeruflich();
        assertNotNull(klassifizierung);
        assertEquals("beruflich", klassifizierung.getKlassifizierung());
    }
}
