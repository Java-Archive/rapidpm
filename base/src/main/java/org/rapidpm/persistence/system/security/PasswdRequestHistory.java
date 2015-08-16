/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:53
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
//@Entity
@Deprecated
public class PasswdRequestHistory {
    private static final Logger logger = Logger.getLogger(PasswdRequestHistory.class);


    @Id
    @TableGenerator(name = "PKGenPasswdRequestHistory", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "PasswdRequestHistory_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenPasswdRequestHistory")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private Date created;
    @Basic
    private String kommentar;

    @ManyToOne(fetch = FetchType.EAGER)
    private PasswdRequestStatus passwdRequestStatus;


    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(final String kommentar) {
        this.kommentar = kommentar;
    }


    public PasswdRequestStatus getPasswdRequestStatus() {
        return passwdRequestStatus;
    }

    public void setPasswdRequestStatus(final PasswdRequestStatus passwdRequestStatus) {
        this.passwdRequestStatus = passwdRequestStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PasswdRequestHistory that = (PasswdRequestHistory) o;

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