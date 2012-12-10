package org.rapidpm.persistence.system.security.berechtigungen;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by RapidPM - www.rapidpm.org
 * User: sven.ruppert
 * Date: 17.07.2010
 * Time: 22:43:52
 * copyright by RapidPM - www.rapidpm.org Germany (RapidPM - www.rapidpm.org) contact sven.ruppert@me.com
 */
//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
@Audited
public class Berechtigung {

    @Id
    @TableGenerator(name = "PKGenBerechtigung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Berechtigung_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBerechtigung")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Column(unique = true, nullable = false)
    @Basic(optional = false)
    private String name;

    public Berechtigung() {
    }


    public Berechtigung(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Berechtigung)) {
            return false;
        }

        final Berechtigung that = (Berechtigung) o;

        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Berechtigung");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
