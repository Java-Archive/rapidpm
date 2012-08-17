/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.web;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:34:00
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.persistence.*;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class WebDomainMetaData {
    private static final Logger logger = Logger.getLogger(WebDomainMetaData.class);
    @Id
    @TableGenerator(name = "PKGenWebDomainMetaData", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "WebDomainMetaData_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenWebDomainMetaData")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    //    @Basic
    //    private String created;
    @Basic
    private String propertyname;
    @Basic
    private String propertyvalue;

    //
    //    public String getCreated(){
    //        return created;
    //    }
    //
    //    public void setCreated(final String created){
    //        this.created = created;
    //    }


    public String getPropertyname() {
        return propertyname;
    }

    public void setPropertyname(final String propertyname) {
        this.propertyname = propertyname;
    }

    public String getPropertyvalue() {
        return propertyvalue;
    }

    public void setPropertyvalue(final String propertyvalue) {
        this.propertyvalue = propertyvalue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebDomainMetaData)) {
            return false;
        }

        final WebDomainMetaData that = (WebDomainMetaData) o;

        if (propertyname != null ? !propertyname.equals(that.propertyname) : that.propertyname != null) {
            return false;
        }
        //        if(!created.equals(that.created)){
        //            return false;
        //        }
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }
        if (propertyvalue != null ? !propertyvalue.equals(that.propertyvalue) : that.propertyvalue != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        //        result = 31 * result + created.hashCode();
        result = 31 * result + (propertyname != null ? propertyname.hashCode() : 0);
        result = 31 * result + (propertyvalue != null ? propertyvalue.hashCode() : 0);
        return result;
    }
}
