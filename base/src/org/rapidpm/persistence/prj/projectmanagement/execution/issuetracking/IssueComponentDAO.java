package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.kernel.Traversal;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueComponentDAO extends GraphBaseDAO<IssueComponent> {
    private static final Logger logger = Logger.getLogger(IssueComponentDAO.class);

    public IssueComponentDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueComponent.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueComponent component) {
        return getConnectedIssuesFromProject(component, 0L);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssueComponent component, final Long projectId) {
        if (component == null)
            throw new NullPointerException("Component is null");
        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");

        if (logger.isDebugEnabled())
            logger.debug("getConnectedIssuesFromProject: " + projectId);

        final List<IssueBase> issueList = new ArrayList<>();
        final Node compNode = graphDb.getNodeById(component.getId());
        IssueBase issue = null;
        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueComponent.class);
        for (Relationship rel : compNode.getRelationships(relType, Direction.INCOMING)) {
            issue = getObjectFromNode(rel.getOtherNode(compNode), IssueBase.class);
            if (issue != null && (projectId == 0 || issue.getProjectId().equals(projectId))) {
                issueList.add(issue);
                if (logger.isDebugEnabled())
                    logger.debug("Is connected Issues: " + issue);
            } else
            if (logger.isDebugEnabled())
                logger.debug("Is not connected: " + issue);
        }
        return issueList;
    }

    public boolean delete(final IssueComponent entity, final IssueComponent assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
