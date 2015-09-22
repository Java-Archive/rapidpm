package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 11:23
 */
@Entity
public class Projektanfrage {

  @Id
  @TableGenerator(name = "PKGenProjektanfrage",
      table = "pk_gen",
      pkColumnName = "gen_key",
      pkColumnValue = "Projektanfrage_id",
      valueColumnName = "gen_value",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenProjektanfrage")
  private Long id; // Lfd. Nr.

  @Basic private String kunde;
  @Basic private Long dsKundenNummer;
  @Basic private Long anfrageNummerKunde;
  @Basic private String bezeichnung1;
  @Basic private String bezeichnung2;
  @Basic private String bezeichnung3;
  @Basic private Long zeichnungsnummer;
  @Basic private String anfrageperson;
  @Basic private Date anfragedatum;
  @Basic private Date angebotsSollTermin;
  @Basic private String bearbeiter;
  @Basic private String angebotsstatus; // QUEST enum?
  @Basic private float erfolgschance;
  @Basic private boolean angebotserstellung; // TODO Begründung bei nein?

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKunde() {
    return kunde;
  }

  public void setKunde(String kunde) {
    this.kunde = kunde;
  }

  public Long getDsKundenNummer() {
    return dsKundenNummer;
  }

  public void setDsKundenNummer(Long dsKundenNummer) {
    this.dsKundenNummer = dsKundenNummer;
  }

  public Long getAnfrageNummerKunde() {
    return anfrageNummerKunde;
  }

  public void setAnfrageNummerKunde(Long anfrageNummerKunde) {
    this.anfrageNummerKunde = anfrageNummerKunde;
  }

  public String getBezeichnung1() {
    return bezeichnung1;
  }

  public void setBezeichnung1(String bezeichnung1) {
    this.bezeichnung1 = bezeichnung1;
  }

  public String getBezeichnung2() {
    return bezeichnung2;
  }

  public void setBezeichnung2(String bezeichnung2) {
    this.bezeichnung2 = bezeichnung2;
  }

  public String getBezeichnung3() {
    return bezeichnung3;
  }

  public void setBezeichnung3(String bezeichnung3) {
    this.bezeichnung3 = bezeichnung3;
  }

  public Long getZeichnungsnummer() {
    return zeichnungsnummer;
  }

  public void setZeichnungsnummer(Long zeichnungsnummer) {
    this.zeichnungsnummer = zeichnungsnummer;
  }

  public String getAnfrageperson() {
    return anfrageperson;
  }

  public void setAnfrageperson(String anfrageperson) {
    this.anfrageperson = anfrageperson;
  }

  public Date getAnfragedatum() {
    return anfragedatum;
  }

  public void setAnfragedatum(Date anfragedatum) {
    this.anfragedatum = anfragedatum;
  }

  public Date getAngebotsSollTermin() {
    return angebotsSollTermin;
  }

  public void setAngebotsSollTermin(Date angebotsSollTermin) {
    this.angebotsSollTermin = angebotsSollTermin;
  }

  public String getBearbeiter() {
    return bearbeiter;
  }

  public void setBearbeiter(String bearbeiter) {
    this.bearbeiter = bearbeiter;
  }

  public String getAngebotsstatus() {
    return angebotsstatus;
  }

  public void setAngebotsstatus(String angebotsstatus) {
    this.angebotsstatus = angebotsstatus;
  }

  public float getErfolgschance() {
    return erfolgschance;
  }

  public void setErfolgschance(float erfolgschance) {
    this.erfolgschance = erfolgschance;
  }

  public boolean isAngebotserstellung() {
    return angebotserstellung;
  }

  public void setAngebotserstellung(boolean angebotserstellung) {
    this.angebotserstellung = angebotserstellung;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Projektanfrage that = (Projektanfrage) o;

    if (angebotserstellung != that.angebotserstellung) return false;
    if (Float.compare(that.erfolgschance, erfolgschance) != 0) return false;
    if (anfrageNummerKunde != null ? !anfrageNummerKunde.equals(that.anfrageNummerKunde) : that.anfrageNummerKunde != null)
      return false;
    if (anfragedatum != null ? !anfragedatum.equals(that.anfragedatum) : that.anfragedatum != null) return false;
    if (anfrageperson != null ? !anfrageperson.equals(that.anfrageperson) : that.anfrageperson != null)
      return false;
    if (angebotsSollTermin != null ? !angebotsSollTermin.equals(that.angebotsSollTermin) : that.angebotsSollTermin != null)
      return false;
    if (angebotsstatus != null ? !angebotsstatus.equals(that.angebotsstatus) : that.angebotsstatus != null)
      return false;
    if (bearbeiter != null ? !bearbeiter.equals(that.bearbeiter) : that.bearbeiter != null) return false;
    if (bezeichnung1 != null ? !bezeichnung1.equals(that.bezeichnung1) : that.bezeichnung1 != null) return false;
    if (bezeichnung2 != null ? !bezeichnung2.equals(that.bezeichnung2) : that.bezeichnung2 != null) return false;
    if (bezeichnung3 != null ? !bezeichnung3.equals(that.bezeichnung3) : that.bezeichnung3 != null) return false;
    if (dsKundenNummer != null ? !dsKundenNummer.equals(that.dsKundenNummer) : that.dsKundenNummer != null)
      return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    if (kunde != null ? !kunde.equals(that.kunde) : that.kunde != null) return false;
    if (zeichnungsnummer != null ? !zeichnungsnummer.equals(that.zeichnungsnummer) : that.zeichnungsnummer != null)
      return false;

    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Projektanfrage");
    sb.append("{id=").append(id);
    sb.append(", kunde='").append(kunde).append('\'');
    sb.append(", dsKundenNummer=").append(dsKundenNummer);
    sb.append(", anfrageNummerKunde=").append(anfrageNummerKunde);
    sb.append(", bezeichnung1='").append(bezeichnung1).append('\'');
    sb.append(", bezeichnung2='").append(bezeichnung2).append('\'');
    sb.append(", bezeichnung3='").append(bezeichnung3).append('\'');
    sb.append(", zeichnungsnummer=").append(zeichnungsnummer);
    sb.append(", anfrageperson='").append(anfrageperson).append('\'');
    sb.append(", anfragedatum=").append(anfragedatum);
    sb.append(", angebotsSollTermin=").append(angebotsSollTermin);
    sb.append(", bearbeiter='").append(bearbeiter).append('\'');
    sb.append(", angebotsstatus='").append(angebotsstatus).append('\'');
    sb.append(", erfolgschance=").append(erfolgschance);
    sb.append(", angebotserstellung=").append(angebotserstellung);
    sb.append('}');
    return sb.toString();
  }
}
