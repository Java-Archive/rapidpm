package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

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
 *        Time: 13:44:24
 */


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class BrancheKlassifizierung {
    private static final Logger logger = Logger.getLogger(BrancheKlassifizierung.class);

    @Id
    @TableGenerator(name = "PKGenBrancheKlassifizierung", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "BrancheKlassifizierung_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenBrancheKlassifizierung")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String klassifizierung;


    public String getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final String klassifizierung) {
        this.klassifizierung = klassifizierung;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BrancheKlassifizierung that = (BrancheKlassifizierung) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }
        if (!klassifizierung.equals(that.klassifizierung)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + klassifizierung.hashCode();
        return result;
    }
}
