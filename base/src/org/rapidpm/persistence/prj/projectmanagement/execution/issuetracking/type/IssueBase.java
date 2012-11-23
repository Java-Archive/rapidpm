package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.rapidpm.persistence.prj.projectmanagement.controlling.BaseControllingunit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.system.security.Benutzer;
import org.neo4j.graphdb.Direction;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.annotations.*;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.persistence.Transient;
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

    @Transient
    private BaseControllingunit totalControllingUnit;
    @Transient
    private BaseControllingunit totalOwnCotntrollingUnit;
    @Transient
    private BaseControllingunit totalSubIssuesBaseControllingUnit;
    
    @Identifier
    private Long id;

    @Simple
    private String text;

    @Simple
    private Long projectId;

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

    @Relational(clazz = IssueComment.class)
    private List<IssueComment> comments = new ArrayList<>();

    @Relational(clazz = IssueTestCase.class)
    private List<IssueTestCase> testcases = new ArrayList<>();

    //private Risk risk;
    @Simple
    private Integer risk;

    @Relational(clazz = PlanningUnit.class)
    private PlanningUnit planningUnit;

    @LazyGraphPersist
    private Map<Method, List<Object[]>> graphMap;


    public IssueBase(final Long projectId) {
        setProjectId(projectId);
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
            Method method = DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).getClass().getMethod(methodName,
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
        //return DaoFactorySingelton.getIssueBaseDAO(projectId).connectEntitiesWithRelationTx(this, issue, relation);
        return addToMap("connectEntitiesWithRelationTx", new Object[]{this, issue, relation});
    }

    public List<IssueBase> getConnectedIssues(final IssueRelation relation) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).getConnectedIssuesWithRelation(this, relation, Direction.BOTH);
    }

    public List<IssueBase> getConnectedIssues(final IssueRelation relation, final Direction direction) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).getConnectedIssuesWithRelation(this, relation, direction);
    }

    public boolean removeConnectionToIssue(final IssueBase issue, final IssueRelation relation) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectId).deleteRelationOfEntitiesTx(this, issue, relation, Direction.BOTH);
        return removeConnectionToIssue(issue, relation, Direction.BOTH);
    }

    public boolean removeConnectionToIssue(final IssueBase issue, final IssueRelation relation, final Direction direction) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectId).deleteRelationOfEntitiesTx(this, issue, relation, direction);
        return addToMap("deleteRelationOfEntitiesTx", new Object[]{this, issue, relation, direction});
    }


    public boolean addSubIssue(final IssueBase subIssue) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).addSubIssueTx(this, subIssue);
//        return addToMap("addSubIssueTx", new Object[]{this, subIssue});
    }

    public List<IssueBase> getSubIssues() {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).getSubIssuesOf(this);
    }

    public boolean removeSubIssue(final IssueBase subIssue) {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).deleteSubIssueRelationTx(this, subIssue);
//        return addToMap("deleteSubIssueRelationTx", new Object[]{this, subIssue});
    }



    public boolean addComponent(final IssueComponent component) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectId).addComponentToTx(this, component);
        return addToMap("addComponentToTx", new Object[]{this, component});
    }

    public List<IssueComponent> getComponents() {
        return DaoFactorySingelton.getInstance().getIssueBaseDAO(projectId).getComponentsOf(this);
    }

    public boolean removeComponent(final IssueComponent component) {
//        return DaoFactorySingelton.getIssueBaseDAO(projectId).deleteComponentRelationTx(this, component);
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

    public Long getProjectId() {
        return projectId;
    }

    private void setProjectId(final Long projectId) {
        this.projectId = projectId;
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

    public BaseControllingunit getTotalControllingUnit() {
        return totalControllingUnit;
    }

    public void setTotalControllingUnit(BaseControllingunit totalControllingUnit) {
        this.totalControllingUnit = totalControllingUnit;
    }

    public BaseControllingunit getTotalOwnCotntrollingUnit() {
        return totalOwnCotntrollingUnit;
    }

    public void setTotalOwnCotntrollingUnit(BaseControllingunit totalOwnCotntrollingUnit) {
        this.totalOwnCotntrollingUnit = totalOwnCotntrollingUnit;
    }

    public BaseControllingunit getTotalSubIssuesBaseControllingUnit() {
        return totalSubIssuesBaseControllingUnit;
    }

    public void setTotalSubIssuesBaseControllingUnit(BaseControllingunit totalSubIssuesBaseControllingUnit) {
        this.totalSubIssuesBaseControllingUnit = totalSubIssuesBaseControllingUnit;
    }
    
    @Override
    public String name() {
        return getText() + " - " + getSummary();
    }
    
}
