package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponentDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTypeDAO;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 22.10.12
 * Time: 09:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAOTest {

    private final IssueComponentDAO dao = GraphDaoFactory.getIssueComponentDAO();

    @Test
    public void addComponent() {
        IssueComponent component = new IssueComponent();
        component.setComponentName("first");
        component = dao.persist(component);
        assertEquals(component, dao.getById(component.getId()));
        dao.delete(component);
    }

    @Test
    public void changeComponent() {
        IssueComponent component = dao.loadAllEntities().get(0);
        component.setComponentName("1st");
        component = dao.persist(component);
        assertEquals(component, dao.getById(component.getId()));
        dao.delete(component);
    }
}
