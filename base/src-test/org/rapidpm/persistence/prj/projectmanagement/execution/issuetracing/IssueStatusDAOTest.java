package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 4:14:14 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueStatusDAOTest {

    private final IssueStatusDAO dao = GraphDaoFactory.getIssueStatusDAO();

    @Test
    public void addStatus() {

        IssueStatus status = new IssueStatus();
        status.setStatusName("first");
        status.setStatusFileName("filename");
        status = dao.persist(status);
        assertEquals(status, dao.findById(status.getId()));
        dao.delete(status);
    }

    @Test
    public void changeStatus() {
        IssueStatus status = new IssueStatus();
        status.setStatusName("1st");
        status.setStatusFileName("second filename");
        status = dao.persist(status);
        assertEquals(status, dao.findById(status.getId()));
        dao.delete(status);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueStatus priority = dao.loadAllEntities().get(0);
        IssueStatus prioTest = new IssueStatus();
        prioTest.setStatusName(priority.getStatusName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssueStatus status : dao.loadAllEntities()) {
            List<IssueBase> issueList = dao.getConnectedIssuesFromProject(status, 1L);
            List<IssueBase> testList = new ArrayList<>();

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getStatus().equals(status))
                    testList.add(issue);
            }

            assertEquals(issueList, testList);
            System.out.println("listsize: " + issueList.size());
        }
    }
}
