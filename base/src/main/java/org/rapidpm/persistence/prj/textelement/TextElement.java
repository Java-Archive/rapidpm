package org.rapidpm.persistence.prj.textelement;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
import org.rapidpm.persistence.prj.textelement.kommentar.TextElementKommentar;

import javax.persistence.*;
import java.util.List;

@Entity
public class TextElement implements Comparable<TextElement> {

    public static final String ID = "id";
    public static final String UNLOCKED = "freigeschaltet";
    public static final String NUMMER = "textElementNummer";
    public static final String BEZEICHNUNG = "bezeichnung";
    public static final String KOMMENTARE = "kommentarliste";
    public static final String TEXT = "text";


    @TableGenerator(name = "PKGenTextElement", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "TextElement_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenTextElement")
    @Id
    private Long id;
    @Basic
    private Boolean freigeschaltet;
    @Basic
    private Integer textElementNummer;
    @Basic
    private String bezeichnung;
    @Basic
    @Column(length = 20000)
    private String text;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<TextElementKommentar> kommentarliste;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    public Integer getTextElementNummer() {
        return textElementNummer;
    }

    public void setTextElementNummer(final Integer textElementnummer) {
        this.textElementNummer = textElementnummer;
    }


    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(final String ueberschrift) {
        this.bezeichnung = ueberschrift;
    }

    public Boolean getFreigeschaltet() {
        return freigeschaltet;
    }

    public void setFreigeschaltet(final Boolean freigeschaltet) {
        this.freigeschaltet = freigeschaltet;
    }

    public List<TextElementKommentar> getKommentarliste() {
        return kommentarliste;
    }

    public void setKommentarliste(List<TextElementKommentar> kommentarliste) {
        this.kommentarliste = kommentarliste;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(TextElement o) {
        if(this.getId() > o.getId()){
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextElement)) return false;

        TextElement that = (TextElement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
