package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(IssueBaseDAO.class);

    public IssueBaseDAO(final GraphDatabaseService graphDb, final DaoFactory relDaoFactory,
                        final Long projectId) {
        super(graphDb, IssueBase.class, relDaoFactory, projectId);
    }


    public boolean connectEntitiesWithRelationTx(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if(logger.isDebugEnabled())
                logger.debug("connectEntitiesWithRelationTx");
            connectEntitiesWithRelation(start, end, relation);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
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

        if(logger.isDebugEnabled())
            logger.debug("connectEntitiesWithRelation");

        boolean alreadyExist = false;
        final Node startNode = graphDb.getNodeById(start.getId());
        final Node endNode = graphDb.getNodeById(end.getId());
        for (final Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(endNode))
                alreadyExist = true;
        }
        if (!alreadyExist)
            startNode.createRelationshipTo(endNode, relation);
        else
            logger.warn("Relation aleady exists: start: " + start + ", end: " + end + ", relation: " + relation);
    }


    public List<IssueBase> getConnectedIssuesWithRelation(final IssueBase issue, final IssueRelation relation, final Direction direction) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (relation == null)
            throw new NullPointerException("Relation issue is null.");
        if (direction == null)
            throw new NullPointerException("Direction is null.");

        if(logger.isDebugEnabled())
            logger.debug("getConnectedIssuesWithRelation");

        final Node startNode = graphDb.getNodeById(issue.getId());
        final List<IssueBase> issueList = new ArrayList<>();

        for (final Relationship rel : startNode.getRelationships(relation, direction)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode)));
        }
        return issueList;
    }


    public boolean deleteRelationOfEntitiesTx(final IssueBase start, final IssueBase end, final IssueRelation relation) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if(logger.isDebugEnabled())
                logger.debug("deleteRelationOfEntitiesTx");
            deleteRelationOfEntities(start, end, relation);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
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

        if (logger.isDebugEnabled())
            logger.debug("deleteRelationOfEntities");

        final Node startNode = graphDb.getNodeById(start.getId());
        for (final Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(graphDb.getNodeById(end.getId()))) {
                if (logger.isDebugEnabled())
                    logger.debug("delete relation between" + start + ", " + end);
                rel.delete();
            }
            else
                if (logger.isDebugEnabled())
                    logger.debug("has no relation to: " + start + ", " + end);
        }
    }


    public boolean addSubIssueTx(IssueBase parent, IssueBase child) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            if(logger.isDebugEnabled())
                logger.debug("addSubIssueTx");
            addSubIssue(parent, child);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
            return success;
        }
    }



    public void addSubIssue(IssueBase parent, IssueBase child) {
        if (parent == null)
            throw new NullPointerException("Parentissue is null.");
        if (child == null)
            throw new NullPointerException("Childissue is null.");

        if (logger.isDebugEnabled())
            logger.debug("addSubIssue");

        Node childNode = graphDb.getNodeById(child.getId());
        final RelationshipType relType = GraphRelationRegistry.getSubIssueRelationshipType();
        if (childNode.hasRelationship(relType, Direction.INCOMING)) {
            if (logger.isDebugEnabled())
                logger.debug("Childissue gets a new parent: " + child);
            Relationship rel = childNode.getSingleRelationship(relType, Direction.INCOMING);
            deleteSubIssueRelation(rel.getStartNode(), childNode);
        } else
            if (logger.isDebugEnabled())
                logger.debug("Childissue has no Parent: " + child);


        graphDb.getNodeById(parent.getId()).createRelationshipTo(childNode,relType );
        childNode.getSingleRelationship(GraphRelationRegistry.getClassRootToChildRelType(),
            Direction.INCOMING).delete();
    }

    public List<IssueBase> getSubIssuesOf(IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");

        if (logger.isDebugEnabled())
            logger.debug("getSubIssuesOf");

        Node startNode = graphDb.getNodeById(issue.getId());
        List<IssueBase> issueList = new ArrayList<>();

        final RelationshipType relType = GraphRelationRegistry.getSubIssueRelationshipType();
        for (Relationship rel : startNode.getRelationships(relType, Direction.OUTGOING)) {
            issueList.add(getObjectFromNode(rel.getOtherNode(startNode)));
        }
        if (logger.isDebugEnabled())
            logger.debug("Subissue: " + issueList);
        return issueList;
    }


    public boolean deleteSubIssueRelationTx(final IssueBase parent, final IssueBase child) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            if (logger.isDebugEnabled())
                logger.debug("deleteSubIssueRelationTx");
            deleteSubIssueRelation(parent, child);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
            return success;
        }
    }


    public void deleteSubIssueRelation(final IssueBase parent, final IssueBase child) {
        deleteSubIssueRelation(graphDb.getNodeById(parent.getId()), graphDb.getNodeById(child.getId()));
    }


    private void deleteSubIssueRelation(final Node parentNode, final Node childNode) {
        if (parentNode == null)
            throw new NullPointerException("Parentissue is null.");
        if (childNode == null)
            throw new NullPointerException("Childissue is null.");

        if (logger.isDebugEnabled())
            logger.debug("deleteSubIssueRelation");

        final RelationshipType relType = GraphRelationRegistry.getSubIssueRelationshipType();
        for (final Relationship rel : parentNode.getRelationships(relType, Direction.OUTGOING)) {
            if (rel.getOtherNode(parentNode).equals(childNode)) {
                if (logger.isDebugEnabled())
                    logger.debug("Delete Subissue");
                rel.delete();
            } else
                if (logger.isDebugEnabled())
                    logger.debug("Is no Subissue");
        }

        class_root_node.createRelationshipTo(childNode, GraphRelationRegistry.getClassRootToChildRelType());
    }



    public boolean addComponentToTx(final IssueBase issue, final IssueComponent component) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if (logger.isDebugEnabled())
                logger.debug("addComponentToTx");
            addComponentTo(issue, component);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
            return success;
        }
    }

    public void addComponentTo(final IssueBase issue, final IssueComponent component) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (component == null)
            throw new NullPointerException("Component is null.");

        if (logger.isDebugEnabled())
            logger.debug("addComponentTo");

        boolean exists = false;
        final Node issueNode = graphDb.getNodeById(issue.getId());
        final Node componentNode = graphDb.getNodeById(component.getId());
        final RelationshipType relShipType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);

        for (final Relationship rel : issueNode.getRelationships())
            if (rel.getOtherNode(issueNode).equals(componentNode))
                exists = true;

        if(!exists)
            issueNode.createRelationshipTo(componentNode, relShipType);
        else
            logger.warn("Relation to Component already exists: " + issue + ", " + component);
    }


    public List<IssueComponent> getComponentsOf(final IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");

        if (logger.isDebugEnabled())
            logger.debug("getComponentsOf");

        final Node startNode = graphDb.getNodeById(issue.getId());
        final List<IssueComponent> componentList = new ArrayList<>();

        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);
        for (final Relationship rel : startNode.getRelationships(relType, Direction.OUTGOING)) {
            componentList.add(GraphDaoFactory.getIssueComponentDAO().findById(rel.getOtherNode(startNode).getId()));
        }

        if (logger.isDebugEnabled())
            logger.debug("Components: " + componentList);
        return componentList;
    }


    public boolean deleteComponentRelationTx(final IssueBase issue, final IssueComponent component) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if (logger.isDebugEnabled())
                logger.debug("deleteComponentRelationTx");
            deleteComponentRelation(issue, component);
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
            return success;
        }
    }

    public void deleteComponentRelation(final IssueBase issue, final IssueComponent component) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        if (component == null)
            throw new NullPointerException("Component is null.");

        if (logger.isDebugEnabled())
            logger.debug("deleteComponentRelation");

        final Node issueNode = graphDb.getNodeById(issue.getId());
        RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);
        for (final Relationship rel : issueNode.getRelationships(relType, Direction.OUTGOING))
            if (rel.getOtherNode(issueNode).equals(graphDb.getNodeById(component.getId()))) {
                if (logger.isDebugEnabled())
                    logger.debug("Delete Relation to Component: " + issue + ", " + component);
                rel.delete();
            } else
                if (logger.isDebugEnabled())
                    logger.debug("Has no relation to Component: " + issue + ", " + component);

    }


    public boolean deleteAllComponentRelationsTx(final IssueBase issue, final List<IssueComponent> componentList) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if (logger.isDebugEnabled())
                logger.debug("deleteAllComponentRelationsTx");
            for (final IssueComponent component : componentList) {
                deleteComponentRelation(issue, component);
            }
            tx.success();
            success = true;
        } catch (NullPointerException e) {
            logger.error("NullpointerException: " + e.getMessage());
        } finally {
            tx.finish();
            if (logger.isDebugEnabled()) {
                if (success)
                    logger.debug("Successfull");
                else
                    logger.debug("Unsuccessfull");
            }
            return success;
        }
    }

    public boolean deleteIssue(final IssueBase entity) {
        return super.deleteIssue(entity);
    }
}
