package org.rapidpm.orm.prj.stammdaten.address;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 3:29:36 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
