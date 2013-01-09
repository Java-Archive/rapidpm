package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.BaseDAOTest;


import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 15.11.12
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class IssueTestCasesDAOTest implements BaseDAOTest {

    private final IssueTestCaseDAO dao = daoFactory.getIssueTestCaseDAO();

    @Test
    public void equalsAndHashCodeTest() {
        List<IssueTestCase> testCaseList = dao.loadAllEntities();
        assertTrue(testCaseList.get(0).equals(testCaseList.get(0)));
        assertEquals(testCaseList.get(0).hashCode(), testCaseList.get(0).hashCode());

        assertFalse(testCaseList.get(0).equals(new IssueComment()));
        assertNotSame(new IssueComment().hashCode(), testCaseList.get(0).hashCode());
    }


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
