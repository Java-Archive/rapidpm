package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.plannedproject;

import org.junit.Before;
import org.junit.Test;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.AbstractPlannedProjectControllingUnitContainerCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue.IssueDurationCalulator;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit.PlanningUnitDurationCalculatorTest;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 13.12.12
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectDurationCalculatorTest  extends BasePlannedProjectCalculatorTest {


    @Test
    public void testCalculationWithSubPlanningUnits(){
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator
                (plannedProjectWithSubPlanningUnitsAndSubIssues);
        assertEquals(plannedProjectWithSubPlanningUnitsAndSubIssues.getTotalProjectControllingunit(),
                plannedProjectWithBothChildreanTotalDuration);
    }

    @Test
    public void testCalculationWithEmptySubPlanningUnitList(){
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator
                (plannedProjectWithEmptySubPlanningUnitList);

        assertEquals(plannedProjectWithEmptySubPlanningUnitList.getTotalProjectControllingunit(),
                plannedProjectWithEmptySubPlanningUnitListDuration);
    }

    @Test(expected = NullPointerException.class)
    public void testNullParameter(){
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator(null);
        durationCalculator.calculate();

    }


}
