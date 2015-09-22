/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.bewegungsdaten;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:54
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.Taetigkeitsfeld;
import org.rapidpm.persistence.prj.stammdaten.person.Anrede;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.*;

//@NamedQueries({
//        @NamedQuery(name = "CheckIfWishedLoginIsAvailable", query = "select r from Registration r where r.login=:login and r.mandantengruppe.mandantengruppe=:mandantengruppe"),
//        @NamedQuery(name = "LoadAllRegistrationsForMandantengruppe", query = "select r from Registration  r where r.mandantengruppe.mandantengruppe=:mandantengruppe")
//})

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Registration {
  private static final Logger logger = Logger.getLogger(Registration.class);

  @Id
  @TableGenerator(name = "PKGenRegistration",
      table = "pk_gen",
      pkColumnName = "gen_key",
      pkColumnValue = "Registration_id",
      valueColumnName = "gen_value",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenRegistration")
  private Long id;

  //TODO Klassen verwenden
  @Basic
  private String login;
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
  private String strasse;
  @Basic
  private String hausnr;


  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private RegistrationStatus registrationStatus;

  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private Taetigkeitsfeld taetigkeitsfeld;


  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private Anrede anrede;

  @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.EAGER)
  private Mandantengruppe mandantengruppe;

  @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.EAGER)
  private BenutzerWebapplikation benutzerWebapplikation;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }


  public String getLogin() {
    return login;
  }

  public void setLogin(final String login) {
    this.login = login;
  }

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


  public RegistrationStatus getRegistrationStatus() {
    return registrationStatus;
  }

  public void setRegistrationStatus(final RegistrationStatus registrationStatus) {
    this.registrationStatus = registrationStatus;
  }


  public Taetigkeitsfeld getTaetigkeitsfeld() {
    return taetigkeitsfeld;
  }

  public void setTaetigkeitsfeld(final Taetigkeitsfeld taetigkeitsfeld) {
    this.taetigkeitsfeld = taetigkeitsfeld;
  }


  public Anrede getAnrede() {
    return anrede;
  }

  public void setAnrede(final Anrede anrede) {
    this.anrede = anrede;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Registration)) {
      return false;
    }

    final Registration that = (Registration) o;

    if (!id.equals(that.id)) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Registration");
    sb.append("{id=").append(id);
    sb.append(", login='").append(login).append('\'');
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
    sb.append(", strasse='").append(strasse).append('\'');
    sb.append(", hausnr='").append(hausnr).append('\'');
    sb.append(", registrationStatus=").append(registrationStatus);
    sb.append(", taetigkeitsfeld=").append(taetigkeitsfeld);
    sb.append(", anrede=").append(anrede);
    sb.append(", mandantengruppe=").append(mandantengruppe);
    sb.append(", benutzerWebapplikation=").append(benutzerWebapplikation);
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


  public String getStrasse() {
    return strasse;
  }

  public void setStrasse(final String strasse) {
    this.strasse = strasse;
  }


  public String getHausnr() {
    return hausnr;
  }

  public void setHausnr(final String hausnr) {
    this.hausnr = hausnr;
  }
}
