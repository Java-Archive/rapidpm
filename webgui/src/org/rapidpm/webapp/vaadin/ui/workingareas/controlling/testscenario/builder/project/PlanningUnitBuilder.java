package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningStatus;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class PlanningUnitBuilder {
    private String description;
    private int estimatedStorypoints;
    private Set<PlanningUnit> kindPlanningUnitList;
    private PlanningUnit parendPlanningUnit;
    private Benutzer responsiblePerson;
    private List<String> testCaseList;
    private PlanningStatus planingStatus;
    private List<IssueBase> issueBaseList;

    public PlanningUnitBuilder setDescription(final String description) {
        this.description = description;
        return this;
    }

    public PlanningUnitBuilder setEstimatedStorypoints( final int estimatedStorypoints) {
        this.estimatedStorypoints = estimatedStorypoints;
        return this;
    }

    public PlanningUnitBuilder setKindPlanningUnitList(final Set<PlanningUnit> kindPlanningUnitList) {
        this.kindPlanningUnitList = kindPlanningUnitList;
        return this;
    }

    public PlanningUnitBuilder setParendPlanningUnit(final PlanningUnit parendPlanningUnit) {
        this.parendPlanningUnit = parendPlanningUnit;
        return this;
    }

    public PlanningUnitBuilder setResponsiblePerson(final Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public PlanningUnitBuilder setPlaningStatus(final PlanningStatus planingStatus) {
        this.planingStatus = planingStatus;
        return this;
    }

    public PlanningUnitBuilder setTestCaseList(final List<String> testCaseList) {
        this.testCaseList = testCaseList;
        return this;
    }

    public PlanningUnit getPlanningUnit(){
        final PlanningUnit planningUnit = new PlanningUnit();
        planningUnit.setDescription(description);
        planningUnit.setEstimatedStoryPoints(estimatedStorypoints);
        planningUnit.setKindPlanningUnits(kindPlanningUnitList);
        planningUnit.setParent(parendPlanningUnit);
        planningUnit.setPlanningStatus(planingStatus);
        planningUnit.setResponsiblePerson(responsiblePerson);
        planningUnit.setTestcases(testCaseList);
        planningUnit.setIssueBaseList(issueBaseList);
        return planningUnit;
    }

    public PlanningUnitBuilder setIssueBaseList(List<IssueBase> planningUnit1_issueBaseList) {
        this.issueBaseList = planningUnit1_issueBaseList;
        return this;
    }
}
