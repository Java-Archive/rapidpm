package org.rapidpm.persistence.prj.projectmanagement.planning.management.travel;

import org.rapidpm.persistence.prj.stammdaten.address.Adresse;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 10:30
 * <p>
 * Eine Übernachtung
 */

@Entity
public class PlannedAccomodation {

  @Id
  @TableGenerator(name = "PKGenPlannedAccomodation", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "PlannedAccomodation_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE,
      generator = "PKGenPlannedAccomodation")
  private Long id;


  @Basic private String accomodationName;
  @Basic private String currency; // USD / EUR / ..
  @Basic private float estimatedCosts;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  private Adresse adresse;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccomodationName() {
    return accomodationName;
  }

  public void setAccomodationName(String accomodationName) {
    this.accomodationName = accomodationName;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public float getEstimatedCosts() {
    return estimatedCosts;
  }

  public void setEstimatedCosts(float estimatedCosts) {
    this.estimatedCosts = estimatedCosts;
  }

  public Adresse getAdresse() {
    return adresse;
  }

  public void setAdresse(Adresse adresse) {
    this.adresse = adresse;
  }
}
