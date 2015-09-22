package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 02.03.2010
 * Time: 17:38:09
 */


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Gesellschaftsform {
  private static final Logger logger = Logger.getLogger(Gesellschaftsform.class);

  @Id
  @TableGenerator(name = "PKGenGesellschaftsform", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Gesellschaftsform_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenGesellschaftsform")
  private Long id;
  @Basic
  private boolean kapitalgesellschaft;
  @Basic
  private boolean personengesellschaft;
  @Basic
  private boolean nichtkapitalistischeKoerperschaft;
  @Basic(optional = false)
  private String bezeichnung;
  @Basic(optional = false)
  private String abkuerzung;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public boolean isKapitalgesellschaft() {
    return kapitalgesellschaft;
  }

  public void setKapitalgesellschaft(final boolean kapitalgesellschaft) {
    this.kapitalgesellschaft = kapitalgesellschaft;
  }


  public boolean isPersonengesellschaft() {
    return personengesellschaft;
  }

  public void setPersonengesellschaft(final boolean personengesellschaft) {
    this.personengesellschaft = personengesellschaft;
  }


  public boolean isNichtkapitalistischeKoerperschaft() {
    return nichtkapitalistischeKoerperschaft;
  }

  public void setNichtkapitalistischeKoerperschaft(final boolean nichtkapitalistischeKoerperschaft) {
    this.nichtkapitalistischeKoerperschaft = nichtkapitalistischeKoerperschaft;
  }


  public String getBezeichnung() {
    return bezeichnung;
  }

  public void setBezeichnung(final String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (kapitalgesellschaft ? 1 : 0);
    result = 31 * result + (personengesellschaft ? 1 : 0);
    result = 31 * result + (nichtkapitalistischeKoerperschaft ? 1 : 0);
    result = 31 * result + bezeichnung.hashCode();
    result = 31 * result + abkuerzung.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Gesellschaftsform)) {
      return false;
    }

    final Gesellschaftsform that = (Gesellschaftsform) o;

    if (kapitalgesellschaft != that.kapitalgesellschaft) {
      return false;
    }
    if (nichtkapitalistischeKoerperschaft != that.nichtkapitalistischeKoerperschaft) {
      return false;
    }
    if (personengesellschaft != that.personengesellschaft) {
      return false;
    }
    if (!abkuerzung.equals(that.abkuerzung)) {
      return false;
    }
    if (!bezeichnung.equals(that.bezeichnung)) {
      return false;
    }
    if (id != null ? !id.equals(that.id) : that.id != null) {
      return false;
    }

    return true;
  }

  public String getAbkuerzung() {
    return abkuerzung;
  }

  public void setAbkuerzung(final String abkuerzung) {
    this.abkuerzung = abkuerzung;
  }
}
