package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.*;
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
@Entity
public class IssueBase {

    public static final String SUMMARY ="summary";
    public static final String TEXT = "text";
    public static final String STORYPOINTS = "storyPoints";
    public static final String DATE_PLANNED = "dueDate_planned";
    public static final String DATE_RESOLVED = "dueDate_resolved";
    public static final String DATE_CLOSED = "dueDate_closed";
    public static final String PRIORITY = "priority";
    public static final String STATUS = "status";
    public static final String REPORTER ="reporter";
    public static final String ASSIGNEE = "assignee";
    public static final String VERSION = "version";
    public static final String COMMENTS = "comments";
    public static final String TESTCASES ="testcases";

    private static final Logger logger = Logger.getLogger(IssueBase.class);

    //TODO TestCases definieren - Klasse erzeugen
    @ElementCollection
    private List<String> testcases;
    public List<String> getTestcases() {
        return testcases;
    }
    public void setTestcases(List<String> testcases) {
        this.testcases = testcases;
    }





    @Id
    @TableGenerator(name = "PKGenIssueBase", table = "pk_gen", pkColumnName = "gen_key", pkColumnValue = "IssueBase_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PKGenIssueBase")
    private Long id;

    @Basic
    private String summary;

    @Basic
    @Column(columnDefinition = "TEXT")
    private String text;

    @Basic
    private Integer storyPoints;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private IssuePriority priority;

    @OneToOne(cascade = CascadeType.REFRESH)
    private IssueStatus status;

    //@OneToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    //private IssueTimeUnit issueTimeUnitEstimated;

    //@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    //private List<IssueTimeUnit> issueTimeUnitsUsed;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer reporter;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Benutzer assignee;

//    @Basic
//    private float euro; //Stundensaetze und Co koennen hinterlegt werden.. Reporting

    @Basic
    private String version;
    @Basic
    private Date dueDate_planned;
    @Basic
    private Date dueDate_resolved;

    @Basic
    private Date dueDate_closed;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<IssueComment> comments;

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IssueBase");
        sb.append("{id=").append(id);
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", text='").append(text).append('\'');
//        sb.append(", fakturierbar=").append(fakturierbar);
        sb.append(", issuePriority=").append(priority);
        sb.append(", issueStatus=").append(status);
        //sb.append(", issueTimeUnitEstimated=").append(issueTimeUnitEstimated);
        //sb.append(", issueTimeUnitsUsed=").append(issueTimeUnitsUsed);
        sb.append(", reporter=").append(reporter);
        sb.append(", assignee=").append(assignee);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueBase issueBase = (IssueBase) o;

        if (id != null ? !id.equals(issueBase.id) : issueBase.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Benutzer getAssignee() {
        return assignee;
    }

    public void setAssignee(final Benutzer assignee) {
        this.assignee = assignee;
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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(final IssuePriority issuePriority) {
        this.priority = issuePriority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(final IssueStatus issueStatus) {
        this.status = issueStatus;
    }

//    public IssueTimeUnit getIssueTimeUnitEstimated() {
//        return issueTimeUnitEstimated;
//    }
//
//    public void setIssueTimeUnitEstimated(final IssueTimeUnit issueTimeUnitEstimated) {
//        this.issueTimeUnitEstimated = issueTimeUnitEstimated;
//    }
//
//    public List<IssueTimeUnit> getIssueTimeUnitsUsed() {
//        return issueTimeUnitsUsed;
//    }
//
//    public void setIssueTimeUnitsUsed(final List<IssueTimeUnit> issueTimeUnitsUsed) {
//        this.issueTimeUnitsUsed = issueTimeUnitsUsed;
//    }


    public Benutzer getReporter() {
        return reporter;
    }

    public void setReporter(final Benutzer reporter) {
        this.reporter = reporter;
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

}
