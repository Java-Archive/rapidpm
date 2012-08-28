package org.rapidpm.persistence.prj.stammdaten.kommunikation;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 20, 2010
 * Time: 9:45:09 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.persistence.prj.BaseDAOTest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class KommunikationsServiceDAOTest extends BaseDAOTest {
    KommunikationsServiceDAO kommunikationsServiceDAO;

    @Test
    public void testLoadEmail() throws Exception {
        fail("Test is not implemented");
    }

    @Test
    public void testLoadServicesForOrganisationseinheit() throws Exception {
        final KommunikationsServiceDAO dao = daoFactory.getKommunikationsServiceDAO();
        final List<KommunikationsService> kommunikationsServices = dao.loadServicesForOrganisationseinheit(1l);
        assertFalse(kommunikationsServices.isEmpty());
    }
}
