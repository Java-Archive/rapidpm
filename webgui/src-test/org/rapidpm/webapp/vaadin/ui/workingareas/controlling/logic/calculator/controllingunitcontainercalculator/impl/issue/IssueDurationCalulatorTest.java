package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue;

import org.junit.Before;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 15:57
 * To change this template use File | Settings | File Templates.
 */
public class IssueDurationCalulatorTest  {

    private static IssueBaseDemoDaten issueBaseDemoDaten;

    @Before
    public void bevoreClass(){
        PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();
        long projectId = plannedProjectDemoDaten.getPlannedProject().getId();
        issueBaseDemoDaten = new IssueBaseDemoDaten(projectId);
    }

    @Test
    public void testCalculationWithoutSubIssues(){
        IssueBase testIssue =  issueBaseDemoDaten.getIssueWithSubIssues();
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(testIssue);
        durationCalulator.calculate();

        int ownPlannedDuration =  durationCalulator.getOwnControllingUnitContainer().getPlannedAbsolutte();
        int ownActualDuration = durationCalulator.getOwnControllingUnitContainer().getActualAbsolute();
        assertEquals(ownPlannedDuration, 120);
        assertEquals(ownActualDuration, 120);

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
        IssueBase testIssue =  issueBaseDemoDaten.getIssueWithSubIssues();
        IssueDurationCalulator durationCalulator = new IssueDurationCalulator(testIssue);
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
//    public void testCalculationWithNullIssue(){
//
//    }
//
//    @Test
//    public void testCalculationWithNullEsistamtedIssueTImeUnit(){
//
//    }
//
//    @Test
//    public void testCalculationWithNullIssueTimeUnitsUsedList(){
//
//    }
//
//    @Test
//    public void testCalculationWitEmptyEstimatedIssueTimeUnit(){
//
//    }
//
//    @Test
//    public void testCalculationWithEmptyTimeUnitsUsedList(){
//
//    }

}
