package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
import org.rapidpm.persistence.GraphRelationRegistry;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelationDAO extends GraphBaseDAO<IssueRelation> {
    private static final Logger logger = Logger.getLogger(IssueRelationDAO.class);

    public IssueRelationDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueRelation.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssuesFromProject(final IssueRelation relation, final Long projectId) {
        return super.getConnectedIssuesFromProject(relation, projectId);
    }

    public boolean delete(final IssueRelation entity) {
        return super.deleteRelation(entity);
    }
}
