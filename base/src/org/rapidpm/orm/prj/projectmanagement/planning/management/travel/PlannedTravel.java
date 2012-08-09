package org.rapidpm.orm.prj.projectmanagement.planning.management.travel;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 09:59
 * <p/>
 * EineReise bestehend aus n Reiseteilen ( TravelUnits )
 * z.B. Taxi zum Flughafen, Flug, Taxi zum Kunden
 */
@Entity
public class PlannedTravel {

    @Id
    @TableGenerator(name = "PKGenPlannedTravel", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedTravel_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlannedTravel")
    private Long id;

    @OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    private List<PlannedTravelUnit> plannedTravelUnits;
    @OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    private List<PlannedAccomodation> plannedAccomodations;

    @Basic private String travelname; //logischer Name der Reise
    @Basic private int travelcount;  //Anzahl der Reisen

    @Basic private int travelerCount;  // Anzahl der Personen

    //es fehlt noch welche Gehaltsgruppe und Verpflegungsmehraufwand.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlannedTravelUnit> getPlannedTravelUnits() {
        return plannedTravelUnits;
    }

    public void setPlannedTravelUnits(List<PlannedTravelUnit> plannedTravelUnits) {
        this.plannedTravelUnits = plannedTravelUnits;
    }

    public List<PlannedAccomodation> getPlannedAccomodations() {
        return plannedAccomodations;
    }

    public void setPlannedAccomodations(List<PlannedAccomodation> plannedAccomodations) {
        this.plannedAccomodations = plannedAccomodations;
    }

    public String getTravelname() {
        return travelname;
    }

    public void setTravelname(String travelname) {
        this.travelname = travelname;
    }

    public int getTravelcount() {
        return travelcount;
    }

    public void setTravelcount(int travelcount) {
        this.travelcount = travelcount;
    }

    public int getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount = travelerCount;
    }
}
