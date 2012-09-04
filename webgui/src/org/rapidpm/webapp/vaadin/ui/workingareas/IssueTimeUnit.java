package org.rapidpm.webapp.vaadin.ui.workingareas;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.system.security.Benutzer;

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
public class IssueTimeUnit {
    private static final Logger logger = Logger.getLogger(IssueTimeUnit.class);

    private Date datum;
    private int minutes;
    private String comment;
    private Benutzer worker;
    private Long id;



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

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + datum.hashCode();
        result = 31 * result + minutes;
        result = 31 * result + comment.hashCode();
        result = 31 * result + worker.hashCode();
        return result;
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
}
