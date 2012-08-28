package org.rapidpm.persistence.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 08:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class KommunikationsServiceUIDPartKlassifikation {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDPartKlassifikation.class);


    @Id
    @TableGenerator(name = "PKGenKommunikationsServiceUIDKlassifikation", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "KommunikationsServiceUIDKlassifikation_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenKommunikationsServiceUIDKlassifikation")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String bezeichnung;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(final String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("KommunikationsServiceUIDPartKlassifikation");
        sb.append("{id=").append(id);
        sb.append(", bezeichnung='").append(bezeichnung).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KommunikationsServiceUIDPartKlassifikation)) {
            return false;
        }

        final KommunikationsServiceUIDPartKlassifikation that = (KommunikationsServiceUIDPartKlassifikation) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
