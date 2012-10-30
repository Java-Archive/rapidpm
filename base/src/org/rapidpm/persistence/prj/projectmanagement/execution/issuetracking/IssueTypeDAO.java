package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

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
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public class IssueTypeDAO extends GraphBaseDAO<IssueType> {

    public IssueTypeDAO(final GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueType.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueType type) {
        return getConnectedIssuesFromProject(type, new Long(0));
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssueType type, final Long projectId) {
        if (type == null)
            throw new NullPointerException("Type is null");
        if (projectId < 0)
            throw new IllegalArgumentException("ProjectId must be positiv");

        final List<IssueBase> issueList = new ArrayList<>();
        final Node typeNode = graphDb.getNodeById(type.getId());
        IssueBase issue = null;
        final RelationshipType relType = GraphRelationRegistry.getRelationshipTypeForClass(IssueType.class);
        for (Relationship rel : typeNode.getRelationships(relType, Direction.INCOMING)) {
            issue = getObjectFromNode(rel.getOtherNode(typeNode), IssueBase.class);
            if (issue != null && (projectId == 0 || issue.getProjectId().equals(projectId)))
                issueList.add(issue);
        }
        return issueList;
    }
}
