package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

import javax.persistence.*;

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

    private String id;
    private int plannedMinutes;

    private transient RessourceGroup ressourceGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanningUnitElement that = (PlanningUnitElement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "PlanningUnitElement{" +
                "id=" + id +
                ", plannedMinutes=" + plannedMinutes +
                ", ressourceGroup=" + ressourceGroup +
                '}';
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
