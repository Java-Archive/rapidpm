package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTestCase;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 13.11.12
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = daoFactory.getIssueBaseDAO(projectId);

    @Test
    public void addIssue() {
        IssueBase issueBase = new IssueBase(projectId);

        issueBase.setSummary("Issue x");
        issueBase.setStory("Story x");
        issueBase.setDueDate_closed(new Date());
        issueBase.setDueDate_planned(new Date());
        issueBase.setDueDate_resolved(new Date());

        Benutzer benutzer = new Benutzer();
        benutzer.setId(1000L);
        benutzer.setLogin("testuser x");
        issueBase.setAssignee(benutzer);

        Benutzer benutzer2 = new Benutzer();
        benutzer2.setId(new Long(1001));
        benutzer2.setLogin("testuser x");
        issueBase.setReporter(benutzer2);

        issueBase.setStatus(daoFactory.getIssueStatusDAO().loadAllEntities().get(0));
        issueBase.setType(daoFactory.getIssueTypeDAO().loadAllEntities().get(0));
        issueBase.setPriority(daoFactory.getIssuePriorityDAO().loadAllEntities().get(0));
        issueBase.setVersion(daoFactory.getIssueVersionDAO().loadAllEntities().get(0));
        issueBase.setStoryPoints(daoFactory.getIssueStoryPointDAO().loadAllEntities().get(0));

        issueBase = dao.persist(issueBase);
        if (logger.isDebugEnabled())
            logger.debug(issueBase.toString());
        //assertTrue(issueBase.equals(dao.findById(issueBase.getId()).hashCode()));

        dao.delete(issueBase);
    }


    @Test
    public void loadAll() {
        List<IssueBase> list = dao.loadAllEntities();
        if (logger.isDebugEnabled())
            for (IssueBase issue : list)
                logger.debug(issue.toString());
        assertEquals(list, dao.loadAllEntities());
    }

    @Test
    public void ChangeGraphAttributes() {
        IssueBase issue = dao.loadAllEntities().get(1);
        issue.setStatus(daoFactory.getIssueStatusDAO().loadAllEntities().get(1));
        issue.setType(daoFactory.getIssueTypeDAO().loadAllEntities().get(1));
        issue.setPriority(daoFactory.getIssuePriorityDAO().loadAllEntities().get(1));

        issue = dao.persist(issue);
        IssueBase proof = dao.findById(issue.getId());
        assertEquals(issue.getStatus(), proof.getStatus());
        assertEquals(issue.getType(), proof.getType());
        assertEquals(issue.getPriority(), proof.getPriority());
    }

    @Test
    public void addComments() {
        IssueBase issue = dao.loadAllEntities().get(0);
        IssueComment comment1 = new IssueComment();
        comment1.setId(1000L);
        comment1.setText("comment1");
        comment1.setCreated(new Date());
        comment1.setCreator(issue.getAssignee());

        IssueComment comment2 = new IssueComment();
        comment2.setId(1001L);
        comment2.setText("comment2");
        comment2.setCreated(new Date());
        comment2.setCreator(issue.getAssignee());

        issue.addOrChangeComment(comment1);
        issue.addOrChangeComment(comment2);

        if (logger.isDebugEnabled())
            logger.debug(issue.toString());
        issue = dao.persist(issue);

        assertTrue(issue.getComments().contains(comment1));
        assertTrue(issue.getComments().contains(comment2));

        comment2.setText("CommentB");
        issue.addOrChangeComment(comment2);

        issue = daoFactory.getIssueBaseDAO(projectId).persist(issue);

        assertTrue(issue.getComments().contains(comment2));

        issue.removeComment(comment1);
        issue.removeComment(comment2);

        assertFalse(issue.getComments().contains(comment1));
        assertFalse(issue.getComments().contains(comment2));
    }

    @Test
    public void addTestCases() {
        IssueBase issue = dao.loadAllEntities().get(0);
        IssueTestCase testCase1 = new IssueTestCase();
        testCase1.setId(1000L);
        testCase1.setText("testcase1");

        IssueTestCase testCase2 = new IssueTestCase();
        testCase2.setId(1001L);
        testCase2.setText("testcase2");

        issue.addOrChangeTestCase(testCase1);
        issue.addOrChangeTestCase(testCase2);

        if (logger.isDebugEnabled())
            logger.debug(issue.toString());
        issue = dao.persist(issue);

        assertTrue(issue.getTestcases().contains(testCase1));
        assertTrue(issue.getTestcases().contains(testCase2));

        testCase2.setText("CommentB");
        issue.addOrChangeTestCase(testCase2);

        issue = daoFactory.getIssueBaseDAO(projectId).persist(issue);

        assertTrue(issue.getTestcases().contains(testCase2));

        issue.removeTestCase(testCase1);
        issue.removeTestCase(testCase2);

        assertFalse(issue.getTestcases().contains(testCase1));
        assertFalse(issue.getTestcases().contains(testCase2));
    }

    @Test(expected = NullPointerException.class)
    public void deleteException() {
        dao.delete(null);
    }
}
