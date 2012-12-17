package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.PlannedProjectBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectDemoDaten {
    private PlannedProject plannedProject;
    private BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();
    private PlanningUnitDemoDaten planningUnitDemoDaten;
    private PlannedProject plannedPorjectWithoutSubPlanningUnits;
    private PlannedProject plannedProjectWithSubPlanningUnitsNull;



    public PlannedProject getPlannedProject() {
        if(planningUnitDemoDaten == null)
            throw new NullPointerException("PlannedProjectDemoDaten: PlanningUnitDemoDaten is null.");
        plannedProject.setCreator(benutzerDemoDaten.getUrsulaBeckerBenutzer());
        plannedProject.setPlanningUnits(planningUnitDemoDaten.getTopLevelPlanningUnits());
        plannedProject.setProjektName("Testscenario Projecteinheit");
        plannedProject.setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer());
        return plannedProject;
    }

    public void setPlanningUnitDemoDaten(PlanningUnitDemoDaten planningUnitDemoDaten) {
        this.planningUnitDemoDaten = planningUnitDemoDaten;
    }

    public PlannedProject getPlannedPorjectWithoutSubPlanningUnits() {
        return plannedPorjectWithoutSubPlanningUnits;
    }

    public PlannedProject getPlannedProjectWithSubPlanningUnitsNull() {

        return plannedProjectWithSubPlanningUnitsNull;
    }

    public PlannedProjectDemoDaten(){
//        planningUnitDemoDaten = new PlanningUnitDemoDaten(plannedProject);
//        plannedProject = new PlannedProjectBuilder()
//                .setCreator(benutzerDemoDaten.getUrsulaBeckerBenutzer())
//                .setPlanningUnitList(planningUnitDemoDaten.getTopLevelPlanningUnits())
//                .setProjectName("Testscenario Projecteinheit")
//                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
//                .getPlannedProject();
        plannedProject = new PlannedProject();
    }

}
