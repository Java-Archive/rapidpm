package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.controlling.ControllingUnitContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.IntegerControllingUnitContainerBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlanningUnitDemoDaten;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.12.12
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class BaseIssueCalculatorTest {
    protected static long projectId;
    protected static PlannedProject plannedProject;
    protected static IssueBase issueWithSubIssues;
    protected static BaseControllingunit issueWithSubIssuesOwnBaeControllingUnit = new BaseControllingunit();
    protected static BaseControllingunit issueWithSubIssueSubIssuesBaeControllingUnit = new BaseControllingunit();
    protected static BaseControllingunit issueWithSubIssuesTotalBaeControllingUnit = new BaseControllingunit();

    protected static IssueBase issueWithoutSubissues;
    protected static BaseControllingunit issueWithoutSubIssuesOwnBaeControllingUnit = new BaseControllingunit();
    protected static BaseControllingunit issueWithoutSubIssueSubIssuesBaeControllingUnit = new BaseControllingunit();
    protected static BaseControllingunit issueWithoutSubIssuesTotalBaeControllingUnit = new BaseControllingunit();

    protected static IssueBase issueWithEmptyTimeUnitEstimated;
    protected static IssueBase issueWithEmptyTimeUnitsUsed;
    protected static IssueBase issueWithTimeUnitsEstimatedNull;
    protected static IssueBase issueWithTimeUnitsUnisUsedNull;
    protected static BaseControllingunit issueWithTimeUnitEstimatedNullOrEmptyOwnBaseControllingUnit = new BaseControllingunit();
    protected static BaseControllingunit issueWithTimeUnitsUsedNullOrEmptyOwnBaseControllingUnit = new BaseControllingunit();

    @BeforeClass
    public static void persistDemoTestScenario(){
        DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();
        plannedProject = plannedProjectDemoDaten.getPlannedProject();
        daoFactory.saveOrUpdate(plannedProject);
        daoFactory.getPlannedProjectDAO().refresh(plannedProject);

        IssueBaseDemoDaten issueBaseDemoDaten = new IssueBaseDemoDaten(plannedProject.getId());

        issueWithSubIssues = issueBaseDemoDaten.getIssueWithSubIssues();
        issueWithSubIssues = daoFactory.getIssueBaseDAO(projectId).persist(issueWithSubIssues);

        issueWithoutSubissues = issueBaseDemoDaten.getIssueWithoutSubIssues();
        issueWithoutSubissues = daoFactory.getIssueBaseDAO(projectId).persist(issueWithoutSubissues);

        issueWithEmptyTimeUnitEstimated = issueBaseDemoDaten.getIssueWithEmptyTimeUnitEstimated();
        issueWithEmptyTimeUnitEstimated = daoFactory.getIssueBaseDAO(projectId).persist(issueWithEmptyTimeUnitEstimated);

        issueWithEmptyTimeUnitsUsed = issueBaseDemoDaten.getIssueWithEmptyTimeUnitsUsed();
        issueWithEmptyTimeUnitsUsed = daoFactory.getIssueBaseDAO(projectId).persist(issueWithEmptyTimeUnitsUsed);

        issueWithTimeUnitsEstimatedNull = issueBaseDemoDaten.getIssueWithTimeUnitsEstimatedNull();
        issueWithTimeUnitsEstimatedNull = daoFactory.getIssueBaseDAO(projectId).persist(issueWithTimeUnitsEstimatedNull);

        issueWithTimeUnitsUnisUsedNull = issueBaseDemoDaten.getIssueWithTimeUnitsUnisUsedNull();
        issueWithTimeUnitsUnisUsedNull = daoFactory.getIssueBaseDAO(projectId).persist(issueWithTimeUnitsUnisUsedNull);

        issueWithSubIssuesOwnBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(1080, 960));
        issueWithSubIssueSubIssuesBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(1080, 960));
        issueWithSubIssuesTotalBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(2160, 2160));
        issueWithoutSubIssuesOwnBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(120, 120));
        issueWithoutSubIssueSubIssuesBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(0, 0));
        issueWithoutSubIssuesTotalBaeControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(120, 120));
        issueWithTimeUnitEstimatedNullOrEmptyOwnBaseControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(120, 120));
        issueWithTimeUnitsUsedNullOrEmptyOwnBaseControllingUnit.setDuration(new IntegerControllingUnitContainerBuilder()
                .getControllingUnitContainer(120, 120));
    }

    @AfterClass
    public static void deleteDemoTestScenario(){
        DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        daoFactory.getIssueBaseDAO(projectId).delete(issueWithSubIssues);
        daoFactory.getIssueBaseDAO(projectId).delete(issueWithoutSubissues);
        daoFactory.getIssueBaseDAO(projectId).delete(issueWithEmptyTimeUnitEstimated);
        daoFactory.getIssueBaseDAO(projectId).delete(issueWithEmptyTimeUnitsUsed);
        daoFactory.getIssueBaseDAO(projectId).delete(issueWithTimeUnitsEstimatedNull);
        daoFactory.getIssueBaseDAO(projectId).delete(issueWithTimeUnitsUnisUsedNull);
        daoFactory.remove(plannedProject);

    }
}
