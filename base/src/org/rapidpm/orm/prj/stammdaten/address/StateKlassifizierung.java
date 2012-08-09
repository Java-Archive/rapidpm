package org.rapidpm.orm.prj.stammdaten.address;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 08:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class StateKlassifizierung {
    private static final Logger logger = Logger.getLogger(StateKlassifizierung.class);

    @Id
    @TableGenerator(name = "PKGenStateKlassifizierung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "StateKlassifizierung_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenStateKlassifizierung")
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
        return "StateKlassifizierung{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                '}';
    }
}
