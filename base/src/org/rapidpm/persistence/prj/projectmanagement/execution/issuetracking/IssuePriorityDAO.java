package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 17.10.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class IssuePriorityDAO extends GraphBaseDAO<IssuePriority> {
    private static final Logger logger = Logger.getLogger(IssuePriorityDAO.class);

    public IssuePriorityDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssuePriority.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssuePriority priority) {
        return getConnectedIssuesFromProject(priority, 0L);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssuePriority priority, final Long projectId) {
        if (priority == null)
            throw new NullPointerException("Priority is null");
        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");

        if (logger.isDebugEnabled())
            logger.debug("getConnectedIssuesFromProject: " + projectId);

        final List<IssueBase> issueList = new ArrayList<>();
        final Node prioNode = graphDb.getNodeById(priority.getId());
        IssueBase issue = null;
        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssuePriority.class);
        for (Relationship rel : prioNode.getRelationships(relType, Direction.INCOMING)) {
            issue = getObjectFromNode(rel.getOtherNode(prioNode), IssueBase.class);
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
}
