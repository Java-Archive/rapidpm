package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
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
        final IssueBase issue = dao.loadAllEntities().get(0);
        final IssueBase sub = dao.loadAllEntities().get(1);

        assertTrue(issue.addSubIssue(sub));
        //issue = dao.persist(issue);
        assertTrue(issue.getSubIssues().contains(sub));

        //try to add subissue again
        assertTrue(issue.addSubIssue(sub));
        //issue = dao.persist(issue);
        assertTrue(issue.getSubIssues().contains(sub));
        List<IssueBase> connected = issue.getSubIssues();
        int i = 0;
        for (final IssueBase subIssues : connected) {
            if (subIssues.equals(sub))
                i++;
        }
        assertTrue(i == 1);

        assertTrue(issue.removeSubIssue(sub));
        //issue = dao.persist(issue);
        assertFalse(issue.getSubIssues().contains(sub));

        //try to delete non existing subissue
        assertFalse(issue.removeSubIssue(sub));
        //issue = dao.persist(issue);
        assertFalse(issue.getSubIssues().contains(sub));

        assertTrue(dao.loadAllEntities().contains(sub));
    }

    @Test
    public void deleteParentOfSubIssue() {
        IssueBase issueBase1 = new IssueBase(projectId);
        issueBase1.setSummary("Issue y");
        issueBase1.setStory("Story y");
        issueBase1.setDueDate_closed(new Date());
        issueBase1.setDueDate_planned(new Date());
        issueBase1.setDueDate_resolved(new Date());
        issueBase1.setAssignee(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        issueBase1.setReporter(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        issueBase1.setStatus(daoFactory.getIssueStatusDAO().loadAllEntities().get(0));
        issueBase1.setType(daoFactory.getIssueTypeDAO().loadAllEntities().get(0));
        issueBase1.setPriority(daoFactory.getIssuePriorityDAO().loadAllEntities().get(0));
        issueBase1.setVersion(daoFactory.getIssueVersionDAO().loadAllEntities().get(0));
        issueBase1.setStoryPoints(daoFactory.getIssueStoryPointDAO().loadAllEntities().get(0));
        issueBase1 = dao.persist(issueBase1);

        IssueBase issueBase2 = new IssueBase(projectId);
        issueBase2.setSummary("Issue z");
        issueBase2.setStory("Story z");
        issueBase2.setDueDate_closed(new Date());
        issueBase2.setDueDate_planned(new Date());
        issueBase2.setDueDate_resolved(new Date());
        issueBase2.setAssignee(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        issueBase2.setReporter(daoFactory.getBenutzerDAO().loadAllEntities().get(0));
        issueBase2.setStatus(daoFactory.getIssueStatusDAO().loadAllEntities().get(0));
        issueBase2.setType(daoFactory.getIssueTypeDAO().loadAllEntities().get(0));
        issueBase2.setPriority(daoFactory.getIssuePriorityDAO().loadAllEntities().get(0));
        issueBase2.setVersion(daoFactory.getIssueVersionDAO().loadAllEntities().get(0));
        issueBase2.setStoryPoints(daoFactory.getIssueStoryPointDAO().loadAllEntities().get(0));
        issueBase2 = dao.persist(issueBase2);

        if (logger.isDebugEnabled()) {
            logger.debug(issueBase1.toString());
            logger.debug(issueBase2.toString());
        }

        assertTrue(issueBase1.equals(dao.findByID(issueBase1.getId())));
        assertTrue(issueBase2.equals(dao.findByID(issueBase2.getId())));
        IssueBase toplevelIssue = dao.loadTopLevelEntities().get(0);
        toplevelIssue.addSubIssue(issueBase1);
        issueBase1.addSubIssue(issueBase2);

        assertTrue(toplevelIssue.getSubIssues().contains(issueBase1));
        assertTrue(issueBase1.getSubIssues().contains(issueBase2));

        dao.delete(issueBase1);
        assertFalse(dao.loadAllEntities().contains(issueBase1));

        assertTrue(toplevelIssue.getSubIssues().contains(issueBase2));

        dao.delete(issueBase2);
        assertFalse(dao.loadAllEntities().contains(issueBase2));
    }

    @Test
    public void setAsRootIssue() {
        List<IssueBase> list = dao.loadTopLevelEntities();
        final IssueBase issue = list.get(0);
        final IssueBase sub = list.get(1);

        assertTrue(issue.addSubIssue(sub));
        assertTrue(issue.getSubIssues().contains(sub));

        assertTrue(sub.setAsRootIssue());
        assertTrue(dao.loadTopLevelEntities().contains(sub));
        assertFalse(issue.getSubIssues().contains(sub));

        assertTrue(sub.setAsRootIssue());
    }

    @Test
    public void deleteSubIssueWithWrongParent() {
        List<IssueBase> list = dao.loadTopLevelEntities();
        final IssueBase issue1 = list.get(0);
        final IssueBase issue2 = list.get(1);
        final IssueBase issue3 = list.get(2);

        assertTrue(issue1.addSubIssue(issue2));
        assertFalse(issue3.removeSubIssue(issue2));

        issue2.setAsRootIssue();
    }


    @Test
    public void addSubIssueTx_Null() {
        assertFalse(dao.addSubIssueTx(null, null));
    }

    @Test
    public void addSubIssueTx_IdNull() {
        assertFalse(dao.addSubIssueTx(new IssueBase(3L), new IssueBase(3L)));
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
    public void deleteSubIssueRelationTx_Null() {
        assertFalse(dao.deleteSubIssueRelationTx(null, null));

    }

    @Test
    public void deleteSubIssueRelationTx_IdNull() {
        assertFalse(dao.deleteSubIssueRelationTx(new IssueBase(3L), new IssueBase(3L)));
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


    @Test
    public void setAsRootIssueTx_Null() {
        assertFalse(dao.setAsRootIssueTx(null));
    }

    @Test
    public void setAsRootIssueTx_IdNull() {
        assertFalse(dao.setAsRootIssueTx(new IssueBase(3L)));
    }

    @Test(expected = NullPointerException.class)
    public void setAsRootIssue_FirstParameterNull() {
        dao.setAsRootIssue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAsRootIssue_FirstParameterId() {
        dao.setAsRootIssue(new IssueBase(3L));
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
