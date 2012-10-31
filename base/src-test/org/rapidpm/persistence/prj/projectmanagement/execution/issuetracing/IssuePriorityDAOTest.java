package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 3:57:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.rapidpm.persistence.GraphDBFactory;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssuePriorityDAOTest {
    private static Logger logger = Logger.getLogger(IssuePriorityDAOTest.class);
    private final IssuePriorityDAO dao = GraphDaoFactory.getIssuePriorityDAO();

    @Test
    public void addPriority() {
        IssuePriority priority = new IssuePriority();
        priority.setPrio(1);
        priority.setPriorityName("first");
        priority.setPriorityFileName("filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findById(priority.getId()));
        dao.delete(priority);
    }

    @Test
    public void changePriority() {
        IssuePriority priority = new IssuePriority();
        priority.setPrio(2);
        priority.setPriorityName("1st");
        priority.setPriorityFileName("second filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findById(priority.getId()));
        dao.delete(priority);
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssuePriority priority = dao.loadAllEntities().get(0);
        IssuePriority prioTest = new IssuePriority();
        prioTest.setPriorityName(priority.getPriorityName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssuePriority priority : dao.loadAllEntities()) {
            List<IssueBase> issueList = dao.getConnectedIssuesFromProject(priority, 1L);
            List<IssueBase> testList = new ArrayList<>();

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getPriority().equals(priority))
                    testList.add(issue);
            }

            assertEquals(issueList, testList);
            if (logger.isDebugEnabled())
                logger.debug("listsize: " + issueList.size());
        }
    }
}
