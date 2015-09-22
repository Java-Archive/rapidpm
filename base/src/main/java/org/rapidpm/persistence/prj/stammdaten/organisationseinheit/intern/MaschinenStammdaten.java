package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class MaschinenStammdaten {

  @Id
  @TableGenerator(name = "PKGenMaschinenStammdaten", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "MaschinenStammdaten_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenMaschinenStammdaten")
  private Long id;

  private String name;
  //private String kontenName;
  private String maschinennummer;
  private Float euroMaschinenStunde;
  private Date inbetriebnahme;
  private Date ausmusterung;
  private Integer anschlussLeistung;
  private Integer druckluftVerbrauch;

  private Integer standflaeche; //qm

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMaschinennummer() {
    return maschinennummer;
  }

  public void setMaschinennummer(String maschinennummer) {
    this.maschinennummer = maschinennummer;
  }

  public Float getEuroMaschinenStunde() {
    return euroMaschinenStunde;
  }

  public void setEuroMaschinenStunde(Float euroMaschinenStunde) {
    this.euroMaschinenStunde = euroMaschinenStunde;
  }

  public Date getInbetriebnahme() {
    return inbetriebnahme;
  }

  public void setInbetriebnahme(Date inbetriebnahme) {
    this.inbetriebnahme = inbetriebnahme;
  }

  public Date getAusmusterung() {
    return ausmusterung;
  }

  public void setAusmusterung(Date ausmusterung) {
    this.ausmusterung = ausmusterung;
  }

  public Integer getAnschlussLeistung() {
    return anschlussLeistung;
  }

  public void setAnschlussLeistung(Integer anschlussLeistung) {
    this.anschlussLeistung = anschlussLeistung;
  }

  public Integer getDruckluftVerbrauch() {
    return druckluftVerbrauch;
  }

  public void setDruckluftVerbrauch(Integer druckluftVerbrauch) {
    this.druckluftVerbrauch = druckluftVerbrauch;
  }

  public Integer getStandflaeche() {
    return standflaeche;
  }

  public void setStandflaeche(Integer standflaeche) {
    this.standflaeche = standflaeche;
  }
}
