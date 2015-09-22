/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.web;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:59
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class WebDomainKlassifizierung {
  private static final Logger logger = Logger.getLogger(WebDomainKlassifizierung.class);

  @Id
  @TableGenerator(name = "PKGenWebDomainKlassifizierung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "WebDomainKlassifizierung_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenWebDomainKlassifizierung")
  private Long id;
  @Basic
  private String klassifizierung;

  public String getKategorie() {
    return klassifizierung;
  }

  public void setKategorie(final String kategorie) {
    this.klassifizierung = kategorie;
  }

  @Override
  public int hashCode() {
    int result = (int) (getId() ^ (getId() >>> 32));
    result = 31 * result + klassifizierung.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WebDomainKlassifizierung)) {
      return false;
    }

    final WebDomainKlassifizierung that = (WebDomainKlassifizierung) o;

    if (getId() != that.getId()) {
      return false;
    }
    if (!klassifizierung.equals(that.klassifizierung)) {
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
