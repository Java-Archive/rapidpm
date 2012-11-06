package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IssueBaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = GraphDaoFactory.getIssueBaseDAO(projectId);

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
//
//            Benutzer benutzer2 = new Benutzer();
//            benutzer2.setId(new Long(1001));
//            benutzer2.setLogin("testuser x");
//            issueBase.setReporter(benutzer2);

        issueBase.setStatus(GraphDaoFactory.getIssueStatusDAO().loadAllEntities().get(0));
        issueBase.setType(GraphDaoFactory.getIssueTypeDAO().loadAllEntities().get(0));
        issueBase.setPriority(GraphDaoFactory.getIssuePriorityDAO().loadAllEntities().get(0));
        issueBase.setVersion(GraphDaoFactory.getIssueVersionDAO().loadAllEntities().get(0));
        issueBase.setStoryPoints(GraphDaoFactory.getIssueStoryPointDAO().loadAllEntities().get(0));

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
        assertEquals(list.size(), 3);
    }

    @Test
    public void ChangeGraphAttributes() {
        IssueBase issue = dao.loadAllEntities().get(1);
        issue.setStatus(GraphDaoFactory.getIssueStatusDAO().loadAllEntities().get(1));
        issue.setType(GraphDaoFactory.getIssueTypeDAO().loadAllEntities().get(1));
        issue.setPriority(GraphDaoFactory.getIssuePriorityDAO().loadAllEntities().get(1));

        issue = dao.persist(issue);
        IssueBase proof = dao.findById(issue.getId());
        assertEquals(issue.getStatus(), proof.getStatus());
        assertEquals(issue.getType(), proof.getType());
        assertEquals(issue.getPriority(), proof.getPriority());
    }

    @Test
    public void addSubIssue() {
        IssueBase issue = dao.loadAllEntities().get(0);
        IssueBase sub = dao.loadAllEntities().get(1);

        issue.addSubIssue(sub);
        assertEquals(issue.getSubIssues().get(0).getId(), sub.getId());

        issue.removeSubIssue(sub);
        assertEquals(issue.getSubIssues().size(), 0);

        assertTrue(dao.loadAllEntities().contains(sub));
    }

    @Test
    public void connectWithRelation() {
        List<IssueBase> list = dao.loadAllEntities();
        IssueBase issue1 = list.get(1);
        IssueBase issue2 = list.get(2);
        IssueRelation rel = GraphDaoFactory.getIssueRelationDAO().loadAllEntities().get(1);

        boolean success = issue1.connectToIssueAs(issue2, rel);
        assertTrue(success);
        List<IssueBase> connected= issue1.getConnectedIssues(rel, Direction.OUTGOING);
        assertTrue(connected.contains(issue2));

        success = issue1.removeConnectionToIssue(issue2, rel);
        assertTrue(success);
        connected= issue1.getConnectedIssues(rel);
        assertFalse(connected.contains(issue2));

    }

    @Test
    public void addComponent() {
        IssueBase issue = dao.loadAllEntities().get(0);
        List<IssueComponent> componentList = GraphDaoFactory.getIssueComponentDAO().loadAllEntities();

        boolean success = issue.addComponent(componentList.get(0));
        assertTrue(success);
        List<IssueComponent> connected= issue.getComponents();
        assertTrue(connected.contains(componentList.get(0)));

        success = issue.addComponent(componentList.get(1));
        assertTrue(success);
        connected= issue.getComponents();
        assertTrue(connected.contains(componentList.get(0)) && connected.contains(componentList.get(1)));


        success = issue.removeComponent(componentList.get(0));
        assertTrue(success);
        connected= issue.getComponents();
        assertFalse(connected.contains(componentList.get(0)));

        success = issue.removeComponent(componentList.get(1));
        assertTrue(success);
        connected= issue.getComponents();
        assertFalse(connected.contains(componentList.get(1)));

    }

    @Ignore
    @Test
    public void addComments() {
        IssueBase issue = dao.loadAllEntities().get(0);
        IssueComment comment1 = new IssueComment();
        comment1.setText("comment1");
        comment1.setCreated(new Date());
        comment1.setCreator(issue.getAssignee());

        IssueComment comment2 = new IssueComment();
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

        issue = GraphDaoFactory.getIssueBaseDAO(projectId).persist(issue);

        assertTrue(issue.getComments().contains(comment2));

        issue.removeComment(comment1);
        issue.removeComment(comment2);

        assertFalse(issue.getComments().contains(comment1));
        assertFalse(issue.getComments().contains(comment2));

    }

}
