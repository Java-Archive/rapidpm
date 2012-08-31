package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung;

import org.apache.log4j.Logger;


import java.util.Date;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 12:24:01
 */
//@CacheStrategy(readOnly = true, warmingQuery = "order by id",useBeanCache = true)
public class IssueBase {
    private static final Logger logger = Logger.getLogger(IssueBase.class);


    private Long id;
    private String summary;
    private String text;
    private Integer storyPoints;
    private IssuePriority issuePriority;
    private IssueStatus issueStatus;
    private IssueTimeUnit issueTimeUnitEstimated;
    private List<IssueTimeUnit> issueTimeUnitsUsed;
    private List<String> testcases;
    private Benutzer issueReporter;
    private Benutzer isssueAssignee;
//    @Basic
//    private float euro; //Stundensaetze und Co koennen hinterlegt werden.. Reporting
    private String version;
    private Date dueDate_planned;
    private Date dueDate_resolved;
    private Date dueDate_closed;
    private List<IssueComment> comments;

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueBase");
        sb.append("{id=").append(id);
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", text='").append(text).append('\'');
        //sb.append(", fakturierbar=").append(fakturierbar);
        sb.append(", issuePriority=").append(issuePriority);
        sb.append(", issueStatus=").append(issueStatus);
        sb.append(", issueTimeUnitEstimated=").append(issueTimeUnitEstimated);
        sb.append(", issueTimeUnitsUsed=").append(issueTimeUnitsUsed);
        sb.append(", reporter=").append(issueReporter);
        sb.append(", assignee=").append(isssueAssignee);
        //        sb.append(", mandantengruppe=").append(mandantengruppe);
        //sb.append(", euro=").append(euro);
        sb.append(", version=").append(version);
        sb.append(", dueDate_planned=").append(dueDate_planned);
        sb.append(", dueDate_resolved=").append(dueDate_resolved);
        sb.append(", dueDate_closed=").append(dueDate_closed);
        sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueBase)) {
            return false;
        }

        final IssueBase issueBase = (IssueBase) o;

//        if (Float.compare(issueBase.euro, euro) != 0) {
//            return false;
//        }
//        if (fakturierbar != issueBase.fakturierbar) {
//            return false;
//        }
        if (isssueAssignee != null ? !isssueAssignee.equals(issueBase.isssueAssignee) : issueBase.isssueAssignee != null) {
            return false;
        }
        if (dueDate_closed != null ? !dueDate_closed.equals(issueBase.dueDate_closed) : issueBase.dueDate_closed != null) {
            return false;
        }
        if (dueDate_planned != null ? !dueDate_planned.equals(issueBase.dueDate_planned) : issueBase.dueDate_planned != null) {
            return false;
        }
        if (dueDate_resolved != null ? !dueDate_resolved.equals(issueBase.dueDate_resolved) : issueBase.dueDate_resolved != null) {
            return false;
        }
        if (id != null ? !id.equals(issueBase.id) : issueBase.id != null) {
            return false;
        }
        if (issuePriority != null ? !issuePriority.equals(issueBase.issuePriority) : issueBase.issuePriority != null) {
            return false;
        }
        if (issueStatus != null ? !issueStatus.equals(issueBase.issueStatus) : issueBase.issueStatus != null) {
            return false;
        }
        if (issueReporter != null ? !issueReporter.equals(issueBase.issueReporter) : issueBase.issueReporter != null) {
            return false;
        }
        if (summary != null ? !summary.equals(issueBase.summary) : issueBase.summary != null) {
            return false;
        }
        if (text != null ? !text.equals(issueBase.text) : issueBase.text != null) {
            return false;
        }
        if (version != null ? !version.equals(issueBase.version) : issueBase.version != null) {
            return false;
        }

        return true;
    }

    public Benutzer getAssignee() {
        return isssueAssignee;
    }

    public void setAssignee(final Benutzer assignee) {
        this.isssueAssignee = assignee;
    }

    public List<IssueComment> getComments() {
        return comments;
    }

    public void setComments(final List<IssueComment> comments) {
        this.comments = comments;
    }

    public Date getDueDate_closed() {
        return dueDate_closed;
    }

    public void setDueDate_closed(final Date dueDate_closed) {
        this.dueDate_closed = dueDate_closed;
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

//    public float getEuro() {
//        return euro;
//    }
//
//    public void setEuro(final float euro) {
//        this.euro = euro;
//    }

//    public boolean isFakturierbar() {
//        return fakturierbar;
//    }
//
//    public void setFakturierbar(final boolean fakturierbar) {
//        this.fakturierbar = fakturierbar;
//    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssuePriority getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(final IssuePriority issuePriority) {
        this.issuePriority = issuePriority;
    }

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(final IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public IssueTimeUnit getIssueTimeUnitEstimated() {
        return issueTimeUnitEstimated;
    }

    public void setIssueTimeUnitEstimated(final IssueTimeUnit issueTimeUnitEstimated) {
        this.issueTimeUnitEstimated = issueTimeUnitEstimated;
    }

    public List<IssueTimeUnit> getIssueTimeUnitsUsed() {
        return issueTimeUnitsUsed;
    }

    public void setIssueTimeUnitsUsed(final List<IssueTimeUnit> issueTimeUnitsUsed) {
        this.issueTimeUnitsUsed = issueTimeUnitsUsed;
    }


    public Benutzer getReporter() {
        return issueReporter;
    }

    public void setReporter(final Benutzer reporter) {
        this.issueReporter = reporter;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public Integer getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints) {
        this.storyPoints = storyPoints;
    }

    public List<String> getTestcases() {
        return testcases;
    }

    public void setTestcases(List<String> testcases) {
        this.testcases = testcases;
    }
}
