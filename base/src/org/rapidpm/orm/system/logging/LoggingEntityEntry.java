package org.rapidpm.orm.system.logging;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.orm.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LoggingEntityEntry {
    private static final Logger logger = Logger.getLogger(LoggingEntityEntry.class);

    @TableGenerator(name = "PKGenLoggingEntityEntry", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "LoggingEntityEntry_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenLoggingEntityEntry")
    @Id
    private Long id;

    @Basic
    private String classname;
    @Basic
    private Long oid;
    @Basic
    private Date date;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer actor;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private LoggingEntityAction action;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("LoggingEntityEntry");
        sb.append("{action=").append(action);
        sb.append(", id=").append(id);
        sb.append(", classname='").append(classname).append('\'');
        sb.append(", oid=").append(oid);
        sb.append(", date=").append(date);
        sb.append(", actor=").append(actor);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoggingEntityEntry)) {
            return false;
        }

        final LoggingEntityEntry that = (LoggingEntityEntry) o;

        if (action != null ? !action.equals(that.action) : that.action != null) {
            return false;
        }
        if (actor != null ? !actor.equals(that.actor) : that.actor != null) {
            return false;
        }
        if (classname != null ? !classname.equals(that.classname) : that.classname != null) {
            return false;
        }
        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (oid != null ? !oid.equals(that.oid) : that.oid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (classname != null ? classname.hashCode() : 0);
        result = 31 * result + (oid != null ? oid.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    public LoggingEntityAction getAction() {
        return action;
    }

    public void setAction(final LoggingEntityAction action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Benutzer getActor() {
        return actor;
    }

    public void setActor(final Benutzer actor) {
        this.actor = actor;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(final String classname) {
        this.classname = classname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(final Long oid) {
        this.oid = oid;
    }
}