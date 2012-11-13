package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
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
public class IssueComponentDAOTest {
    private static Logger logger = Logger.getLogger(IssueComponentDAOTest.class);

    private final IssueComponentDAO dao = GraphDaoFactory.getIssueComponentDAO();

    @Test
    public void addChangeDelete() {
        IssueComponent component = new IssueComponent("test");
        component = dao.persist(component);
        assertEquals(component, dao.findById(component.getId()));

        component.setComponentName("second_test");
        component = dao.persist(component);
        assertEquals(component, dao.findById(component.getId()));

        dao.delete(component);
        assertFalse(dao.loadAllEntities().contains(component));
    }


    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueComponent component = dao.loadAllEntities().get(0);
        IssueComponent compTest = new IssueComponent();
        compTest.setComponentName(component.getComponentName());
        dao.persist(compTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssueComponent component : dao.loadAllEntities()) {
            List<IssueBase> issueList = component.getConnectedIssuesFromProject(1L);

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                for (IssueComponent comp : issue.getComponents())
                    if (comp.equals(component))
                        assertTrue(issueList.contains(issue));
            }
        }
    }
}
