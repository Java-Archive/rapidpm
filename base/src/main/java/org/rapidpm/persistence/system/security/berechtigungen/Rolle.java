package org.rapidpm.persistence.system.security.berechtigungen;

import org.hibernate.envers.Audited;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;
import java.util.Set;

/**
 * User: Alexander Vos
 * Date: 27.11.12
 * Time: 14:21
 */
@Entity
@Audited
public class Rolle {
    @Id
    @TableGenerator(name = "PKGenRolle", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Rolle_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenRolle")
    private Long id;

    @Column(unique = true, nullable = false)
    @Basic(optional = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Berechtigung> berechtigungen;

    public Rolle() {
    }

    public Rolle(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Berechtigung> getBerechtigungen() {
        return berechtigungen;
    }

    public void setBerechtigungen(final Set<Berechtigung> berechtigungen) {
        this.berechtigungen = berechtigungen;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Rolle rolle = (Rolle) o;

        if (!name.equals(rolle.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Rolle");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", berechtigungen=").append(berechtigungen);
        sb.append('}');
        return sb.toString();
    }
}
