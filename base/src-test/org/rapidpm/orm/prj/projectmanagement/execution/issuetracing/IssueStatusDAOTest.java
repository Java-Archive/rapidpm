package org.rapidpm.orm.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:14:14 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueStatusDAOTest extends BaseDAOTest {

    @Test
    public void testStatusOpen() throws Exception {
        final IssueStatus issueStatus = daoFactory.getIssueStatusDAO().loadStatusOpen();
        assertNotNull(issueStatus);
        assertEquals(issueStatus.getStatusName(), "open");
    }
}
