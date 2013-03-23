package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseIssueTrackingDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 13.11.12
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDAOTest_Components extends BaseIssueTrackingDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_Components.class);

    private final IssueBaseDAO dao = daoFactory.getIssueBaseDAO();

    @Test
    public void addComponent() {
        IssueBase issue = dao.loadAllEntities(PROJECTID).get(0);
        final List<IssueComponent> componentList = daoFactory.getIssueComponentDAO().loadAllEntities(PROJECTID);

        assertTrue(issue.addComponent(componentList.get(0)));
        issue = dao.persist(issue);
        assertTrue(issue.getComponents().contains(componentList.get(0)));

        assertTrue(issue.addComponent(componentList.get(1)));
        issue = dao.persist(issue);
        List<IssueComponent> connected = issue.getComponents();
        assertTrue(connected.contains(componentList.get(0)) && connected.contains(componentList.get(1)));


        assertTrue(issue.removeComponent(componentList.get(0)));
        issue = dao.persist(issue);
        assertFalse(issue.getComponents().contains(componentList.get(0)));

        assertTrue(issue.removeComponent(componentList.get(1)));
        issue = dao.persist(issue);
        assertFalse(issue.getComponents().contains(componentList.get(1)));
    }


    @Test
    public void addComponent2() {
        IssueBase issue = dao.loadAllEntities(PROJECTID).get(0);
        final List<IssueComponent> componentList = daoFactory.getIssueComponentDAO().loadAllEntities(PROJECTID);

        boolean success;

        for (final IssueComponent component : componentList) {
            success = issue.addComponent(component);
            assertTrue(success);
            issue = dao.persist(issue);
        }

        final List<IssueComponent> connected = issue.getComponents();
        for (final IssueComponent comp : connected) {
            assertTrue(componentList.contains(comp));
        }


        success = issue.addComponent(componentList.get(0));
        assertTrue(success);
        issue = dao.persist(issue);

        assertEquals(connected.size(), issue.getComponents().size());

        for (final IssueComponent comp : componentList) {
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
    public void addComponentTo_FirstParameterNoId() {
        dao.addComponentTo(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void addComponentTo_SecondParameterNull() {
        dao.addComponentTo(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addComponentTo_SecondParameterNoId() {
        dao.addComponentTo(dao.loadAllEntities(PROJECTID).get(0), new IssueComponent());
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
    public void deleteComponentRelation_FirstParameterNoId() {
        dao.deleteComponentRelation(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteComponentRelation_SecondParameterNull() {
        dao.deleteComponentRelation(dao.loadAllEntities(PROJECTID).get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteComponentRelation_SecondParameterNoId() {
        dao.deleteComponentRelation(dao.loadAllEntities(PROJECTID).get(0), new IssueComponent());
    }


    @Test(expected = NullPointerException.class)
    public void getComponentsOf_FirstParameterNull() {
        dao.getComponentsOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getComponentsOf_FirstParameterNoId() {
        dao.getComponentsOf(new IssueBase(3L));
    }
}
