package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelationDAO;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class IssueBaseDAOTest_Relations implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_Relations.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = daoFactory.getIssueBaseDAO(projectId);

    @Test
    public void connectWithDeleteRelation() {
        final List<IssueBase> list = dao.loadAllEntities();
        IssueBase issue1 = list.get(1);
        IssueBase issue2 = list.get(2);
        final IssueRelation rel = daoFactory.getIssueRelationDAO().loadAllEntities().get(1);

        assertTrue(issue1.connectToIssueAs(issue2, rel));
        issue1 = dao.persist(issue1);
        assertTrue(issue1.getConnectedIssues(rel, Direction.OUTGOING).contains(issue2));

        //try to add the same relation again
        assertTrue(issue1.connectToIssueAs(issue2, rel));
        issue1 = dao.persist(issue1);
        List<IssueBase> connected = issue1.getConnectedIssues(rel, Direction.OUTGOING);
        int i = 0;
        for (final IssueBase issueCon : connected) {
            if (issueCon.equals(issue2))
                i++;
        }
        assertTrue(i == 1);


        //delete non existing relation
        List<IssueBase> connected1 = issue1.getConnectedIssues(rel);
        assertTrue(issue1.removeConnectionToIssue(list.get(list.size() - 1), rel));
        issue1 = dao.persist(issue1);
        assertEquals(connected1.size(), issue1.getConnectedIssues(rel).size());


        //delete relation
        assertTrue(issue1.removeConnectionToIssue(issue2, rel));
        issue1 = dao.persist(issue1);
        assertFalse(issue1.getConnectedIssues(rel).contains(issue2));
    }

    @Test
    public void removeRelations() {
        final List<IssueBase> list = dao.loadAllEntities();
        IssueBase issue1 = list.get(1);
        IssueBase issue2 = list.get(2);
        IssueBase issue3 = list.get(3);
        final IssueRelationDAO relationDAO = daoFactory.getIssueRelationDAO();
        final IssueRelation relation1 = relationDAO.loadAllEntities().get(1);
        final IssueRelation relation2 = relationDAO.loadAllEntities().get(1);

        assertTrue(issue1.connectToIssueAs(issue2, relation1));
        issue1 = dao.persist(issue1);
        assertTrue(issue1.getConnectedIssues(relation1, Direction.OUTGOING).contains(issue2));

        assertTrue(issue1.connectToIssueAs(issue3, relation1));
        issue1 = dao.persist(issue1);
        assertTrue(issue1.getConnectedIssues(relation1, Direction.OUTGOING).contains(issue3));

        assertTrue(issue1.connectToIssueAs(issue3, relation2));
        issue1 = dao.persist(issue1);
        assertTrue(issue1.getConnectedIssues(relation2, Direction.OUTGOING).contains(issue3));

        assertTrue(relationDAO.getConnectedIssuesFromProject(relation1, issue1.getProjectid()).contains(issue1));
        assertTrue(relationDAO.getConnectedIssuesFromProject(relation2, issue1.getProjectid()).contains(issue1));

        issue2.removeConnectionToIssue(issue1, relation1);
        issue2 = dao.persist(issue2);
        assertFalse(issue1.getConnectedIssues(relation1).contains(issue2));
        assertTrue(issue1.getConnectedIssues(relation1).contains(issue3));

        assertTrue(relationDAO.getConnectedIssuesFromProject(relation1, issue1.getProjectid()).contains(issue1));
        assertTrue(relationDAO.getConnectedIssuesFromProject(relation2, issue1.getProjectid()).contains(issue1));

        issue1.removeConnectionToIssue(issue3, relation1);
        issue1 = dao.persist(issue1);
        assertFalse(issue1.getConnectedIssues(relation1).contains(issue3));
        assertFalse(relationDAO.getConnectedIssuesFromProject(relation1, issue1.getProjectid()).contains(issue1));

        issue1.removeConnectionToIssue(issue3, relation2);
        issue1 = dao.persist(issue1);
        assertFalse(issue1.getConnectedIssues(relation2).contains(issue3));
        assertFalse(relationDAO.getConnectedIssuesFromProject(relation2, issue1.getProjectid()).contains(issue1));
    }


    @Test
    public void connectEntitiesWithRelationTx_Fail() {
        dao.connectEntitiesWithRelationTx(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void connectEntitiesWithRelation_FirstParameterNull() {
        dao.connectEntitiesWithRelation(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectEntitiesWithRelation_FirstParameterId() {
        dao.connectEntitiesWithRelation(new IssueBase(3L), null, null);
    }

    @Test(expected = NullPointerException.class)
    public void connectEntitiesWithRelation_SecondParameterNull() {
        dao.connectEntitiesWithRelation(dao.loadAllEntities().get(0), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectEntitiesWithRelation_SecondParameterId() {
        dao.connectEntitiesWithRelation(dao.loadAllEntities().get(0), new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void connectEntitiesWithRelation_ThirdParameterNull() {
        dao.connectEntitiesWithRelation(dao.loadAllEntities().get(0), dao.loadAllEntities().get(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectEntitiesWithRelation_ThirdParameterId() {
        dao.connectEntitiesWithRelation(dao.loadAllEntities().get(0), dao.loadAllEntities().get(1), new IssueRelation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void connectEntitiesWithRelation_FirstEqualsSecond() {
        dao.connectEntitiesWithRelation(dao.loadAllEntities().get(0), dao.loadAllEntities().get(0),
                new IssueRelation());
    }


    @Test
    public void deleteRelationOfEntitiesTx_Fail() {
        dao.deleteRelationOfEntitiesTx(null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteRelationOfEntities_FirstParameterNull() {
        dao.deleteRelationOfEntities(null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteRelationOfEntities_FirstParameterId() {
        dao.deleteRelationOfEntities(new IssueBase(3L), null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteRelationOfEntities_SecondParameterNull() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteRelationOfEntities_SecondParameterId() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), new IssueBase(3L), null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteRelationOfEntities_ThirdParameterNull() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), dao.loadAllEntities().get(1), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteRelationOfEntities_ThirdParameterId() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), dao.loadAllEntities().get(1), new IssueRelation(), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteRelationOfEntities_ForthParameterNull() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), dao.loadAllEntities().get(1),
                daoFactory.getIssueRelationDAO().loadAllEntities().get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteRelationOfEntities_FirstEqualsSecond() {
        dao.deleteRelationOfEntities(dao.loadAllEntities().get(0), dao.loadAllEntities().get(0),
                new IssueRelation(), null);
    }


    @Test(expected = NullPointerException.class)
    public void getConnectedIssuesWithRelation_FirstParameterNull() {
        dao.getConnectedIssuesWithRelation(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssuesWithRelation_FirstParameterId() {
        dao.getConnectedIssuesWithRelation(new IssueBase(3L), null, null);
    }

    @Test(expected = NullPointerException.class)
    public void getConnectedIssuesWithRelation_SecondParameterNull() {
        dao.getConnectedIssuesWithRelation(dao.loadAllEntities().get(0), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getConnectedIssuesWithRelation_SecondParameterId() {
        dao.getConnectedIssuesWithRelation(dao.loadAllEntities().get(0), new IssueRelation(), null);
    }

    @Test(expected = NullPointerException.class)
    public void getConnectedIssuesWithRelation_ThirdParameterNull() {
        dao.getConnectedIssuesWithRelation(dao.loadAllEntities().get(0), daoFactory.getIssueRelationDAO().loadAllEntities().get
                (0), null);
    }
}
