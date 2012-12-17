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

    private final PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();

    @Test
    public void testCalculationWithSubPlanningUnits(){
        PlannedProject plannedProject = plannedProjectDemoDaten.getPlannedProject();
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator
                (plannedProject);

        int plannedProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getPlannedAbsolutte();
        int actualProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getActualAbsolute();
        int remainingProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getRemainingAbsolute();
        double usedRelative = plannedProject.getTotalProjectControllingunit().getDuration().getUsedRelative();
        double remainingRelative = plannedProject.getTotalProjectControllingunit().getDuration().getRemainingRelative
                ();

        assertEquals(plannedProjectDuration, 3000);
        assertEquals(actualProjectDuration, 2730);
        assertEquals(remainingProjectDuration,270);
        assertEquals(usedRelative, 91.0);
        assertEquals(remainingRelative, 9.0);
    }

    @Test
    public void testCalculationWithEmptySubPlanningUnitList(){
        PlannedProject plannedProject = plannedProjectDemoDaten.getPlannedPorjectWithoutSubPlanningUnits();
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator
                (plannedProject);

        int plannedProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getPlannedAbsolutte();
        int actualProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getActualAbsolute();
        int remainingProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getRemainingAbsolute();
        double usedRelative = plannedProject.getTotalProjectControllingunit().getDuration().getUsedRelative();
        double remainingRelative = plannedProject.getTotalProjectControllingunit().getDuration().getRemainingRelative
                ();

        assertEquals(plannedProjectDuration, 0);
        assertEquals(actualProjectDuration, 0);
        assertEquals(remainingProjectDuration,0);
        assertEquals(usedRelative, 0.0);
        assertEquals(remainingRelative, 0.0);
    }

    @Test
    public void testCalculationWithSubPlanningUnitsListIsNull(){
        PlannedProject plannedProject = plannedProjectDemoDaten.getPlannedProjectWithSubPlanningUnitsNull();
        PlannedProjectDurationCalculator durationCalculator = new PlannedProjectDurationCalculator
                (plannedProject);

        int plannedProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getPlannedAbsolutte();
        int actualProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getActualAbsolute();
        int remainingProjectDuration = plannedProject.getTotalProjectControllingunit().getDuration()
                .getRemainingAbsolute();
        double usedRelative = plannedProject.getTotalProjectControllingunit().getDuration().getUsedRelative();
        double remainingRelative = plannedProject.getTotalProjectControllingunit().getDuration().getRemainingRelative
                ();

        assertEquals(plannedProjectDuration, 0);
        assertEquals(actualProjectDuration, 0);
        assertEquals(remainingProjectDuration,0);
        assertEquals(usedRelative, 0.0);
        assertEquals(remainingRelative, 0.0);
    }


}
