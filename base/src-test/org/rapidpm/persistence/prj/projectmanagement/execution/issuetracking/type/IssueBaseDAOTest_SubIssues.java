package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 13.11.12
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class IssueBaseDAOTest_SubIssues implements BaseDAOTest {
    private static Logger logger = Logger.getLogger(IssueBaseDAOTest_SubIssues.class);

    private final Long projectId = 1L;
    private final IssueBaseDAO dao = daoFactory.getIssueBaseDAO(projectId);

    @Test
    public void addSubIssue() {
        IssueBase issue = dao.loadAllEntities().get(0);
        final IssueBase sub = dao.loadAllEntities().get(1);

        boolean success = issue.addSubIssue(sub);
        assertTrue(success);
        issue = dao.persist(issue);
        assertTrue(issue.getSubIssues().contains(sub));

        //try to add subissue again
        success = issue.addSubIssue(sub);
        assertTrue(success);
        issue = dao.persist(issue);
        assertTrue(issue.getSubIssues().contains(sub));
        List<IssueBase> connected = issue.getSubIssues();
        int i = 0;
        for (final IssueBase subIssues : connected) {
            if (subIssues.equals(sub))
                i++;
        }
        assertTrue(i == 1);

        success = issue.removeSubIssue(sub);
        assertTrue(success);
        issue = dao.persist(issue);
        assertFalse(issue.getSubIssues().contains(sub));

        //try to delete non existing subissue
        success = issue.removeSubIssue(sub);
        assertTrue(success);
        issue = dao.persist(issue);
        assertFalse(issue.getSubIssues().contains(sub));

        assertTrue(dao.loadAllEntities().contains(sub));
    }


    @Test
    public void addSubIssueTx_Fail() {
        dao.addSubIssueTx(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void addSubIssue_FirstParameterNull() {
        dao.addSubIssue(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSubIssue_FirstParameterId() {
        dao.addSubIssue(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void addSubIssue_SecondParameterNull() {
        dao.addSubIssue(dao.loadAllEntities().get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSubIssue_SecondParameterId() {
        dao.addSubIssue(dao.loadAllEntities().get(0), new IssueBase(3L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSubIssue_FirstEqualsSecond() {
        dao.addSubIssue(dao.loadAllEntities().get(0), dao.loadAllEntities().get(0));
    }


    @Test
    public void deleteSubIssueRelationTx_Fail() {
        dao.deleteSubIssueRelationTx(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteSubIssueRelation_FirstParameterNull() {
        dao.deleteSubIssueRelation(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSubIssueRelation_FirstParameterId() {
        dao.deleteSubIssueRelation(new IssueBase(3L), null);
    }

    @Test(expected = NullPointerException.class)
    public void deleteSubIssueRelation_SecondParameterNull() {
        dao.deleteSubIssueRelation(dao.loadAllEntities().get(0), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSubIssueRelation_SecondParameterId() {
        dao.deleteSubIssueRelation(dao.loadAllEntities().get(0), new IssueBase(3L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteSubIssueRelation_FirstEqualsSecond() {
        dao.deleteSubIssueRelation(dao.loadAllEntities().get(0), dao.loadAllEntities().get(0));
    }



    @Test(expected = NullPointerException.class)
    public void getSubIssuesOf_FirstParameterNull() {
        dao.getSubIssuesOf(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSubIssuesOf_FirstParameterId() {
        dao.getSubIssuesOf(new IssueBase(3L));
    }
}
