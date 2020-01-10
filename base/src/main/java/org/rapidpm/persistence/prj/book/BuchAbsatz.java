package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 17:01
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.prj.book.kommentar.BuchAbsatzKommentar;

import javax.persistence.*;
import java.util.List;

@Entity
public class BuchAbsatz {

    @TableGenerator(name = "PKGenBuchAbsatz", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "BuchAbsatz_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBuchAbsatz")
    @Id
    private Long id;
    @Basic
    private Boolean freigeschaltet;
    @Basic
    private Integer absatznummer;

    @Basic
//    @Column(columnDefinition = "TEXT")
    @Column(length = 20000)
    private String text;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BuchAbsatzKommentar> kommentarliste;


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

    public Integer getAbsatznummer() {
        return absatznummer;
    }

    public void setAbsatznummer(final Integer absatznummer) {
        this.absatznummer = absatznummer;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public List<BuchAbsatzKommentar> getKommentarliste() {
        return kommentarliste;
    }

    public void setKommentarliste(final List<BuchAbsatzKommentar> kommentarliste) {
        this.kommentarliste = kommentarliste;
    }
}
