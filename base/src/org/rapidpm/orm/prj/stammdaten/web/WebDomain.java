/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.web;
/**
 * NeoScio
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:57
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;


//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
//@Cacheable(value = true)
@Entity
public class WebDomain {
    private static final Logger logger = Logger.getLogger(WebDomain.class);

    @Id
    @TableGenerator(name = "PKGenWebDomain",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "WebDomain_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenWebDomain")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


//    @ManyToOne
//    private SuchmodulFilter suchmodulfilter;

    @Basic
    private String domainName;
    @Basic
    private boolean active;
    @Basic
    private boolean blocked;
    //    @Basic
    //    private Date created;
    //    @Basic
    //    private Date modified;
    @ManyToOne(fetch = FetchType.EAGER)
    private WebDomainKlassifizierung webDomainKlassifizierung;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<WebDomainMetaData> webDomainMetaDatas;


    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    //    public Date getCreated(){
    //        return created;
    //    }
    //
    //    public void setCreated(Date created){
    //        this.created = created;
    //    }
    //
    //    public Date getModified(){
    //        return modified;
    //    }
    //
    //    public void setModified(Date modified){
    //        this.modified = modified;
    //    }


    public WebDomainKlassifizierung getWebDomainKlassifizierung() {
        return webDomainKlassifizierung;
    }

    public void setWebDomainKlassifizierung(WebDomainKlassifizierung klassifizierung) {
        this.webDomainKlassifizierung = klassifizierung;
    }


    public List<WebDomainMetaData> getWebDomainMetaDatas() {
        return webDomainMetaDatas;
    }

    public void setWebDomainMetaDatas(List<WebDomainMetaData> webDomainMetaDatas) {
        this.webDomainMetaDatas = webDomainMetaDatas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebDomain)) {
            return false;
        }

        WebDomain webDomain = (WebDomain) o;

        if (!domainName.equals(webDomain.domainName)) {
            return false;
        }
        if (getId() != null ? !getId().equals(webDomain.getId()) : webDomain.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + domainName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("WebDomain");
        sb.append("{active=").append(active);
        sb.append(", id=").append(id);
        sb.append(", domainName='").append(domainName).append('\'');
        sb.append(", blocked=").append(blocked);
        //        sb.append(", created=").append(created);
        //        sb.append(", modified=").append(modified);
        sb.append(", klassifizierung=").append(webDomainKlassifizierung);
        sb.append(", webDomainMetaDatas=").append(webDomainMetaDatas);
        sb.append('}');
        return sb.toString();
    }


}
