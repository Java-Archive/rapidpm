package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: sven.ruppert
 * Date: 02.12.11
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */


@Entity
public class MitarbeiterUrlaub {

    @Id
    @TableGenerator(name = "PKGenMitarbeiterUrlaub", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "MitarbeiterUrlaub_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenMitarbeiterUrlaub")
    private Long id;


    private Date startDate;
    private Date stopDate;

    private int freeDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getFreeDays() {
        return freeDays;
    }

    public void setFreeDays(int freeDays) {
        this.freeDays = freeDays;
    }
}
