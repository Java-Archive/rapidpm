package org.rapidpm.persistence.prj.projectmanagement.planning;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 28.04.11
 * Time: 21:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class PlannedProjectName {
  private static final Logger logger = Logger.getLogger(PlannedProjectName.class);

  @Id
  @TableGenerator(name = "PKGenPlannedProjectName", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "PlannedProjectName_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenPlannedProjectName")
  private Long id;

  @Basic
  private String namepart;

  @Basic
  private int ordernr;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("PlannedProjectName");
    sb.append("{id=").append(id);
    sb.append(", namepart='").append(namepart).append('\'');
    sb.append(", ordernr=").append(ordernr);
    sb.append('}');
    return sb.toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getNamepart() {
    return namepart;
  }

  public void setNamepart(final String namepart) {
    this.namepart = namepart;
  }

  public int getOrdernr() {
    return ordernr;
  }

  public void setOrdernr(final int ordernr) {
    this.ordernr = ordernr;
  }
}
