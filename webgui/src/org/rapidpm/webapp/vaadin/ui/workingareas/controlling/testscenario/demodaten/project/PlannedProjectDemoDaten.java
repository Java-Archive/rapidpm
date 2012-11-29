package org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.project;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.builder.project.PlannedProjectBuilder;
import org.rapidpm.webapp.vaadin.ui.workingareas.controlling.testscenario.demodaten.stab.BenutzerDemoDaten;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 20.11.12
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class PlannedProjectDemoDaten {
    private static long projectIdCounter=0;
    private PlannedProject plannedProject;
    private BenutzerDemoDaten benutzerDemoDaten = new BenutzerDemoDaten();
    private PlanningUnitDemoDaten planningUnitDemoDaten;


    public PlannedProject getPlannedProject() {
        return plannedProject;
    }

    public PlannedProjectDemoDaten(){
        projectIdCounter++;
        planningUnitDemoDaten = new PlanningUnitDemoDaten(projectIdCounter);
        plannedProject = new PlannedProjectBuilder()
                 .setId(projectIdCounter)
                .setCreator(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .setPlanningUnitList(planningUnitDemoDaten.getTopLevelPlanningUnits())
                .setProjectName("Testscenario Projecteinheit")
                .setResponsiblePerson(benutzerDemoDaten.getUrsulaBeckerBenutzer())
                .getPlannedProject();
    }
}
