package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStoryPoint;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStoryPointDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueVersion;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueVersionDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.11.12
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class IssueStoryPointDAOTest {
    private static Logger logger = Logger.getLogger(IssueStoryPointDAOTest.class);

    private final IssueStoryPointDAO dao = GraphDaoFactory.getIssueStoryPointDAO();
    private final IssueStoryPoint assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssueStoryPoint storyPoint = new IssueStoryPoint();
        storyPoint.setStorypoint(1000);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findById(storyPoint.getId()));

        storyPoint.setStorypoint(1001);
        storyPoint = dao.persist(storyPoint);
        assertEquals(storyPoint, dao.findById(storyPoint.getId()));

        dao.delete(storyPoint, assignTo);
        assertFalse(dao.loadAllEntities().contains(storyPoint));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueStoryPoint storyPoint = dao.loadAllEntities().get(0);
        IssueStoryPoint stpTest = new IssueStoryPoint();
        stpTest.setStorypoint(storyPoint.getStorypoint());
        dao.persist(stpTest);
    }
}
