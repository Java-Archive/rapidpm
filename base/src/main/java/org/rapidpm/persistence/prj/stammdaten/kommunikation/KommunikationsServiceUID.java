/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:34
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
//@Table(name = "kommunikations_service_uid")
//@Inheritance(strategy = InheritanceType.JOINED)
public class KommunikationsServiceUID {

    @Id
    @TableGenerator(name = "PKGenKommunikationsServiceUID",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "KommunikationsServiceUID_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenKommunikationsServiceUID")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<KommunikationsServiceUIDPart> uidparts;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private KommunikationsServiceKlassifizierung klassifizierung;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private KommunikationsService service;

    //    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    //    private Person person;

    //    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    //    private Organisationseinheit organisationseinheit;

    public String generateSingleUID() {
        String uid = "";
        if (getUidparts() == null) {

        } else {
            final List<KommunikationsServiceUIDPart> uidparts = getUidparts();
            Collections.sort(uidparts, new KommunikationsServiceUIDPartComparator());
            for (final KommunikationsServiceUIDPart uidpart : uidparts) {
                uid = uid + " " + uidpart.getUidPart();
            }
        }
        return uid;
    }

    public void add(final KommunikationsServiceUIDPart uidPart) {
        if (getUidparts() == null && uidPart != null) {
            setUidparts(new ArrayList<KommunikationsServiceUIDPart>());
        } else {

        }
        if (uidPart != null) {
            getUidparts().add(uidPart);
        } else {
            //
        }
    }

    public List<KommunikationsServiceUIDPart> getUidparts() {
        return uidparts;
    }

    public void setUidparts(final List<KommunikationsServiceUIDPart> uidparts) {
        this.uidparts = uidparts;
    }

    public KommunikationsServiceKlassifizierung getKlassifizierung() {
        return klassifizierung;
    }

    public void setKlassifizierung(final KommunikationsServiceKlassifizierung klassifizierung) {
        this.klassifizierung = klassifizierung;
    }

    public KommunikationsService getService() {
        return service;
    }

    public void setService(final KommunikationsService service) {
        this.service = service;
    }

    //    public Person getPerson(){
    //        return person;
    //    }
    //
    //    public void setPerson(final Person person){
    //        this.person = person;
    //    }
    //
    //    public Organisationseinheit getOrganisationseinheit(){
    //        return organisationseinheit;
    //    }
    //
    //    public void setOrganisationseinheit(final Organisationseinheit organisationseinheit){
    //        this.organisationseinheit = organisationseinheit;
    //    }
}
