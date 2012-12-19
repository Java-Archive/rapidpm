package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.planningunit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IntegerControllingUnitContainerBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlanningUnitDemoDaten;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.12.12
 * Time: 14:08
 * To change this template use File | Settings | File Templates.
 */
public class BasePlanningUnitCalculatorTest {

    protected static PlannedProject plannedProject;

    protected static PlanningUnit planningUnitWithSubPlanningUnitsAndSubIssues;
    protected static BaseControllingunit plannedProjectWithBothChildreanOwnDuration = new BaseControllingunit();
    protected static BaseControllingunit plannedProjectWithBothChildreanSubPlanningUnitsDuration = new BaseControllingunit();
    protected static BaseControllingunit plannedProjectWithBothChildreanTotalDuration = new BaseControllingunit();

    protected static PlanningUnit planningUnitWithoutSubPlanningUnitsAndoutSubIssues;
    protected static BaseControllingunit plannedProjectWithoutBothChildreanOwnDuration = new BaseControllingunit();
    protected static BaseControllingunit plannedProjectWithoutBothChildreanSubPlanningUnitsDuration = new BaseControllingunit();
    protected static BaseControllingunit plannedProjectWithoutBothChildreanTotalDuration = new BaseControllingunit();

    protected static PlanningUnit planningUnitWithoutSubPlanningUnitWithSubIssues;
    protected static BaseControllingunit planningUnitWithoutSubPlanningUnitWithSubIssuesTotalDuration = new BaseControllingunit();

    protected static PlanningUnit planningUnitWithSubPlanningUnitsWithoztSubIssues;
    protected static BaseControllingunit
            planningUnitWithSubPlanningUnitsWithoztSubIssuesSubPlanningUnitsDuration = new BaseControllingunit();


    @BeforeClass
    public static void persistDemoTestScenario(){
        DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();
        plannedProject = plannedProjectDemoDaten.getPlannedProject();
        daoFactory.saveOrUpdate(plannedProject);
        daoFactory.getPlannedProjectDAO().refresh(plannedProject);

        PlanningUnitDemoDaten planningUnitDemoDaten = new PlanningUnitDemoDaten(plannedProject);
        planningUnitWithSubPlanningUnitsAndSubIssues = planningUnitDemoDaten.getPlanningUnit_1();
        planningUnitWithoutSubPlanningUnitsAndoutSubIssues = planningUnitDemoDaten.getPlanningUnitWithoutSubPlanningUnitsWithoutSubIssues();
        planningUnitWithoutSubPlanningUnitWithSubIssues = planningUnitDemoDaten.getPlanningUnit_1_2();
        planningUnitWithSubPlanningUnitsWithoztSubIssues = planningUnitDemoDaten
                .getPlanningUnitWithSubPlanningUnitWithoutSubIssues();

        plannedProjectWithBothChildreanOwnDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(120, 120));
        plannedProjectWithBothChildreanSubPlanningUnitsDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(2520 , 2280));
        plannedProjectWithBothChildreanTotalDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(2640 , 2400));

        plannedProjectWithoutBothChildreanOwnDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));
        plannedProjectWithoutBothChildreanSubPlanningUnitsDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));
        plannedProjectWithoutBothChildreanTotalDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));

        planningUnitWithoutSubPlanningUnitWithSubIssuesTotalDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(360, 120));
        planningUnitWithSubPlanningUnitsWithoztSubIssuesSubPlanningUnitsDuration.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));
    }

    @AfterClass
    public static void deleteDemoTestScenario(){
        // nothing to delete here atm
    }
}
