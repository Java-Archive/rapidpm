package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 24, 2010
 * Time: 12:22:50 AM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Ignore;
import org.junit.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class IssueTimeUnitDAOTest extends BaseDAOTest {

    @Test
    public void testLoadTimeUnitsFor() throws Exception {
        final IssueTimeUnitDAO issueTimeUnitDAO = daoFactory.getIssueTimeUnitDAO();
        List<IssueTimeUnit> issueTimeUnits = issueTimeUnitDAO.loadTimeUnitsFor("NeoScioPortal", "sven.ruppert", null, null, null, null);
        assertNotNull(issueTimeUnits);
        assertEquals(2, issueTimeUnits.size());

        issueTimeUnits = issueTimeUnitDAO.loadTimeUnitsFor("NeoScioPortal", "sven.ruppert", "2010-11-23", null, null, null);
        assertNotNull(issueTimeUnits);
        assertEquals(2, issueTimeUnits.size());

        issueTimeUnits = issueTimeUnitDAO.loadTimeUnitsFor("NeoScioPortal", "sven.ruppert", "2010-11-24", null, null, null);
        assertNotNull(issueTimeUnits);
        assertEquals(1, issueTimeUnits.size());

        issueTimeUnits = issueTimeUnitDAO.loadTimeUnitsFor(null, null, "2010-11-24", null, null, null);
        assertNotNull(issueTimeUnits);
        assertEquals(1, issueTimeUnits.size());

        issueTimeUnits = issueTimeUnitDAO.loadTimeUnitsFor(null, null, null, null, null, null);
        assertNotNull(issueTimeUnits);
        assertEquals(2, issueTimeUnits.size());


    }
}
