package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.EntityMapper;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Date;
import java.util.List;

/**
 * User: Alexander Vos
 * Date: 12.12.12
 * Time: 17:37
 */
public class FlatIssueBase extends FlatEntity<IssueBase> {
    private String text;
    private Long projectId;
    private String summary;
    private String story;
    private Long priorityId;
    private Long statusId;
    private Long typeId;
    private Long reporterId;
    private Long assigneeId;
    private Long versionId;
    //    private IssueStoryPoint storyPoints;
    private Date dueDate_planned;
    private Date dueDate_resolved;
    private Date dueDate_closed;
    private FlatIssueTimeUnit timeUnitEstimated;
    private List<FlatIssueTimeUnit> timeUnitsUsed;
    private List<FlatIssueComment> comments;
    //    private List<IssueTestCase> testcases;
    private Integer risk;
//    private PlanningUnit planningUnit;


    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(final Long projectId) {
        this.projectId = projectId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getStory() {
        return story;
    }

    public void setStory(final String story) {
        this.story = story;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(final Long priorityId) {
        this.priorityId = priorityId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(final Long statusId) {
        this.statusId = statusId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(final Long typeId) {
        this.typeId = typeId;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(final Long reporterId) {
        this.reporterId = reporterId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(final Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(final Long versionId) {
        this.versionId = versionId;
    }

    public Date getDueDate_planned() {
        return dueDate_planned;
    }

    public void setDueDate_planned(final Date dueDate_planned) {
        this.dueDate_planned = dueDate_planned;
    }

    public Date getDueDate_resolved() {
        return dueDate_resolved;
    }

    public void setDueDate_resolved(final Date dueDate_resolved) {
        this.dueDate_resolved = dueDate_resolved;
    }

    public Date getDueDate_closed() {
        return dueDate_closed;
    }

    public void setDueDate_closed(final Date dueDate_closed) {
        this.dueDate_closed = dueDate_closed;
    }

    public FlatIssueTimeUnit getTimeUnitEstimated() {
        return timeUnitEstimated;
    }

    public void setTimeUnitEstimated(final FlatIssueTimeUnit timeUnitEstimated) {
        this.timeUnitEstimated = timeUnitEstimated;
    }

    public List<FlatIssueTimeUnit> getTimeUnitsUsed() {
        return timeUnitsUsed;
    }

    public void setTimeUnitsUsed(final List<FlatIssueTimeUnit> timeUnitsUsed) {
        this.timeUnitsUsed = timeUnitsUsed;
    }

    public List<FlatIssueComment> getComments() {
        return comments;
    }

    public void setComments(final List<FlatIssueComment> comments) {
        this.comments = comments;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(final Integer risk) {
        this.risk = risk;
    }

    @Override
    public void fromEntity(final IssueBase issueBase) {
        id = issueBase.getId();
        text = issueBase.getText();
        projectId = issueBase.getProjectId();
        summary = issueBase.getSummary();
        story = issueBase.getStory();
        priorityId = getId(issueBase.getPriority());
        statusId = getId(issueBase.getStatus());
        typeId = getId(issueBase.getType());
        reporterId = getId(issueBase.getReporter());
        assigneeId = getId(issueBase.getAssignee());
        versionId = getId(issueBase.getVersion());
        dueDate_planned = issueBase.getDueDate_planned();
        dueDate_resolved = issueBase.getDueDate_resolved();
        dueDate_closed = issueBase.getDueDate_closed();
        timeUnitEstimated = issueTimeUnitMapper.toFlatEntity(issueBase.getTimeUnitEstimated());
        timeUnitsUsed = issueTimeUnitMapper.toFlatEntityList(issueBase.getTimeUnitsUsed());
        comments = issueCommentMapper.toFlatEntityList(issueBase.getComments());
        risk = issueBase.getRisk();
    }

    @Override
    public void toEntity(final IssueBase issueBase, final DaoFactory daoFactory) {
//        issueBase.setText(text);
        issueBase.setProjectId(projectId);
        issueBase.setSummary(summary);
        issueBase.setStory(story);
        if (priorityId != null) {
            final IssuePriorityDAO issuePriorityDAO = daoFactory.getIssuePriorityDAO();
            issueBase.setPriority(issuePriorityDAO.findByID(priorityId));
        }
        if (statusId != null) {
            final IssueStatusDAO issueStatusDAO = daoFactory.getIssueStatusDAO();
            issueBase.setStatus(issueStatusDAO.findByID(statusId));
        }
        if (typeId != null) {
            final IssueTypeDAO issueTypeDAO = daoFactory.getIssueTypeDAO();
            issueBase.setType(issueTypeDAO.findByID(typeId));
        }
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        if (reporterId != null) {
            issueBase.setReporter(benutzerDAO.findByID(reporterId));
        }
        if (assigneeId != null) {
            issueBase.setAssignee(benutzerDAO.findByID(assigneeId));
        }
        if (versionId != null) {
            final IssueVersionDAO issueVersionDAO = daoFactory.getIssueVersionDAO();
            issueBase.setVersion(issueVersionDAO.findByID(versionId));
        }
        issueBase.setDueDate_planned(dueDate_planned);
        issueBase.setDueDate_resolved(dueDate_resolved);
        issueBase.setDueDate_closed(dueDate_closed);
        issueBase.setTimeUnitEstimated(issueTimeUnitMapper.toEntity(timeUnitEstimated));
        issueBase.setTimeUnitsUsed(issueTimeUnitMapper.toEntityList(timeUnitsUsed));
        issueBase.setComments(issueCommentMapper.toEntityList(comments));
        issueBase.setRisk(risk);
    }

    // TODO extract to registry?
    private static final EntityMapper<IssueTimeUnit, FlatIssueTimeUnit> issueTimeUnitMapper =
            new EntityMapper<IssueTimeUnit, FlatIssueTimeUnit>(IssueTimeUnit.class, FlatIssueTimeUnit.class) {
                @Override
                protected IssueTimeUnit findEntityById(final Long id) {
                    return daoFactory.getIssueTimeUnitDAO().findByID(id);
                }
            };

    // TODO extract to registry?
    private static final EntityMapper<IssueComment, FlatIssueComment> issueCommentMapper =
            new EntityMapper<IssueComment, FlatIssueComment>(IssueComment.class, FlatIssueComment.class) {
                @Override
                protected IssueComment findEntityById(final Long id) {
                    return daoFactory.getIssueCommentDAO().findByID(id);
                }
            };
}
