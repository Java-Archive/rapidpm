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
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;

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
        assertEquals(status, dao.getById(status.getId()));
        dao.delete(status);
    }

    @Test
    public void changeStatus() {
        IssueStatus status = new IssueStatus();
        status.setStatusName("1st");
        status.setStatusFileName("second filename");
        status = dao.persist(status);
        assertEquals(status, dao.getById(status.getId()));
        dao.delete(status);
    }
}
