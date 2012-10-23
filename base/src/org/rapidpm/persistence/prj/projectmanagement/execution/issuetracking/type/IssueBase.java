package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;
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

    public static final String NAME ="summary";


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
    public static final String TYPE ="type";

    private static final Logger logger = Logger.getLogger(IssueBase.class);

    @Identifier
    private Long id;

    @Simple
    private String summary;

    @Simple
    private String text;

    @Graph(clazz = IssuePriority.class)
    private IssuePriority priority;

    @Graph(clazz = IssueStatus.class)
    private IssueStatus status;

    @Graph(clazz = IssueType.class)
    private IssueType type;

    @Relational(clazz = Benutzer.class)
    private Benutzer reporter;

    @Relational(clazz = Benutzer.class)
    private Benutzer assignee;

    @Simple
    private String version;

    @Simple
    private Integer storyPoints;

    @Simple(clazz = "Date")
    private Date dueDate_planned;

    @Simple(clazz = "Date")
    private Date dueDate_resolved;

    @Simple(clazz = "Date")
    private Date dueDate_closed;

    //@Relational
    private IssueTimeUnit timeUnitEstimated;

    //@Relational
    private List<IssueTimeUnit> timeUnitsUsed;

    //@Graph(clazz = IssueComment.class)
    @Relational(clazz = IssueComment.class)
    private List<IssueComment> comments;

    //@Relational
    private List<TestCase> testcases;

    //private Risk risk;
    @Simple
    private String risk;

    @Relational(clazz = PlanningUnit.class)
    private PlanningUnit planningUnit;


    public IssueBase() {
        //empty on Purpose
    }



    public boolean connectToIssueAs(IssueBase issue, IssueRelation relation) {
        return GraphDaoFactory.getIssueBaseDAO().connectEntitiesWithRelationTx(this, issue, relation);
    }

    public List<IssueBase> getConnectedIssues(IssueRelation relation) {
        return getConnectedIssues(relation, Direction.BOTH);
    }

    public List<IssueBase> getConnectedIssues(IssueRelation relation, Direction direction) {
        return GraphDaoFactory.getIssueBaseDAO().getConnectedIssuesWithRelation(this, relation, direction);
    }

    public boolean removeConnectionToIssue(IssueBase issue, IssueRelation relation) {
        return GraphDaoFactory.getIssueBaseDAO().deleteRelationOfEntitiesTx(this, issue, relation);
    }



    public boolean addSubIssue(IssueBase subIssue) {
        return GraphDaoFactory.getIssueBaseDAO().addSubIssueTx(this, subIssue);
    }

    public List<IssueBase> getSubIssues() {
        return GraphDaoFactory.getIssueBaseDAO().getSubIssuesOf(this);
    }

    public boolean removeSubIssue(IssueBase subIssue) {
        return GraphDaoFactory.getIssueBaseDAO().deleteSubIssueRelationTx(this, subIssue);
    }



    public boolean addComponent(IssueComponent component) {
        return GraphDaoFactory.getIssueBaseDAO().addComponentToTx(this, component);
    }

    public List<IssueComponent> getComponents() {
        return GraphDaoFactory.getIssueBaseDAO().getComponentsOf(this);
    }

    public boolean removeComponent(IssueComponent component) {
        return GraphDaoFactory.getIssueBaseDAO().deleteComponentRelationTx(this, component);
    }

    public boolean removeAllComponents(List<IssueComponent> componentList) {
        return GraphDaoFactory.getIssueBaseDAO().deleteAllComponentRelationsTx(this, componentList);
    }


    public boolean addComment(IssueComment comment) {
        return comments.add(comment);
    }

    public boolean removeComment(IssueComment comment) {
        return comments.remove(comment);
    }


    public boolean addTestCase(TestCase testcase) {
        return testcases.add(testcase);
    }

    public boolean removeTestCase(TestCase testcase) {
        return testcases.remove(testcase);
    }


    public boolean addTimeUnitUsed(IssueTimeUnit timeUnit) {
        return timeUnitsUsed.add(timeUnit);
    }

    public boolean removeTimeUnitUsed(IssueTimeUnit timeUnit) {
        return timeUnitsUsed.remove(timeUnit);
    }


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public PlanningUnit getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(PlanningUnit planningUnit) {
        this.planningUnit = planningUnit;
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

    public Benutzer getReporter() {
        return reporter;
    }

    public void setReporter(final Benutzer reporter) {
        this.reporter = reporter;
    }

    public Benutzer getAssignee() {
        return assignee;
    }

    public void setAssignee(final Benutzer assignee) {
        this.assignee = assignee;
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

    public void setStoryPoints(final Integer storyPoints) {
        this.storyPoints = storyPoints;
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

    public IssueTimeUnit getTimeUnitEstimated() {
        return timeUnitEstimated;
    }

    public void setTimeUnitEstimated(final IssueTimeUnit timeUnitEstimated) {
        this.timeUnitEstimated = timeUnitEstimated;
    }

    public List<IssueTimeUnit> getTimeUnitsUsed() {
        return timeUnitsUsed;
    }

    public void setTimeUnitsUsed(final List<IssueTimeUnit> timeUnitsUsed) {
        this.timeUnitsUsed = timeUnitsUsed;
    }

    public List<IssueComment> getComments() {
        return comments;
    }

    public void setComments(final List<IssueComment> comments) {
        this.comments = comments;
    }

    public List<TestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(final List<TestCase> testcases) {
        this.testcases = testcases;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueBase issueBase = (IssueBase) o;

        if (assignee != null ? !assignee.equals(issueBase.assignee) : issueBase.assignee != null) return false;
        if (comments != null ? !comments.equals(issueBase.comments) : issueBase.comments != null) return false;
        if (dueDate_closed != null ? !dueDate_closed.equals(issueBase.dueDate_closed) : issueBase.dueDate_closed != null)
            return false;
        if (dueDate_planned != null ? !dueDate_planned.equals(issueBase.dueDate_planned) : issueBase.dueDate_planned != null)
            return false;
        if (dueDate_resolved != null ? !dueDate_resolved.equals(issueBase.dueDate_resolved) : issueBase.dueDate_resolved != null)
            return false;
        if (priority != null ? !priority.equals(issueBase.priority) : issueBase.priority != null) return false;
        if (reporter != null ? !reporter.equals(issueBase.reporter) : issueBase.reporter != null) return false;
        if (risk != null ? !risk.equals(issueBase.risk) : issueBase.risk != null) return false;
        if (status != null ? !status.equals(issueBase.status) : issueBase.status != null) return false;
        if (storyPoints != null ? !storyPoints.equals(issueBase.storyPoints) : issueBase.storyPoints != null)
            return false;
        if (summary != null ? !summary.equals(issueBase.summary) : issueBase.summary != null) return false;
        if (testcases != null ? !testcases.equals(issueBase.testcases) : issueBase.testcases != null) return false;
        if (text != null ? !text.equals(issueBase.text) : issueBase.text != null) return false;
        if (timeUnitEstimated != null ? !timeUnitEstimated.equals(issueBase.timeUnitEstimated) : issueBase.timeUnitEstimated != null)
            return false;
        if (timeUnitsUsed != null ? !timeUnitsUsed.equals(issueBase.timeUnitsUsed) : issueBase.timeUnitsUsed != null)
            return false;
        if (type != null ? !type.equals(issueBase.type) : issueBase.type != null) return false;
        if (version != null ? !version.equals(issueBase.version) : issueBase.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (reporter != null ? reporter.hashCode() : 0);
        result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (storyPoints != null ? storyPoints.hashCode() : 0);
        result = 31 * result + (dueDate_planned != null ? dueDate_planned.hashCode() : 0);
        result = 31 * result + (dueDate_resolved != null ? dueDate_resolved.hashCode() : 0);
        result = 31 * result + (dueDate_closed != null ? dueDate_closed.hashCode() : 0);
        result = 31 * result + (timeUnitEstimated != null ? timeUnitEstimated.hashCode() : 0);
        result = 31 * result + (timeUnitsUsed != null ? timeUnitsUsed.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (testcases != null ? testcases.hashCode() : 0);
        result = 31 * result + (risk != null ? risk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueBase{" +
                "id=" + id +
                ", planningUnit=" + planningUnit +
                ", summary='" + summary + '\'' +
                ", text='" + text + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", type=" + type +
                ", reporter=" + reporter +
                ", assignee=" + assignee +
                ", version='" + version + '\'' +
                ", storyPoints=" + storyPoints +
                ", dueDate_planned=" + dueDate_planned +
                ", dueDate_resolved=" + dueDate_resolved +
                ", dueDate_closed=" + dueDate_closed +
                ", timeUnitEstimated=" + timeUnitEstimated +
                ", timeUnitsUsed=" + timeUnitsUsed +
                ", comments=" + comments +
                ", testcases=" + testcases +
                ", risk='" + risk + '\'' +
                '}';
    }

}
