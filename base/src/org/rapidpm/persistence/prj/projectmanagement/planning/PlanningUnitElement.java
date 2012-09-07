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
@Entity
public class PlanningUnitElement {

    @Id
    @TableGenerator(name = "PKGenPlanningUnitElement", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlanningUnitElement_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningUnitElement")
    private Long id;

    @Basic private int plannedDays;
    @Basic private int plannedHours;
    @Basic private int plannedMinutes;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
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