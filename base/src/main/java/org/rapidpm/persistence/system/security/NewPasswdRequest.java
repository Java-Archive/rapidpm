/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.system.security;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:52
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class NewPasswdRequest {

    @Id
    @TableGenerator(name = "PKGenNewPasswdRequest", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "NewPasswdRequest_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenNewPasswdRequest")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String vorname;
    @Basic
    private String nachname;
    @Basic
    private String email;

    //    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    //    private List<PasswdRequestHistory> passwdRequestHistories;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NewPasswdRequest)) {
            return false;
        }

        final NewPasswdRequest that = (NewPasswdRequest) o;

        if (!email.equals(that.email)) {
            return false;
        }
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }
        if (!mandantengruppe.equals(that.mandantengruppe)) {
            return false;
        }
        if (nachname != null ? !nachname.equals(that.nachname) : that.nachname != null) {
            return false;
        }
        if (vorname != null ? !vorname.equals(that.vorname) : that.vorname != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (vorname != null ? vorname.hashCode() : 0);
        result = 31 * result + (nachname != null ? nachname.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + mandantengruppe.hashCode();
        return result;
    }


    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(final String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(final String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }


    //    public List<PasswdRequestHistory> getPasswdRequestHistories(){
    //        return passwdRequestHistories;
    //    }
    //
    //    public void setPasswdRequestHistories(final List<PasswdRequestHistory> passwdRequestHistories){
    //        this.passwdRequestHistories = passwdRequestHistories;
    //    }
}