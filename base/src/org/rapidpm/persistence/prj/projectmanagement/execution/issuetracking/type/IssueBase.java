package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


//import org.rapidpm.persistence.GraphDaoFactory;
import org.neo4j.graphdb.Direction;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import java.lang.reflect.Method;
import java.util.*;

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

public class IssueBase implements PersistInGraph {

    //private static final Logger logger = Logger.getLogger(IssueBase.class);

    @Identifier
    private Long id;

    @Simple
    private String text;

    @Simple
    private Long projectid;

    @Simple
    private String summary;

    @Simple
    private String story;

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

    @Graph(clazz = IssueVersion.class)
    private IssueVersion version;

    @Graph(clazz = IssueStoryPoint.class)
    private IssueStoryPoint storyPoints;

    @Simple
    private Date dueDate_planned;

    @Simple
    private Date dueDate_resolved;

    @Simple
    private Date dueDate_closed;

    //@Relational
    private IssueTimeUnit timeUnitEstimated;

    //@Relational
    private List<IssueTimeUnit> timeUnitsUsed = new ArrayList<>();

    @Relational(clazz = IssueComment.class, onDeleteCascade = true)
    private List<IssueComment> comments = new ArrayList<>();

    @Relational(clazz = IssueTestCase.class, onDeleteCascade = true)
    private List<IssueTestCase> testcases = new ArrayList<>();

    @Simple
    //private Risk risk;
    private Integer risk;

    @Relational(clazz = PlanningUnit.class)
    private PlanningUnit planningUnit;

    @LazyGraphPersist
    private Map<Method, List<Object[]>> graphMap;


    public IssueBase(final Long projectid) {
        setProjectid(projectid);
    }


    private boolean addToMap(String methodName, Object[] args) {
        boolean success = false;
        if (graphMap == null) {
            graphMap = new HashMap<>();
        }

        Class[] methodParams = new Class[args.length];
        int i = 0;
        for (Object obj : args) {
            methodParams[i++] = obj.getClass();
        }

        try {
            Method method = IssueBaseDAO.class.getMethod(methodName,
                    methodParams);
            if (method != null) {
                List<Object[]> list = graphMap.get(method);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(args);
                graphMap.put(method, list);
                success = true;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return success;
    }


    public boolean connectToIssueAs(final IssueBase issue, final IssueRelation relation) {
        //return DaoFactorySingelton.getIssueBaseDAO(projectid).connectEntitiesWithRelationTx(this, issue, relation);
        return addToMap("connectEntitiesWithRelationTx", new Object[]{this, issue, relation});
    }

    public List<IssueBase> getConnectedIssues(final IssueRelation relation) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).getConnectedIssuesWithRelation(this, relation, Direction.BOTH);
    }

    public List<IssueBase> getConnectedIssues(final IssueRelation relation, final Direction direction) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).getConnectedIssuesWithRelation(this, relation, direction);
    }

    public boolean removeConnectionToIssue(final IssueBase issue, final IssueRelation relation) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectid).deleteRelationOfEntitiesTx(this, issue, relation, Direction.BOTH);
        return removeConnectionToIssue(issue, relation, Direction.BOTH);
    }

    public boolean removeConnectionToIssue(final IssueBase issue, final IssueRelation relation, final Direction direction) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectid).deleteRelationOfEntitiesTx(this, issue, relation, direction);
        return addToMap("deleteRelationOfEntitiesTx", new Object[]{this, issue, relation, direction});
    }


    public boolean addSubIssue(final IssueBase subIssue) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).addSubIssueTx(this, subIssue);
//        return addToMap("addSubIssueTx", new Object[]{this, subIssue});
    }

    public List<IssueBase> getSubIssues() {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).getSubIssuesOf(this);
    }

    public boolean removeSubIssue(final IssueBase subIssue) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).deleteSubIssueRelationTx(this, subIssue);
