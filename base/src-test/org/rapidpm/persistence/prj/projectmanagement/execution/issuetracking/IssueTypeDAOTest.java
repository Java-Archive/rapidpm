package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 12.10.12
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAOTest {
    private static Logger logger = Logger.getLogger(IssueTypeDAOTest.class);

    private final IssueTypeDAO dao = GraphDaoFactory.getIssueTypeDAO();
    private final IssueType assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssueType type = new IssueType("test");
        type.setTypeFileName("test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findById(type.getId()));

        type.setTypeName("second_test");
        type.setTypeFileName("second_test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findById(type.getId()));

        dao.delete(type, assignTo);
        assertFalse(dao.loadAllEntities().contains(type));
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
            List<IssueBase> issueList = type.getConnectedIssuesFromProject(1L);
            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getType().equals(type))
                    assertTrue(issueList.contains(issue));
            }
        }
    }
}
