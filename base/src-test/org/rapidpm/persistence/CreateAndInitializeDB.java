package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
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
        if (start) System.out.println("Removing old db");
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
        System.out.println("Start creating");
        createDB();
        System.out.println("Finished creating");
        System.out.println("Start initializing");
        initializeAttributes();
        initializeProject1();
        initializeProject2();
        System.out.println("Finished initializing");
        graphDb.shutdown();
    }


    public void createDB() {
        final Transaction tx = graphDb.beginTx();
        try{
            final RelationshipType relProject = GraphRelationRegistry.getRootToClassRootRelType(IssueBase.class);
            Node project_root = graphDb.createNode();
            project_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relProject.name());
            root.createRelationshipTo(project_root, relProject);

            Node project1 = graphDb.createNode();
            project1.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), 1L);
            project1.setProperty(GraphRelationRegistry.getRelationAttributeProjectToken(), "PRO1");
            project1.setProperty(GraphRelationRegistry.getRelationAttributeTokenId(), 1);
            project_root.createRelationshipTo(project1, relProject);

            Node project2 = graphDb.createNode();
            project2.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), 2L);
            project2.setProperty(GraphRelationRegistry.getRelationAttributeProjectToken(), "PRO2");
            project2.setProperty(GraphRelationRegistry.getRelationAttributeTokenId(), 1);
            project_root.createRelationshipTo(project2, relProject);

            final RelationshipType relStatus = GraphRelationRegistry.getRootToClassRootRelType(IssueStatus.class);
            Node status_root = graphDb.createNode();
            status_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relStatus.name());
            root.createRelationshipTo(status_root, relStatus);

            final RelationshipType relPriority = GraphRelationRegistry.getRootToClassRootRelType(IssuePriority.class);
            Node priority_root = graphDb.createNode();
            priority_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relPriority.name());
            root.createRelationshipTo(priority_root, relPriority);

            final RelationshipType relType = GraphRelationRegistry.getRootToClassRootRelType(IssueType.class);
            Node type_root = graphDb.createNode();
            type_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relType.name());
            root.createRelationshipTo(type_root, relType);

            final RelationshipType relComponent = GraphRelationRegistry.getRootToClassRootRelType(IssueComponent.class);
            Node component_root = graphDb.createNode();
            component_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relComponent.name());
            root.createRelationshipTo(component_root, relComponent);

            final RelationshipType relRelation = GraphRelationRegistry.getRootToClassRootRelType(IssueRelation.class);
            Node issueRelation_root = graphDb.createNode();
            issueRelation_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relRelation.name());
            root.createRelationshipTo(issueRelation_root, relRelation);

            final RelationshipType relVersion = GraphRelationRegistry.getRootToClassRootRelType(IssueVersion.class);
            Node issueversion_root = graphDb.createNode();
            issueversion_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relVersion.name());
            root.createRelationshipTo(issueversion_root, relVersion);

            final RelationshipType relStoryPoint = GraphRelationRegistry.getRootToClassRootRelType(IssueStoryPoint.class);
            Node issuestorypoint_root = graphDb.createNode();
            issuestorypoint_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), relStoryPoint.name());
            root.createRelationshipTo(issuestorypoint_root, relStoryPoint);

            tx.success();
        } finally {
            tx.finish();
        }
    }

    private void initializeAttributes(){

        final IssueStatus status1 = new IssueStatus();
        status1.setStatusName("Open");
        status1.setStatusFileName("status_open.gif");
        statusList.add(daoFactory.getIssueStatusDAO().persist(status1));

        final IssueStatus status2 = new IssueStatus();
        status2.setStatusName("In Progress");
        status2.setStatusFileName("status_inprogress.gif");
        statusList.add(daoFactory.getIssueStatusDAO().persist(status2));

        final IssueStatus status3 = new IssueStatus();
        status3.setStatusName("Resolved");
        status3.setStatusFileName("status_resolved.gif");
        statusList.add(daoFactory.getIssueStatusDAO().persist(status3));

        final IssueStatus status4 = new IssueStatus();
        status4.setStatusName("Closed");
        status4.setStatusFileName("status_closed.gif");
        statusList.add(daoFactory.getIssueStatusDAO().persist(status4));

        final IssueStatus status5 = new IssueStatus();
        status5.setStatusName("On Hold");
        status5.setStatusFileName("status_onhold.gif");
        statusList.add(daoFactory.getIssueStatusDAO().persist(status5));



        final IssuePriority priority1 = new IssuePriority();
        priority1.setPriorityName("Trivial");
        priority1.setPriorityFileName("priority_trivial.gif");
        priority1.setPrio(0);
        priorityList.add(daoFactory.getIssuePriorityDAO().persist(priority1));

        final IssuePriority priority2 = new IssuePriority();
        priority2.setPriorityName("Minor");
        priority2.setPriorityFileName("priority_minor.gif");
        priority2.setPrio(1);
        priorityList.add(daoFactory.getIssuePriorityDAO().persist(priority2));

        final IssuePriority priority3 = new IssuePriority();
        priority3.setPriorityName("Major");
        priority3.setPriorityFileName("priority_major.gif");
        priority3.setPrio(2);
        priorityList.add(daoFactory.getIssuePriorityDAO().persist(priority3));

        final IssuePriority priority4 = new IssuePriority();
        priority4.setPriorityName("Critical");
        priority4.setPriorityFileName("priority_critical.gif");
        priority4.setPrio(3);
        priorityList.add(daoFactory.getIssuePriorityDAO().persist(priority4));

        final IssuePriority priority5 = new IssuePriority();
        priority5.setPriorityName("Blocker");
        priority5.setPriorityFileName("priority_blocker.gif");
        priority5.setPrio(4);
        priorityList.add(daoFactory.getIssuePriorityDAO().persist(priority5));



        final IssueType type1 = new IssueType("Bug");
        typeList.add(daoFactory.getIssueTypeDAO().persist(type1));

        final IssueType type2 = new IssueType("Task");
        typeList.add(daoFactory.getIssueTypeDAO().persist(type2));

        final IssueType type3 = new IssueType("Improvement");
        typeList.add(daoFactory.getIssueTypeDAO().persist(type3));

        final IssueType type4 = new IssueType("New Function");
        typeList.add(daoFactory.getIssueTypeDAO().persist(type4));

        final IssueType type5 = new IssueType("Epic");
        typeList.add(daoFactory.getIssueTypeDAO().persist(type5));



        final IssueVersion version1 = new IssueVersion(" - ");
        versionList.add(daoFactory.getIssueVersionDAO().persist(version1));

        final IssueVersion version2 = new IssueVersion("Alpha");
        versionList.add(daoFactory.getIssueVersionDAO().persist(version2));

        final IssueVersion version3 = new IssueVersion("1.0");
        versionList.add(daoFactory.getIssueVersionDAO().persist(version3));

        final IssueVersion version4 = new IssueVersion("2.0");
        versionList.add(daoFactory.getIssueVersionDAO().persist(version4));



        for(int i = 1; i < 11; i++) {
            final IssueStoryPoint storypoint = new IssueStoryPoint(i);
            storypointList.add(daoFactory.getIssueStoryPointDAO().persist(storypoint));
        }



        final IssueRelation relation1 = new IssueRelation();
        relation1.setRelationName("Duplicate");
        relation1.setOutgoingName("duplicates");
        relation1.setIncomingName("is duplicated by");
        relationList.add(daoFactory.getIssueRelationDAO().persist(relation1));

        final IssueRelation relation2 = new IssueRelation();
        relation2.setRelationName("Block");
        relation2.setOutgoingName("blocks");
        relation2.setIncomingName("is blocked by");
        relationList.add(daoFactory.getIssueRelationDAO().persist(relation2));

        final IssueRelation relation3 = new IssueRelation();
        relation3.setRelationName("Dependence");
        relation3.setOutgoingName("relates");
        relation3.setIncomingName("depends on");
        relationList.add(daoFactory.getIssueRelationDAO().persist(relation3));


        final IssueComponent component1 = new IssueComponent("GUI");
        componentList.add(daoFactory.getIssueComponentDAO().persist(component1));

        final IssueComponent component2 = new IssueComponent("IssueTracking");
        componentList.add(daoFactory.getIssueComponentDAO().persist(component2));

        final IssueComponent component3 = new IssueComponent("Planning");
        componentList.add(daoFactory.getIssueComponentDAO().persist(component3));

        final IssueComponent component4 = new IssueComponent("Controlling");
        componentList.add(daoFactory.getIssueComponentDAO().persist(component4));

        final IssueComponent component5 = new IssueComponent("Documentation");
        componentList.add(daoFactory.getIssueComponentDAO().persist(component5));


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



        int i, x = 0;
        for (List<Integer> values : commentValues) {
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


        for (i = 0; i < 20; i++) {
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
            issues.add(daoFactory.getIssueBaseDAO(projectId).persist(issue));
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
            daoFactory.getIssueBaseDAO(projectId).persist(issue);
        }

        if (debug)
            for (final IssueBase singleIssue : issues)
                System.out.println(singleIssue.toString());

    }



    private void initializeProject2() {
        final Long projectId = 2L;
        final BenutzerDAO benutzerDao = daoFactory.getBenutzerDAO();

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

            issueBase = daoFactory.getIssueBaseDAO(projectId).persist(issueBase);
            if (debug)
                System.out.println(issueBase.toString());
        }
    }

}