//        return addToMap("deleteSubIssueRelationTx", new Object[]{this, subIssue});
    }

    public boolean setAsRootIssue() {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).setAsRootIssueTx(this);
    }

    public boolean addComponent(final IssueComponent component) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectid).addComponentToTx(this, component);
        return addToMap("addComponentToTx", new Object[]{this, component});
    }

    public List<IssueComponent> getComponents() {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectid).getComponentsOf(this);
    }

    public boolean removeComponent(final IssueComponent component) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectid).deleteComponentRelationTx(this, component);
        return addToMap("deleteComponentRelationTx", new Object[]{this, component});
    }


    public boolean addOrChangeComment(final IssueComment comment) {
        for (IssueComment com : comments)
            if (comment.getId() != null && com.getId().equals(comment.getId())) {
                comments.set(comments.indexOf(com), comment);
                return true;
            }
        return comments.add(comment);
    }

    public boolean removeComment(final IssueComment comment) {

        return comments.remove(comment);
    }



    public boolean addOrChangeTestCase(final IssueTestCase testcase) {
        for (IssueTestCase tCase : testcases)
            if (testcase.getId() != null && tCase.getId().equals(testcase.getId())) {
                testcases.set(testcases.indexOf(tCase), testcase);
                return true;
            }
        return testcases.add(testcase);
    }

    public boolean removeTestCase(final IssueTestCase testcase) {
        return testcases.remove(testcase);
    }


    public boolean addTimeUnitUsed(final IssueTimeUnit timeUnit) {
        return timeUnitsUsed.add(timeUnit);
    }

    public boolean removeTimeUnitUsed(final IssueTimeUnit timeUnit) {
        return timeUnitsUsed.remove(timeUnit);
    }



    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public Long getProjectid() {
        return projectid;
    }

    private void setProjectid(final Long projectid) {
        this.projectid = projectid;
    }

    public String getText() {
        return text;
    }

    private void setText(final String text) {
        this.text = text;
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

    public IssueVersion getVersion() {
        return version;
    }

    public void setVersion(final IssueVersion version) {
        this.version = version;
    }

    public IssueStoryPoint getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(final IssueStoryPoint storyPoints) {
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

    public List<IssueTestCase> getTestcases() {
        return testcases;
    }

    public void setTestcases(final List<IssueTestCase> testcases) {
        this.testcases = testcases;
    }

    public Integer getRisk() {
        return risk;
    }

    public void setRisk(final Integer risk) {
        this.risk = risk;
    }

    public PlanningUnit getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(final PlanningUnit planningUnit) {
        this.planningUnit = planningUnit;
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
        if (id != null ? !id.equals(issueBase.id) : issueBase.id != null) return false;
        if (planningUnit != null ? !planningUnit.equals(issueBase.planningUnit) : issueBase.planningUnit != null)
            return false;
        if (priority != null ? !priority.equals(issueBase.priority) : issueBase.priority != null) return false;
        if (projectid != null ? !projectid.equals(issueBase.projectid) : issueBase.projectid != null) return false;
        if (reporter != null ? !reporter.equals(issueBase.reporter) : issueBase.reporter != null) return false;
        if (risk != null ? !risk.equals(issueBase.risk) : issueBase.risk != null) return false;
        if (status != null ? !status.equals(issueBase.status) : issueBase.status != null) return false;
        if (story != null ? !story.equals(issueBase.story) : issueBase.story != null) return false;
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
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (projectid != null ? projectid.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (story != null ? story.hashCode() : 0);
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
        result = 31 * result + (planningUnit != null ? planningUnit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueBase{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", projectid=" + projectid +
                ", summary='" + summary + '\'' +
                ", story='" + story + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", type=" + type +
                ", reporter=" + reporter +
                ", assignee=" + assignee +
                ", version=" + version +
                ", storyPoints=" + storyPoints +
                ", dueDate_planned=" + dueDate_planned +
                ", dueDate_resolved=" + dueDate_resolved +
                ", dueDate_closed=" + dueDate_closed +
                ", timeUnitEstimated=" + timeUnitEstimated +
                ", timeUnitsUsed=" + timeUnitsUsed +
                ", comments=" + comments +
                ", testcases=" + testcases +
                ", risk=" + risk +
                ", planningUnit=" + planningUnit +
                '}';
    }

    @Override
    public String name() {
        return getText() + " - " + getSummary();
    }
}
