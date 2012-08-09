package org.rapidpm.orm.prj.stammdaten.organisationseinheit;

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
 *        Time: 10:55:46
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class OrganisationseinheitMetaData {
    private static final Logger logger = Logger.getLogger(OrganisationseinheitMetaData.class);

    @Id
    @TableGenerator(name = "PKGenOrganisationseinheitMetaData", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "OrganisationseinheitMetaData_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenOrganisationseinheitMetaData")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String metaDataValue;

    public String getMetaDataValue() {
        return metaDataValue;
    }

    public void setMetaDataValue(final String metaDataValue) {
        this.metaDataValue = metaDataValue;
    }

    @Basic
    private String metaDataName;

    public String getMetaDataName() {
        return metaDataName;
    }

    public void setMetaDataName(final String metaDataName) {
        this.metaDataName = metaDataName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationseinheitMetaData)) {
            return false;
        }

        final OrganisationseinheitMetaData that = (OrganisationseinheitMetaData) o;

        if (id != that.id) {
            return false;
        }
        if (!metaDataName.equals(that.metaDataName)) {
            return false;
        }
        if (!metaDataValue.equals(that.metaDataValue)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + metaDataValue.hashCode();
        result = 31 * result + metaDataName.hashCode();
        return result;
    }
}
