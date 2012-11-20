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
public class ProjectBuilder {
    private Benutzer creator;
    private Benutzer responsiblePerson;
    private List<PlanningUnit> planningUnitList;
    private String projectName;

    public ProjectBuilder setCreator(Benutzer creator) {
        this.creator = creator;
        return this;
    }

    public ProjectBuilder setResponsiblePerson(Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public ProjectBuilder setPlanningUnitList(List<PlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
        return this;
    }

    public ProjectBuilder setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public PlannedProject getPlannedProject(){
        if(creator == null || responsiblePerson == null || planningUnitList == null || projectName.isEmpty())
            throw new IllegalStateException("ProjectBuilder: Es wurden nicht alle notwendigen Daten angegeben.");
        PlannedProject plannedProject = new PlannedProject();
        plannedProject.setCreator(creator);
        plannedProject.setResponsiblePerson(responsiblePerson);
        plannedProject.setPlanningUnits(planningUnitList);
        plannedProject.setProjektName(projectName);
        return plannedProject;
    }
}
