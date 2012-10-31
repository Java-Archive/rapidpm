package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponentDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    public void addComponent() {
        IssueComponent component = new IssueComponent();
        component.setComponentName("first");
        component = dao.persist(component);
        assertEquals(component, dao.findById(component.getId()));
        dao.delete(component);
    }

    @Test
    public void changeComponent() {
        IssueComponent component = dao.loadAllEntities().get(0);
        component.setComponentName("1st");
        component = dao.persist(component);
        assertEquals(component, dao.findById(component.getId()));
        dao.delete(component);
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
            List<IssueBase> issueList = dao.getConnectedIssuesFromProject(component, 1L);
            List<IssueBase> testList = new ArrayList<>();

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                for (IssueComponent comp : issue.getComponents())
                    if (comp.equals(component))
                        testList.add(issue);
            }

            assertEquals(issueList, testList);
            if (logger.isDebugEnabled())
                logger.debug("listsize: " + issueList.size());
        }
    }
}
