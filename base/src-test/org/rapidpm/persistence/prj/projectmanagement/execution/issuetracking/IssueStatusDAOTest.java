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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class IssueStatusDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueStatusDAOTest.class);

    private final IssueStatusDAO dao = daoFactory.getIssueStatusDAO();
    private final IssueStatus assignTo = dao.loadAllEntities(PROJECTID).get(0);

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueStatus> statusList = dao.loadAllEntities(PROJECTID);
        assertTrue(statusList.get(0).equals(statusList.get(0)));
        assertEquals(statusList.get(0).hashCode(), statusList.get(0).hashCode());

        assertFalse(statusList.get(0).equals(new IssueStatus()));
        assertNotSame(new IssueStatus(PROJECTID).hashCode(), statusList.get(0).hashCode());
    }


    @Test
    public void addChangeDelete() {
        IssueStatus status = new IssueStatus("test");
        status.setProjectId(PROJECTID);
        status.setStatusFileName("test_filename");
        status = dao.persist(status);
        assertEquals(status, dao.findByID(status.getId()));

        status.setStatusName("second_test");
        status.setStatusFileName("second_test_filename");
        status = dao.persist(status);
        assertEquals(status, dao.findByID(status.getId()));

        dao.delete(status, assignTo);
        assertFalse(dao.loadAllEntities(PROJECTID).contains(status));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueStatus status = dao.loadAllEntities(PROJECTID).get(0);
        final IssueStatus statTest = new IssueStatus();
        statTest.setProjectId(PROJECTID);
        statTest.setStatusName(status.getStatusName());
        statTest.setStatusFileName(status.getStatusFileName());
        dao.persist(statTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueStatus status : dao.loadAllEntities(PROJECTID)) {
            final List<IssueBase> statConnIssueList = status.getConnectedIssues();
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(PROJECTID)) {
                if (issue.getStatus().equals(status))
                    issueList.add(issue);
            }

            assertEquals(statConnIssueList.size(), issueList.size());
            for (final IssueBase match : statConnIssueList) {
                assertTrue(issueList.contains(match));
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void delete_FirstParameterNull() {
        dao.delete(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_FirstParameterNoId() {
        dao.delete(new IssueStatus(), null);
    }

    @Test(expected = NullPointerException.class)
    public void delete_SecondParameterNull() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SecondParameterNoId() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), new IssueStatus());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssues(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssues(new IssueStatus());
    }
}
