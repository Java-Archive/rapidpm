/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:51
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;


public class BenutzerWebapplikation {
  private static final Logger logger = Logger.getLogger(BenutzerWebapplikation.class);

  private String id;
  private String webappName;

  public String getWebappName() {
    return webappName;
  }

  public void setWebappName(final String webappName) {
    this.webappName = webappName;
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

    final BenutzerWebapplikation that = (BenutzerWebapplikation) o;

    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }

    return true;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("BenutzerWebapplikation");
    sb.append("{id=").append(getId());
    sb.append(", webappName='").append(webappName).append('\'');
    sb.append('}');
    return sb.toString();
  }
}