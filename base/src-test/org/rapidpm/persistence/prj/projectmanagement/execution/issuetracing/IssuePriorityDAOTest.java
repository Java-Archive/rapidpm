package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Dec 16, 2010
 * Time: 3:57:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssuePriorityDAOTest {

    private final IssuePriorityDAO dao = GraphDaoFactory.getInstance().getIssuePriorityDAO();

    @Test
    public void addPriority() throws Exception {
        IssuePriority priority = new IssuePriority();
        priority.setPrio(1);
        priority.setPriorityName("first");
        priority.setPriorityFileName("filename");
        priority = dao.persist(priority);
        System.out.println(priority.toString());
        assertEquals(priority, dao.getById(priority.getId()));
        dao.delete(priority);
    }

    @Test
    public void changeComponent() {
        IssuePriority priority = new IssuePriority();
        priority.setPriorityName("first");
        priority = dao.persist(priority);
        System.out.println(priority.toString());
        assertEquals(priority, dao.getById(priority.getId()));

        priority.setPriorityName("second");
        priority = dao.persist(priority);
        assertEquals(priority, dao.getById(priority.getId()));
        dao.delete(priority);
    }
}
