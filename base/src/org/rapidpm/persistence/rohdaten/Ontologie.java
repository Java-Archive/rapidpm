/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.rohdaten;

import org.rapidpm.persistence.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 24.10.2010
 *        Time: 21:02:43
 */
//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Ontologie {
    private static final Logger logger = Logger.getLogger(Ontologie.class);

    @Id
    @TableGenerator(name = "PKGenOntologie", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Ontologie_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenOntologie")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String symbolischerName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OntologieEntry> entryListe;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;


    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    public String getSymbolischerName() {
        return symbolischerName;
    }

    public void setSymbolischerName(final String symbolischerName) {
        this.symbolischerName = symbolischerName;
    }

    public List<OntologieEntry> getEntryListe() {
        return entryListe;
    }

    public void setEntryListe(final List<OntologieEntry> entryListe) {
        this.entryListe = entryListe;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Ontologie");
        sb.append("{symbolischerName='").append(symbolischerName).append('\'');
        sb.append(", entryListe=").append(entryListe);
        sb.append(", id=").append(getId());
        sb.append(", mandantengruppe=").append(mandantengruppe);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ontologie)) {
            return false;
        }

        final Ontologie ontologie = (Ontologie) o;

        if (!entryListe.equals(ontologie.entryListe)) {
            return false;
        }
        if (!getId().equals(ontologie.getId())) {
            return false;
        }
        if (!mandantengruppe.equals(ontologie.mandantengruppe)) {
            return false;
        }
        if (!symbolischerName.equals(ontologie.symbolischerName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = symbolischerName.hashCode();
        result = 31 * result + entryListe.hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + mandantengruppe.hashCode();
        return result;
    }

}
