package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueBaseDAOTest {

    private final IssueBaseDAO dao = GraphDaoFactory.getInstance().getIssueBaseDAO();

    @Test
    public void addIssue() throws Exception {
        IssueBase issueBase = new IssueBase();
        issueBase.setVersion("1.0");
        issueBase.setStoryPoints(5);
        issueBase.setSummary("Issue 2");
        issueBase.setText("Text 2");
        issueBase.setDueDate_closed(new Date());
        issueBase.setDueDate_planned(new Date());
        issueBase.setDueDate_resolved(new Date());

        Benutzer benutzer = new Benutzer();
        benutzer.setId(new Long(12315));
        benutzer.setLogin("testuser");

        issueBase.setAssignee(benutzer);

        Benutzer benutzer2 = new Benutzer();
        benutzer2.setId(new Long(1234));
        benutzer2.setLogin("testuser2");

        issueBase.setReporter(benutzer2);

        issueBase = dao.persist(issueBase);
        System.out.println(issueBase.toString());
        //assertEquals(issueBase, dao.getById(issueBase.getId()));
        // dao.delete(issueBase);
    }

    @Test
    public void setRelation() {
        IssueBaseDAO issueBaseDAO = GraphDaoFactory.getInstance().getIssueBaseDAO();
        IssueBase issue = issueBaseDAO.loadAllEntities().get(0);
        System.out.println(issue.toString());
        issue.setStatus(GraphDaoFactory.getInstance().getIssueStatusDAO().loadAllEntities().get(1));
        issue.setType(GraphDaoFactory.getInstance().getIssueTypeDAO().loadAllEntities().get(1));
        issue.setPriority(GraphDaoFactory.getInstance().getIssuePriorityDAO().loadAllEntities().get(1));

        issueBaseDAO.persist(issue);
    }

    @Test
    public void setListRelations() {
        IssueBaseDAO issueBaseDAO = GraphDaoFactory.getInstance().getIssueBaseDAO();
        IssueBase issue = issueBaseDAO.loadAllEntities().get(0);

        List<IssueBase> list = new ArrayList<>();
        list.add(issueBaseDAO.loadAllEntities().get(1));
        issue.setSubIssues(list);
        issue.setComponents(GraphDaoFactory.getInstance().getIssueComponentDAO().loadAllEntities());
        issueBaseDAO.persist(issue);
    }

    @Test
    public void loadAll() {
        IssueBaseDAO issueBaseDAO = GraphDaoFactory.getInstance().getIssueBaseDAO();
        for (IssueBase issue : issueBaseDAO.loadAllEntities())
            System.out.println(issue.toString());
    }
}
