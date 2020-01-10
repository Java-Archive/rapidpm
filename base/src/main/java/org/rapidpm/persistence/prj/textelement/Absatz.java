package org.rapidpm.persistence.prj.textelement;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:11
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
import org.rapidpm.persistence.prj.textelement.kommentar.AbsatzKommentar;

import javax.persistence.*;
import java.util.List;

@Entity
public class Absatz {

    public static final String ID = "id";
    public static final String UNLOCKED = "freigeschaltet";
    public static final String NUMMER = "absatznummer";
    public static final String TEXT = "text";
    public static final String KOMMENTARE = "kommentarliste";


    @TableGenerator(name = "PKGenAbsatz", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "Absatz_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenAbsatz")
    @Id
    private Long id;
    @Basic
    private Boolean freigeschaltet;
    @Basic
    private Integer absatznummer;

    @Basic
//    @Column(columnDefinition = "String")
    @Column(length = 20000)
    private String text;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<AbsatzKommentar> kommentarliste;


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

    public List<AbsatzKommentar> getKommentarliste() {
        return kommentarliste;
    }

    public void setKommentarliste(final List<AbsatzKommentar> kommentarliste) {
        this.kommentarliste = kommentarliste;
    }
}
