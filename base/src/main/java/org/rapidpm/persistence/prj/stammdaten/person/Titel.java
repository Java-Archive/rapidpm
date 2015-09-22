/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.person;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:40
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Titel {
  private static final Logger logger = Logger.getLogger(Titel.class);

  @Id
  @TableGenerator(name = "PKGenTitel", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Titel_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTitel")
  private Long id;
  @Basic
  private String titel;
  @Basic
  private int reihenfolge;

  public String getTitel() {
    return titel;
  }

  public void setTitel(final String titel) {
    this.titel = titel;
  }

  public int getTitelNr() {
    return reihenfolge;
  }

  public void setTitelNr(final int order) {
    this.reihenfolge = order;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + titel.hashCode();
    result = 31 * result + reihenfolge;
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Titel)) {
      return false;
    }

    final Titel titel1 = (Titel) o;

    if (reihenfolge != titel1.reihenfolge) {
      return false;
    }
    if (getId() != null ? !getId().equals(titel1.getId()) : titel1.getId() != null) {
      return false;
    }
    if (!titel.equals(titel1.titel)) {
      return false;
    }

    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
