package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.GraphBaseDAO;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.GraphRelationFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

public class IssueBaseDAO extends GraphBaseDAO<IssueBase> {
    private static final Logger logger = Logger.getLogger(IssueBaseDAO.class);

    public IssueBaseDAO(final GraphDatabaseService graphDb, final DaoFactory relDaoFactory) {
        super(graphDb, IssueBase.class, relDaoFactory);
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
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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
        Long startId = start.getId();
        if (startId == null)
            throw new IllegalArgumentException("Id of first issue cant be null. Persist first.");

        if (end == null)
            throw new NullPointerException("Second issue is null.");
        Long endId = end.getId();
        if (endId == null)
            throw new IllegalArgumentException("Id of second issue cant be null. Persist first.");

        if (start.equals(end))
            throw new IllegalArgumentException("Start and end issue are the same. Can't create relation to itself.");

        if (relation == null)
            throw new NullPointerException("Relation is null.");
        Long relationId = relation.getId();
        if (relationId == null)
            throw new IllegalArgumentException("Id of relation cant be null. Persist first.");


        if(logger.isDebugEnabled())
            logger.debug("connectEntitiesWithRelation");

        boolean alreadyExist = false;
        final Node startNode = graphDb.getNodeById(startId);
        final Node endNode = graphDb.getNodeById(endId);
        final Node relationNode = graphDb.getNodeById(relationId);
        for (final Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
            if (rel.getOtherNode(startNode).equals(endNode))
                alreadyExist = true;
        }
        if (!alreadyExist) {
            startNode.createRelationshipTo(endNode, relation);
            final RelationshipType relType = GraphRelationFactory.getRelationshipTypeForClass(IssueRelation.class);

            boolean relExist = false;
            for (Relationship rel : startNode.getRelationships(relType, Direction.OUTGOING)) {
                if (rel.getEndNode().equals(relationNode)) {
                    relExist = true;
                    break;
                }
            }

            if (!relExist) {
                startNode.createRelationshipTo(relationNode, relType);
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Relation to RelationNode already exists. continue...");
            }
        } else {
            logger.warn("Relation aleady exists: start: " + start + ", end: " + end + ", relation: " + relation);
        }
    }


    public List<IssueBase> getConnectedIssuesWithRelation(final IssueBase issue, final IssueRelation relation, final Direction direction) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of Issue cant be null. Persist first.");

        if (relation == null)
            throw new NullPointerException("Relation is null.");
        Long relationId = relation.getId();
        if (relationId == null)
            throw new IllegalArgumentException("Id of relation cant be null. Persist first.");

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


