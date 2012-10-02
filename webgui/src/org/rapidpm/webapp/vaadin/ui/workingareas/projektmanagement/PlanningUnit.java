package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;


import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:50
 *
 * Verbindung der PlanungsEinheit mit den Aufwänden der PlanungsRessourcen.
 *
 */

public class PlanningUnit {

    private Long id;
    private int orderNumber;
    private String planningUnitName;
    private List<PlanningUnit> kindPlanningUnits;
    private List<PlanningUnitElement> planningUnitElementList;
    private IssueBase issueBase;

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

    public List<PlanningUnit> getKindPlanningUnits() {
        return kindPlanningUnits;
    }

    public void setKindPlanningUnits(List<PlanningUnit> kindPlanningUnits) {
        this.kindPlanningUnits = kindPlanningUnits;
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

        PlanningUnit that = (PlanningUnit) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (planningUnitName != null ? !planningUnitName.equals(that.planningUnitName) : that.planningUnitName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (planningUnitName != null ? planningUnitName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlanningUnit{" +
                "id=" + id +
                ", planningUnitName='" + planningUnitName + '\'' +
                '}';
    }
}
