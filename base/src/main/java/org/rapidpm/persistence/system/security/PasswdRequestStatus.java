/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:53
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
//@Entity
@Deprecated
public class PasswdRequestStatus {
  private static final Logger logger = Logger.getLogger(PasswdRequestStatus.class);

  @Id
  @TableGenerator(name = "PKGenPasswdRequestStatus", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "PasswdRequestStatus_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenPasswdRequestStatus")
  private Long id;
  @Basic
  private String status;

  public String getStatus() {
    return status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final PasswdRequestStatus that = (PasswdRequestStatus) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
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