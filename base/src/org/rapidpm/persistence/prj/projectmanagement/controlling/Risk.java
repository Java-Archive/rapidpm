package org.rapidpm.persistence.prj.projectmanagement.controlling;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 07.11.12
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Risk {
    @Id
    @TableGenerator(name = "PKGenRisk", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Risk_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPlannedProject")
    private Long id;

    @Basic
    double propabillity;
    @Basic
    String name;
}
