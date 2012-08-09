package org.rapidpm.orm.prj.projectmanagement.execution.management.travel.car;

import javax.persistence.*;

/**
 *
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:11
 *
 */
@Entity
public class Car {

    @Id
    @TableGenerator(name = "PKGenCar", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Car_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenCar")
    private Long id;

    @Basic
    private String currency; // USD / EUR / ..
    @Basic
    private float costPerKM;
    @Basic
    private float distanceKM;

    @Basic
    private String carDescription;
    @Basic
    private int travelDurationMinutes;

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

    public float getCostPerKM() {
        return costPerKM;
    }

    public void setCostPerKM(float costPerKM) {
        this.costPerKM = costPerKM;
    }

    public float getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(float distanceKM) {
        this.distanceKM = distanceKM;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public int getTravelDurationMinutes() {
        return travelDurationMinutes;
    }

    public void setTravelDurationMinutes(int travelDurationMinutes) {
        this.travelDurationMinutes = travelDurationMinutes;
    }
}
