package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.prj.projectmanagement.planning.management.travel.PlannedTravelUnit;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */
//@Entity
public class IssueTravelUnit extends IssueBase {

    @Id
    @TableGenerator(name = "PKGenIssueTravelUnit", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "IssueTravelUnit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenIssueTravelUnit")
    private Long id;



    //status
    //wer
    //dauer

    @Basic private Float usedEuro;
    @Basic private Date startDate;
    @Basic private Date stopDate;

    @OneToOne(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY)
    private PlannedTravelUnit plannedTravelUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getUsedEuro() {
        return usedEuro;
    }

    public void setUsedEuro(Float usedEuro) {
        this.usedEuro = usedEuro;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public PlannedTravelUnit getPlannedTravelUnit() {
        return plannedTravelUnit;
    }

    public void setPlannedTravelUnit(PlannedTravelUnit plannedTravelUnit) {
        this.plannedTravelUnit = plannedTravelUnit;
    }
}
