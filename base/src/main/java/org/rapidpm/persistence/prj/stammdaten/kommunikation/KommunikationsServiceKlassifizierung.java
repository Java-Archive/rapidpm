/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:33
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import javax.persistence.*;


//@NamedQueries({
//        @NamedQuery(name = "LoadKommunikationsServiceKlassifizierungPrivat", query = "select ksk from KommunikationsServiceKlassifizierung ksk where ksk.klassifizierung='privat'"),
//        @NamedQuery(name = "LoadKommunikationsServiceKlassifizierungBeruflich", query = "select ksk from KommunikationsServiceKlassifizierung ksk where ksk.klassifizierung='beruflich'")
//})


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class KommunikationsServiceKlassifizierung {

    @Id
    @TableGenerator(name = "PKGenKommunikationsServiceKlassifizierung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "KommunikationsServiceKlassifizierung_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.TABLE,
            generator = "PKGenKommunikationsServiceKlassifizierung")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String klassifizierung;


    public String getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final String klassifizierung) {
        this.klassifizierung = klassifizierung;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final KommunikationsServiceKlassifizierung that = (KommunikationsServiceKlassifizierung) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
