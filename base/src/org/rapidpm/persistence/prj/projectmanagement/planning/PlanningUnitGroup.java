package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.PlannedMeeting;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.prj.stammdaten.person.Person;

import javax.persistence.*;
import java.util.List;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Planungseinheit, eine Gruppe von Elemeneten
 * die zu einem funktionalen Block zusammengefasst werden.
 *
 * z.B.
 * PlanungsEinheit = Schulung
 * PlanungsEinheitElement
 *  - Vorbereitungen
 *  - Durchführung
 *  - Nachbereitung
 *
 *  verbunden werden müssen noch die Reisen.
 *
 */
@Entity
public class PlanningUnitGroup {

    @Id
    @TableGenerator(name = "PKGenPlanningUnitGroup", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlanningUnitGroup_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningUnitGroup")
    private Long id;


    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedTravel> plannedTravelList;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private PlanningStatus planningStatus;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Person responsiblePerson;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private IssueBase issueBase;

    @Basic
    private String planningUnitGroupName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlanningUnit> planningUnitList;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedMeeting> plannedMeetingList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlannedTravel> getPlannedTravelList() {
        return plannedTravelList;
    }

    public void setPlannedTravelList(List<PlannedTravel> plannedTravelList) {
        this.plannedTravelList = plannedTravelList;
    }

    public PlanningStatus getPlanningStatus() {
        return planningStatus;
    }

    public void setPlanningStatus(PlanningStatus planningStatus) {
        this.planningStatus = planningStatus;
    }

    public Person getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Person responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getPlanningUnitGroupName() {
        return planningUnitGroupName;
    }

    public void setPlanningUnitGroupName(String planningUnitName) {
        this.planningUnitGroupName = planningUnitName;
    }

    public List<PlanningUnit> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<PlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<PlannedMeeting> getPlannedMeetingList() {
        return plannedMeetingList;
    }

    public void setPlannedMeetingList(List<PlannedMeeting> plannedMeetingList) {
        this.plannedMeetingList = plannedMeetingList;
    }

    public IssueBase getIssueBase() {
        return issueBase;
    }

    public void setIssueBase(IssueBase issueBase) {
        this.issueBase = issueBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanningUnitGroup that = (PlanningUnitGroup) o;

        if (!id.equals(that.id)) return false;
        if (!planningUnitGroupName.equals(that.planningUnitGroupName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + planningUnitGroupName.hashCode();
        return result;
    }
}
