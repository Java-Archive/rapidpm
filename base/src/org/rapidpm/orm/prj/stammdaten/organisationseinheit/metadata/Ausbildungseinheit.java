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
 * @since 02.03.2010
 *        Time: 17:48:35
 */


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "ausbildungseinheit")
public class Ausbildungseinheit {
    private static final Logger logger = Logger.getLogger(Ausbildungseinheit.class);

    @Id
    @TableGenerator(name = "PKGenAusbildungseinheit", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "Ausbildungseinheit_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenAusbildungseinheit")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    private String ausbildungsName;
    @Basic
    private boolean promotionsrecht;
    @Basic
    private boolean habilitationsrecht;
    @Basic
    private boolean staatlichAnerkannt;
    @Basic
    private boolean staatlich;
    @Basic
    private boolean privatwirtschaftlich;
    @Basic
    private boolean kirchlich;
    @Basic
    private boolean kommunal;


    public String getAusbildungsName() {
        return ausbildungsName;
    }

    public void setAusbildungsName(String ausbildungsName) {
        this.ausbildungsName = ausbildungsName;
    }

    public boolean isPromotionsrecht() {
        return promotionsrecht;
    }

    public void setPromotionsrecht(boolean promotionsrecht) {
        this.promotionsrecht = promotionsrecht;
    }

    public boolean isHabilitationsrecht() {
        return habilitationsrecht;
    }

    public void setHabilitationsrecht(boolean habilitationsrecht) {
        this.habilitationsrecht = habilitationsrecht;
    }

    public boolean isStaatlichAnerkannt() {
        return staatlichAnerkannt;
    }

    public void setStaatlichAnerkannt(final boolean staatlichAnerkannt) {
        this.staatlichAnerkannt = staatlichAnerkannt;
    }

    public boolean isStaatlich() {
        return staatlich;
    }

    public void setStaatlich(final boolean staatlich) {
        this.staatlich = staatlich;
    }

    public boolean isPrivatwirtschaftlich() {
        return privatwirtschaftlich;
    }

    public void setPrivatwirtschaftlich(final boolean privatwirtschaftlich) {
        this.privatwirtschaftlich = privatwirtschaftlich;
    }

    public boolean getKirchlich() {
        return kirchlich;
    }

    public void setKirchlich(final boolean kirchlich) {
        this.kirchlich = kirchlich;
    }

    public boolean getKommunal() {
        return kommunal;
    }

    public void setKommunal(final boolean kommunal) {
        this.kommunal = kommunal;
    }
}
