/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:29
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.address.Adresse;
import org.rapidpm.persistence.prj.stammdaten.kommunikation.KommunikationsServiceUID;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Ausbildungseinheit;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Verwaltungseinheit;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata.Wirtschaftseinheit;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomain;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.*;
import java.util.List;


//@Cacheable(value = true)
@Entity
public class Organisationseinheit {
    private static final Logger logger = Logger.getLogger(Organisationseinheit.class);

    @Id
    @TableGenerator(name = "PKGenOrganisationseinheit",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "Organisationseinheit_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenOrganisationseinheit")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String organisationsName;
    @Basic
    private String gruendungsdatum;

    @Basic(optional = true)
    private String bvdepId;

    //    @Basic
    //    private Date created;
    //
    //    @Basic
    //    private Date modified;

    @Basic
    private boolean active;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<TaetigkeitsfeldAssoc> taetigkeitsfeldAssocs;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Mandantengruppe mandantengruppe;
    //    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    //    private List<Mandantengruppe> mandantengruppen;

    //    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BrancheAssoc> brancheAssocs;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<KommunikationsServiceUID> kommunikationsServiceUIDs;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<OrganisationseinheitMetaData> metadata;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Gesellschaftsform gesellschaftsform;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Adresse> adressen;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<WebDomain> webDomains;

    @OneToOne(cascade = CascadeType.PERSIST, optional = true, fetch = FetchType.LAZY)
    private Ausbildungseinheit ausbildungseinheit;

    @OneToOne(cascade = CascadeType.PERSIST, optional = true, fetch = FetchType.LAZY)
    private Verwaltungseinheit verwaltungseinheit;

    @OneToOne(cascade = CascadeType.PERSIST, optional = true, fetch = FetchType.LAZY)
    private Wirtschaftseinheit wirtschaftseinheit;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisationseinheit)) {
            return false;
        }

        final Organisationseinheit that = (Organisationseinheit) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Organisationseinheit");
        sb.append("{id=").append(getId());
        sb.append(", organisationsName='").append(organisationsName).append('\'');
        //        sb.append(", mandantengruppen=").append(mandantengruppen);
        sb.append('}');
        return sb.toString();
    }

    public String getOrganisationsName() {
        return organisationsName;
    }

    public void setOrganisationsName(final String organisationsName) {
        this.organisationsName = organisationsName;
    }

    public String getGruendungsdatum() {
        return gruendungsdatum;
    }

    public void setGruendungsdatum(final String gruendungsdatum) {
        this.gruendungsdatum = gruendungsdatum;
    }

    public String getBvdepId() {
        return bvdepId;
    }

    public void setBvdepId(final String bvdepId) {
        this.bvdepId = bvdepId;
    }

    //    public Date getCreated(){
    //        return created;
    //    }
    //
    //    public void setCreated(final Date created){
    //        this.created = created;
    //    }
    //
    //    public Date getModified(){
    //        return modified;
    //    }
    //
    //    public void setModified(final Date modified){
    //        this.modified = modified;
    //    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<TaetigkeitsfeldAssoc> getTaetigkeitsfeldAssocs() {
        return taetigkeitsfeldAssocs;
    }

    public void setTaetigkeitsfeldAssocs(final List<TaetigkeitsfeldAssoc> taetigkeitsfeldAssocs) {
        this.taetigkeitsfeldAssocs = taetigkeitsfeldAssocs;
    }

    //    public List<Mandantengruppe> getMandantengruppen(){
    //        return mandantengruppen;
    //    }
    //
    //    public void setMandantengruppen(final List<Mandantengruppe> mandantengruppe){
    //        this.mandantengruppen = mandantengruppe;
    //    }

    public Mandantengruppe getMandantengruppe() {
        return mandantengruppe;
    }

    public void setMandantengruppe(final Mandantengruppe mandantengruppe) {
        this.mandantengruppe = mandantengruppe;
    }

    public List<BrancheAssoc> getBrancheAssocs() {
        return brancheAssocs;
    }

    public void setBrancheAssocs(final List<BrancheAssoc> brancheAssocs) {
        this.brancheAssocs = brancheAssocs;
    }

    public List<KommunikationsServiceUID> getKommunikationsServiceUIDs() {
        return kommunikationsServiceUIDs;
    }

    public void setKommunikationsServiceUIDs(final List<KommunikationsServiceUID> kommunikationsServiceUIDs) {
        this.kommunikationsServiceUIDs = kommunikationsServiceUIDs;
    }

    public List<OrganisationseinheitMetaData> getMetadata() {
        return metadata;
    }

    public void setMetadata(final List<OrganisationseinheitMetaData> metadata) {
        this.metadata = metadata;
    }

    public Gesellschaftsform getGesellschaftsform() {
        return gesellschaftsform;
    }

    public void setGesellschaftsform(final Gesellschaftsform gesellschaftsform) {
        this.gesellschaftsform = gesellschaftsform;
    }

    public List<Adresse> getAdressen() {
        return adressen;
    }

    public void setAdressen(final List<Adresse> adressen) {
        this.adressen = adressen;
    }

    public List<WebDomain> getWebdomains() {
        return webDomains;
    }

    public void setWebdomains(final List<WebDomain> webdomains) {
        this.webDomains = webdomains;
    }

    public Ausbildungseinheit getAusbildungseinheit() {
        return ausbildungseinheit;
    }

    public void setAusbildungseinheit(final Ausbildungseinheit ausbildungseinheit) {
        this.ausbildungseinheit = ausbildungseinheit;
    }

    public Verwaltungseinheit getVerwaltungseinheit() {
        return verwaltungseinheit;
    }

    public void setVerwaltungseinheit(final Verwaltungseinheit verwaltungseinheit) {
        this.verwaltungseinheit = verwaltungseinheit;
    }

    public Wirtschaftseinheit getWirtschaftseinheit() {
        return wirtschaftseinheit;
    }

    public void setWirtschaftseinheit(final Wirtschaftseinheit wirtschaftseinheit) {
        this.wirtschaftseinheit = wirtschaftseinheit;
    }
}
