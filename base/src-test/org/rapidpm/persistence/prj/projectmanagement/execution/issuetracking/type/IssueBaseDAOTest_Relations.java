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
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IssueBaseDAOTest_Relations {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_Relations.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = GraphDaoFactory.getIssueBaseDAO(projectId);

    @Test
    public void connectWithDeleteRelation() {
        List<IssueBase> list = dao.loadAllEntities();
        IssueBase issue1 = list.get(1);
        IssueBase issue2 = list.get(2);
        IssueRelation rel = GraphDaoFactory.getIssueRelationDAO().loadAllEntities().get(1);

        boolean success = issue1.connectToIssueAs(issue2, rel);
        assertTrue(success);
        issue1 = dao.persist(issue1);
        List<IssueBase> connected = issue1.getConnectedIssues(rel, Direction.OUTGOING);
        assertTrue(connected.contains(issue2));

        //try to add the same relation again
        success = issue1.connectToIssueAs(issue2, rel);
        assertTrue(success);
        issue1 = dao.persist(issue1);
        connected = issue1.getConnectedIssues(rel, Direction.OUTGOING);
        int i = 0;
        for (IssueBase issueCon : connected) {
            if (issueCon.equals(issue2))
                i++;
        }
        assertTrue(i == 1);


        //delete non existing relation
        connected = issue1.getConnectedIssues(rel);
        success = issue1.removeConnectionToIssue(list.get(list.size() - 1), rel);
        assertTrue(success);
        issue1 = dao.persist(issue1);
        assertEquals(connected.size(), issue1.getConnectedIssues(rel).size());


        //delete relation
        success = issue1.removeConnectionToIssue(issue2, rel);
        assertTrue(success);
        issue1 = dao.persist(issue1);
        connected= issue1.getConnectedIssues(rel);
        assertFalse(connected.contains(issue2));
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
                GraphDaoFactory.getIssueRelationDAO().loadAllEntities().get(0), null);
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
        dao.getConnectedIssuesWithRelation(dao.loadAllEntities().get(0), GraphDaoFactory.getIssueRelationDAO().loadAllEntities().get
                (0), null);
    }
}
