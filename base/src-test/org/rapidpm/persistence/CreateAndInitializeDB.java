package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 16.10.12
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public class CreateAndInitializeDB {
    private static final Logger logger = Logger.getLogger(CreateAndInitializeDB.class);

    private final boolean debug;
    private final GraphDatabaseService graphDb;
    private final Node root;
    private final DaoFactory daoFactory;

    private final Calendar calendar = Calendar.getInstance();
    private final List<IssueStatus> statusList = new ArrayList<>();
    private final List<IssuePriority> priorityList = new ArrayList<>();
    private final List<IssueType> typeList = new ArrayList<>();
    private final List<IssueComponent> componentList = new ArrayList<>();
    private final List<IssueRelation> relationList = new ArrayList<>();
    private final List<IssueVersion> versionList = new ArrayList<>();
    private final List<IssueStoryPoint> storypointList = new ArrayList<>();
    private final List<IssueComment> commentList = new ArrayList<>();
    private final List<IssueTestCase> testCaseList = new ArrayList<>();

    public static void main(final String[] args) {
        final CreateAndInitializeDB setUp = new CreateAndInitializeDB(true);
        setUp.CreateAndInitialize();
    }

    public CreateAndInitializeDB(final boolean debug) {
        this.debug = debug;
        //deleteRelationDependencies();
        deleteFileOrDirectory(true, new File(GraphDBFactory.DB_PATH));
        graphDb = GraphDBFactory.getInstance().getGraphDBService();
        daoFactory = DaoFactorySingelton.getInstance();
        root = graphDb.getNodeById(0);
    }

//    private void deleteRelationDependencies() {
//        IssueBaseDAO tmpDAO = DaoFactorySingelton.getInstance().getIssueBaseDAO(1L);
//        List<IssueBase> issueList = tmpDAO.loadAllEntities();
//        for (IssueBase issue : issueList) {
//            issue.setComments(new ArrayList<IssueComment>());
//            issue.setTestcases(new ArrayList<IssueTestCase>());
//            tmpDAO.persist(issue);
//        }
//    }

    private void deleteFileOrDirectory( final boolean start, final File file ) {
        if (start) System.out.println("Removing old DB");
        if ( file.exists() ) {
            if ( file.isDirectory() ) {
                for ( File child : file.listFiles() ) {
                    deleteFileOrDirectory( false , child );
                }
            }
            file.delete();
        }
    }


    public void CreateAndInitialize(){
        System.out.println("Created DB");
        System.out.println("Start initializing");
        initializeAttributes();
        initializeProject1();
        initializeProject2();
        System.out.println("Finished initializing");
        graphDb.shutdown();
    }

    private void initializeAttributes(){
        int i;

        final IssueStatusDAO statusDao = daoFactory.getIssueStatusDAO();
        final List<List<String>> statusStrings = new ArrayList<>();
        statusStrings.add(new ArrayList<>(Arrays.asList("Open", "status_open.gif")));
        statusStrings.add(new ArrayList<>(Arrays.asList("In Progress", "status_inprogress.gif")));
        statusStrings.add(new ArrayList<>(Arrays.asList("Resolved", "status_resolved.gif")));
        statusStrings.add(new ArrayList<>(Arrays.asList("Closed", "status_closed.gif")));
        statusStrings.add(new ArrayList<>(Arrays.asList("On Hold", "status_onhold.gif")));

        for (List<String> statusStringSingle : statusStrings) {
            i = 0;
            IssueStatus status = statusDao.findByName(statusStringSingle.get(i));
            if (status == null) {
                status = new IssueStatus();
                status.setStatusName(statusStringSingle.get(i++));
                status.setStatusFileName(statusStringSingle.get(i++));
                status = statusDao.persist(status);
            }
            statusList.add(status);
        }


        final IssuePriorityDAO priorityDao = daoFactory.getIssuePriorityDAO();
        final List<List<String>> priorityStrings = new ArrayList<>();
        priorityStrings.add(new ArrayList<>(Arrays.asList("Trivial", "priority_trivial.gif", "0")));
        priorityStrings.add(new ArrayList<>(Arrays.asList("Minor", "priority_minor.gif", "1")));
        priorityStrings.add(new ArrayList<>(Arrays.asList("Major", "priority_major.gif", "2")));
        priorityStrings.add(new ArrayList<>(Arrays.asList("Critical", "priority_critical.gif", "3")));
        priorityStrings.add(new ArrayList<>(Arrays.asList("Blocker", "priority_blocker.gif", "4")));

        for (List<String> priorityStringsSingle : priorityStrings) {
            i = 0;
            IssuePriority priority = priorityDao.findByName(priorityStringsSingle.get(i));
            if (priority == null) {
                priority = new IssuePriority();
                priority.setPriorityName(priorityStringsSingle.get(i++));
                priority.setPriorityFileName(priorityStringsSingle.get(i++));
                priority.setPrio(Integer.valueOf(priorityStringsSingle.get(i++)));
                priority = priorityDao.persist(priority);
            }
            priorityList.add(priority);
        }


        final IssueTypeDAO typeDao = daoFactory.getIssueTypeDAO();
        final List<List<String>> typeStrings = new ArrayList<>();
        typeStrings.add(new ArrayList<>(Arrays.asList("Bug", "type_bug.gif")));
        typeStrings.add(new ArrayList<>(Arrays.asList("Task", "type_task.gif")));
        typeStrings.add(new ArrayList<>(Arrays.asList("Improvement", "type_improvement.gif")));
        typeStrings.add(new ArrayList<>(Arrays.asList("New Function", "type_newfeature.gif")));
        typeStrings.add(new ArrayList<>(Arrays.asList("Epic", "type_epic.gif")));

        for (List<String> typeStringSingle : typeStrings) {
            i = 0;
            IssueType type = typeDao.findByName(typeStringSingle.get(i));
            if (type == null) {
                type = new IssueType();
                type.setTypeName(typeStringSingle.get(i++));
                type.setTypeFileName(typeStringSingle.get(i++));
                type = typeDao.persist(type);
            }
            typeList.add(type);
        }


        final IssueVersionDAO versionDao = daoFactory.getIssueVersionDAO();
        final List<String> versionStrings = new ArrayList<>();
        versionStrings.add(" - ");
        versionStrings.add("Alpha");
        versionStrings.add("1.0");
        versionStrings.add("2.0");

        for (String versionStringSingle : versionStrings) {
            IssueVersion version = versionDao.findByName(versionStringSingle);
            if (version == null) {
                version = new IssueVersion();
                version.setVersionName(versionStringSingle);
                version = versionDao.persist(version);
            }
            versionList.add(version);
        }


        final IssueStoryPointDAO storypointDao = daoFactory.getIssueStoryPointDAO();
        for( int j = 1 ; j < 11 ; j++ ) {
            IssueStoryPoint storyPoint = storypointDao.findByName(String.valueOf(j));
            if (storyPoint == null) {
                storyPoint = new IssueStoryPoint();
                storyPoint.setStorypoint(j);
                storyPoint = storypointDao.persist(storyPoint);
            }
            storypointList.add(storyPoint);
        }


        final IssueComponentDAO componentDAO = daoFactory.getIssueComponentDAO();
        final List<String> componentStrings = new ArrayList<>();
        componentStrings.add("GUI");
        componentStrings.add("IssueTracking");
        componentStrings.add("Planning");
        componentStrings.add("Controlling");
        componentStrings.add("Documentation");

        for (String componentStringSingle : componentStrings) {
            IssueComponent component = componentDAO.findByName(componentStringSingle);
            if (component == null) {
                component = new IssueComponent();
                component.setComponentName(componentStringSingle);
                component = componentDAO.persist(component);
            }
            componentList.add(component);
        }


        final IssueRelationDAO relationDAO = daoFactory.getIssueRelationDAO();
        final List<List<String>> relationStrings = new ArrayList<>();
        relationStrings.add(new ArrayList<>(Arrays.asList("Duplicate", "duplicates", "is duplicated by")));
        relationStrings.add(new ArrayList<>(Arrays.asList("Block", "blocks", "is blocked by")));
        relationStrings.add(new ArrayList<>(Arrays.asList("Dependence", "relates", "depends on")));

        for (List<String> relationStringSingle : relationStrings) {
            i = 0;
            IssueRelation relation = relationDAO.findByName(relationStringSingle.get(i));
            if (relation == null) {
                relation = new IssueRelation();
                relation.setRelationName(relationStringSingle.get(i++));
                relation.setOutgoingName(relationStringSingle.get(i++));
                relation.setIncomingName(relationStringSingle.get(i++));
                relation = relationDAO.persist(relation);
            }
            relationList.add(relation);
        }


        List<List<Integer>> commentValues = new ArrayList<>();
        commentValues.add(Arrays.asList(4, 2012, 8, 8));
        commentValues.add(Arrays.asList(3, 2012, 8, 14));
        commentValues.add(Arrays.asList(2, 2012, 8, 28));
        commentValues.add(Arrays.asList(5, 2012, 9, 2));
        commentValues.add(Arrays.asList(5, 2012, 9, 13));
        commentValues.add(Arrays.asList(4, 2012, 9, 20));
        commentValues.add(Arrays.asList(4, 2012, 9, 29));
        commentValues.add(Arrays.asList(3, 2012, 10, 19));
        commentValues.add(Arrays.asList(3, 2012, 10, 20));
        commentValues.add(Arrays.asList(2, 2012, 11, 11));
        commentValues.add(Arrays.asList(2, 2012, 11, 12));
        commentValues.add(Arrays.asList(3, 2012, 11, 13));
        commentValues.add(Arrays.asList(2, 2012, 11, 14));
        commentValues.add(Arrays.asList(5, 2012, 11, 15));
        commentValues.add(Arrays.asList(4, 2012, 11, 16));
        commentValues.add(Arrays.asList(4, 2012,  9, 29));
        commentValues.add(Arrays.asList(5, 2012, 10, 19));
        commentValues.add(Arrays.asList(3, 2012, 10, 20));
        commentValues.add(Arrays.asList(2, 2012, 11, 11));
        commentValues.add(Arrays.asList(2, 2012, 11, 12));
        commentValues.add(Arrays.asList(3, 2012, 11, 13));
        commentValues.add(Arrays.asList(2, 2012, 11, 14));
        commentValues.add(Arrays.asList(5, 2012, 11, 15));
        commentValues.add(Arrays.asList(4, 2012, 11, 16));
        commentValues.add(Arrays.asList(4, 2012,  9, 29));



        int x = 0;
        for ( List<Integer> values : commentValues ) {
            i = 0;
            IssueComment comment = new IssueComment();
            comment.setText("Comment " + (x + 1));
            comment.setCreator(daoFactory.getBenutzerDAO().findByID(new Long(values.get(i++))));
            calendar.set(values.get(i++), values.get(i++) - 1, values.get(i++));
            comment.setCreated(calendar.getTime());
            comment = daoFactory.saveOrUpdateTX(comment);
            commentList.add(comment);
            x++;
        }


        for ( i = 0 ; i < 20 ; i++ ) {
            IssueTestCase testCase = new IssueTestCase("TestCase " + i);
            testCase = daoFactory.saveOrUpdateTX(testCase);
            testCaseList.add(testCase);
        }
    }

    private void initializeProject1() {
        final List<IssueBase> issues = new ArrayList<>();
        final List<List<Integer>> issueAttr = new ArrayList<>();
        final Long projectId = 1L;

        //status,priority,type,  reporter,assignee,  dueDate_planned(3),_resolved(3),_closed(3),   version,
        // storypoints,  components(2),
        //-timeunit_estimated-,  -timeunit_used(3)-,  testcases(2),  comments(2),  risk,  planningunit

        //Month - 1;  January = 0 ; December = 11;
issueAttr.add(Arrays.asList(0,2,4,  2,2,  2012, 7,12, 2012, 8,13, 2012, 8,21,  2, 2,  2,-1,  -1,-1,  14,-1,  25,  -1));
issueAttr.add(Arrays.asList(3,3,1,  3,2,  2012, 7,12, 2012, 7,14, 2012, 7,15,  2, 1,  2,-1,  -1,-1,   1, 2,  -1,  -1));
issueAttr.add(Arrays.asList(2,3,1,  3,3,  2012, 7,12, 2012, 7,18, 2012, 7,19,  2, 6,  2,-1,  -1,-1,   3,-1,  -1,  -1));
issueAttr.add(Arrays.asList(1,0,1,  2,5,  2012, 7,12, 2012, 8, 5, 2012, 8, 6,  2, 1,  2,-1,   1,-1,  13,-1,  -1,  -1));
issueAttr.add(Arrays.asList(4,1,3,  4,2,  2012, 7,12, 2012, 8, 8, 2012, 8, 9,  2, 6,  2,-1,   2,17,   7,-1,  -1,  -1));
issueAttr.add(Arrays.asList(0,2,3,  2,4,  2012, 7,12, 2012, 8,13, 2012, 8,13,  2, 3,  2,-1,   5,-1,   4,-1,  -1,  -1));
issueAttr.add(Arrays.asList(0,3,1,  3,2,  2012, 7,12, 2012, 8,13, 2012, 8,13,  2, 4,  2,-1,   4,16,   8,-1,  -1,  -1));

issueAttr.add(Arrays.asList(0,0,4,  2,2,  2012, 7,12, 2012, 8,30, 2012, 8,30,  2, 8,  3,-1,   6,-1,  16,-1,  50,  -1));
issueAttr.add(Arrays.asList(3,1,1,  5,4,  2012, 7,12, 2012, 8,18, 2012, 8,19,  2, 3,  3,-1,   7,-1,   5,-1,  -1,  -1));
issueAttr.add(Arrays.asList(0,2,0,  4,5,  2012, 7,12, 2012, 8,22, 2012, 8,23,  2, 7,  3,-1,   8,-1,  17,18,  -1,  -1));
issueAttr.add(Arrays.asList(0,3,2,  2,3,  2012, 7,12, 2012, 8,28, 2012, 8,28,  2, 4,  3,-1,   9,-1,  12,-1,  -1,  -1));

issueAttr.add(Arrays.asList(0,4,4,  2,2,  2012, 7,12, 2013, 0,14, 2012, 0,17,  2,10,  1, 2,  10,-1,  -1,-1,  75,  -1));
issueAttr.add(Arrays.asList(3,2,2,  4,5,  2012, 7,12, 2012, 9, 4, 2012, 9, 5,  2, 3,  1,-1,  11,-1,   6,-1,  -1,  -1));
issueAttr.add(Arrays.asList(2,3,3,  2,4,  2012, 7,12, 2012,11, 1, 2012,11, 2,  2, 6,  1,-1,   3,-1,  19,20,  -1,  -1));
issueAttr.add(Arrays.asList(0,4,2,  3,2,  2012, 7,12, 2013, 0,13, 2012, 0,14,  2,10,  1,-1,  13,18,  15,-1,  -1,  -1));

issueAttr.add(Arrays.asList(1,0,4,  3,1,  2012, 7,12, 2012, 2,31, 2012, 3, 9,  2, 5,  4, 2,  15,-1,  -1,-1, 100,  -1));
issueAttr.add(Arrays.asList(2,1,1,  5,2,  2012, 7,12, 2012, 0,31, 2012, 1,14,  2, 2,  4,-1,  -1,-1,   9,-1,  -1,  -1));
issueAttr.add(Arrays.asList(3,2,0,  4,4,  2012, 7,12, 2012, 1,12, 2012, 1,14,  2, 7,  4,-1,  14,19,  21,-1,  -1,  -1));
issueAttr.add(Arrays.asList(0,3,2,  2,5,  2012, 7,12, 2012, 1,14, 2012, 1,14,  2, 6,  4,-1,  20,-1,  22,-1,  -1,  -1));
issueAttr.add(Arrays.asList(1,4,1,  4,3,  2012, 7,12, 2012, 1,14, 2012, 1,14,  2, 1,  4,-1,  -1,-1,  10,-1,  -1,  -1));
issueAttr.add(Arrays.asList(2,1,2,  2,2,  2012, 7,12, 2012, 2,12, 2012, 2,19,  2, 9,  4, 1,  -1,-1,  11,-1,  -1,  -1));
issueAttr.add(Arrays.asList(3,2,4,  5,4,  2012, 7,12, 2012, 2,19, 2012, 2,19,  2, 6,  4,-1,  12,-1,  23,24,  -1,  -1));
issueAttr.add(Arrays.asList(0,3,3,  3,2,  2012, 7,12, 2012, 2,24, 2012, 2,31,  2, 3,  4,-1,  -1,-1,  25,-1,  -1,  -1));

        final BenutzerDAO benutzerDao = daoFactory.getBenutzerDAO();
        final IssueBaseDAO issueDao = daoFactory.getIssueBaseDAO(projectId);
        //fill Issues with Attributes
        int i, x = 0;
        for (final List<Integer> attributes : issueAttr) {
            i = 0;
            final IssueBase issue = new IssueBase(projectId);
            //issue.setText("TPR-" + x);
            issue.setSummary("Issue Summary " + x);
            issue.setStory("Issue Story " + x);
            issue.setStatus(statusList.get(attributes.get(i++)));
            issue.setPriority(priorityList.get(attributes.get(i++)));
            issue.setType(typeList.get(attributes.get(i++)));
            issue.setReporter(benutzerDao.findByID(new Long(attributes.get(i++))));
            issue.setAssignee(benutzerDao.findByID(new Long(attributes.get(i++))));

            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_planned(calendar.getTime());
            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_resolved(calendar.getTime());
            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_closed(calendar.getTime());

            issue.setVersion(versionList.get(attributes.get(i++)));
            issue.setStoryPoints(storypointList.get(attributes.get(i++) - 1));
            for (int j = 0; j < 2; j++) {
                if (attributes.get(i) != -1)
                    issue.addComponent(componentList.get(attributes.get(i)));
                i++;
            }

            for (int j = 0; j < 2; j++) {
                if (attributes.get(i) != -1) {
                    final IssueTestCase testcase = testCaseList.get(attributes.get(i) - 1);
                    issue.addOrChangeTestCase(testcase);
                }
                i++;
            }

            for (int j = 0; j < 2; j++) {
                if (attributes.get(i) != -1) {
                    final IssueComment comment = commentList.get(attributes.get(i) - 1);
                    issue.addOrChangeComment(comment);
                }
                i++;
            }

            if (attributes.get(i) != -1)
                issue.setRisk(attributes.get(i));
            i++;

            if (attributes.get(i) != -1) {
                final PlanningUnit pu = new PlanningUnit();
                pu.setId(new Long(attributes.get(i)));
                issue.setPlanningUnit(pu);
            }
            issues.add(issueDao.persist(issue));
            x++;
        }

        //Create Sublists
        issues.get(0).addSubIssue(issues.get(1));
            issues.get(1).addSubIssue(issues.get(2));
            issues.get(1).addSubIssue(issues.get(3));
        issues.get(0).addSubIssue(issues.get(4));
        issues.get(0).addSubIssue(issues.get(5));
        issues.get(0).addSubIssue(issues.get(6));

        issues.get(7).addSubIssue(issues.get(8));
        issues.get(7).addSubIssue(issues.get(9));
        issues.get(7).addSubIssue(issues.get(10));

        issues.get(11).addSubIssue(issues.get(12));
        issues.get(11).addSubIssue(issues.get(13));
        issues.get(11).addSubIssue(issues.get(14));

        issues.get(15).addSubIssue(issues.get(16));
            issues.get(16).addSubIssue(issues.get(17));
            issues.get(16).addSubIssue(issues.get(18));
            issues.get(16).addSubIssue(issues.get(19));
        issues.get(15).addSubIssue(issues.get(20));
            issues.get(20).addSubIssue(issues.get(21));
            issues.get(15).addSubIssue(issues.get(22));


        issues.get(2).connectToIssueAs(issues.get(3), relationList.get(0));
        issues.get(19).connectToIssueAs(issues.get(21), relationList.get(1));
        issues.get(2).connectToIssueAs(issues.get(1), relationList.get(1));
        issues.get(1).connectToIssueAs(issues.get(2), relationList.get(0));

        for (final IssueBase issue : issues) {
            issueDao.persist(issue);
        }

        if (debug)
            for (final IssueBase singleIssue : issues)
                System.out.println(singleIssue.toString());

    }



    private void initializeProject2() {
        final Long projectId = 2L;
        final BenutzerDAO benutzerDao = daoFactory.getBenutzerDAO();
        final IssueBaseDAO issueDao = daoFactory.getIssueBaseDAO(projectId);

        for (int i = 0; i<3 ;i++) {
            IssueBase issueBase = new IssueBase(projectId);
            issueBase.setSummary("Issue Summary" + projectId + " " + i);
            issueBase.setStory("Issue Story" + projectId + " " + i);
            issueBase.setDueDate_closed(new Date());
            issueBase.setDueDate_planned(new Date());
            issueBase.setDueDate_resolved(new Date());
            issueBase.setVersion(versionList.get(0));
            issueBase.setStoryPoints(storypointList.get(i));

            issueBase.setAssignee(benutzerDao.findByID(i + projectId));
            issueBase.setReporter(benutzerDao.findByID(0 + projectId));

            issueBase.setStatus(statusList.get(i));
            issueBase.setType(typeList.get(i));
            issueBase.setPriority(priorityList.get(i));
            issueBase.addComponent(componentList.get(i));

            issueBase = issueDao.persist(issueBase);
            if (debug)
                System.out.println(issueBase.toString());
        }
    }

}
