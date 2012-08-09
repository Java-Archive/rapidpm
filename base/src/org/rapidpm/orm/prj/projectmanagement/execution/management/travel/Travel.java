package org.rapidpm.orm.prj.projectmanagement.execution.management.travel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: svenruppert
 * Date: 30.07.12
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Travel {
    @Id
    @TableGenerator(name = "PKGenTravel", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Travel_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTravel")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
