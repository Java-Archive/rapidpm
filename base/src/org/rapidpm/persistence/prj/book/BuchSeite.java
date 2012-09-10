package org.rapidpm.persistence.prj.book;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 17:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.prj.book.kommentar.BuchSeitenKommentar;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

@Entity
public class BuchSeite {
    private static final Logger logger = Logger.getLogger(BuchSeite.class);


    @TableGenerator(name = "PKGenBuchSeite", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "BuchSeite_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBuchSeite")
    @Id
    private Long id;

    @Basic
    private Boolean freigeschaltet;
    @Basic
    private Integer seitennummer;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BuchSeitenKommentar> kommentarliste;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BuchAbsatz> absatzliste;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BuchSeitenFussnote> fusnotenliste;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Boolean getFreigeschaltet() {
        return freigeschaltet;
    }

    public void setFreigeschaltet(final Boolean freigeschaltet) {
        this.freigeschaltet = freigeschaltet;
    }

    public Integer getSeitennummer() {
        return seitennummer;
    }

    public void setSeitennummer(final Integer seitennummer) {
        this.seitennummer = seitennummer;
    }

    public List<BuchSeitenKommentar> getKommentarliste() {
        return kommentarliste;
    }

    public void setKommentarliste(final List<BuchSeitenKommentar> kommentarliste) {
        this.kommentarliste = kommentarliste;
    }

    public List<BuchAbsatz> getAbsatzliste() {
        return absatzliste;
    }

    public void setAbsatzliste(final List<BuchAbsatz> absatzliste) {
        this.absatzliste = absatzliste;
    }

    public List<BuchSeitenFussnote> getFusnotenliste() {
        return fusnotenliste;
    }

    public void setFusnotenliste(final List<BuchSeitenFussnote> fusnotenliste) {
        this.fusnotenliste = fusnotenliste;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BuchSeite");
        sb.append("{id=").append(id);
        sb.append(", freigeschaltet=").append(freigeschaltet);
        sb.append(", seitennummer=").append(seitennummer);
        sb.append(", kommentarliste=").append(kommentarliste);
        sb.append(", absatzliste=").append(absatzliste);
        sb.append(", fusnotenliste=").append(fusnotenliste);
        sb.append('}');
        return sb.toString();
    }
}
