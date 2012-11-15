package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 15.11.12
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class IssueTestCasesDAOTest implements BaseDAOTest {

    private final IssueTestCaseDAO dao = daoFactory.getIssueTestCaseDAO();

    @Test
    public void addChangeDelete() {
        IssueTestCase testCase = new IssueTestCase();
        testCase.setText("test");
        daoFactory.saveOrUpdateTX(testCase);
        assertTrue(testCase.getId() != null);
        assertEquals(testCase, dao.findByID(testCase.getId()));

        testCase.setText("second_test");
        daoFactory.saveOrUpdateTX(testCase);
        assertTrue(testCase.getId() != null);
        assertEquals(testCase, dao.findByID(testCase.getId()));


        daoFactory.removeTX(testCase);
        assertFalse(dao.loadAllEntities().contains(testCase));
    }

}
