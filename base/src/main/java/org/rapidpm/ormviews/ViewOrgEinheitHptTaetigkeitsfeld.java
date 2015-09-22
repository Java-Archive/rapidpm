package org.rapidpm.ormviews; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.04.11
 * Time: 01:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

public class ViewOrgEinheitHptTaetigkeitsfeld {
  private static final Logger logger = Logger.getLogger(ViewOrgEinheitHptTaetigkeitsfeld.class);

  private Long orgeinheitOID;
  private Long taetigkeitsfeldOID;

  private String nameDesUnternehmens;
  private String taetigkeitsfeldName;

  public ViewOrgEinheitHptTaetigkeitsfeld(final String nameDesUnternehmens, final Long orgeinheitOID, final String taetigkeitsfeldName, final Long taetigkeitsfeldOID) {
    this.nameDesUnternehmens = nameDesUnternehmens;
    this.orgeinheitOID = orgeinheitOID;
    this.taetigkeitsfeldName = taetigkeitsfeldName;
    this.taetigkeitsfeldOID = taetigkeitsfeldOID;
  }

  @Override
  public int hashCode() {
    int result = orgeinheitOID.hashCode();
    result = 31 * result + taetigkeitsfeldOID.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ViewOrgEinheitHptTaetigkeitsfeld)) {
      return false;
    }

    final ViewOrgEinheitHptTaetigkeitsfeld that = (ViewOrgEinheitHptTaetigkeitsfeld) o;

    if (!orgeinheitOID.equals(that.orgeinheitOID)) {
      return false;
    }
    if (!taetigkeitsfeldOID.equals(that.taetigkeitsfeldOID)) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ViewOrgEinheitHptTaetigkeitsfeld");
    sb.append("{nameDesUnternehmens='").append(nameDesUnternehmens).append('\'');
    sb.append(", orgeinheitOID=").append(orgeinheitOID);
    sb.append(", taetigkeitsfeldOID=").append(taetigkeitsfeldOID);
    sb.append(", taetigkeitsfeldName='").append(taetigkeitsfeldName).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getNameDesUnternehmens() {
    return nameDesUnternehmens;
  }

  public void setNameDesUnternehmens(final String nameDesUnternehmens) {
    this.nameDesUnternehmens = nameDesUnternehmens;
  }

  public Long getOrgeinheitOID() {
    return orgeinheitOID;
  }

  public void setOrgeinheitOID(final Long orgeinheitOID) {
    this.orgeinheitOID = orgeinheitOID;
  }

  public String getTaetigkeitsfeldName() {
    return taetigkeitsfeldName;
  }

  public void setTaetigkeitsfeldName(final String taetigkeitsfeldName) {
    this.taetigkeitsfeldName = taetigkeitsfeldName;
  }

  public Long getTaetigkeitsfeldOID() {
    return taetigkeitsfeldOID;
  }

  public void setTaetigkeitsfeldOID(final Long taetigkeitsfeldOID) {
    this.taetigkeitsfeldOID = taetigkeitsfeldOID;
  }
}
