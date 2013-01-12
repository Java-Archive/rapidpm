package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.11.12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class IssueStoryPointDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueStoryPointDAOTest.class);

    private final IssueStoryPointDAO dao = daoFactory.getIssueStoryPointDAO();
    private final IssueStoryPoint assignTo = dao.loadAllEntities(1L).get(0);

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueStoryPoint> storyPointList = dao.loadAllEntities(1L);
        assertTrue(storyPointList.get(0).equals(storyPointList.get(0)));
        assertEquals(storyPointList.get(0).hashCode(), storyPointList.get(0).hashCode());

        assertFalse(storyPointList.get(0).equals(new IssueComment()));
        assertNotSame(new IssueComment().hashCode(), storyPointList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssueStoryPoint storyPoint = new IssueStoryPoint(1000);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findByID(storyPoint.getId()));

        storyPoint.setStorypoint(1001);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findByID(storyPoint.getId()));

        dao.delete(storyPoint, assignTo);
        assertFalse(dao.loadAllEntities(1L).contains(storyPoint));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueStoryPoint storyPoint = dao.loadAllEntities(1L).get(0);
        final IssueStoryPoint stpTest = new IssueStoryPoint();
        stpTest.setStorypoint(storyPoint.getStorypoint());
        dao.persist(stpTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueStoryPoint storyPoint : dao.loadAllEntities(1L)) {
            final List<IssueBase> stpConnIssueList = storyPoint.getConnectedIssuesFromProject(1L);
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(1L)) {
                if (issue.getStoryPoints().equals(storyPoint))
                    issueList.add(issue);
            }

            assertEquals(stpConnIssueList.size(), issueList.size());
            for (final IssueBase match : stpConnIssueList) {
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
        dao.delete(new IssueStoryPoint(), null);
    }

    @Test(expected = NullPointerException.class)
    public void delete_SecondParameterNull() {
        dao.delete(dao.loadAllEntities(1L).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_SecondParameterNoId() {
        dao.delete(dao.loadAllEntities(1L).get(0), new IssueStoryPoint());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssuesFromProject(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssuesFromProject(new IssueStoryPoint(), 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_SecondParameterNull() {
        dao.getConnectedIssuesFromProject(dao.loadAllEntities(1L).get(0), -1L);
    }
}
