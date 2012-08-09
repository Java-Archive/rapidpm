package org.rapidpm.orm.prj.projectmanagement.planning; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 01.12.11
 * Time: 11:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class PlanningStatus {
    private static final Logger logger = Logger.getLogger(PlanningStatus.class);


    @Id
    @TableGenerator(name = "PKGenPlanningStatus", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "PlanningStatus_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlanningStatus")
    private Long id;

    @Basic
    private int orderNumber;
    @Basic
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
