package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;
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

    public IssueRelationDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueRelation.class, relDaoFactory);
    }

    public List<IssueBase> getConnectedIssues(final IssueRelation relation) {
        return super.getConnectedIssues(relation);
    }

    public boolean delete(final IssueRelation entity) {
        return super.deleteRelation(entity);
    }
}
