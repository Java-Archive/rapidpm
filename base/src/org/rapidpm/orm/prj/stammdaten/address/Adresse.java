/*
 * Copyright (c) 2009. This is part of the NeoScio Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.orm.prj.stammdaten.address;
/**
 * NeoScio
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:18
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "adresse")
public class Adresse {
    private static final Logger logger = Logger.getLogger(Adresse.class);

    @Id
    @TableGenerator(name = "PKGenAdresse", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Adresse_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenAdresse")
    private Long id;

    @Basic
    private String strasse;
    @Basic
    private String hausnummer;
    @Basic
    private String notiz;

    @Basic
    private String ortsname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.REFRESH)
    private AdressKlassifizierung klassifizierung;

    @Basic private String plz;

    @Basic boolean grosskundenPLZ;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REFRESH)
    private State state;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Postfach> postfachListe;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresse)) {
            return false;
        }

        final Adresse adresse = (Adresse) o;

        if (grosskundenPLZ != adresse.grosskundenPLZ) {
            return false;
        }
        if (hausnummer != null ? !hausnummer.equals(adresse.hausnummer) : adresse.hausnummer != null) {
            return false;
        }
        if (id != null ? !id.equals(adresse.id) : adresse.id != null) {
            return false;
        }
        if (klassifizierung != null ? !klassifizierung.equals(adresse.klassifizierung) : adresse.klassifizierung != null) {
            return false;
        }
        if (notiz != null ? !notiz.equals(adresse.notiz) : adresse.notiz != null) {
            return false;
        }
        if (ortsname != null ? !ortsname.equals(adresse.ortsname) : adresse.ortsname != null) {
            return false;
        }
        if (plz != null ? !plz.equals(adresse.plz) : adresse.plz != null) {
            return false;
        }
        if (postfachListe != null ? !postfachListe.equals(adresse.postfachListe) : adresse.postfachListe != null) {
            return false;
        }
        if (state != null ? !state.equals(adresse.state) : adresse.state != null) {
            return false;
        }
        if (strasse != null ? !strasse.equals(adresse.strasse) : adresse.strasse != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (strasse != null ? strasse.hashCode() : 0);
        result = 31 * result + (hausnummer != null ? hausnummer.hashCode() : 0);
        result = 31 * result + (notiz != null ? notiz.hashCode() : 0);
        result = 31 * result + (ortsname != null ? ortsname.hashCode() : 0);
        result = 31 * result + (klassifizierung != null ? klassifizierung.hashCode() : 0);
        result = 31 * result + (plz != null ? plz.hashCode() : 0);
        result = 31 * result + (grosskundenPLZ ? 1 : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postfachListe != null ? postfachListe.hashCode() : 0);
        return result;
    }

    public boolean getGrosskundenPLZ() {
        return grosskundenPLZ;
    }

    public void setGrosskundenPLZ(final boolean grosskundenPLZ) {
        this.grosskundenPLZ = grosskundenPLZ;
    }

    public List<Postfach> getPostfachListe() {
        return postfachListe;
    }

    public void setPostfachListe(final List<Postfach> postfachListe) {
        this.postfachListe = postfachListe;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(final String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public AdressKlassifizierung getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final AdressKlassifizierung klassifizierung) {
        this.klassifizierung = klassifizierung;
    }

    public String getNotiz() {
        return notiz;
    }

    public void setNotiz(final String notiz) {
        this.notiz = notiz;
    }

    public String getOrtsname() {
        return ortsname;
    }

    public void setOrtsname(final String ortsname) {
        this.ortsname = ortsname;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(final String plz) {
        this.plz = plz;
    }

    public State getState() {
        return state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(final String strasse) {
        this.strasse = strasse;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Adresse{" +
                "id=" + id +
                ", strasse='" + strasse + '\'' +
                ", hausnummer='" + hausnummer + '\'' +
                ", notiz='" + notiz + '\'' +
                ", ortsname='" + ortsname + '\'' +
                ", klassifizierung=" + klassifizierung +
                ", plz='" + plz + '\'' +
                ", grosskundenPLZ=" + grosskundenPLZ +
                ", state=" + state +
                ", postfachListe=" + postfachListe +
                '}';
    }
}
