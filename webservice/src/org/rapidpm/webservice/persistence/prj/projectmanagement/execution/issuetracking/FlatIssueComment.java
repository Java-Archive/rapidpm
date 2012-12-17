package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Date;

/**
 * User: Alexander Vos
 * Date: 17.12.12
 * Time: 13:36
 */
public class FlatIssueComment extends FlatEntity<IssueComment> {
    private String text;
    private Long creatorId;
    private Date created;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(final Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public void fromEntity(final IssueComment issueComment) {
        id = issueComment.getId();
        text = issueComment.getText();
        creatorId = getId(issueComment.getCreator());
        created = issueComment.getCreated();
    }

    @Override
    public void toEntity(final IssueComment issueComment, final DaoFactory daoFactory) {
        issueComment.setText(text);
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        issueComment.setCreator(benutzerDAO.findByID(creatorId));
        issueComment.setCreated(created);
    }
}
