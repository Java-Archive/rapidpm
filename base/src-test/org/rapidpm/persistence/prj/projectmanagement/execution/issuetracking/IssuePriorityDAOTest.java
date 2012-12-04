package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 3:57:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.*;

public class IssuePriorityDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssuePriorityDAOTest.class);

    private final IssuePriorityDAO dao = daoFactory.getIssuePriorityDAO();
    private final IssuePriority assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssuePriority priority = new IssuePriority(1, "test");
        priority.setPriorityFileName("test_filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findByID(priority.getId()));

        priority.setPrio(2);
        priority.setPriorityName("second_test");
        priority.setPriorityFileName("second_test_filename");
        priority = dao.persist(priority);
        assertEquals(priority, dao.findByID(priority.getId()));

        dao.delete(priority, assignTo);
        assertFalse(dao.loadAllEntities().contains(priority));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssuePriority priority = dao.loadAllEntities().get(0);
        final IssuePriority prioTest = new IssuePriority();
        prioTest.setPriorityName(priority.getPriorityName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssuePriority priority : dao.loadAllEntities()) {
            final List<IssueBase> issueList = priority.getConnectedIssuesFromProject(1L);

            for (final IssueBase issue : daoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getPriority().equals(priority))
                    assertTrue(issueList.contains(issue));
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void firstParameterException() {
        dao.getConnectedIssuesFromProject(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondParameterException() {
        dao.getConnectedIssuesFromProject(new IssuePriority(), -1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void itemWithoutId() {
        dao.getConnectedIssuesFromProject(new IssuePriority(), 1L);
    }
}
