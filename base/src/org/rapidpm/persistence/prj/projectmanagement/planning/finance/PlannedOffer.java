package org.rapidpm.persistence.prj.projectmanagement.planning.finance;

import org.rapidpm.persistence.prj.stammdaten.person.Person;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;

import javax.persistence.*;
import java.util.Date;

/**
 * User: svenruppert
 * Date: 30.07.12
 * Time: 08:25
 * <p/>
 * Betriebswirtschaftliche Eckdaten der Projektplanung,
 * Risiko, Verhandlungsaufschlag und co.
 */
@Entity
public class PlannedOffer {

    @Id
    @TableGenerator(name = "PKGenPlannedOffer", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedOffer_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenPlannedOffer")
    private Long id;

    @Basic private Date planningDate;
    @Basic private String name;
    @Basic private float percent;
    @Basic private double eurosPerHour;
    @Basic private boolean deleteable;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Person responsiblePerson;

    @Transient private DaysHoursMinutesItem daysHoursMinutesItem;
    @Transient private float percentageAllocationWithDistributionSpread;
    @Transient private float percentageAllocationWithoutDistributionSpread;
    @Transient private double costs;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlanningDate() {
        return planningDate;
    }

    public void setPlanningDate(Date planningDate) {
        this.planningDate = planningDate;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public DaysHoursMinutesItem getDaysHoursMinutesItem() {
        return daysHoursMinutesItem;
    }

    public void setDaysHoursMinutesItem(DaysHoursMinutesItem daysHoursMinutesItem) {
        this.daysHoursMinutesItem = daysHoursMinutesItem;
    }

    public double getEurosPerHour() {
        return eurosPerHour;
    }

    public void setEurosPerHour(double eurosPerHour) {
        this.eurosPerHour = eurosPerHour;
    }

    public float getPercentageAllocationWithDistributionSpread() {
        return percentageAllocationWithDistributionSpread;
    }

    public void setPercentageAllocationWithDistributionSpread(float percentageAllocationWithDistributionSpread) {
        this.percentageAllocationWithDistributionSpread = percentageAllocationWithDistributionSpread;
    }

    public float getPercentageAllocationWithoutDistributionSpread() {
        return percentageAllocationWithoutDistributionSpread;
    }

    public void setPercentageAllocationWithoutDistributionSpread(float percentageAllocationWithoutDistributionSpread) {
        this.percentageAllocationWithoutDistributionSpread = percentageAllocationWithoutDistributionSpread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public boolean isDeleteable() {
        return deleteable;
    }

    public void setDeleteable(boolean deleteable) {
        this.deleteable = deleteable;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }
}
