/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:50
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;

public class BenutzerGruppe {
    private static final Logger logger = Logger.getLogger(BenutzerGruppe.class);

    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

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