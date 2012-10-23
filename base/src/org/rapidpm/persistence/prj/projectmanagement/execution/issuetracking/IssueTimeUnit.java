package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 12:29:14
 */

//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
@Entity
public class IssueTimeUnit {
    private static final Logger logger = Logger.getLogger(IssueTimeUnit.class);

    @Id
    @TableGenerator(name = "PKGenIssueTimeUnit", table = "pk_gen",
            pkColumnName = "gen_key", pkColumnValue = "IssueTimeUnit_id",
            valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenIssueTimeUnit")
    private Long id;

    @Basic
    private Date datum;

    @Basic
    private int minutes;

    @Basic
    private String comment;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer worker;

    public IssueTimeUnit() {
        //empty on purpose
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(final Date datum) {
        this.datum = datum;
    }

    public Benutzer getWorker() {
        return worker;
    }

    public void setWorker(final Benutzer worker) {
        this.worker = worker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(final int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "IssueTimeUnit{" +
                "id=" + id +
                ", datum=" + datum +
                ", minutes=" + minutes +
                ", comment='" + comment + '\'' +
                ", worker=" + worker +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueTimeUnit that = (IssueTimeUnit) o;

        if (minutes != that.minutes) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (datum != null ? !datum.equals(that.datum) : that.datum != null) return false;
        if (worker != null ? !worker.equals(that.worker) : that.worker != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = datum != null ? datum.hashCode() : 0;
        result = 31 * result + minutes;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (worker != null ? worker.hashCode() : 0);
        return result;
    }
}
