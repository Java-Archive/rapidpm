package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.11.12
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelationDAOTest implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueRelationDAOTest.class);

    private final IssueRelationDAO dao = daoFactory.getIssueRelationDAO();

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueRelation> relationList = dao.loadAllEntities();
        assertTrue(relationList.get(0).equals(relationList.get(0)));
        assertEquals(relationList.get(0).hashCode(), relationList.get(0).hashCode());

        assertFalse(relationList.get(0).equals(new IssueComment()));
        assertNotSame(new IssueComment().hashCode(), relationList.get(0).hashCode());
    }

    @Test
    public void addChangeDelete() {
        IssueRelation relation = new IssueRelation();
        relation.setRelationName("test");
        relation.setOutgoingName("outgoing_Test");
        relation.setIncomingName("incoming_Test");
        relation = dao.persist(relation);
        assertEquals(relation, dao.findByID(relation.getId()));

        relation.setRelationName("second_test");
        relation.setOutgoingName("second_outgoing_Test");
        relation.setIncomingName("second_incoming_Test");
        relation = dao.persist(relation);
        assertEquals(relation, dao.findByID(relation.getId()));

        IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO(1L);
        List<IssueBase> issueList = issueDAO.loadAllEntities();
        IssueBase issue1 = issueList.get(0);
        issue1.connectToIssueAs(issueList.get(1), relation);
        issue1.connectToIssueAs(issueList.get(2), relation);
        issue1 = issueDAO.persist(issue1);

        dao.delete(relation);
        assertFalse(dao.loadAllEntities().contains(relation));
        assertTrue(issue1.getConnectedIssues(relation).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        final IssueRelation relation = dao.loadAllEntities().get(0);
        final IssueRelation relTest = new IssueRelation();
        relTest.setRelationName(relation.getRelationName());
        relTest.setOutgoingName(relation.getOutgoingName());
        relTest.setIncomingName(relation.getIncomingName());
        dao.persist(relTest);
    }
}
