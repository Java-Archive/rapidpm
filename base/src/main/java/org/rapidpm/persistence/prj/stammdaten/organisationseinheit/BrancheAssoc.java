package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

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
 * @since 03.03.2010
 * Time: 14:00:05
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class BrancheAssoc {
  private static final Logger logger = Logger.getLogger(BrancheAssoc.class);

  @Id
  @TableGenerator(name = "PKGenBrancheAssoc", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "BrancheAssoc_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenBrancheAssoc")
  private Long id;
  @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
  private BrancheKlassifizierung brancheKlassifizierung;
  @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
  private Branche branche;

  public Branche getBranche() {
    return branche;
  }

  public void setBranche(final Branche branche) {
    this.branche = branche;
  }

  public BrancheKlassifizierung getBrancheKlassifizierung() {
    return brancheKlassifizierung;
  }

  public void setBrancheKlassifizierung(final BrancheKlassifizierung brancheKlassifizierung) {
    this.brancheKlassifizierung = brancheKlassifizierung;
  }

  @Override
  public int hashCode() {
    int result = getId() != null ? getId().hashCode() : 0;
    result = 31 * result + branche.hashCode();
    result = 31 * result + brancheKlassifizierung.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BrancheAssoc)) {
      return false;
    }

    final BrancheAssoc that = (BrancheAssoc) o;

    if (!branche.equals(that.branche)) {
      return false;
    }
    if (!brancheKlassifizierung.equals(that.brancheKlassifizierung)) {
      return false;
    }
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
