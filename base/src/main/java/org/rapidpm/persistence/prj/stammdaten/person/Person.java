/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.person;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:39
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.rapidpm.persistence.prj.stammdaten.address.Adresse;
import org.rapidpm.persistence.prj.stammdaten.kommunikation.KommunikationsServiceUID;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomain;
import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Person {
    private static final Logger logger = Logger.getLogger(Person.class);

    @Id
    @TableGenerator(name = "PKGenPerson",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "Person_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPerson")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private Date geburtsdatum;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REFRESH)
    private Geschlecht geschlecht;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Anrede anrede;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<PersonenName> namen;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Benutzer> benutzer;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<KommunikationsServiceUID> kommunikationsServiceUIDs;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Adresse> adressen;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<WebDomain> webdomains;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Titel> titel;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }

        final Person person = (Person) o;

        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) {
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
        return "Person{" + "id=" + getId() + ", geburtsdatum='" + geburtsdatum + '\'' + '}';
    }

    public List<Adresse> getAdressen() {
        return adressen;
    }

    public void setAdressen(final List<Adresse> adressen) {
        this.adressen = adressen;
    }

    public Anrede getAnrede() {
        return anrede;
    }

    public void setAnrede(final Anrede anrede) {
        this.anrede = anrede;
    }

    public List<Benutzer> getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(final List<Benutzer> benutzer) {
        this.benutzer = benutzer;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(final Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(final Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    public List<KommunikationsServiceUID> getKommunikationsServiceUIDs() {
        return kommunikationsServiceUIDs;
    }

    public void setKommunikationsServiceUIDs(final List<KommunikationsServiceUID> kommunikationsServiceUIDs) {
        this.kommunikationsServiceUIDs = kommunikationsServiceUIDs;
    }

    public List<PersonenName> getNamen() {
        return namen;
    }

    public void setNamen(final List<PersonenName> namen) {
        this.namen = namen;
    }

    //    public List<Position> getPositionen(){
    //        return positionen;
    //    }
    //
    //    public void setPositionen(final List<Position> positionen){
    //        this.positionen = positionen;
    //    }

    public List<Titel> getTitel() {
        return titel;
    }

    public void setTitel(final List<Titel> titel) {
        this.titel = titel;
    }

    public List<WebDomain> getWebdomains() {
        return webdomains;
    }

    public void setWebdomains(final List<WebDomain> webdomains) {
        this.webdomains = webdomains;
    }
}
