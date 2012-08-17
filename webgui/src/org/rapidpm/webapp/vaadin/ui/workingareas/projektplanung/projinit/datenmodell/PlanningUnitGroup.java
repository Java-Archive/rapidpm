package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell;

import org.rapidpm.orm.prj.projectmanagement.planning.PlanningStatus;
import org.rapidpm.orm.prj.projectmanagement.planning.management.PlannedMeeting;
import org.rapidpm.orm.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.orm.prj.stammdaten.person.Person;

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

public class PlanningUnitGroup {

    private Long id;
    private List<PlannedTravel> plannedTravelList;
    private PlanningStatus planningStatus;
    private Person responsiblePerson;
    private String planningUnitName;
    private List<PlanningUnit> planningUnitList;
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

    public String getPlanningUnitName() {
        return planningUnitName;
    }

    public void setPlanningUnitName(String planningUnitName) {
        this.planningUnitName = planningUnitName;
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
}
