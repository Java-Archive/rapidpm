package org.rapidpm.orm.prj.stammdaten.organisationseinheit.metadata;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 03.03.2010
 *        Time: 09:24:59
 */


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "wirtschaftseinheit")
public class Wirtschaftseinheit {
    private static final Logger logger = Logger.getLogger(Wirtschaftseinheit.class);

    @Id
    @TableGenerator(name = "PKGenWirtschaftseinheit", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Wirtschaftseinheit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenWirtschaftseinheit")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String anzahlTochterunternehmen;

    public String getAnzahlTochterunternehmen() {
        return anzahlTochterunternehmen;
    }

    public void setAnzahlTochterunternehmen(final String anzahlTochterunternehmen) {
        this.anzahlTochterunternehmen = anzahlTochterunternehmen;
    }

    @Basic
    private String umsatzInTausendEuro;

    public String getUmsatzInTausendEuro() {
        return umsatzInTausendEuro;
    }

    public void setUmsatzInTausendEuro(final String umsatzInTausendEuro) {
        this.umsatzInTausendEuro = umsatzInTausendEuro;
    }
}
