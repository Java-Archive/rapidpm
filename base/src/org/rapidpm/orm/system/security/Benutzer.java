/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.system.security;
/**
 * NeoScio
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:48
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.rapidpm.orm.system.security.berechtigungen.Berechtigung;
import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "Benutzer")
@Entity
@Audited
public class Benutzer {
    private static final Logger logger = Logger.getLogger(Benutzer.class);

    @Id
    @TableGenerator(name = "PKGenBenutzer",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "Benutzer_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBenutzer")
    private Long id;


    @Basic
    private Boolean hidden;

    @Basic
    @NotNull
    @Size(min = 3)
    private String login;

    @Basic  //TODO unique Constrain fehlt evtl pro Mandant
    private String email;


    @Basic
    @NotNull //@Size(min = 8)
    private String passwd;
    @Basic
    private Date lastLogin;
    @Basic
    private Integer failedLogins;
    @Basic
    private Boolean active;
    @Basic
    private Date validFrom;
    @Basic
    private Date validUntil;

    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = false, fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;

    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = false, fetch = FetchType.EAGER)
    private BenutzerGruppe benutzerGruppe;

    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = false, fetch = FetchType.EAGER)
    private BenutzerWebapplikation benutzerWebapplikation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Berechtigung> berechtigungen;


    public Benutzer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    public List<Berechtigung> getBerechtigungen() {
        return berechtigungen;
    }

    public void setBerechtigungen(final List<Berechtigung> berechtigungen) {
        this.berechtigungen = berechtigungen;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Benutzer)) return false;
//
//        Benutzer benutzer = (Benutzer) o;
//
//        if (!active.equals(benutzer.active)) return false;
//        if (benutzerGruppe != null ? !benutzerGruppe.equals(benutzer.benutzerGruppe) : benutzer.benutzerGruppe != null)
//            return false;
//        if (benutzerWebapplikation != null ? !benutzerWebapplikation.equals(benutzer.benutzerWebapplikation) : benutzer.benutzerWebapplikation != null)
//            return false;
//        if (!berechtigungen.equals(benutzer.berechtigungen)) return false;
//        if (!hidden.equals(benutzer.hidden)) return false;
//        if (id != null ? !id.equals(benutzer.id) : benutzer.id != null) return false;
//        if (!login.equals(benutzer.login)) return false;
//        if (!mandantengruppe.equals(benutzer.mandantengruppe)) return false;
//        if (!passwd.equals(benutzer.passwd)) return false;
//        if (!validFrom.equals(benutzer.validFrom)) return false;
//        if (!validUntil.equals(benutzer.validUntil)) return false;
//
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id != null ? id.hashCode() : 0;
//        result = 31 * result + hidden.hashCode();
//        result = 31 * result + login.hashCode();
//        result = 31 * result + passwd.hashCode();
//        result = 31 * result + active.hashCode();
//        result = 31 * result + validFrom.hashCode();
//        result = 31 * result + validUntil.hashCode();
//        result = 31 * result + mandantengruppe.hashCode();
//        result = 31 * result + berechtigungen.hashCode();
//        return result;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benutzer)) return false;

        Benutzer benutzer = (Benutzer) o;

        if (id != null ? !id.equals(benutzer.id) : benutzer.id != null) return false;
        if (login != null ? !login.equals(benutzer.login) : benutzer.login != null) return false;
        if (email != null ? !email.equals(benutzer.email) : benutzer.email != null) return false;

        if (passwd != null ? !passwd.equals(benutzer.passwd) : benutzer.passwd != null) return false;
        if (active != null ? !active.equals(benutzer.active) : benutzer.active != null) return false;
        if (benutzerGruppe != null ? !benutzerGruppe.equals(benutzer.benutzerGruppe) : benutzer.benutzerGruppe != null)
            return false;
        if (benutzerWebapplikation != null ? !benutzerWebapplikation.equals(benutzer.benutzerWebapplikation) : benutzer.benutzerWebapplikation != null)
            return false;
        if (berechtigungen != null ? !berechtigungen.equals(benutzer.berechtigungen) : benutzer.berechtigungen != null)
            return false;
        if (failedLogins != null ? !failedLogins.equals(benutzer.failedLogins) : benutzer.failedLogins != null)
            return false;
        if (hidden != null ? !hidden.equals(benutzer.hidden) : benutzer.hidden != null) return false;
        if (lastLogin != null ? !lastLogin.equals(benutzer.lastLogin) : benutzer.lastLogin != null) return false;
        if (mandantengruppe != null ? !mandantengruppe.equals(benutzer.mandantengruppe) : benutzer.mandantengruppe != null)
            return false;
        if (validFrom != null ? !validFrom.equals(benutzer.validFrom) : benutzer.validFrom != null) return false;
        if (validUntil != null ? !validUntil.equals(benutzer.validUntil) : benutzer.validUntil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Benutzer");
        sb.append("{active=").append(active);
        sb.append(", id=").append(id);
        sb.append(", hidden=").append(hidden);
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", passwd='").append(passwd).append('\'');
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", failedLogins=").append(failedLogins);
        sb.append(", validFrom=").append(validFrom);
        sb.append(", validUntil=").append(validUntil);
        sb.append(", mandantengruppe=").append(mandantengruppe != null ? mandantengruppe.getId() : "");
        sb.append(", benutzerGruppe=").append(benutzerGruppe != null ? benutzerGruppe.getId() : "");
        sb.append(", benutzerWebapplikation=").append(benutzerWebapplikation != null ? benutzerWebapplikation.getId() : "");
        sb.append(", berechtigungen=").append(berechtigungen);
        sb.append('}');
        return sb.toString();
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
