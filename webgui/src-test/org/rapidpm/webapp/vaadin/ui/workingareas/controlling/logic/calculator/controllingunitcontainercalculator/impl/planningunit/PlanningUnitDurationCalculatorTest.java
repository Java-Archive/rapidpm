package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractPlanningUnitControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue.IssueDurationCalulatorTest;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlanningUnitDemoDaten;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 03.12.12
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitDurationCalculatorTest  extends BasePlanningUnitCalculatorTest {

    @Test(expected = NullPointerException.class)
    public void testNullParameter(){
        PlanningUnitDurationCalculator durationCalculator = new PlanningUnitDurationCalculator(null);
        durationCalculator.calculate();
    }

    @Test
    public void testCalculationWithBothSubTypes(){
        PlanningUnitDurationCalculator durationCalculator = new PlanningUnitDurationCalculator(planningUnitWithSubPlanningUnitsAndSubIssues);
        durationCalculator.calculate();
        assertEquals(planningUnitWithSubPlanningUnitsAndSubIssues.getTotalOwnIssuesCotntrollingUnit(),
                plannedProjectWithBothChildreanOwnDuration);
        assertEquals(planningUnitWithSubPlanningUnitsAndSubIssues.getTotalSubPlaningUnitsControllingUnit(),
                plannedProjectWithBothChildreanSubPlanningUnitsDuration);
        assertEquals(planningUnitWithSubPlanningUnitsAndSubIssues.getTotalControllingUnit(),
                plannedProjectWithBothChildreanTotalDuration);
    }

    @Test
    public void testCalculationWithoutAnyChildrean(){
        PlanningUnitDurationCalculator durationCalculator = new PlanningUnitDurationCalculator(planningUnitWithoutSubPlanningUnitsAndoutSubIssues);
        durationCalculator.calculate();
        assertEquals(planningUnitWithoutSubPlanningUnitsAndoutSubIssues.getTotalOwnIssuesCotntrollingUnit(),
                plannedProjectWithoutBothChildreanOwnDuration);
        assertEquals(planningUnitWithoutSubPlanningUnitsAndoutSubIssues.getTotalSubPlaningUnitsControllingUnit(),
                plannedProjectWithoutBothChildreanSubPlanningUnitsDuration);
        assertEquals(planningUnitWithoutSubPlanningUnitsAndoutSubIssues.getTotalControllingUnit(),
                plannedProjectWithoutBothChildreanTotalDuration);
    }

    @Test
    public void testCalculationWithNoSubPlanningUnitsWithSubIssues(){
        PlanningUnitDurationCalculator durationCalculator = new PlanningUnitDurationCalculator(planningUnitWithoutSubPlanningUnitWithSubIssues);
        durationCalculator.calculate();
        assertEquals(planningUnitWithoutSubPlanningUnitWithSubIssues, planningUnitWithoutSubPlanningUnitWithSubIssuesTotalDuration);
    }

    @Test
    public void testCalculationWithoutSubIssuesWithSubPlanningUnits(){
        PlanningUnitDurationCalculator durationCalculator = new PlanningUnitDurationCalculator
                (planningUnitWithSubPlanningUnitsWithoztSubIssues);
        durationCalculator.calculate();
        assertEquals(planningUnitWithSubPlanningUnitsWithoztSubIssues.getTotalSubPlaningUnitsControllingUnit(),
                planningUnitWithSubPlanningUnitsWithoztSubIssuesSubPlanningUnitsDuration);
    }




}
