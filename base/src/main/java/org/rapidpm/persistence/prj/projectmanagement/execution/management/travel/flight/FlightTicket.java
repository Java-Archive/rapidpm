package org.rapidpm.persistence.prj.projectmanagement.execution.management.travel.flight;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class FlightTicket {

  @Id
  @TableGenerator(name = "PKGenFlightTicket", table = "pk_gen", pkColumnName = "gen_key",
      pkColumnValue = "FlightTicket_id", valueColumnName = "gen_value", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenFlightTicket")
  private Long id;

  @Basic private String currency; // USD / EUR / ..
  @Basic private float costs;
  @Basic private String flightNumber;
  @Basic private String eTicketNumber;

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

  public String getFlightNumber() {
    return flightNumber;
  }

  public void setFlightNumber(String flightNumber) {
    this.flightNumber = flightNumber;
  }

  public String geteTicketNumber() {
    return eTicketNumber;
  }

  public void seteTicketNumber(String eTicketNumber) {
    this.eTicketNumber = eTicketNumber;
  }

  public int getTravelDurationMinutes() {
    return travelDurationMinutes;
  }

  public void setTravelDurationMinutes(int travelDurationMinutes) {
    this.travelDurationMinutes = travelDurationMinutes;
  }
}
