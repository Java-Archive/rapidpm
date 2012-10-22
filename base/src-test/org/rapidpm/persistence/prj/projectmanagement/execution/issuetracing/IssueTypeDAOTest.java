package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;

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

    private final IssueTypeDAO dao = GraphDaoFactory.getInstance().getIssueTypeDAO();

    @Test
    public void addType() throws Exception {
        IssueType type = new IssueType();
        type.setTypeName("first");
        type.setTypeFileName("filename");
        type = dao.persist(type);
        System.out.println(type.toString());
        assertEquals(type, dao.getById(type.getId()));
        dao.delete(type);
    }

    @Test
    public void changeComponent() {
        IssueType type = new IssueType();
        type.setTypeName("first");
        type = dao.persist(type);
        System.out.println(type.toString());
        assertEquals(type, dao.getById(type.getId()));

        type.setTypeName("second");
        type = dao.persist(type);
        assertEquals(type, dao.getById(type.getId()));
        dao.delete(type);
    }
}
