/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:48
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import com.tinkerpop.blueprints.Vertex;
import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.system.security.berechtigungen.Rolle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;

public class Benutzer {

    public static final String CLASS = "Benutzer";

    public static final String ID ="id";
    public static final String ACTIVE = "active";
    public static final String HIDDEN = "hidden";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWD = "passwd";
    public static final String LASTLOGIN = "lastLogin";

    private static final Logger logger = Logger.getLogger(Benutzer.class);

    private String id;
    private Boolean hidden;
    private String login;
    private String email;


    private String passwd;
    private Date lastLogin;
    private Integer failedLogins;
    private Boolean active;
    private Date validFrom;
    private Date validUntil;

    private Mandantengruppe mandantengruppe;
    private BenutzerGruppe benutzerGruppe;
    private BenutzerWebapplikation benutzerWebapplikation;
    private Set<Rolle> rollen;

    public Benutzer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Der Login darf nur unique pro Mandantengruppe sein.
    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(final String passwd) {
        this.passwd = passwd;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(final Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(final Integer failedLogins) {
        this.failedLogins = failedLogins;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(final Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(final Date validUntil) {
        this.validUntil = validUntil;
    }


    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }


    public BenutzerGruppe getBenutzerGruppe() {
        return benutzerGruppe;
    }

    public void setBenutzerGruppe(final BenutzerGruppe benutzerGruppe) {
        this.benutzerGruppe = benutzerGruppe;
    }


    public BenutzerWebapplikation getBenutzerWebapplikation() {
        return benutzerWebapplikation;
    }

    public void setBenutzerWebapplikation(final BenutzerWebapplikation benutzerWebapplikation) {
        this.benutzerWebapplikation = benutzerWebapplikation;
    }

    public Set<Rolle> getRollen() {
        return rollen;
    }

    public void setRollen(final Set<Rolle> rollen) {
        this.rollen = rollen;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benutzer)) return false;

        Benutzer benutzer = (Benutzer) o;

        if (id != null ? !id.equals(benutzer.id) : benutzer.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "id=" + id +
                ", hidden=" + hidden +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", lastLogin=" + lastLogin +
                ", failedLogins=" + failedLogins +
                ", active=" + active +
                ", validFrom=" + validFrom +
                ", validUntil=" + validUntil +
                ", mandantengruppe=" + mandantengruppe +
                ", benutzerGruppe=" + benutzerGruppe +
                ", benutzerWebapplikation=" + benutzerWebapplikation +
                ", rollen=" + rollen +
                '}';
    }

//    public List<SearchQuery> getSearchQueries(){
    //        return searchQueries;
    //    }
    //
    //    public void setSearchQueries(final List<SearchQuery> searchQueries){
    //        this.searchQueries = searchQueries;
    //    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(final Boolean hidden) {
        this.hidden = hidden;
    }
}
