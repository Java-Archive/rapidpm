package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracing.type;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 23, 2010
 * Time: 1:09:49 PM
 * To change this template use File | Settings | File Templates.
 */

import org.joda.time.DateTime;
import org.junit.Test;
import org.rapidpm.persistence.DAOTest;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class IssueBaseDAOTest extends DAOTest {

//    @Test
//    public void testLoadAllIssuesFor001() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        final Date version = Date.valueOf("2010-11-26");
//        final List<IssueBase> issueBases = issueDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", version, null, null);
//        assertNotNull(issueBases);
//    }
//
//    @Test
//    public void testLoadAllIssuesFor002() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        final Date version = Date.valueOf("2010-11-26");
//        final List<IssueBase> issueBases = issueDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", null, null, null);
//        assertNotNull(issueBases);
//    }
//
//    @Test
//    public void testLoadAllIssuesFor003() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        final DateTime dateTime = new DateTime("2010-11-26");
//        final List<IssueBase> issueBases = issueDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", dateTime.toDate(), null, null);
//        assertNotNull(issueBases);
//    }
//
//    @Test
//    public void testLoadAllIssuesFor004() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        final List<IssueBase> issueBases = issueDAO.loadAllIssuesFromVersionToVersion(null, null, null, null, null, null, null);
//        assertNotNull(issueBases);
//    }
//
//    @Test
//    public void testLoadAllIssuesFromVersionToVersion() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        List<IssueBase> issueBases = issueDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", null, null, null);
//        assertNotNull(issueBases);
//        issueBases = issueDAO.loadAllIssuesFromVersionToVersion("NeoScioPortal", "NeoScio - intern - allgemeines", "sven.ruppert", "open", new DateTime("2010-11-23").toDate(), new DateTime("2010-12-03").toDate(), null);
//        assertNotNull(issueBases);
//    }
//
//    @Test
//    public void testAddIssue() throws Exception {
//        //To change body of created methods use File | Settings | File Templates.
//    }
//
//    @Test
//    public void testLoadAllIssuesForBenutzer() throws Exception {
//        final IssueBaseDAO issueDAO = daoFactory.getIssueBaseDAO();
//        assertNotNull(issueDAO.loadAllIssuesForBenutzer("sven.ruppert"));
//        assertNotNull(issueDAO.loadAllIssuesForBenutzer(6L));
//    }
}
