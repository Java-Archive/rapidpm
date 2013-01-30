package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Date;

/**
 * User: Alexander Vos
 * Date: 17.12.12
 * Time: 13:36
 */
public class FlatIssueTimeUnit extends FlatEntity<IssueTimeUnit> {
    private Date date;
    private int minutes;
    private String comment;
    private Long workerId;

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(final int minutes) {
        this.minutes = minutes;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(final Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public void fromEntity(final IssueTimeUnit issueTimeUnit) {
        id = issueTimeUnit.getId();
        date = issueTimeUnit.getDate();
        minutes = issueTimeUnit.getMinutes();
        comment = issueTimeUnit.getComment();
        workerId = getId(issueTimeUnit.getWorker());
    }

    @Override
    public void toEntity(final IssueTimeUnit issueTimeUnit, final DaoFactory daoFactory) {
        issueTimeUnit.setDate(date);
        issueTimeUnit.setMinutes(minutes);
        issueTimeUnit.setComment(comment);
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        issueTimeUnit.setWorker(benutzerDAO.findByID(workerId));
    }
}
