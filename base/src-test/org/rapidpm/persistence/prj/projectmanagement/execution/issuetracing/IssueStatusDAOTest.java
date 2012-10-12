package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:14:14 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueStatusDAOTest extends BaseDAOTest {

    @Test
    public void testStatusOpen() throws Exception {
        final IssueStatus issueStatus = daoFactoryFactory.getIssueStatusDAO().loadStatusOpen();
        assertNotNull(issueStatus);
        assertEquals(issueStatus.getStatusName(), "open");
    }
}
