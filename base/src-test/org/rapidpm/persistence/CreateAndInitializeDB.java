package org.rapidpm.persistence;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

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
        System.out.println("Start creating");
        setUp.createDB();
        System.out.println("Finished creating");
        System.out.println("Start initializing");
        setUp.initializeDB();
        System.out.println("Finished initializing");
    }


    public void createDB() {

        graphDb = GraphDBFactory.getInstance().getGraphDBService();
        root = graphDb.getNodeById(0);

        Transaction tx = graphDb.beginTx();
        try{
            RelationshipType rel = GraphRelationRegistry.getRootRelationshipTypeToClass(PlannedProject.class);
            Node project_root = graphDb.createNode();
            project_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(project_root, rel);

            Node project1 = graphDb.createNode();
            project1.setProperty(rel.name(), "Project 1");
            project_root.createRelationshipTo(project1, rel);

            Node project2 = graphDb.createNode();
            project2.setProperty(rel.name(), "Project 2");
            project_root.createRelationshipTo(project2, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssueStatus.class);
            Node status_root = graphDb.createNode();
            status_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(status_root, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssuePriority.class);
            Node priority_root = graphDb.createNode();
            priority_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(priority_root, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssueType.class);
            Node type_root = graphDb.createNode();
            type_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(type_root, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssueComponent.class);
            Node component_root = graphDb.createNode();
            component_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(component_root, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssueBase.class);
            Node issueBase_root = graphDb.createNode();
            issueBase_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
            root.createRelationshipTo(issueBase_root, rel);

            rel = GraphRelationRegistry.getRootRelationshipTypeToClass(IssueRelation.class);
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
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("closed");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("resolved");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("onhold");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);

        status = new IssueStatus();
        status.setStatusName("inprogress");
        GraphDaoFactory.getInstance().getIssueStatusDAO().persist(status);


        IssuePriority priority = new IssuePriority();
        priority.setPriorityName("trivial");
        priority.setPrio(0);
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("minor");
        priority.setPrio(1);
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("major");
        priority.setPrio(2);
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("critical");
        priority.setPrio(3);
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);

        priority = new IssuePriority();
        priority.setPriorityName("blocker");
        priority.setPrio(4);
        GraphDaoFactory.getInstance().getIssuePriorityDAO().persist(priority);


        IssueType type = new IssueType();
        type.setTypeName("bug");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("task");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("improvement");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);

        type = new IssueType();
        type.setTypeName("newfunction");
        GraphDaoFactory.getInstance().getIssueTypeDAO().persist(type);



        IssueRelation relation = new IssueRelation();
        relation.setRelationName("Duplicate");
        relation.setOutgoingName("duplicates");
        relation.setIncomingName("is duplicated by");
        GraphDaoFactory.getInstance().getIssueRelationDAO().persist(relation);

        relation = new IssueRelation();
        relation.setRelationName("Block");
        relation.setOutgoingName("blocks");
        relation.setIncomingName("is blocked by");
        GraphDaoFactory.getInstance().getIssueRelationDAO().persist(relation);


        IssueComponent component = new IssueComponent("Oberfläche");
        GraphDaoFactory.getInstance().getIssueComponentDAO().persist(component);

        component = new IssueComponent("IssueTracking");
        GraphDaoFactory.getInstance().getIssueComponentDAO().persist(component);

        component = new IssueComponent("Dokumentation");
        GraphDaoFactory.getInstance().getIssueComponentDAO().persist(component);
    }

}
