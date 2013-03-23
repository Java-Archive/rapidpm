package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseIssueTrackingDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 12.10.12
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAOTest extends BaseIssueTrackingDAOTest {
    private static Logger logger = Logger.getLogger(IssueTypeDAOTest.class);

    private final IssueTypeDAO dao = daoFactory.getIssueTypeDAO();
    private final IssueType assignTo = dao.loadAllEntities(1L).get(0);

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueType> typeList = dao.loadAllEntities(1L);
        assertTrue(typeList.get(0).equals(typeList.get(0)));
        assertEquals(typeList.get(0).hashCode(), typeList.get(0).hashCode());

        assertFalse(typeList.get(0).equals(new IssueType()));
        assertNotSame(new IssueType(PROJECTID).hashCode(), typeList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssueType type = new IssueType("test");
        type.setProjectId(PROJECTID);
        type.setTypeFileName("test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findByID(type.getId()));

        type.setTypeName("second_test");
        type.setTypeFileName("second_test_filename");
        type = dao.persist(type);
        assertEquals(type, dao.findByID(type.getId()));

        dao.delete(type, assignTo);
        assertFalse(dao.loadAllEntities(PROJECTID).contains(type));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueType priority = dao.loadAllEntities(PROJECTID).get(0);
        final IssueType prioTest = new IssueType();
        prioTest.setProjectId(PROJECTID);
        prioTest.setTypeName(priority.getTypeName());
        prioTest.setTypeFileName(priority.getTypeFileName());
        dao.persist(prioTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueType type : dao.loadAllEntities(PROJECTID)) {
            final List<IssueBase> typeConnIssueList = type.getConnectedIssues();
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(PROJECTID)) {
                if (issue.getType().equals(type))
                    issueList.add(issue);
            }

            assertEquals(typeConnIssueList.size(), issueList.size());
            for (final IssueBase match : typeConnIssueList) {
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
        dao.delete(new IssueType(), null);
    }

    @Test(expected = NullPointerException.class)
    public void delete_SecondParameterNull() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SecondParameterNoId() {
        dao.delete(dao.loadAllEntities(PROJECTID).get(0), new IssueType());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssues(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssues(new IssueType());
    }
}
