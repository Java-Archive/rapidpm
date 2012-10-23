package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.neo4j.graphdb.*;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;

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

    public void connectEntitiesWithRelationTx(IssueBase start, IssueBase end, IssueRelation relation) {
        connectEntitiesWithRelationTx(start.getId(), end.getId(), relation);
    }

    public void connectEntitiesWithRelationTx(Long startId, Long endId, IssueRelation relation) {
        Transaction tx = graphDb.beginTx();
        try {
            boolean alreadyExist = false;
            Node startNode = graphDb.getNodeById(startId);
            for (Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
                if (rel.getOtherNode(startNode).equals(graphDb.getNodeById(endId)))
                    alreadyExist = true;
            }
            if (!alreadyExist) startNode.createRelationshipTo(graphDb.getNodeById(endId), relation);
            tx.success();
        } finally {
            tx.finish();
        }
    }


    public void deleteRelationOfEntitiesTx(IssueBase start, IssueBase end, IssueRelation relation) {
        deleteRelationOfEntitiesTx(start.getId(), end.getId(), relation);
    }

    public void deleteRelationOfEntitiesTx(Long startId, Long endId, IssueRelation relation) {
        Transaction tx = graphDb.beginTx();
        try {
            Node startNode = graphDb.getNodeById(startId);
            for (Relationship rel : startNode.getRelationships(relation, Direction.BOTH)) {
                if (rel.getOtherNode(startNode).equals(graphDb.getNodeById(endId)))
                    rel.delete();
            }
            tx.success();
        } finally {
            tx.finish();
        }
    }

    public void setAsChildTx(IssueBase parent, IssueBase child) {
        setAsChildTx(parent.getId(), child.getId());
    }

    public void setAsChildTx(Long parentId, Long childId) {
        Transaction tx = graphDb.beginTx();
        try {
            graphDb.getNodeById(parentId).createRelationshipTo(graphDb.getNodeById(childId),
                    GraphRelationRegistry.getSubIssueRelationshipType());
            graphDb.getNodeById(childId).getSingleRelationship(GraphRelationRegistry.getClassRootToChildRelType(),
                    Direction.INCOMING).delete();
            tx.success();
        } finally {
            tx.finish();
        }
    }
}
