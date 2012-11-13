package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.11.12
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class IssueVersionDAOTest {
    private static Logger logger = Logger.getLogger(IssueVersionDAOTest.class);

    private final IssueVersionDAO dao = GraphDaoFactory.getIssueVersionDAO();
    private final IssueVersion assignTo = dao.loadAllEntities().get(0);

    @Test
    public void addChangeDelete() {
        IssueVersion version = new IssueVersion("test");
        version = dao.persist(version);
        assertEquals(version, dao.findById(version.getId()));

        version.setVersionName("second_test");
        version = dao.persist(version);
        assertEquals(version, dao.findById(version.getId()));

        dao.delete(version, assignTo);
        assertFalse(dao.loadAllEntities().contains(version));
    }

    @Test(expected = IllegalArgumentException.class)
    public void persistExistingName() {
        IssueVersion version = dao.loadAllEntities().get(0);
        IssueVersion verTest = new IssueVersion();
        verTest.setVersionName(version.getVersionName());
        dao.persist(verTest);
    }

    @Test
    public void getConnectedIssus() {
        for (IssueVersion version : dao.loadAllEntities()) {
            List<IssueBase> issueList = version.getConnectedIssuesFromProject(1L);

            for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(1L).loadAllEntities()) {
                if (issue.getVersion().equals(version))
                    assertTrue(issueList.contains(issue));
            }
        }
    }
}
