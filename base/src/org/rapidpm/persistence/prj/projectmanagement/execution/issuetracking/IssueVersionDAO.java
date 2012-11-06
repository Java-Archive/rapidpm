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
 * Date: 05.11.12
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
public class IssueVersionDAO extends GraphBaseDAO<IssueVersion> {
    private static final Logger logger = Logger.getLogger(IssueStatusDAO.class);

    public IssueVersionDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueVersion.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueVersion status) {
        return getConnectedIssuesFromProject(status, 0L);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssueVersion status, final Long projectId) {
        if (status == null)
            throw new NullPointerException("Status is null");
        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");

        if (logger.isDebugEnabled())
            logger.debug("getConnectedIssuesFromProject: " + projectId);

        final List<IssueBase> issueList = new ArrayList<>();
        final Node statusNode = graphDb.getNodeById(status.getId());
        IssueBase issue = null;
        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueVersion.class);
        for (Relationship rel : statusNode.getRelationships(relType, Direction.INCOMING)) {
            issue = getObjectFromNode(rel.getOtherNode(statusNode), IssueBase.class);
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

    public boolean delete(final IssueVersion entity, final IssueVersion assignTo){
        return super.deleteAttribute(entity, assignTo);
    }
}
