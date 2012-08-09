/*
 * Copyright (c) 2009. This is part of the NeoScio Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.orm.system.security;
/**
 * NeoScio
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:50
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
//
//@NamedQueries({
//        @NamedQuery(name = "LoadBenutzerGruppeByName", query = "select bg from BenutzerGruppe  bg where bg.gruppenName=:gruppenname")
//})
//

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
@Audited
//@Table(name = "benutzer_gruppe")
public class BenutzerGruppe {
    private static final Logger logger = Logger.getLogger(BenutzerGruppe.class);

    @Id
    @TableGenerator(name = "PKGenBenutzerGruppe", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "BenutzerGruppe_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenBenutzerGruppe")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(unique = true)
    @Basic
    @NotNull
    private String gruppenname;

    public String getGruppenname() {
        return gruppenname;
    }

    public void setGruppenname(final String gruppenname) {
        this.gruppenname = gruppenname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final BenutzerGruppe that = (BenutzerGruppe) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BenutzerGruppe");
        sb.append("{id=").append(id);
        sb.append(", gruppenName='").append(gruppenname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}