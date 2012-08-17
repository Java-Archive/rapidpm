package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;



/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 07:34
 *
 * PlanungsUnitElement, die verbindung zwischen dem
 * Planungseintrag in der Planungsgruppe und den PlanRessourcen.
 *
 */

public class PlanningUnitElement {

    private Long id;
    private int plannedDays;
    private int plannedHours;
    private int plannedMinutes;
    private RessourceGroup ressourceGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlannedDays() {
        return plannedDays;
    }

    public void setPlannedDays(int plannedDays) {
        this.plannedDays = plannedDays;
    }

    public int getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(int plannedHours) {
        this.plannedHours = plannedHours;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public void setPlannedMinutes(int plannedMinutes) {
        this.plannedMinutes = plannedMinutes;
    }

    public RessourceGroup getRessourceGroup() {
        return ressourceGroup;
    }

    public void setRessourceGroup(RessourceGroup ressourceGroup) {
        this.ressourceGroup = ressourceGroup;
    }
}
