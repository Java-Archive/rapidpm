package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.plannedproject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.12.12
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class BasePlannedProjectCalculatorTest {
    protected static PlannedProject plannedProjectWithSubPlanningUnitsAndSubIssues;
    protected static PlannedProject plannedProjectWithEmptySubPlanningUnitList;
    protected static PlannedProject plannedProjectWithNullSubPlanningUnitList;

    @BeforeClass
    public static void persistDemoTestScenario(){

    }

    @AfterClass
    public static void deleteDemoTestScenario(){
    }
}
