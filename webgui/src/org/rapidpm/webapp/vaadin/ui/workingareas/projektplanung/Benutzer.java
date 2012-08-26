/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung;
/**
 * NeoScio
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:48
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "Benutzer")

public class Benutzer {

    private static final Logger logger = Logger.getLogger(Benutzer.class);
    private Long id;
    private String login;
    private String email;
    private String passwd;


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
        sb.append(", id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", passwd='").append(passwd).append('\'');

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
}
