package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.projectmanagement.planning.management.PlannedMeeting;
import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravel;
import org.rapidpm.persistence.prj.textelement.TextElement;
import org.rapidpm.persistence.system.security.Benutzer;

import java.util.List;

/**
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 * <p>
 * Verbindung der PlanungsEinheit mit den AufwÃ¤nden der PlanungsRessourcen.
 */

public class PlanningUnit {

  public static final String NAME = "planningUnitName";
  public static final String STORYPTS = "estimatedStoryPoints";
  public static final String COMPLEXITY = "komplexitaet";
  public static final String RESPONSIBLE = "responsiblePerson";
  public static final String TESTCASES = "testcases";
  public static final String DESCRIPTIONS = "descriptions";
  public static final String ORDERNUMBER = "orderNumber";
  public static final String PARENT = "parent";

  private String id;
  private int orderNumber;
  private String planningUnitName;
  private int komplexitaet;
  private int estimatedStoryPoints;

  private transient List<PlannedTravel> plannedTravelList;
  private transient PlanningStatus planningStatus;
  private transient Benutzer responsiblePerson;
  private transient List<PlannedMeeting> plannedMeetingList;
  private transient List<PlanningUnit> kindPlanningUnits;
  private transient PlanningUnit parent;
  private transient List<PlanningUnitElement> planningUnitElementList;
  private transient List<TextElement> testcases;
  private transient List<TextElement> descriptions;

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
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

  public List<PlanningUnit> getKindPlanningUnits() {
    return kindPlanningUnits;
  }

  public void setKindPlanningUnits(List<PlanningUnit> kindPlanningUnits) {
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

  public List<TextElement> getTestcases() {
    return testcases;
  }

  public void setTestcases(List<TextElement> testcases) {
    this.testcases = testcases;
  }

  public List<TextElement> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<TextElement> descriptions) {
    this.descriptions = descriptions;
  }

  public PlanningUnit getParent() {
    return parent;
  }

  public void setParent(PlanningUnit parent) {
    this.parent = parent;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
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
  public String toString() {
    return planningUnitName;
  }
}
