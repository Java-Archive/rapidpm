package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.neo4j.graphdb.*;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

public class IssueBaseDAO extends GraphBaseDAO<IssueBase> {

    public IssueBaseDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueBase.class);
    }


    public boolean connectEntitiesWithRelationTx(IssueBase start, IssueBase end, IssueRelation relation) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            connectEntitiesWithRelation(start, end, relation);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void connectEntitiesWithRelation(IssueBase start, IssueBase end, IssueRelation relation) {

        boolean alreadyExist = false;
        Node startNode = graphDb.getNodeById(start.getId());
        Node endNode = graphDb.getNodeById(end.getId());
        for (Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(endNode))
                alreadyExist = true;
        }
        if (!alreadyExist) startNode.createRelationshipTo(endNode, relation);
    }


    public List<IssueBase> getConnectedIssuesWithRelation(IssueBase issue, IssueRelation relation,  Direction direction) {
        Node startNode = graphDb.getNodeById(issue.getId());
        List<IssueBase> issueList = new ArrayList<>();

        for (Relationship rel : startNode.getRelationships(relation, direction)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode), IssueBase.class));
        }
        return issueList;
    }


    public boolean deleteRelationOfEntitiesTx(IssueBase start, IssueBase end, IssueRelation relation) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            deleteRelationOfEntities(start, end, relation);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void deleteRelationOfEntities(IssueBase start, IssueBase end, IssueRelation relation) {
        Node startNode = graphDb.getNodeById(start.getId());
        for (Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(graphDb.getNodeById(end.getId())))
                rel.delete();
        }
    }


    public boolean addSubIssueTx(IssueBase parent, IssueBase child) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            addSubIssue(parent, child);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void addSubIssue(IssueBase parent, IssueBase child) {
        Node childNode = graphDb.getNodeById(child.getId());
        if (childNode.hasRelationship(GraphRelationRegistry.getSubIssueRelationshipType(), Direction.INCOMING)) {
            throw new IllegalStateException("ChildIssue already is a SubIssue.");
        }

        graphDb.getNodeById(parent.getId()).createRelationshipTo(childNode,
                GraphRelationRegistry.getSubIssueRelationshipType());
        childNode.getSingleRelationship(GraphRelationRegistry.getClassRootToChildRelType(),
            Direction.INCOMING).delete();
    }

    public List<IssueBase> getSubIssuesOf(IssueBase issue) {
        Node startNode = graphDb.getNodeById(issue.getId());
        List<IssueBase> issueList = new ArrayList<>();

        for (Relationship rel : startNode.getRelationships(GraphRelationRegistry.getSubIssueRelationshipType(),
                Direction.OUTGOING)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode), IssueBase.class));
        }
        return issueList;
    }


    public boolean deleteSubIssueRelationTx(IssueBase parent, IssueBase child) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            deleteSubIssueRelation(parent, child);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void deleteSubIssueRelation(IssueBase parent, IssueBase child) {
        Node parentNode = graphDb.getNodeById(parent.getId());
        Node childNode = graphDb.getNodeById(child.getId());
        for (Relationship rel : parentNode.getRelationships(GraphRelationRegistry.getSubIssueRelationshipType(),
                Direction.OUTGOING))
            if (rel.getOtherNode(parentNode).equals(childNode))
                rel.delete();

        class_root_node.createRelationshipTo(childNode, GraphRelationRegistry.getClassRootToChildRelType());
    }



    public boolean addComponentToTx(IssueBase issue, IssueComponent component) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            addComponentTo(issue, component);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void addComponentTo(IssueBase issue, IssueComponent component) {
        Node issueNode = graphDb.getNodeById(issue.getId());
        Node componentNode = graphDb.getNodeById(component.getId());
        RelationshipType relShipType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);

        for (Relationship rel : issueNode.getRelationships())
            if (rel.getOtherNode(issueNode).equals(componentNode))
                throw new IllegalStateException("Relation to component aleady exists");
        issueNode.createRelationshipTo(componentNode, relShipType);
    }


    public List<IssueComponent> getComponentsOf(IssueBase issue) {
        Node startNode = graphDb.getNodeById(issue.getId());
        List<IssueComponent> issueList = new ArrayList<>();

        for (Relationship rel : startNode.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass
                (IssueComponent.class),
                Direction.OUTGOING)) {
            issueList.add(GraphDaoFactory.getIssueComponentDAO().getById(rel.getOtherNode(startNode).getId()));
        }
        return issueList;
    }


    public boolean deleteComponentRelationTx(IssueBase issue, IssueComponent component) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            deleteComponentRelation(issue, component);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void deleteComponentRelation(IssueBase issue, IssueComponent component) {
        Node issueNode = graphDb.getNodeById(issue.getId());
        for (Relationship rel : issueNode.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass
                (IssueComponent.class), Direction.OUTGOING))
            if (rel.getOtherNode(issueNode).equals(graphDb.getNodeById(component.getId())))
                rel.delete();
    }


    public boolean deleteAllComponentRelationsTx(IssueBase issue, List<IssueComponent> componentList) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            for (IssueComponent component : componentList) {
                deleteComponentRelation(issue, component);
            }
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

}
