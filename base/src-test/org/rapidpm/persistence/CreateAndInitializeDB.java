package org.rapidpm.persistence;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.system.security.Benutzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public static void main(final String[] args) {
        CreateAndInitializeDB setUp = new CreateAndInitializeDB();
        System.out.println("Removing old db");
        setUp.deleteFileOrDirectory(new File(GraphDBFactory.DB_PATH));
        System.out.println("Finished removing");
        System.out.println("Start creating");
        setUp.createDB();
        System.out.println("Finished creating");
        System.out.println("Start initializing");
        setUp.initializeDB();
        System.out.println("Finished initializing");
    }

    public static void deleteFileOrDirectory( final File file ) {
        if ( file.exists() ) {
            if ( file.isDirectory() ) {
                for ( File child : file.listFiles() ) {
                    deleteFileOrDirectory( child );
                }
            }
            file.delete();
        }
    }


    public void createDB() {

        graphDb = GraphDBFactory.getInstance().getGraphDBService();
        root = graphDb.getNodeById(0);

        Transaction tx = graphDb.beginTx();
        try{
            RelationshipType rel = GraphRelationRegistry.getRootToClassRootRelType(PlannedProject.class);
            Node project_root = graphDb.createNode();
            project_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(project_root, rel);

            Node project1 = graphDb.createNode();
            project1.setProperty(rel.name(), "Project 1");
            project_root.createRelationshipTo(project1, rel);

            Node project2 = graphDb.createNode();
            project2.setProperty(rel.name(), "Project 2");
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

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueBase.class);
            Node issueBase_root = graphDb.createNode();
            issueBase_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(issueBase_root, rel);

            rel = GraphRelationRegistry.getRootToClassRootRelType(IssueRelation.class);
            Node issueRelation_root = graphDb.createNode();
            issueRelation_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(issueRelation_root, rel);


            tx.success();
        } finally {
            tx.finish();
        }
    }


    public void initializeDB(){
        IssueStatus status = new IssueStatus();
        status.setStatusName("open");
        status.setStatusFileName("status_open.gif");
        GraphDaoFactory.getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("closed");
        status.setStatusFileName("status_closed.gif");
        GraphDaoFactory.getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("resolved");
        status.setStatusFileName("status_resolved.gif");
        GraphDaoFactory.getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("onhold");
        status.setStatusFileName("status_onhold.gif");
        GraphDaoFactory.getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("inprogress");
        status.setStatusFileName("status_inprogress.gif");
        status = GraphDaoFactory.getIssueStatusDAO().persist(status);


        IssuePriority priority = new IssuePriority();
        priority.setPriorityName("trivial");
        priority.setPriorityFileName("priority_trivial.gif");
        priority.setPrio(0);
        GraphDaoFactory.getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("minor");
        priority.setPriorityFileName("priority_minor.gif");
        priority.setPrio(1);
        GraphDaoFactory.getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("major");
        priority.setPriorityFileName("priority_major.gif");
        priority.setPrio(2);
        GraphDaoFactory.getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("critical");
        priority.setPriorityFileName("priority_critical.gif");
        priority.setPrio(3);
        GraphDaoFactory.getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("blocker");
        priority.setPriorityFileName("priority_blocker.gif");
        priority.setPrio(4);
        priority = GraphDaoFactory.getIssuePriorityDAO().persist(priority);


        IssueType type = new IssueType();
        type.setTypeName("bug");
        GraphDaoFactory.getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("task");
        GraphDaoFactory.getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("improvement");
        GraphDaoFactory.getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("newfunction");
        type = GraphDaoFactory.getIssueTypeDAO().persist(type);



        IssueRelation relation = new IssueRelation();
        relation.setRelationName("Duplicate");
        relation.setOutgoingName("duplicates");
        relation.setIncomingName("is duplicated by");
        GraphDaoFactory.getIssueRelationDAO().persist(relation);

        relation = new IssueRelation();
        relation.setRelationName("Block");
        relation.setOutgoingName("blocks");
        relation.setIncomingName("is blocked by");
        GraphDaoFactory.getIssueRelationDAO().persist(relation);


        IssueComponent component = new IssueComponent("Oberfläche");
        GraphDaoFactory.getIssueComponentDAO().persist(component);

        component = new IssueComponent("IssueTracking");
        GraphDaoFactory.getIssueComponentDAO().persist(component);

        component = new IssueComponent("Dokumentation");
        GraphDaoFactory.getIssueComponentDAO().persist(component);

        List<IssueBase> list = new ArrayList<>();
        for (int i = 1; i<4 ;i++) {
            IssueBase issueBase = new IssueBase();
            issueBase.setVersion("1.0");
            issueBase.setStoryPoints(i);
            issueBase.setSummary("Issue " + i);
            issueBase.setText("Text " + i);
            issueBase.setDueDate_closed(new Date());
            issueBase.setDueDate_planned(new Date());
            issueBase.setDueDate_resolved(new Date());

            Benutzer benutzer = new Benutzer();
            benutzer.setId(new Long(4));
            issueBase.setAssignee(benutzer);
            issueBase.setReporter(benutzer);

            issueBase.setStatus(status);
            issueBase.setType(type);
            issueBase.setPriority(priority);

            issueBase = GraphDaoFactory.getIssueBaseDAO().persist(issueBase);
            list.add(issueBase);
            System.out.println(issueBase.toString());
        }
    }

}
