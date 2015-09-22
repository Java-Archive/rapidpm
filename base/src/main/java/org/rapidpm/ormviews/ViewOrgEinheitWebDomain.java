package org.rapidpm.ormviews; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 20:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

public class ViewOrgEinheitWebDomain {
  private static final Logger logger = Logger.getLogger(ViewOrgEinheitWebDomain.class);
  private String nameDesUnternehmens;
  private String webdomains;
  private long ordOID;
  private long webdmoainOID;

  public ViewOrgEinheitWebDomain(final String nameDesUnternehmens, final long ordOID, final long webdmoainOID, final String webdomains) {
    this.nameDesUnternehmens = nameDesUnternehmens;
    this.ordOID = ordOID;
    this.webdmoainOID = webdmoainOID;
    this.webdomains = webdomains;
  }

  @Override
  public int hashCode() {
    int result = (int) (ordOID ^ (ordOID >>> 32));
    result = 31 * result + (int) (webdmoainOID ^ (webdmoainOID >>> 32));
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ViewOrgEinheitWebDomain)) {
      return false;
    }

    final ViewOrgEinheitWebDomain that = (ViewOrgEinheitWebDomain) o;

    if (ordOID != that.ordOID) {
      return false;
    }
    if (webdmoainOID != that.webdmoainOID) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ViewOrgEinheitWebDomain");
    sb.append("{nameDesUnternehmens='").append(nameDesUnternehmens).append('\'');
    sb.append(", webdomains='").append(webdomains).append('\'');
    sb.append(", ordOID=").append(ordOID);
    sb.append(", webdmoainOID=").append(webdmoainOID);
    sb.append('}');
    return sb.toString();
  }

  public String getNameDesUnternehmens() {
    return nameDesUnternehmens;
  }

  public String getWebdomains() {
    return webdomains;
  }

  public long getOrdOID() {
    return ordOID;
  }

  public long getWebdmoainOID() {
    return webdmoainOID;
  }
}
