package org.rapidpm.webservice.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueVersion;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webservice.mapping.FlatEntity;

import java.util.Date;

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
    private IssuePriority priority; // TODO ID only?
    private IssueStatus status; // TODO ID only?
    private IssueType type; // TODO ID only?
    private Long reporterId;
    private Long assigneeId;
    private IssueVersion version; // TODO ID only?
    //    private IssueStoryPoint storyPoints;
    private Date dueDate_planned;
    private Date dueDate_resolved;
    private Date dueDate_closed;
//    private IssueTimeUnit timeUnitEstimated;
//    private List<IssueTimeUnit> timeUnitsUsed = new ArrayList<>();
//    private List<IssueComment> comments = new ArrayList<>();
//    private List<IssueTestCase> testcases = new ArrayList<>();
//    private Integer risk;
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

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(final IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(final IssueStatus status) {
        this.status = status;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(final IssueType type) {
        this.type = type;
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

    public IssueVersion getVersion() {
        return version;
    }

    public void setVersion(final IssueVersion version) {
        this.version = version;
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

    @Override
    public void fromEntity(final IssueBase issueBase) {
        id = issueBase.getId();
        text = issueBase.getText();
        projectId = issueBase.getProjectid();
        summary = issueBase.getSummary();
        story = issueBase.getStory();
        priority = issueBase.getPriority();
        status = issueBase.getStatus();
        type = issueBase.getType();
        final Benutzer reporter = issueBase.getReporter();
        reporterId = reporter != null ? reporter.getId() : null;
        final Benutzer assignee = issueBase.getAssignee();
        assigneeId = assignee != null ? assignee.getId() : null;
        version = issueBase.getVersion();
        dueDate_planned = issueBase.getDueDate_planned();
        dueDate_resolved = issueBase.getDueDate_resolved();
        dueDate_closed = issueBase.getDueDate_closed();
    }

    @Override
    public void toEntity(final IssueBase issueBase, final DaoFactory daoFactory) {
//        issueBase.setText(text);
//        issueBase.setProjectid(projectId);
        issueBase.setSummary(summary);
        issueBase.setStory(story);
        issueBase.setPriority(priority);
        issueBase.setStatus(status);
        issueBase.setType(type);
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        issueBase.setReporter(benutzerDAO.findByID(reporterId));
        issueBase.setAssignee(benutzerDAO.findByID(assigneeId));
        issueBase.setVersion(version);
        issueBase.setDueDate_planned(dueDate_planned);
        issueBase.setDueDate_resolved(dueDate_resolved);
        issueBase.setDueDate_closed(dueDate_closed);
    }
}
