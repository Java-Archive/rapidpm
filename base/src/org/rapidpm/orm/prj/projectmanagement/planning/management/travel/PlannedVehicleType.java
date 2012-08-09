package org.rapidpm.orm.prj.projectmanagement.planning.management.travel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:30
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class PlannedVehicleType {
    @Id
    @TableGenerator(name = "PKGenPlannedVehicleType", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlannedVehicleType_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenPlannedVehicleType")
    private Long id;

    @Basic
    private String typename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }


}
