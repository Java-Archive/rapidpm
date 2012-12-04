package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 22.10.12
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueComponentDAOTest.class);

    private final IssueComponentDAO dao = daoFactory.getIssueComponentDAO();

    @Test
    public void addChangeDelete() {
        IssueComponent component = new IssueComponent("test");
        component = dao.persist(component);
        assertEquals(component, dao.findByID(component.getId()));

        component.setComponentName("second_test");
        component = dao.persist(component);
        assertEquals(component, dao.findByID(component.getId()));

        dao.delete(component);
        assertFalse(dao.loadAllEntities().contains(component));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueComponent component = dao.loadAllEntities().get(0);
        final IssueComponent compTest = new IssueComponent();
        compTest.setComponentName(component.getComponentName());
        dao.persist(compTest);
    }

    @Test
    public void getConnectedIssus() {
        for (final IssueComponent component : dao.loadAllEntities()) {
            final List<IssueBase> issueList = component.getConnectedIssuesFromProject(1L);

            for (final IssueBase issue : daoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                for (final IssueComponent comp : issue.getComponents())
                    if (comp.equals(component))
                        assertTrue(issueList.contains(issue));
            }
        }
    }
}
