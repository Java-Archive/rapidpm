package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.PlannedMeeting;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.prj.stammdaten.person.Person;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Verbindung der PlanungsEinheit mit den AufwÃ¤nden der PlanungsRessourcen.
 *
 */

@Entity
public class PlanningUnit {

    public static final String NAME = "planningUnitName";
    public static final String STORYPTS = "estimatedStoryPoints";
    public static final String COMPLEXITY = "komplexitaet";
    public static final String RESPONSIBLE = "responsiblePerson";
    public static final String TESTCASES = "testcases";
    public static final String ORDERNUMBER = "orderNumber";
    public static final String DESCPRIPTION = "description";
    public static final String PARENT = "parent";



    @Id
    @TableGenerator(name = "PKGenPlanningUnit", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlanningUnit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningUnit")
    private Long id;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedTravel> plannedTravelList;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private PlanningStatus planningStatus;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer responsiblePerson;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlannedMeeting> plannedMeetingList;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Set<PlanningUnit> kindPlanningUnits;

    @Basic
    private int orderNumber;

    @Basic
    private String planningUnitName;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private PlanningUnit parent;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<PlanningUnitElement> planningUnitElementList;


    @Basic
    private int komplexitaet;

    @Basic
    private int estimatedStoryPoints;

    @Basic
    private String description;

    @ElementCollection
    private List<String> testcases;


    //@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    //private PlanningRisk planningRisk;


//    public PlanningRisk getPlanningRisk() {
//        return planningRisk;
//    }
//
//    public void setPlanningRisk(PlanningRisk planningRisk) {
//        this.planningRisk = planningRisk;
//    }

    public int getEstimatedStoryPoints() {
        return estimatedStoryPoints;
    }

    public void setEstimatedStoryPoints(int storypoints) {
        this.estimatedStoryPoints = storypoints;
    }

    public int getKomplexitaet() {
        return komplexitaet;
    }

    public void setKomplexitaet(int komplexitaet) {
        this.komplexitaet = komplexitaet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPlanningUnitName() {
        return planningUnitName;
    }

    public void setPlanningUnitName(String planningUnitName) {
        this.planningUnitName = planningUnitName;
    }

    public List<PlanningUnitElement> getPlanningUnitElementList() {
        return planningUnitElementList;
    }

    public void setPlanningUnitElementList(List<PlanningUnitElement> planningUnitElementList) {
        this.planningUnitElementList = planningUnitElementList;
    }

    public Set<PlanningUnit> getKindPlanningUnits() {
        return kindPlanningUnits;
    }

    public void setKindPlanningUnits(Set<PlanningUnit> kindPlanningUnits) {
        this.kindPlanningUnits = kindPlanningUnits;
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

    public Benutzer getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Benutzer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public List<PlannedMeeting> getPlannedMeetingList() {
        return plannedMeetingList;
    }

    public void setPlannedMeetingList(List<PlannedMeeting> plannedMeetingList) {
        this.plannedMeetingList = plannedMeetingList;
    }

    public List<String> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<String> testcases) {
        this.testcases = testcases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlanningUnit getParent() {
        return parent;
    }

    public void setParent(PlanningUnit parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanningUnit)) return false;

        PlanningUnit that = (PlanningUnit) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PlanningUnit{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", estimatedStoryPoints=" + estimatedStoryPoints +
                ", komplexitaet=" + komplexitaet +
                ", planningUnitElementList=" + planningUnitElementList +
                ", parent=" + parent +
                ", planningUnitName='" + planningUnitName + '\'' +
                ", orderNumber=" + orderNumber +
                ", kindPlanningUnits=" + kindPlanningUnits +
                ", plannedMeetingList=" + plannedMeetingList +
                ", responsiblePerson=" + responsiblePerson +
                ", planningStatus=" + planningStatus +
                ", plannedTravelList=" + plannedTravelList +
                ", testcases=" + testcases +
                '}';
    }
}
