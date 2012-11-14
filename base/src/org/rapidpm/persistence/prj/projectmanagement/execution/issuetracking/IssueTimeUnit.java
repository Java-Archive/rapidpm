package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.prj.projectmanagement.controlling.Risk;
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Basic
    private Date datum;
    @Basic
    private int minutes;

    @Basic
    private String comment;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer worker;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Risk risk;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueTimeUnit");
        sb.append("{id=").append(getId());
        sb.append(", datum=").append(datum);
        sb.append(", minutes=").append(minutes);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", worker=").append(worker);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueTimeUnit)) {
            return false;
        }

        final IssueTimeUnit issueTimeUnit = (IssueTimeUnit) o;
        if (getId() != issueTimeUnit.getId()) {
            return false;
        }
        if (minutes != issueTimeUnit.minutes) {
            return false;
        }
        if (!comment.equals(issueTimeUnit.comment)) {
            return false;
        }
        if (!datum.equals(issueTimeUnit.datum)) {
            return false;
        }
        if (!worker.equals(issueTimeUnit.worker)) {
            return false;
        }

        if(!risk.equals(issueTimeUnit.risk)){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + datum.hashCode();
        result = 31 * result + minutes;
        result = 31 * result + comment.hashCode();
        result = 31 * result + worker.hashCode();
        result = 31 * result + risk.hashCode();
        return result;
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
}
