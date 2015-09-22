/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:45
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.person.Anrede;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.*;
import java.util.Date;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class KontaktAnfrage {
  private static final Logger logger = Logger.getLogger(KontaktAnfrage.class);

  @Id
  @TableGenerator(name = "PKGenKontaktAnfrage", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "KontaktAnfrage_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenKontaktAnfrage")
  private Long id;
  @Basic
  private String vorname;
  @Basic
  private String nachname;
  @Basic
  private String unternehmen;
  @Basic
  private String position;
  @Basic
  private String email;
  @Basic
  private String plz;
  @Basic
  private String ort;
  @Basic
  private String laendercode;
  @Basic
  private String vorwahl;
  @Basic
  private String nummer;
  @Basic
  private String durchwahl;
  @Basic
  private String titel;
  @Basic
  private Boolean rueckruf;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Anrede anrede;
  @ManyToOne
  private Mandantengruppe mandantengruppe;
  @ManyToOne(optional = false)
  private BenutzerWebapplikation benutzerWebapplikation;
  @Basic
  private Date created;

  public String getVorname() {
    return vorname;
  }

  public void setVorname(final String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(final String nachname) {
    this.nachname = nachname;
  }

  public String getUnternehmen() {
    return unternehmen;
  }

  public void setUnternehmen(final String unternehmen) {
    this.unternehmen = unternehmen;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(final String position) {
    this.position = position;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(final String plz) {
    this.plz = plz;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(final String ort) {
    this.ort = ort;
  }

  public String getLaendercode() {
    return laendercode;
  }

  public void setLaendercode(final String laendercode) {
    this.laendercode = laendercode;
  }

  public String getVorwahl() {
    return vorwahl;
  }

  public void setVorwahl(final String vorwahl) {
    this.vorwahl = vorwahl;
  }

  public String getNummer() {
    return nummer;
  }

  public void setNummer(final String nummer) {
    this.nummer = nummer;
  }

  public String getDurchwahl() {
    return durchwahl;
  }

  public void setDurchwahl(final String durchwahl) {
    this.durchwahl = durchwahl;
  }

  public String getTitel() {
    return titel;
  }

  public void setTitel(final String titel) {
    this.titel = titel;
  }

  public boolean getRueckruf() {
    return rueckruf;
  }

  public void setRueckruf(final boolean rueckruf) {
    this.rueckruf = rueckruf;
  }

  public Anrede getAnrede() {
    return anrede;
  }

  public void setAnrede(final Anrede anrede) {
    this.anrede = anrede;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + mandantengruppe.hashCode();
    result = 31 * result + benutzerWebapplikation.hashCode();
    result = 31 * result + created.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KontaktAnfrage)) {
      return false;
    }

    final KontaktAnfrage that = (KontaktAnfrage) o;

    if (!benutzerWebapplikation.equals(that.benutzerWebapplikation)) {
      return false;
    }
    if (!created.equals(that.created)) {
      return false;
    }
    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (!mandantengruppe.equals(that.mandantengruppe)) {
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("KontaktAnfrage");
    sb.append("{id=").append(getId());
    sb.append(", vorname='").append(vorname).append('\'');
    sb.append(", nachname='").append(nachname).append('\'');
    sb.append(", unternehmen='").append(unternehmen).append('\'');
    sb.append(", position='").append(position).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", plz='").append(plz).append('\'');
    sb.append(", ort='").append(ort).append('\'');
    sb.append(", laendercode='").append(laendercode).append('\'');
    sb.append(", vorwahl='").append(vorwahl).append('\'');
    sb.append(", nummer='").append(nummer).append('\'');
    sb.append(", durchwahl='").append(durchwahl).append('\'');
    sb.append(", titel='").append(titel).append('\'');
    sb.append(", rueckruf=").append(rueckruf);
    sb.append(", anrede=").append(anrede);
    sb.append(", mandantengruppe=").append(mandantengruppe);
    sb.append(", benutzerWebapplikation=").append(benutzerWebapplikation);
    sb.append(", created=").append(created);
    sb.append('}');
    return sb.toString();
  }

  public Mandantengruppe getMandantengruppe() {
    return mandantengruppe;
  }

  public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
    this.mandantengruppe = mandantengruppe;
  }

  public BenutzerWebapplikation getBenutzerWebapplikation() {
    return benutzerWebapplikation;
  }

  public void setBenutzerWebapplikation(final BenutzerWebapplikation benutzerWebapplikation) {
    this.benutzerWebapplikation = benutzerWebapplikation;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(final Date created) {
    this.created = created;
  }
}
