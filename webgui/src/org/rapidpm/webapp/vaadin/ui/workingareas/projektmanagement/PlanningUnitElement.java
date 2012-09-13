package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;


import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 07:34
 *
 * PlanungsUnitElement, die Verbindung zwischen dem
 * Planungseintrag in der Planungsgruppe und den PlanRessourcen.
 *
 */

public class PlanningUnitElement {

    private Long id;
    private int plannedDays;
    private int plannedHours;
    private int plannedMinutes;
    private OldRessourceGroup oldRessourceGroup;

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

    public OldRessourceGroup getOldRessourceGroup() {
        return oldRessourceGroup;
    }

    public void setOldRessourceGroup(OldRessourceGroup oldRessourceGroup) {
        this.oldRessourceGroup = oldRessourceGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanningUnitElement that = (PlanningUnitElement) o;

        if (plannedDays != that.plannedDays) return false;
        if (plannedHours != that.plannedHours) return false;
        if (plannedMinutes != that.plannedMinutes) return false;
        if (oldRessourceGroup != null ? !oldRessourceGroup.equals(that.oldRessourceGroup) : that.oldRessourceGroup != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + plannedDays;
        result = 31 * result + plannedHours;
        result = 31 * result + plannedMinutes;
        result = 31 * result + (oldRessourceGroup != null ? oldRessourceGroup.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlanningUnitElement{" +
                "plannedDays=" + plannedDays +
                ", plannedHours=" + plannedHours +
                ", plannedMinutes=" + plannedMinutes +
                ", oldRessourceGroup=" + oldRessourceGroup.getName() +
                '}';
    }
}
