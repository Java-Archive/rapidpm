package org.rapidpm.persistence.prj.textelement.kommentar;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:13
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AbsatzKommentar {
    private static final Logger logger = Logger.getLogger(AbsatzKommentar.class);


    @TableGenerator(name = "PKGenAbsatzKommentar", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "AbsatzKommentar_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenAbsatzKommentar")
    @Id
    private Long id;

    @Basic
    private String kommentar;
    @Basic
    private Date datum;

    @OneToOne
    private Benutzer kommentator;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(final String kommentar) {
        this.kommentar = kommentar;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(final Date datum) {
        this.datum = datum;
    }

    public Benutzer getKommentator() {
        return kommentator;
    }

    public void setKommentator(final Benutzer kommentator) {
        this.kommentator = kommentator;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AbsatzKommentar");
        sb.append("{id=").append(id);
        sb.append(", kommentar='").append(kommentar).append('\'');
        sb.append(", datum=").append(datum);
        //        sb.append(", kommentator=").append(kommentator);
        sb.append('}');
        return sb.toString();
    }
}

