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

        int ownPlannedDuration =  durationCalulator.getOwnControllingUnitContainer().getPlannedAbsolutte();
        int ownActualDuration = durationCalulator.getOwnControllingUnitContainer().getActualAbsolute();
        assertEquals(ownPlannedDuration, 120);
        assertEquals(ownActualDuration, 120);
        System.out.println(ownPlannedDuration + " | " + ownActualDuration);

        int subIssuesPlannedDuration = durationCalulator.getTotalSubIssueControllingContainer().getPlannedAbsolutte();
        int subIssuesActualDuration = durationCalulator.getTotalSubIssueControllingContainer().getActualAbsolute();
        assertEquals(subIssuesPlannedDuration, 0);
        assertEquals(subIssuesActualDuration, 0);

        int totalPlannedDuration = durationCalulator.getTotalControllingUnitContainer().getPlannedAbsolutte();
        int totalActualDuration = durationCalulator.getTotalControllingUnitContainer().getActualAbsolute();
        assertEquals(totalPlannedDuration, 120);
        assertEquals(totalActualDuration, 120);
    }

    @Test
    public void testCalculationWithSubIssues(){
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithSubIssues);
        durationCalulator.calculate();

        int ownPlannedDuration =  durationCalulator.getOwnControllingUnitContainer().getPlannedAbsolutte();
        int ownActualDuration = durationCalulator.getOwnControllingUnitContainer().getActualAbsolute();
        assertEquals(ownPlannedDuration, 1080);
        assertEquals(ownActualDuration, 960);

        int subIssuesPlannedDuration = durationCalulator.getTotalSubIssueControllingContainer().getPlannedAbsolutte();
        int subIssuesActualDuration = durationCalulator.getTotalSubIssueControllingContainer().getActualAbsolute();
        assertEquals(subIssuesPlannedDuration, 1089);
        assertEquals(subIssuesActualDuration, 960);

        int totalPlannedDuration = durationCalulator.getTotalControllingUnitContainer().getPlannedAbsolutte();
        int totalActualDuration = durationCalulator.getTotalControllingUnitContainer().getActualAbsolute();
        assertEquals(totalPlannedDuration, 2160);
        assertEquals(totalActualDuration, 2160);
    }

//    @Test
//    public void testCalculationWithNullEsistamtedIssueTImeUnit(){
//        IssueBase issueWithNullEstimatesTimeUnit = issueBaseDemoDaten.getIssueWithTimeUnitsEstimatedNull();
//        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithNullEstimatesTimeUnit);
//        durationCalulator.calculate();
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalControllingUnit().getDuration());
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalSubIssuesBaseControllingUnit().getDuration());
//    }
//
//    @Test
//    public void testCalculationWithNullIssueTimeUnitsUsedList(){
//        IssueBase issueWithNullEstimatesTimeUnit = issueBaseDemoDaten.getIssueWithTimeUnitsUnisUsedNull();
//        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithNullEstimatesTimeUnit);
//        durationCalulator.calculate();
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalControllingUnit().getDuration());
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalSubIssuesBaseControllingUnit().getDuration());
//    }
//
//    @Test
//    public void testCalculationWitEmptyEstimatedIssueTimeUnit(){
//        IssueBase issueWithNullEstimatesTimeUnit = issueBaseDemoDaten.getIssueWithEmptyTimeUnitEstimated();
//        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithNullEstimatesTimeUnit);
//        durationCalulator.calculate();
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalControllingUnit().getDuration());
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalSubIssuesBaseControllingUnit().getDuration());
//    }
//
//    @Test
//    public void testCalculationWithEmptyTimeUnitsUsedList(){
//        IssueBase issueWithNullEstimatesTimeUnit = issueBaseDemoDaten.getIssueWithEmptyTimeUnitsUsed();
//        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(issueWithNullEstimatesTimeUnit);
//        durationCalulator.calculate();
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalControllingUnit().getDuration());
//        assertNotNull(issueWithNullEstimatesTimeUnit.getTotalSubIssuesBaseControllingUnit().getDuration());
//    }

}
