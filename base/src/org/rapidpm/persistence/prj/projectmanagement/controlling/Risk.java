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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenRisk")
    private Long id;

    @Basic
    private double propabillity;
    @Basic
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Risk risk = (Risk) o;

        if (Double.compare(risk.propabillity, propabillity) != 0) return false;
        if (id != null ? !id.equals(risk.id) : risk.id != null) return false;
        if (name != null ? !name.equals(risk.name) : risk.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = propabillity != +0.0d ? Double.doubleToLongBits(propabillity) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
