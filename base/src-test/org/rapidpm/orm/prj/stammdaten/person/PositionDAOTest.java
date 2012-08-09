package org.rapidpm.orm.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 01.04.11
 * Time: 13:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.rapidpm.orm.prj.stammdaten.organisationseinheit.Organisationseinheit;
import org.rapidpm.orm.prj.stammdaten.organisationseinheit.Position;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;

public class PositionDAOTest extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(PositionDAOTest.class);

    @Test
    public void testLoad4OrgEinheit() throws Exception {

        final List<Organisationseinheit> organisationseinheits = daoFactory.getOrganisationseinheitDAO().loadOrganisationseinheitenForMandantengruppe("KIO Oberberg", true);
        for (final Organisationseinheit organisationseinheit : organisationseinheits) {

            final List<Position> positions = daoFactory.getPositionDAO().loadPositionForOrgeinheit(organisationseinheit.getId());
            assertNotNull(positions);
            for (final Position position : positions) {
                assertNotNull(position);
                final String name = position.getName();
                System.out.println("name = " + name);
            }

        }


    }
}
