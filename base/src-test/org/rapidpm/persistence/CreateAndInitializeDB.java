package org.rapidpm.persistence;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.system.security.Benutzer;

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
    private GraphDatabaseService graphDb;
    private Node root;

    private static final List<IssueStatus> statusList = new ArrayList<>();
    private static final List<IssuePriority> priorityList = new ArrayList<>();
    private static final List<IssueType> typeList = new ArrayList<>();
    private static final List<IssueComponent> componentList = new ArrayList<>();
    private static final List<IssueRelation> relationList = new ArrayList<>();

    public static void main(final String[] args) {
        CreateAndInitializeDB setUp = new CreateAndInitializeDB();
        setUp.createDB();
        setUp.initializeDB();
    }

    public CreateAndInitializeDB() {
        deleteFileOrDirectory(true, new File(GraphDBFactory.DB_PATH));
        graphDb = GraphDBFactory.getInstance().getGraphDBService();
        root = graphDb.getNodeById(0);
    }

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


    public void createDB() {
        System.out.println("Start creating");

        Transaction tx = graphDb.beginTx();
        try{
            RelationshipType rel = GraphRelationRegistry.getRootToClassRootRelType(IssueBase.class);
            Node project_root = graphDb.createNode();
            project_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(project_root, rel);

            Node project1 = graphDb.createNode();
            project1.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), new Long(1));
            project_root.createRelationshipTo(project1, rel);

            Node project2 = graphDb.createNode();
            project2.setProperty(GraphRelationRegistry.getRelationAttributeProjectId(), new Long(2));
            project_root.createRelationshipTo(project2, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueStatus.class);
            Node status_root = graphDb.createNode();
            status_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(status_root, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssuePriority.class);
            Node priority_root = graphDb.createNode();
            priority_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(priority_root, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueType.class);
            Node type_root = graphDb.createNode();
            type_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(type_root, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueComponent.class);
            Node component_root = graphDb.createNode();
            component_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(component_root, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueRelation.class);
            Node issueRelation_root = graphDb.createNode();
            issueRelation_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(issueRelation_root, rel);


            tx.success();
        } finally {
            tx.finish();
        }
        System.out.println("Finished creating");
    }


    public void initializeDB(){
        System.out.println("Start initializing");
        initializeAttributes();
        initializeProject1();
        initializeProject2();
        System.out.println("Finished initializing");
    }

    private void initializeAttributes(){

        IssueStatus status = new IssueStatus();
        status.setStatusName("Open");
        status.setStatusFileName("status_open.gif");
        statusList.add(GraphDaoFactory.getIssueStatusDAO().persist(status));

        status = new IssueStatus();
        status.setStatusName("In Progress");
        status.setStatusFileName("status_inprogress.gif");
        statusList.add(GraphDaoFactory.getIssueStatusDAO().persist(status));

        status = new IssueStatus();
        status.setStatusName("Resolved");
        status.setStatusFileName("status_resolved.gif");
        statusList.add(GraphDaoFactory.getIssueStatusDAO().persist(status));

        status = new IssueStatus();
        status.setStatusName("Closed");
        status.setStatusFileName("status_closed.gif");
        statusList.add(GraphDaoFactory.getIssueStatusDAO().persist(status));

        status = new IssueStatus();
        status.setStatusName("On Hold");
        status.setStatusFileName("status_onhold.gif");
        statusList.add(GraphDaoFactory.getIssueStatusDAO().persist(status));


        IssuePriority priority = new IssuePriority();
        priority.setPriorityName("Trivial");
        priority.setPriorityFileName("priority_trivial.gif");
        priority.setPrio(0);
        priorityList.add(GraphDaoFactory.getIssuePriorityDAO().persist(priority));

        priority = new IssuePriority();
        priority.setPriorityName("Minor");
        priority.setPriorityFileName("priority_minor.gif");
        priority.setPrio(1);
        priorityList.add(GraphDaoFactory.getIssuePriorityDAO().persist(priority));

        priority = new IssuePriority();
        priority.setPriorityName("Major");
        priority.setPriorityFileName("priority_major.gif");
        priority.setPrio(2);
        priorityList.add(GraphDaoFactory.getIssuePriorityDAO().persist(priority));

        priority = new IssuePriority();
        priority.setPriorityName("Critical");
        priority.setPriorityFileName("priority_critical.gif");
        priority.setPrio(3);
        priorityList.add(GraphDaoFactory.getIssuePriorityDAO().persist(priority));

        priority = new IssuePriority();
        priority.setPriorityName("Blocker");
        priority.setPriorityFileName("priority_blocker.gif");
        priority.setPrio(4);
        priorityList.add(GraphDaoFactory.getIssuePriorityDAO().persist(priority));



        IssueType type = new IssueType();
        type.setTypeName("Bug");
        typeList.add(GraphDaoFactory.getIssueTypeDAO().persist(type));

        type = new IssueType();
        type.setTypeName("Task");
        typeList.add(GraphDaoFactory.getIssueTypeDAO().persist(type));

        type = new IssueType();
        type.setTypeName("Improvement");
        typeList.add(GraphDaoFactory.getIssueTypeDAO().persist(type));

        type = new IssueType();
        type.setTypeName("New Function");
        typeList.add(GraphDaoFactory.getIssueTypeDAO().persist(type));

        type = new IssueType();
        type.setTypeName("Epic");
        typeList.add(GraphDaoFactory.getIssueTypeDAO().persist(type));



        IssueRelation relation = new IssueRelation();
        relation.setRelationName("Duplicate");
        relation.setOutgoingName("duplicates");
        relation.setIncomingName("is duplicated by");
        relationList.add(GraphDaoFactory.getIssueRelationDAO().persist(relation));

        relation = new IssueRelation();
        relation.setRelationName("Block");
        relation.setOutgoingName("blocks");
        relation.setIncomingName("is blocked by");
        relationList.add(GraphDaoFactory.getIssueRelationDAO().persist(relation));


        IssueComponent component = new IssueComponent("GUI");
        componentList.add(GraphDaoFactory.getIssueComponentDAO().persist(component));

        component = new IssueComponent("IssueTracking");
        componentList.add(GraphDaoFactory.getIssueComponentDAO().persist(component));

        component = new IssueComponent("Planning");
        componentList.add(GraphDaoFactory.getIssueComponentDAO().persist(component));

        component = new IssueComponent("Controlling");
        componentList.add(GraphDaoFactory.getIssueComponentDAO().persist(component));

        component = new IssueComponent("Documentation");
        componentList.add(GraphDaoFactory.getIssueComponentDAO().persist(component));
    }

    private void initializeProject1() {
        List<IssueBase> issues = new ArrayList<>();
        List<List<Integer>> issueAttr = new ArrayList<>();
        Long projectId = new Long(1);
        Calendar calendar = Calendar.getInstance();
        Benutzer user;

        //status,priority,type,  reporter,assignee,  dueDate_planned(3),_resolved(3),_closed(3),   storypoints,  components(2),
        //-timeunit_estimated-,  -timeunit_used(3)-,  -testcases(3)-,  comments(1),  risk,  planningunit

        //Month - 1;  January = 0 ; December = 11;
        issueAttr.add(Arrays.asList(0,2,4,  2,2,  2012, 7,12, 2012, 8,13, 2012, 8,21,  2,  2,-1,       14,  -1,  -1));
        issueAttr.add(Arrays.asList(3,3,1,  3,2,  2012, 7,12, 2012, 7,14, 2012, 7,15,  1,  2,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(2,3,1,  3,3,  2012, 7,12, 2012, 7,18, 2012, 7,19,  6,  2,-1,        3,  -1,  -1));
        issueAttr.add(Arrays.asList(1,0,1,  2,5,  2012, 7,12, 2012, 8, 5, 2012, 8, 6,  1,  2,-1,       13,  -1,  -1));
        issueAttr.add(Arrays.asList(4,1,3,  4,2,  2012, 7,12, 2012, 8, 8, 2012, 8, 9,  6,  2,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(0,2,3,  2,4,  2012, 7,12, 2012, 8,13, 2012, 8,13,  3,  2,-1,        4,  -1,  -1));
        issueAttr.add(Arrays.asList(0,3,1,  3,2,  2012, 7,12, 2012, 8,13, 2012, 8,13,  4,  2,-1,       -1,  -1,  -1));

        issueAttr.add(Arrays.asList(0,0,4,  2,2,  2012, 7,12, 2012, 8,30, 2012, 8,30,  8,  3,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(3,1,1,  5,4,  2012, 7,12, 2012, 8,18, 2012, 8,19,  3,  3,-1,        5,  -1,  -1));
        issueAttr.add(Arrays.asList(0,2,0,  4,5,  2012, 7,12, 2012, 8,22, 2012, 8,23,  7,  3,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(0,3,2,  2,3,  2012, 7,12, 2012, 8,28, 2012, 8,28,  4,  3,-1,       12,  -1,  -1));

        issueAttr.add(Arrays.asList(0,4,4,  2,2,  2012, 7,12, 2013, 0,14, 2012, 0,17, 10,  1, 2,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(3,2,2,  4,5,  2012, 7,12, 2012, 9,04, 2012, 9, 5,  3,  1,-1,        6,  -1,  -1));
        issueAttr.add(Arrays.asList(2,3,3,  2,4,  2012, 7,12, 2012,11, 1, 2012,11, 2,  6,  1,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(0,4,2,  3,2,  2012, 7,12, 2013, 0,13, 2012, 0,14, 10,  1,-1,       15,  -1,  -1));

        issueAttr.add(Arrays.asList(1,0,4,  3,1,  2012, 7,12, 2012, 2,31, 2012, 3, 9,  5,  4, 2,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(2,1,1,  5,2,  2012, 7,12, 2012, 0,31, 2012, 1,14,  2,  4,-1,        9,  -1,  -1));
        issueAttr.add(Arrays.asList(3,2,0,  4,4,  2012, 7,12, 2012, 1,12, 2012, 1,14,  7,  4,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(0,3,2,  2,5,  2012, 7,12, 2012, 1,14, 2012, 1,14,  6,  4,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(1,4,1,  4,3,  2012, 7,12, 2012, 1,14, 2012, 1,14,  1,  4,-1,       10,  -1,  -1));
        issueAttr.add(Arrays.asList(2,1,2,  2,2,  2012, 7,12, 2012, 2,12, 2012, 2,19,  9,  4, 1,       11,  -1,  -1));
        issueAttr.add(Arrays.asList(3,2,4,  5,4,  2012, 7,12, 2012, 2,19, 2012, 2,19,  6,  4,-1,       -1,  -1,  -1));
        issueAttr.add(Arrays.asList(0,3,3,  3,2,  2012, 7,12, 2012, 2,24, 2012, 2,31,  3,  4,-1,       -1,  -1,  -1));
        
        //fill Issues with Attributes
        int i, x = 0;
        for (List<Integer> attributes : issueAttr) {
            i = 0;
            IssueBase issue = new IssueBase(projectId);
            issue.setSummary("Issue " + x);
            issue.setText("TPR-" + x);
            issue.setStatus(statusList.get(attributes.get(i++)));
            issue.setPriority(priorityList.get(attributes.get(i++)));
            issue.setType(typeList.get(attributes.get(i++)));
            user = new Benutzer();
            user.setId(new Long(attributes.get(i++)));
            issue.setReporter(user);
            user = new Benutzer();
            user.setId(new Long(attributes.get(i++)));
            issue.setAssignee(user);

            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_planned(calendar.getTime());
            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_resolved(calendar.getTime());
            calendar.set(attributes.get(i++), attributes.get(i++), attributes.get(i++));
            issue.setDueDate_closed(calendar.getTime());

            issue.setVersion("1.0");
            issue.setStoryPoints(attributes.get(i++));
            for (int j = 0; j < 2; j++) {
                if (attributes.get(i) != -1)
                    issue.addComponent(componentList.get(attributes.get(i)));
                i++;
            }

            issue.setRisk(String.valueOf(attributes.get(i++)));
            if (attributes.get(i) != -1) {
                PlanningUnit pu = new PlanningUnit();
                pu.setId(new Long(attributes.get(i)));
                issue.setPlanningUnit(pu);
            }
            i++;
            issues.add(GraphDaoFactory.getIssueBaseDAO(projectId).persist(issue));
            x++;
        }


        for (IssueBase singleIssue : issues)
            System.out.println(singleIssue.toString());


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

    }



    private void initializeProject2() {
        Long projectId = new Long(2);


        for (int i = 0; i<3 ;i++) {
            IssueBase issueBase = new IssueBase(projectId);
            issueBase.setVersion("1.0");
            issueBase.setStoryPoints(i);
            issueBase.setSummary("Issue " + projectId + " " + i);
            issueBase.setText("Text " + projectId + " " + i);
            issueBase.setDueDate_closed(new Date());
            issueBase.setDueDate_planned(new Date());
            issueBase.setDueDate_resolved(new Date());

            Benutzer benutzer = new Benutzer();
            benutzer.setId(new Long(i+projectId));
            issueBase.setAssignee(benutzer);

            benutzer = new Benutzer();
            benutzer.setId(new Long(0+projectId));
            issueBase.setReporter(benutzer);

            issueBase.setStatus(statusList.get(i));
            issueBase.setType(typeList.get(i));
            issueBase.setPriority(priorityList.get(i));
            issueBase.addComponent(componentList.get(i));

            issueBase = GraphDaoFactory.getIssueBaseDAO(projectId).persist(issueBase);
            System.out.println(issueBase.toString());
        }
    }

}
