/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:51
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Mandantengruppe {
    private static final Logger logger = Logger.getLogger(Mandantengruppe.class);

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    private String mandantengruppe;


    public String getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final String mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Mandantengruppe that = (Mandantengruppe) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Mandantengruppe");
        sb.append("{id=").append(getId());
        sb.append(", mandantengruppe='").append(mandantengruppe).append('\'');
        sb.append('}');
        return sb.toString();
    }
}