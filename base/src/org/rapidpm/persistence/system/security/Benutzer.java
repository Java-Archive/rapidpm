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

import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "Benutzer")
@Entity
@Audited
public class Benutzer {

    public static final String ID ="id";
    public static final String HIDDEN = "hidden";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWD = "passwd";
    public static final String LASTLOGIN = "lastLogin";

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
    private Set<Berechtigung> berechtigungen;


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

    public Set<Berechtigung> getBerechtigungen() {
        return berechtigungen;
    }

    public void setBerechtigungen(final Set<Berechtigung> berechtigungen) {
        this.berechtigungen = berechtigungen;
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
                ", berechtigungen=" + berechtigungen +
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
