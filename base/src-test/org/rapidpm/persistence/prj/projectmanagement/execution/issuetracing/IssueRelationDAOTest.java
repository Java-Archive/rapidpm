package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelationDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.11.12
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelationDAOTest {
    private static Logger logger = Logger.getLogger(IssueRelationDAOTest.class);

    private final IssueRelationDAO dao = GraphDaoFactory.getIssueRelationDAO();

    @Test
    public void addChangeDelete() {
        IssueRelation relation = new IssueRelation();
        relation.setRelationName("test");
        relation.setOutgoingName("outgoing_Test");
        relation.setIncomingName("incoming_Test");
        relation = dao.persist(relation);
        assertEquals(relation, dao.findById(relation.getId()));

        relation.setRelationName("second_test");
        relation.setOutgoingName("second_outgoing_Test");
        relation.setIncomingName("second_incoming_Test");
        relation = dao.persist(relation);
        assertEquals(relation, dao.findById(relation.getId()));

        dao.delete(relation);
        assertFalse(dao.loadAllEntities().contains(relation));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueRelation relation = dao.loadAllEntities().get(0);
        IssueRelation relTest = new IssueRelation();
        relTest.setRelationName(relation.getRelationName());
        dao.persist(relTest);
    }
}
