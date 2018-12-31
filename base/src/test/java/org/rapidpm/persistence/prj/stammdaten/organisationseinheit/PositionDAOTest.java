package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 24.03.11
 * Time: 10:01
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PositionDAOTest extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(PositionDAOTest.class);

    @Test
    public void testLoadPositionen4OrgEinheit() throws Exception {
        final List<Position> positionList = daoFactory.getPositionDAO().loadPositionForOrgeinheit(34L);
        assertNotNull(positionList);
        assertFalse(positionList.isEmpty());
        for (final Position position : positionList) {
            System.out.println("position = " + position);
        }


    }
}
