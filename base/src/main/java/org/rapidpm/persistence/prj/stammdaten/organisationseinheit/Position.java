/*
 * Copyright (c) 2009. This is part of the RapidPM Project from RapidPM - www.rapidpm.org.
 * please contact sven.ruppert@me.com
 */

package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;
/**
 * RapidPM
 * User: svenruppert
 * Date: 20.12.2009
 * Time: 15:33:30
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.stammdaten.person.Person;

import javax.persistence.*;
import java.util.Date;

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class Position {
    private static final Logger logger = Logger.getLogger(Position.class);

    @Id
    @TableGenerator(name = "PKGenPosition",
            table = "pk_gen",
            pkColumnName = "gen_key",
            pkColumnValue = "Position_id",
            valueColumnName = "gen_value",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenPosition")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private String name;
    //    @Basic
    //    private Date created;
    //    @Basic
    //    private Date modified;

    @Basic
    private Date since;
    @Basic
    private Date until;
    @Basic
    private String beschreibung;


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Organisationseinheit organisationseinheit;

    @OneToOne(cascade = {CascadeType.REFRESH})
    private Person person;

    public Date getSince() {
        return since;
    }

    public void setSince(final Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(final Date until) {
        this.until = until;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(final String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }

        final Position position = (Position) o;

        if (beschreibung != null ? !beschreibung.equals(position.beschreibung) : position.beschreibung != null) {
            return false;
        }
        //        if(!created.equals(position.created)){
        //            return false;
        //        }
        if (getId() != null ? !getId().equals(position.getId()) : position.getId() != null) {
            return false;
        }
        //        if(!modified.equals(position.modified)){
        //            return false;
        //        }
        if (!name.equals(position.name)) {
            return false;
        }
        if (organisationseinheit != null ? !organisationseinheit.equals(position.organisationseinheit) : position.organisationseinheit != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + name.hashCode();
        //        result = 31 * result + created.hashCode();
        //        result = 31 * result + modified.hashCode();
        result = 31 * result + (beschreibung != null ? beschreibung.hashCode() : 0);
        result = 31 * result + (organisationseinheit != null ? organisationseinheit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Position");
        sb.append("{id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public Organisationseinheit getOrganisationseinheit() {
        return organisationseinheit;
    }

    public void setOrganisationseinheit(final Organisationseinheit organisationseinheit) {
        this.organisationseinheit = organisationseinheit;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
