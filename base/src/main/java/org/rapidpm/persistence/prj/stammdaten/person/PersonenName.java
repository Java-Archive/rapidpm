/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.person;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:38
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity()
public class PersonenName {
  private static final Logger logger = Logger.getLogger(PersonenName.class);

  @Id
  @TableGenerator(name = "PKGenPersonenName", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "PersonenName_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPersonenName")
  private Long id;
  @Basic
  private String name;
  @Basic
  private int reihenfolge;
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private NamensKlassifizierung namensKlassifizierung;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getReihenfolge() {
    return reihenfolge;
  }

  public void setReihenfolge(final int reihenfolge) {
    this.reihenfolge = reihenfolge;
  }

  public NamensKlassifizierung getNamensKlassifizierung() {
    return namensKlassifizierung;
  }

  public void setNamensKlassifizierung(final NamensKlassifizierung namensKlassifizierung) {
    this.namensKlassifizierung = namensKlassifizierung;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + name.hashCode();
    result = 31 * result + reihenfolge;
    result = 31 * result + namensKlassifizierung.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PersonenName)) {
      return false;
    }

    final PersonenName name1 = (PersonenName) o;

    if (reihenfolge != name1.reihenfolge) {
      return false;
    }
    if (getId() != null ? !getId().equals(name1.getId()) : name1.getId() != null) {
      return false;
    }
    if (!name.equals(name1.name)) {
      return false;
    }
    if (!namensKlassifizierung.equals(name1.namensKlassifizierung)) {
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
