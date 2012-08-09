package org.rapidpm.orm.prj.projectmanagement.planning.management.travel;

import org.rapidpm.orm.prj.stammdaten.address.Adresse;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 09:54
 * <p/>
 * Eine Travelunit beinhaltet die Reisetätigkeit eines Reiseabschnitts.
 * <p/>
 * Anzahl StartOrt StopOrt PlannedVehicle Dauer Wer
 */
@Entity
public class PlannedTravelUnit {

    @Id
    @TableGenerator(name = "PKGenPlannedTravelUnit", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedTravelUnit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenPlannedTravelUnit")
    private Long id;

    @Basic private int orderNumber; // Reihenfolge der TravelUnits

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Adresse startAdresse;
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY) private Adresse stopAdresse;
    private Integer plannedDurationMinutes;

    @Basic private String currency; // USD / EUR / ..
    @Basic private float estimatedCosts;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    private PlannedVehicleType plannedVehicleType;

    //    private Date startDate;
//    private Date stopDate;
//    private IssueTravelUnit issue;
//    private Person reisender;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Adresse getStartAdresse() {
        return startAdresse;
    }

    public void setStartAdresse(Adresse startAdresse) {
        this.startAdresse = startAdresse;
    }

    public Adresse getStopAdresse() {
        return stopAdresse;
    }

    public void setStopAdresse(Adresse stopAdresse) {
        this.stopAdresse = stopAdresse;
    }

    public Integer getPlannedDurationMinutes() {
        return plannedDurationMinutes;
    }

    public void setPlannedDurationMinutes(Integer plannedDurationMinutes) {
        this.plannedDurationMinutes = plannedDurationMinutes;
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

    public PlannedVehicleType getPlannedVehicleType() {
        return plannedVehicleType;
    }

    public void setPlannedVehicleType(PlannedVehicleType plannedVehicleType) {
        this.plannedVehicleType = plannedVehicleType;
    }
}
