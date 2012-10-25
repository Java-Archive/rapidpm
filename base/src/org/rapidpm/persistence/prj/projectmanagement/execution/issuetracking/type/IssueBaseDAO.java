package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

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

    public IssueBaseDAO(final GraphDatabaseService graphDb, final DaoFactory relDaoFactory,
                        final Long projectId) {
        super(graphDb, IssueBase.class, relDaoFactory, projectId);
    }


    public boolean connectEntitiesWithRelationTx(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            connectEntitiesWithRelation(start, end, relation);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void connectEntitiesWithRelation(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        if (start == null)
            throw new NullPointerException("First issue is null.");
        if (end == null)
            throw new NullPointerException("Second issue is null.");
        if (relation == null)
            throw new NullPointerException("Relation is null.");

        boolean alreadyExist = false;
        final Node startNode = graphDb.getNodeById(start.getId());
        final Node endNode = graphDb.getNodeById(end.getId());
        for (final Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(endNode))
                alreadyExist = true;
        }
        if (!alreadyExist) startNode.createRelationshipTo(endNode, relation);
    }


    public List<IssueBase> getConnectedIssuesWithRelation(final IssueBase issue, final IssueRelation relation, final Direction direction) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (relation == null)
            throw new NullPointerException("Relation issue is null.");
        if (direction == null)
            throw new NullPointerException("Direction is null.");

        final Node startNode = graphDb.getNodeById(issue.getId());
        final List<IssueBase> issueList = new ArrayList<>();

        for (final Relationship rel : startNode.getRelationships(relation, direction)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode), IssueBase.class));
        }
        return issueList;
    }


    public boolean deleteRelationOfEntitiesTx(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            deleteRelationOfEntities(start, end, relation);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void deleteRelationOfEntities(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        if (start == null)
            throw new NullPointerException("First issue is null.");
        if (end == null)
            throw new NullPointerException("Second issue is null.");
        if (relation == null)
            throw new NullPointerException("Relation is null.");

        final Node startNode = graphDb.getNodeById(start.getId());
        for (final Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
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
        if (parent == null)
            throw new NullPointerException("Parentissue is null.");
        if (child == null)
            throw new NullPointerException("Childissue is null.");


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
        if (issue == null)
            throw new NullPointerException("Issue is null.");


        Node startNode = graphDb.getNodeById(issue.getId());
        List<IssueBase> issueList = new ArrayList<>();

        for (Relationship rel : startNode.getRelationships(GraphRelationRegistry.getSubIssueRelationshipType(),
                Direction.OUTGOING)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode), IssueBase.class));
        }
        return issueList;
    }


    public boolean deleteSubIssueRelationTx(final IssueBase parent, final IssueBase child) {
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

    public void deleteSubIssueRelation(final IssueBase parent, final IssueBase child) {
        if (parent == null)
            throw new NullPointerException("Parentissue is null.");
        if (child == null)
            throw new NullPointerException("Childissue is null.");

        final Node parentNode = graphDb.getNodeById(parent.getId());
        final Node childNode = graphDb.getNodeById(child.getId());
        for (final Relationship rel : parentNode.getRelationships(GraphRelationRegistry.getSubIssueRelationshipType(),
                Direction.OUTGOING))
            if (rel.getOtherNode(parentNode).equals(childNode))
                rel.delete();

        class_root_node.createRelationshipTo(childNode, GraphRelationRegistry.getClassRootToChildRelType());
    }



    public boolean addComponentToTx(final IssueBase issue, final IssueComponent component) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            addComponentTo(issue, component);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void addComponentTo(final IssueBase issue, final IssueComponent component) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (component == null)
            throw new NullPointerException("Component is null.");

        final Node issueNode = graphDb.getNodeById(issue.getId());
        final Node componentNode = graphDb.getNodeById(component.getId());
        final RelationshipType relShipType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);

        for (final Relationship rel : issueNode.getRelationships())
            if (rel.getOtherNode(issueNode).equals(componentNode))
                throw new IllegalStateException("Relation to component aleady exists");

        issueNode.createRelationshipTo(componentNode, relShipType);
    }


    public List<IssueComponent> getComponentsOf(final IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");

        final Node startNode = graphDb.getNodeById(issue.getId());
        final List<IssueComponent> issueList = new ArrayList<>();

        for (final Relationship rel : startNode.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass
                (IssueComponent.class), Direction.OUTGOING)) {
            issueList.add(GraphDaoFactory.getIssueComponentDAO().findById(rel.getOtherNode(startNode).getId()));
        }
        return issueList;
    }


    public boolean deleteComponentRelationTx(final IssueBase issue, final IssueComponent component) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            deleteComponentRelation(issue, component);
            tx.success();
            success = true;
        } finally {
            tx.finish();
            return success;
        }
    }

    public void deleteComponentRelation(final IssueBase issue, final IssueComponent component) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (component == null)
            throw new NullPointerException("Component is null.");

        final Node issueNode = graphDb.getNodeById(issue.getId());
        for (final Relationship rel : issueNode.getRelationships(GraphRelationRegistry.getRelationshipTypeForClass
                (IssueComponent.class), Direction.OUTGOING))
            if (rel.getOtherNode(issueNode).equals(graphDb.getNodeById(component.getId())))
                rel.delete();
    }


    public boolean deleteAllComponentRelationsTx(final IssueBase issue, final List<IssueComponent> componentList) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            for (final IssueComponent component : componentList) {
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
