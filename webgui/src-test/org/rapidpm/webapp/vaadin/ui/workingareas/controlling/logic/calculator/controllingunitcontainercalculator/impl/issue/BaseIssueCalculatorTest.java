package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.logic.calculator.controllingunitcontainercalculator.impl.issue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.IssueBaseDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlannedProjectDemoDaten;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project.PlanningUnitDemoDaten;

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
    protected static BaseControllingunit issueWithSubIssuesOwnBaeControllingUnit;
    protected static BaseControllingunit issueWithSubIssueSubIssuesBaeControllingUnit;
    protected static BaseControllingunit issueWithSubIssuesTotalBaeControllingUnit;

    protected static IssueBase issueWithoutSubissues;
    protected static BaseControllingunit issueWithoutSubIssuesOwnBaeControllingUnit;
    protected static BaseControllingunit issueWithoutSubIssueSubIssuesBaeControllingUnit;
    protected static BaseControllingunit issueWithoutSubIssuesTotalBaeControllingUnit;

    protected static IssueBase issueWithEmptyTimeUnitEstimated;
    protected static IssueBase issueWithEmptyTimeUnitsUsed;
    protected static IssueBase issueWithTimeUnitsEstimatedNull;
    protected static IssueBase issueWithTimeUnitsUnisUsedNull;
    protected static BaseControllingunit issueWithTimeUnitEstimatedNullOrEmptyOwnBaseControllingUnit;
    protected static BaseControllingunit issueWithTimeUnitsUsedNullOrEmptyOwnBaseControllingUnit;

    @BeforeClass
    public static void persistDemoTestScenario(){
        DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        PlannedProjectDemoDaten plannedProjectDemoDaten = new PlannedProjectDemoDaten();
        daoFactory.saveOrUpdate(plannedProject);
        daoFactory.getPlannedProjectDAO().refresh(plannedProject);
        PlanningUnitDemoDaten planningUnitDemoDaten = new PlanningUnitDemoDaten(plannedProject);



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

        System.out.println("ProjectID = " + plannedProject.getId());
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
