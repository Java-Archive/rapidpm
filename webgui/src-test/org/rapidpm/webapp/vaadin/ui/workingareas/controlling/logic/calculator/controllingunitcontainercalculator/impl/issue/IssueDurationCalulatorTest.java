package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueDurationCalulatorTest extends BaseIssueCalculatorTest {


    @Test
    public void testCalculationWithoutSubIssues(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithoutSubissues);
        durationCalulator.calculate();

        assertEquals(issueWithoutSubissues.getTotalOwnCotntrollingUnit(), issueWithoutSubIssuesOwnBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalSubIssuesBaseControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
    }

    @Test
    public void testCalculationWithSubIssues(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithSubIssues);
        durationCalulator.calculate();

        assertEquals(issueWithSubIssues.getTotalOwnCotntrollingUnit(), issueWithSubIssues);
        assertEquals(issueWithSubIssues.getTotalSubIssuesBaseControllingUnit(), issueWithSubIssues);
        assertEquals(issueWithSubIssues.getTotalControllingUnit(), issueWithSubIssues);
    }

    @Test
    public void testCalculationWithNullEsistamtedIssueTImeUnit(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithTimeUnitsEstimatedNull);
        durationCalulator.calculate();

        assertEquals(issueWithoutSubissues.getTotalOwnCotntrollingUnit(), issueWithoutSubIssuesOwnBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalSubIssuesBaseControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
    }

    @Test
    public void testCalculationWithNullIssueTimeUnitsUsedList(){

        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithTimeUnitsUnisUsedNull);
        durationCalulator.calculate();

        assertEquals(issueWithoutSubissues.getTotalOwnCotntrollingUnit(), issueWithoutSubIssuesOwnBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalSubIssuesBaseControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
    }

    @Test
    public void testCalculationWitEmptyEstimatedIssueTimeUnit(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithEmptyTimeUnitEstimated);
        durationCalulator.calculate();

        assertEquals(issueWithoutSubissues.getTotalOwnCotntrollingUnit(), issueWithoutSubIssuesOwnBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalSubIssuesBaseControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
    }

    @Test
    public void testCalculationWithEmptyTimeUnitsUsedList(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithEmptyTimeUnitsUsed);
        durationCalulator.calculate();

        assertEquals(issueWithoutSubissues.getTotalOwnCotntrollingUnit(), issueWithoutSubIssuesOwnBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalSubIssuesBaseControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
        assertEquals(issueWithoutSubissues.getTotalControllingUnit(), issueWithoutSubIssuesTotalBaeControllingUnit);
    }

}
