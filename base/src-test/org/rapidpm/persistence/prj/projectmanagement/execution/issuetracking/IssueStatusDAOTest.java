package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:14:14 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.*;

public class IssueStatusDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueStatusDAOTest.class);

    private final IssueStatusDAO dao = daoFactory.getIssueStatusDAO();
    private final IssueStatus assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssueStatus status = new IssueStatus("test");
        status.setStatusFileName("test_filename");
        status = dao.persist(status);
        assertEquals(status, dao.findByID(status.getId()));

        status.setStatusName("second_test");
        status.setStatusFileName("second_test_filename");
        status = dao.persist(status);
        assertEquals(status, dao.findByID(status.getId()));

        dao.delete(status, assignTo);
        assertFalse(dao.loadAllEntities().contains(status));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueStatus priority = dao.loadAllEntities().get(0);
        final IssueStatus prioTest = new IssueStatus();
        prioTest.setStatusName(priority.getStatusName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueStatus status : dao.loadAllEntities()) {
            final List<IssueBase> issueList = status.getConnectedIssuesFromProject(1L);
            for (final IssueBase issue : daoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getStatus().equals(status))
                    assertTrue(issueList.contains(issue));
            }
        }
    }
}
