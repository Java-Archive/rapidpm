package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.apache.catalina.deploy.Test;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningStatus;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.PlanningUnitBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitDemoDaten {
    private final static PlanningStatus pendingPlanningStatus;
    private final static PlanningStatus inprogressPlanningStatus;
    private final static PlanningStatus resolvedPlanningStatus;
    private final static PlanningStatus shippedPlanningStatus;
    static {
        pendingPlanningStatus = new PlanningStatus();
        pendingPlanningStatus.setName("Planned");

        inprogressPlanningStatus = new PlanningStatus();
        inprogressPlanningStatus.setName("In Progress");

        resolvedPlanningStatus = new PlanningStatus();
        resolvedPlanningStatus.setName("Resolved");

        shippedPlanningStatus = new PlanningStatus();
        shippedPlanningStatus.setName("Shipped");
    }

    private PlanningUnit planningUnit_1;
    private PlanningUnit planningUnit_1_1;
    private PlanningUnit planningUnit_1_2;
    private PlanningUnit planningUnit_2;

    private Set<PlanningUnit> topLevelPlanningUnits;

    private TestCaseDemoDaten testCaseDemoDaten = new TestCaseDemoDaten();
    private BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();
    private IssueBaseDemoDaten issueBaseDemoDaten;

    public Set<PlanningUnit> getTopLevelPlanningUnits() {
        return topLevelPlanningUnits;
    }

    public PlanningUnitDemoDaten(PlannedProject plannedProject){
        issueBaseDemoDaten = new IssueBaseDemoDaten(plannedProject.getId());

        planningUnit_1 = new PlanningUnitBuilder()
                .setDescription("Planeinheit 1")
                .setEstimatedStorypoints(25)
                .setPlaningStatus(inprogressPlanningStatus)
                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setTestCaseList(testCaseDemoDaten.getTestCaseList_PlanningUnit1())
                .setIssueBaseList(issueBaseDemoDaten.getPlanningUnit1_issueBaseList())
                .getPlanningUnit();

        planningUnit_1_1 = new PlanningUnitBuilder()
                .setDescription("Planeinheit 1.1")
                .setEstimatedStorypoints(10)
                .setKindPlanningUnitList(null)
                .setPlaningStatus(resolvedPlanningStatus)
                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setTestCaseList(testCaseDemoDaten.getTestCaseList_PlanningUnit1_1())
                .setIssueBaseList(issueBaseDemoDaten.getPlanningUnit1_1_issueBaseList())
                .getPlanningUnit();

        planningUnit_1_2 = new PlanningUnitBuilder()
                .setDescription("Planeinheit 1.2")
                .setEstimatedStorypoints(10)
                .setKindPlanningUnitList(null)
                .setPlaningStatus(resolvedPlanningStatus)
                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setTestCaseList(testCaseDemoDaten.getTestCaseList_PlanningUnit1_2())
                .setIssueBaseList(issueBaseDemoDaten.getPlanningUnit1_2_issueBaseList())
                .getPlanningUnit();

        Set<PlanningUnit> planingUnit1KindPlaningUitList = new HashSet<>();
        planingUnit1KindPlaningUitList.add(planningUnit_1_1);
        planingUnit1KindPlaningUitList.add(planningUnit_1_2);
        planningUnit_1.setKindPlanningUnits(planingUnit1KindPlaningUitList);
        planningUnit_1_1.setParent(planningUnit_1);
        planningUnit_1_2.setParent(planningUnit_1);


        planningUnit_2 = new PlanningUnitBuilder()
                .setDescription("Planeinheit 2")
                .setEstimatedStorypoints(8)
                .setKindPlanningUnitList(null)
                .setPlaningStatus(inprogressPlanningStatus)
                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setTestCaseList(testCaseDemoDaten.getTestCaseList_PlanningUnit2())
                .setIssueBaseList(issueBaseDemoDaten.getPlanningUnit2_issueBaseList())
                .getPlanningUnit();

        topLevelPlanningUnits = new HashSet<>();
        topLevelPlanningUnits.add(planningUnit_1);
        topLevelPlanningUnits.add(planningUnit_2);
    }

}
