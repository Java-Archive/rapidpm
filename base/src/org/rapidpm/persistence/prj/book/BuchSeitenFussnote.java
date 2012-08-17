package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 17:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

@Entity
public class BuchSeitenFussnote {
    private static final Logger logger = Logger.getLogger(BuchSeitenFussnote.class);


    @TableGenerator(name = "PKGenBuchSeitenFussnote", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "BuchSeitenFussnote_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBuchSeitenFussnote")
    @Id
    private Long id;
    @Basic
    private String fussnotenzeichen;
    @Basic
    @Column(columnDefinition = "TEXT")
    private String fussnotentext;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BuchSeitenFussnote");
        sb.append("{id=").append(id);
        sb.append(", fussnotenzeichen='").append(fussnotenzeichen).append('\'');
        sb.append(", fussnotentext='").append(fussnotentext).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFussnotenzeichen() {
        return fussnotenzeichen;
    }

    public void setFussnotenzeichen(final String fussnotenzeichen) {
        this.fussnotenzeichen = fussnotenzeichen;
    }

    public String getFussnotentext() {
        return fussnotentext;
    }

    public void setFussnotentext(final String fussnotentext) {
        this.fussnotentext = fussnotentext;
    }
}
