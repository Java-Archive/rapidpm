package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import javax.persistence.*;
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

@Entity
public class PlanningUnit {

    @Id
    @TableGenerator(name = "PKGenPlanningUnit", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlanningUnit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningUnit")
    private Long id;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlanningUnit> kindPlanningUnits;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private IssueBase issueBase;

    @Basic private int orderNumber;

    @Basic
    private String planningUnitName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<PlanningUnitElement> planningUnitElementList;

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

        if (!id.equals(that.id)) return false;
        if (!planningUnitName.equals(that.planningUnitName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + planningUnitName.hashCode();
        return result;
    }
}