    public boolean deleteRelationOfEntitiesTx(final IssueBase start, final IssueBase end, final IssueRelation relation,
                                              final Direction direction) {
        boolean success = false;
        final Transaction tx = graphDb.beginTx();
        try {
            if(logger.isDebugEnabled())
                logger.debug("deleteRelationOfEntitiesTx");
            deleteRelationOfEntities(start, end, relation, direction);
            tx.success();
            success = true;
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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

    public void deleteRelationOfEntities(final IssueBase start, final IssueBase end, final IssueRelation relation,
                                         final Direction direction) {
        if (start == null)
            throw new NullPointerException("First issue is null.");
        Long startId = start.getId();
        if (startId == null)
            throw new IllegalArgumentException("Id of first issue cant be null. Persist first.");

        if (end == null)
            throw new NullPointerException("Second issue is null.");
        Long endId = end.getId();
        if (endId == null)
            throw new IllegalArgumentException("Id of second issue cant be null. Persist first.");

        if (start.equals(end))
            throw new IllegalArgumentException("Start and end issue are the same. Can't create relation to itself.");

        if (relation == null)
            throw new NullPointerException("Relation is null.");
        Long relationId = relation.getId();
        if (relationId == null)
            throw new IllegalArgumentException("Id of relation cant be null. Persist first.");

        if (direction == null)
            throw new NullPointerException("Direction is null.");


        if (logger.isDebugEnabled())
            logger.debug("deleteRelationOfEntities");

        final Node startNode = graphDb.getNodeById(start.getId());
        final Node endNode = graphDb.getNodeById(end.getId());
        for (final Relationship rel : startNode.getRelationships(relation, direction)) {
            if (rel.getOtherNode(startNode).equals(endNode)) {
                if (logger.isDebugEnabled())
                    logger.debug("delete relation between" + start + ", " + end);

                final Node referenceNode = rel.getStartNode();
                rel.delete();

                if (!referenceNode.hasRelationship(relation, Direction.OUTGOING)) {

                    final Node relationNode = graphDb.getNodeById(relation.getId());
                    final RelationshipType relType;
                    relType = GraphRelationFactory.getRelationshipTypeForClass(IssueRelation.class);

                    for (Relationship relToRelNode : referenceNode.getRelationships(relType, Direction.OUTGOING)) {
                        if (relToRelNode.getEndNode().equals(relationNode)) {
                            relToRelNode.delete();
                            if (logger.isDebugEnabled())
                                logger.debug("No more relations of type: " + relation + " present. Delete relation to " +
                                        "RelationNode");
                            break;
                        }
                    }

                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Issue has more relations of type: " + relation);
                }
            }
            else
                if (logger.isDebugEnabled())
                    logger.debug("Issue " + start + "has no relation to: " + end);
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
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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
        Long parentId = parent.getId();
        if (parentId == null)
            throw new IllegalArgumentException("Id of parent issue cant be null. Persist first.");

        if (child == null)
            throw new NullPointerException("Childissue is null.");
        Long childId = child.getId();
        if (childId == null)
            throw new IllegalArgumentException("Id of child issue cant be null. Persist first.");

        if (parent.equals(child))
            throw new IllegalArgumentException("Parent and Child issue are the same. Can't create relation to itself" +
                    ".");


        if (logger.isDebugEnabled())
            logger.debug("addSubIssue");

        Node childNode = graphDb.getNodeById(childId);
        final RelationshipType relType = GraphRelationFactory.getSubIssueRelationshipType();
        setAsRootIssue(child);

        graphDb.getNodeById(parentId).createRelationshipTo(childNode, relType);
        if (childNode.hasRelationship(GraphRelationFactory.getClassRootToChildRelType(), Direction.INCOMING)) {
            childNode.getSingleRelationship(GraphRelationFactory.getClassRootToChildRelType(), Direction.INCOMING)
                    .delete();
        }
    }

    public List<IssueBase> getSubIssuesOf(IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of issue cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("getSubIssuesOf");

        Node startNode = graphDb.getNodeById(issueId);
        List<IssueBase> issueList = new ArrayList<>();

        final RelationshipType relType = GraphRelationFactory.getSubIssueRelationshipType();
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
            boolean ret = deleteSubIssueRelation(parent, child);
            tx.success();
            if (ret) {
                success = true;
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Method 'deleteSubIssueRelation' unsuccessfull");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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


    public boolean deleteSubIssueRelation(final IssueBase parent, final IssueBase child) {
        boolean success = false;
        if (parent == null)
            throw new NullPointerException("Parentissue is null.");
        Long parentId = parent.getId();
        if (parentId == null)
            throw new IllegalArgumentException("Id of parent issue cant be null. Persist first.");

        if (child == null)
            throw new NullPointerException("Childissue is null.");
        Long childId = child.getId();
        if (childId == null)
            throw new IllegalArgumentException("Id of child issue cant be null. Persist first.");

        if (parent.equals(child))
            throw new IllegalArgumentException("Parent and Child issue are the same.");

        if (logger.isDebugEnabled())
            logger.debug("deleteSubIssueRelation");

        Node childNode = graphDb.getNodeById(childId);
        Node parentNode = graphDb.getNodeById(parentId);

        final RelationshipType relType = GraphRelationFactory.getSubIssueRelationshipType();
        if (childNode.hasRelationship(relType, Direction.INCOMING)) {
            for (Relationship rel : childNode.getRelationships(relType, Direction.INCOMING)) {
                if (rel.getOtherNode(childNode).equals(parentNode)) {
                    setAsRootIssue(child);
                    success = true;
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Parent Issue doesn't match to child. continue...");
                }
            }
        } else {
            logger.error("Childissue has no parent!");
        }
        return success;
    }


    public boolean setAsRootIssueTx(final IssueBase issue) {
        boolean success = false;
        Transaction tx = graphDb.beginTx();
        try {
            if (logger.isDebugEnabled())
                logger.debug("setAsRootIssueTx");
            setAsRootIssue(issue);
            tx.success();
            success = true;
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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

    public void setAsRootIssue(final IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of issue cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("setAsRootIssue");

        Node node = graphDb.getNodeById(issueId);

        final RelationshipType relType = GraphRelationFactory.getSubIssueRelationshipType();
        if (node.hasRelationship(relType, Direction.INCOMING)) {
            if (logger.isDebugEnabled())
                logger.debug("Delete all subissue relations. Set as rootissue");
            for (Relationship rel : node.getRelationships(relType, Direction.INCOMING)) {
                rel.delete();
            }

            getProjectRootNode(issue.getProjectId()).createRelationshipTo(node,
                    GraphRelationFactory.getClassRootToChildRelType());
        } else {
            if (logger.isDebugEnabled())
                logger.debug("Is already a rootissue");
        }
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
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of Issue cant be null. Persist first.");

        if (component == null)
            throw new NullPointerException("Component is null.");
        Long componentId = component.getId();
        if (componentId == null)
            throw new IllegalArgumentException("Id of Component cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("addComponentTo");

        boolean exists = false;
        final Node issueNode = graphDb.getNodeById(issueId);
        final Node componentNode = graphDb.getNodeById(componentId);
        final RelationshipType relShipType = GraphRelationFactory.getRelationshipTypeForClass(IssueComponent.class);

        for (final Relationship rel : issueNode.getRelationships())
            if (rel.getOtherNode(issueNode).equals(componentNode)) {
                exists = true;
                break;
            }

        if(!exists)
            issueNode.createRelationshipTo(componentNode, relShipType);
        else
            logger.warn("Relation to Component already exists: " + issue + ", " + component);
    }


    public List<IssueComponent> getComponentsOf(final IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of Issue cant be null. Persist first.");

        if (logger.isDebugEnabled())
            logger.debug("getComponentsOf");

        final Node startNode = graphDb.getNodeById(issueId);
        final List<IssueComponent> componentList = new ArrayList<>();

        final RelationshipType relType = GraphRelationFactory.getRelationshipTypeForClass(IssueComponent.class);
        for (final Relationship rel : startNode.getRelationships(relType, Direction.OUTGOING)) {
            componentList.add(DaoFactorySingleton.getInstance().getIssueComponentDAO().findByID(rel.getOtherNode(startNode).getId()));
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
            boolean ret = deleteComponentRelation(issue, component);
            tx.success();
            if (ret) {
                success = true;
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Method 'deleteSubIssueRelation' unsuccessfull");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
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

    public boolean deleteComponentRelation(final IssueBase issue, final IssueComponent component) {
        if (issue == null)
            throw new NullPointerException("Issue is null.");
        Long issueId = issue.getId();
        if (issueId == null)
            throw new IllegalArgumentException("Id of Issue cant be null. Persist first.");

        if (component == null)
            throw new NullPointerException("Component is null.");
        Long componentId = component.getId();
        if (componentId == null)
            throw new IllegalArgumentException("Id of Component cant be null. Persist first.");

        boolean success = false;
        if (logger.isDebugEnabled())
            logger.debug("deleteComponentRelation");

        final Node issueNode = graphDb.getNodeById(issueId);
        RelationshipType relType = GraphRelationFactory.getRelationshipTypeForClass(IssueComponent.class);
        for (final Relationship rel : issueNode.getRelationships(relType, Direction.OUTGOING))
            if (rel.getOtherNode(issueNode).equals(graphDb.getNodeById(componentId))) {
                if (logger.isDebugEnabled())
                    logger.debug("Delete Relation to Component: " + issue + ", " + component);
                rel.delete();
                success = true;
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Has no relation to Component: " + issue + ", " + component);
            }

        return success;
    }

    public boolean delete(final IssueBase issue) {
        return super.deleteIssue(issue);
    }
}
