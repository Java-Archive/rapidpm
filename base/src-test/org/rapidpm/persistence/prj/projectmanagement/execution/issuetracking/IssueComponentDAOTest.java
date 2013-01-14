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
 * Date: 22.10.12
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueComponentDAOTest.class);

    private final IssueComponentDAO dao = daoFactory.getIssueComponentDAO();

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueComponent> componentList = dao.loadAllEntities(PROJECTID);
        assertTrue(componentList.get(0).equals(componentList.get(0)));
        assertEquals(componentList.get(0).hashCode(), componentList.get(0).hashCode());

        assertFalse(componentList.get(0).equals(new IssueComponent()));
        assertNotSame(new IssueComponent(PROJECTID).hashCode(), componentList.get(0).hashCode());
    }


    @Test
    public void addChangeDelete() {
        IssueComponent component = new IssueComponent("test");
        component.setProjectId(PROJECTID);
        component = dao.persist(component);
        assertEquals(component, dao.findByID(component.getId()));

        component.setComponentName("second_test");
        component = dao.persist(component);
        assertEquals(component, dao.findByID(component.getId()));

        dao.delete(component);
        assertFalse(dao.loadAllEntities(PROJECTID).contains(component));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueComponent component = dao.loadAllEntities(PROJECTID).get(0);
        final IssueComponent compTest = new IssueComponent();
        compTest.setProjectId(PROJECTID);
        compTest.setComponentName(component.getComponentName());
        dao.persist(compTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueComponent component : dao.loadAllEntities(PROJECTID)) {
            final List<IssueBase> compConnIssueList = component.getConnectedIssues();
            final List<IssueBase> issueList = new ArrayList<>();

            for (final IssueBase issue : daoFactory.getIssueBaseDAO().loadAllEntities(PROJECTID)) {
                for (final IssueComponent comp : issue.getComponents())
                    if (comp.equals(component))
                        issueList.add(issue);
            }

            assertEquals(compConnIssueList.size(), issueList.size());
            for (final IssueBase match : compConnIssueList) {
                assertTrue(issueList.contains(match));
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void delete_FirstParameterNull() {
        dao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delete_FirstParameterNoId() {
        dao.delete(new IssueComponent());
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssues_FirstParameterNull() {
        dao.getConnectedIssuesFromProject(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_firstParameterNoId() {
        dao.getConnectedIssuesFromProject(new IssueComponent(), -1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssues_SecondParameterNull() {
        dao.getConnectedIssuesFromProject(dao.loadAllEntities(PROJECTID).get(0), -1L);
    }
}
