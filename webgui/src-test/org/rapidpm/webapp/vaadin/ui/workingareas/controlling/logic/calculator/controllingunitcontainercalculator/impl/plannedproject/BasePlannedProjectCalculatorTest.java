package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.plannedproject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.BaseControllingUnitBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IntegerControllingUnitContainerBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.12.12
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class BasePlannedProjectCalculatorTest {

    protected static PlannedProject plannedProjectWithSubPlanningUnitsAndSubIssues;
    protected static BaseControllingunit plannedProjectWithBothChildreanTotalDuration = new BaseControllingunit();

    protected static PlannedProject plannedProjectWithEmptySubPlanningUnitList;
    protected static BaseControllingunit plannedProjectWithEmptySubPlanningUnitListDuration = new BaseControllingunit
            ();

    @BeforeClass
    public static void persistDemoTestScenario(){
        PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();
        plannedProjectWithSubPlanningUnitsAndSubIssues = plannedProjectDemoDaten.getPlannedProject();
        plannedProjectWithEmptySubPlanningUnitList = plannedProjectDemoDaten.getPlannedPorjectWithoutSubPlanningUnits();

        plannedProjectWithBothChildreanTotalDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(3000, 2730 ));

        plannedProjectWithEmptySubPlanningUnitListDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));
    }

    @AfterClass
    public static void deleteDemoTestScenario(){
        // noting to delete atm
    }
}
