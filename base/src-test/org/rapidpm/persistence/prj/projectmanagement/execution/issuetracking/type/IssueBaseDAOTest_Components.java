package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 13.11.12
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDAOTest_Components implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_Components.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = daoFactory.getIssueBaseDAO(projectId);

    @Test
    public void addComponent() {
        IssueBase issue = dao.loadAllEntities().get(0);
        List<IssueComponent> componentList = daoFactory.getIssueComponentDAO().loadAllEntities();

        boolean success = issue.addComponent(componentList.get(0));
        assertTrue(success);
        issue = dao.persist(issue);
        List<IssueComponent> connected= issue.getComponents();
        assertTrue(connected.contains(componentList.get(0)));

        success = issue.addComponent(componentList.get(1));
        assertTrue(success);
        issue = dao.persist(issue);
        connected= issue.getComponents();
        assertTrue(connected.contains(componentList.get(0)) && connected.contains(componentList.get(1)));


        success = issue.removeComponent(componentList.get(0));
        assertTrue(success);
        issue = dao.persist(issue);
        connected= issue.getComponents();
        assertFalse(connected.contains(componentList.get(0)));

        success = issue.removeComponent(componentList.get(1));
        assertTrue(success);
        issue = dao.persist(issue);
        connected= issue.getComponents();
        assertFalse(connected.contains(componentList.get(1)));
    }


    @Test
    public void addComponent2() {
        IssueBase issue = dao.loadAllEntities().get(0);
        List<IssueComponent> componentList = daoFactory.getIssueComponentDAO().loadAllEntities();

        boolean success;

        for (IssueComponent component : componentList) {
            success = issue.addComponent(component);
            assertTrue(success);
            issue = dao.persist(issue);
        }

        List<IssueComponent> connected = issue.getComponents();
        for (IssueComponent comp : connected) {
            assertTrue(componentList.contains(comp));
        }


        success = issue.addComponent(componentList.get(0));
        assertTrue(success);
        issue = dao.persist(issue);

        assertEquals(connected.size(), issue.getComponents().size());

        for (IssueComponent comp : componentList) {
            success = issue.removeComponent(comp);
            assertTrue(success);
        }
        issue = dao.persist(issue);

        assertEquals(issue.getComponents().size(), 0);
    }



    @Test
    public void addComponentToTx_Fail() {
        dao.addComponentToTx(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addComponentTo_FirstParameterNull() {
        dao.addComponentTo(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addComponentTo_FirstParameterId() {
        dao.addComponentTo(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void addComponentTo_SecondParameterNull() {
        dao.addComponentTo(dao.loadAllEntities().get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addComponentTo_SecondParameterId() {
        dao.addComponentTo(dao.loadAllEntities().get(0), new IssueComponent());
    }

    @Test
    public void deleteComponentRelationTx_Fail() {
        dao.deleteComponentRelationTx(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteComponentRelation_FirstParameterNull() {
        dao.deleteComponentRelation(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteComponentRelation_FirstParameterId() {
        dao.deleteComponentRelation(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteComponentRelation_SecondParameterNull() {
        dao.deleteComponentRelation(dao.loadAllEntities().get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteComponentRelation_SecondParameterId() {
        dao.deleteComponentRelation(dao.loadAllEntities().get(0), new IssueComponent());
    }


    @Test(expected = NullPointerException.class)
    public void getComponentsOf_FirstParameterNull() {
        dao.getComponentsOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getComponentsOf_FirstParameterId() {
        dao.getComponentsOf(new IssueBase(3L));
    }
}
