package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Test;
import org.neo4j.graphdb.Relationship;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueBaseDAOTest {

    private final IssueBaseDAO dao = GraphDaoFactory.getIssueBaseDAO();

    @Test
    public void addIssue() {
        for (int i = 1; i<4 ;i++) {
            IssueBase issueBase = new IssueBase();
            issueBase.setVersion("1.0");
            issueBase.setStoryPoints(5);
            issueBase.setSummary("Issue " + i);
            issueBase.setText("Text " + i);
            issueBase.setDueDate_closed(new Date());
            issueBase.setDueDate_planned(new Date());
            issueBase.setDueDate_resolved(new Date());

            Benutzer benutzer = new Benutzer();
            benutzer.setId(new Long(i));
            benutzer.setLogin("testuser " + i);

            issueBase.setAssignee(benutzer);

            Benutzer benutzer2 = new Benutzer();
            benutzer2.setId(new Long(i + 100));
            benutzer2.setLogin("testuser " + (i+100));

            issueBase.setReporter(benutzer2);
            issueBase.setStatus(GraphDaoFactory.getIssueStatusDAO().loadAllEntities().get(0));
            issueBase.setType(GraphDaoFactory.getIssueTypeDAO().loadAllEntities().get(0));
            issueBase.setPriority(GraphDaoFactory.getIssuePriorityDAO().loadAllEntities().get(0));

            issueBase = dao.persist(issueBase);
            System.out.println(issueBase.toString());
            //assertEquals(issueBase, dao.getById(issueBase.getId()));
        }

    }

    @Test
    public void ChangeAttributeRelation() {
        IssueBase issue = dao.loadAllEntities().get(1);
        System.out.println(issue.toString());
        issue.setStatus(GraphDaoFactory.getIssueStatusDAO().loadAllEntities().get(1));
        issue.setType(GraphDaoFactory.getIssueTypeDAO().loadAllEntities().get(1));
        issue.setPriority(GraphDaoFactory.getIssuePriorityDAO().loadAllEntities().get(1));

        dao.persist(issue);
    }

    @Test
    public void setListRelations() {
        IssueBase issue = dao.loadAllEntities().get(0);

        List<IssueBase> list = new ArrayList<>();
        list.add(dao.loadAllEntities().get(1));
        issue.setSubIssues(list);
        issue.setComponents(GraphDaoFactory.getIssueComponentDAO().loadAllEntities());
        dao.persist(issue);
    }

    @Test
    public void loadAll() {
        for (IssueBase issue : dao.loadAllEntities())
            System.out.println(issue.toString());
    }

    @Test
    public void connectWithRelation() {
        List<IssueBase> list = dao.loadAllEntities();
        dao.connectEntitiesWithRelationTx(list.get(1), list.get(2),
                GraphDaoFactory.getIssueRelationDAO()
                .loadAllEntities().get(1));
    }

    @Test
    public void deleteRelationOfEntities()  {
        List<IssueBase> list = dao.loadAllEntities();
        dao.deleteRelationOfEntitiesTx(list.get(1), list.get(2), GraphDaoFactory.getIssueRelationDAO()
                .loadAllEntities().get(1));
    }

    @Test
    public void setAsChild() {
        List<IssueBase> list = dao.loadAllEntities();
        dao.setAsChildTx(list.get(1), list.get(0));
    }
}
