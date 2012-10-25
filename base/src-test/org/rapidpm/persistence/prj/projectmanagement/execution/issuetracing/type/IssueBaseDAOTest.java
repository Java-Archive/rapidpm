package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IssueBaseDAOTest {

    private final IssueBaseDAO dao = GraphDaoFactory.getIssueBaseDAO();

    @Test
    public void addIssue() {
        IssueBase issueBase = new IssueBase();
        issueBase.setVersion("1.0");
        issueBase.setStoryPoints(10);
        issueBase.setSummary("Issue x");
        issueBase.setText("Text x");
        issueBase.setDueDate_closed(new Date());
        issueBase.setDueDate_planned(new Date());
        issueBase.setDueDate_resolved(new Date());

            Benutzer benutzer = new Benutzer();
            benutzer.setId(new Long(1000));
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

        issueBase = dao.persist(issueBase);
        System.out.println(issueBase.toString());
        //assertTrue(issueBase.equals(dao.findById(issueBase.getId()).hashCode()));

        dao.delete(issueBase);
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

    @Test
    public void loadAll() {
        List<IssueBase> list = dao.loadAllEntities();
        for (IssueBase issue : list)
            System.out.println(issue.toString());
        assertEquals(list.size(), 3);
    }
}
