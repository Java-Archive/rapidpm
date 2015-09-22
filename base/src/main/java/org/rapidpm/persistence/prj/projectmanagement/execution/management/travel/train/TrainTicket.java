package org.rapidpm.persistence.prj.projectmanagement.execution.management.travel.train;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TrainTicket {

  @Id
  @TableGenerator(name = "PKGenTrainTicket", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "TrainTicket_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTrainTicket")
  private Long id;

  @Basic private String currency; // USD / EUR / ..
  @Basic private float costs;

  @Basic private int travelDurationMinutes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public float getCosts() {
    return costs;
  }

  public void setCosts(float costs) {
    this.costs = costs;
  }

  public int getTravelDurationMinutes() {
    return travelDurationMinutes;
  }

  public void setTravelDurationMinutes(int travelDurationMinutes) {
    this.travelDurationMinutes = travelDurationMinutes;
  }
}
