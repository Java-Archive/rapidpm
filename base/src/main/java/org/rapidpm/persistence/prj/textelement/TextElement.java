package org.rapidpm.persistence.prj.textelement;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.textelement.kommentar.TextElementKommentar;

import javax.persistence.Column;
import java.util.List;

public class TextElement implements Comparable<TextElement> {

  public static final String ID = "id";
  public static final String UNLOCKED = "freigeschaltet";
  public static final String NUMMER = "textElementNummer";
  public static final String BEZEICHNUNG = "bezeichnung";
  public static final String KOMMENTARE = "kommentarliste";
  public static final String TEXT = "text";

  private static final Logger logger = Logger.getLogger(TextElement.class);

  private String id;
  private Boolean freigeschaltet;
  private Integer textElementNummer;
  private String bezeichnung;
  @Column(length = 20000)
  private String text;
  private transient List<TextElementKommentar> kommentarliste;

  public Integer getTextElementNummer() {
    return textElementNummer;
  }

  public void setTextElementNummer(final Integer textElementnummer) {
    this.textElementNummer = textElementnummer;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public void setBezeichnung(final String ueberschrift) {
    this.bezeichnung = ueberschrift;
  }

  public Boolean getFreigeschaltet() {
    return freigeschaltet;
  }

  public void setFreigeschaltet(final Boolean freigeschaltet) {
    this.freigeschaltet = freigeschaltet;
  }

  public List<TextElementKommentar> getKommentarliste() {
    return kommentarliste;
  }

  public void setKommentarliste(List<TextElementKommentar> kommentarliste) {
    this.kommentarliste = kommentarliste;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int compareTo(TextElement o) {
    return this.getId().compareTo(o.getId());
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TextElement)) return false;

    TextElement that = (TextElement) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;

    return true;
  }
}
