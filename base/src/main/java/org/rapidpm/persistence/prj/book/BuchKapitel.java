package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 17:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.book.kommentar.BuchKapitelKommentar;

import javax.persistence.*;
import java.util.List;

@Entity
public class BuchKapitel {
    private static final Logger logger = Logger.getLogger(BuchKapitel.class);

    @TableGenerator(name = "PKGenBuchKapitel", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "BuchKapitel_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBuchKapitel")
    @Id
    private Long id;
    @Basic
    private Boolean freigeschaltet;
    @Basic
    private Integer kapitelnummer;
    @Basic
    private String ueberschrift;
    @Basic
    private String untertitel;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BuchKapitelKommentar> kommentarliste;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    public Integer getKapitelnummer() {
        return kapitelnummer;
    }

    public void setKapitelnummer(final Integer kapitelnummer) {
        this.kapitelnummer = kapitelnummer;
    }


    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(final String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public void setUntertitel(final String untertitel) {
        this.untertitel = untertitel;
    }

    public Boolean getFreigeschaltet() {
        return freigeschaltet;
    }

    public void setFreigeschaltet(final Boolean freigeschaltet) {
        this.freigeschaltet = freigeschaltet;
    }

    public List<BuchKapitelKommentar> getKapitelkommentarliste() {
        return kommentarliste;
    }

    public void setKapitelkommentarliste(final List<BuchKapitelKommentar> kapitelkommentarliste) {
        this.kommentarliste = kapitelkommentarliste;
    }
}
