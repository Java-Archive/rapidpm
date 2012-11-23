package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
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
public class PlannedProjectBuilder {
    private Benutzer creator;
    private Benutzer responsiblePerson;
    private List<PlanningUnit> planningUnitList;
    private String projectName;

    public PlannedProjectBuilder setCreator(final Benutzer creator) {
        this.creator = creator;
        return this;
    }

    public PlannedProjectBuilder setResponsiblePerson(final Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public PlannedProjectBuilder setPlanningUnitList(final List<PlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
        return this;
    }

    public PlannedProjectBuilder setProjectName(final String projectName) {
        this.projectName = projectName;
        return this;
    }

    public PlannedProject getPlannedProject(){
        if(creator == null || responsiblePerson == null || planningUnitList == null || projectName.isEmpty())
            throw new IllegalStateException("PlannedProjectBuilder: Es wurden nicht alle notwendigen Daten angegeben.");

        final PlannedProject plannedProject = new PlannedProject();
        plannedProject.setCreator(creator);
        plannedProject.setResponsiblePerson(responsiblePerson);
        plannedProject.setPlanningUnits(planningUnitList);
        plannedProject.setProjektName(projectName);
        return plannedProject;
    }
}
