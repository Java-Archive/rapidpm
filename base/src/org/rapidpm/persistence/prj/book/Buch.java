package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 16:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.prj.book.kommentar.BuchKommentar;
import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

@Entity
public class Buch {
    private static final Logger logger = Logger.getLogger(Buch.class);

    @TableGenerator(name = "PKGenBuch", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Buch_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenBuch")
    @Id
    private Long id;


    @Basic
    private String titel;
    @Basic
    private String untertitel;
    @Basic
    private String summary;
    @Basic
    private String version;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<BuchKapitel> buchKapitelListe;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Benutzer> autorenliste;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Benutzer> leserliste;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<BuchKommentar> kommentarliste;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<BuchKapitel> getBuchKapitelListe() {
        return buchKapitelListe;
    }

    public void setBuchKapitelListe(final List<BuchKapitel> buchKapitelListe) {
        this.buchKapitelListe = buchKapitelListe;
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(final String titel) {
        this.titel = titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public void setUntertitel(final String untertitel) {
        this.untertitel = untertitel;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public List<Benutzer> getLeserliste() {
        return leserliste;
    }

    public void setLeserliste(final List<Benutzer> leserliste) {
        this.leserliste = leserliste;
    }

    public List<Benutzer> getAutorenliste() {
        return autorenliste;
    }

    public void setAutorenliste(final List<Benutzer> autorenliste) {
        this.autorenliste = autorenliste;
    }


    public List<BuchKommentar> getKommentarliste() {
        return kommentarliste;
    }

    public void setKommentarliste(final List<BuchKommentar> kommentarliste) {
        this.kommentarliste = kommentarliste;
    }
}
