package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAOTest {

    private final IssueTypeDAO dao = GraphDaoFactory.getIssueTypeDAO();

    @Test
    public void addType() {
        IssueType type = new IssueType();
        type.setTypeName("first");
        type.setTypeFileName("filename");
        type = dao.persist(type);
        assertEquals(type, dao.findById(type.getId()));
        dao.delete(type);
    }

    @Test
    public void changeType() {
        IssueType type = new IssueType();
        type.setTypeName("1st");
        type.setTypeFileName("second fileName");
        type = dao.persist(type);
        assertEquals(type, dao.findById(type.getId()));
        dao.delete(type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueType priority = dao.loadAllEntities().get(0);
        IssueType prioTest = new IssueType();
        prioTest.setTypeName(priority.getTypeName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssueType type : dao.loadAllEntities()) {
            List<IssueBase> issueList = dao.getConnectedIssuesFromProject(type, 1L);
            List<IssueBase> testList = new ArrayList<>();

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getType().equals(type))
                    testList.add(issue);
            }

            assertEquals(issueList, testList);
            System.out.println("listsize: " + issueList.size());
        }
    }
}
