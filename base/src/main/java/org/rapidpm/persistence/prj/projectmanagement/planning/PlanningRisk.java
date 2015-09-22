package org.rapidpm.persistence.prj.projectmanagement.planning;

import javax.persistence.*;
import java.util.List;

/**
 * Copyright by Sven Ruppert // chef@sven-ruppert.de
 * <p>
 * User: svenruppert
 * Date: 12.10.12
 * Time: 23:00
 * <p>
 * Version:
 */
@Entity
public class PlanningRisk {

  @Id
  @TableGenerator(name = "PKGenPlanningRisk", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "PlanningRisk_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningRisk")
  private Long id;


  @Basic
  private String comment;

  @Basic
  private int eintrittswahrscheinlichkeit;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<PlanningUnitElement> planningUnitElementList;

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + eintrittswahrscheinlichkeit;
    result = 31 * result + (planningUnitElementList != null ? planningUnitElementList.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PlanningRisk)) return false;

    PlanningRisk that = (PlanningRisk) o;

    if (eintrittswahrscheinlichkeit != that.eintrittswahrscheinlichkeit) return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (planningUnitElementList != null ? !planningUnitElementList.equals(that.planningUnitElementList) : that.planningUnitElementList != null)
      return false;

    return true;
  }

  @Override
  public String toString() {
    return "PlanningRisk{" +
        "id=" + id +
        ", eintrittswahrscheinlichkeit=" + eintrittswahrscheinlichkeit +
        ", planningUnitElementList=" + planningUnitElementList +
        '}';
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getEintrittswahrscheinlichkeit() {
    return eintrittswahrscheinlichkeit;
  }

  public void setEintrittswahrscheinlichkeit(int eintrittswahrscheinlichkeit) {
    this.eintrittswahrscheinlichkeit = eintrittswahrscheinlichkeit;
  }

  public List<PlanningUnitElement> getPlanningUnitElementList() {
    return planningUnitElementList;
  }

  public void setPlanningUnitElementList(List<PlanningUnitElement> planningUnitElementList) {
    this.planningUnitElementList = planningUnitElementList;
  }
}
