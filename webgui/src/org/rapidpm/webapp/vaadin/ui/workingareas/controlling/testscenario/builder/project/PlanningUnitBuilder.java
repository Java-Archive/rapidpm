package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningStatus;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.List;

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
    private List<PlanningUnit> kindPlanningUnitList;
    private PlanningUnit parendPlanningUnit;
    private Benutzer responsiblePerson;
    private List<String> testCaseList;
    private PlanningStatus planingStatus;

    public PlanningUnit getPlanningUnit(){
        if(description.isEmpty()
                || estimatedStorypoints <= 0
                || kindPlanningUnitList == null
                || parendPlanningUnit == null
                || responsiblePerson == null
                || testCaseList == null
                || planingStatus == null
                )
            throw new IllegalStateException("");

        PlanningUnit planningUnit = new PlanningUnit();
        planningUnit.setDescription(description);
        planningUnit.setEstimatedStoryPoints(estimatedStorypoints);
        planningUnit.setKindPlanningUnits(kindPlanningUnitList);
        planningUnit.setParent(parendPlanningUnit);
        planningUnit.setPlanningStatus(planingStatus);
        planningUnit.setResponsiblePerson(responsiblePerson);
        planningUnit.setTestcases(testCaseList);
        return planningUnit;
    }
}
