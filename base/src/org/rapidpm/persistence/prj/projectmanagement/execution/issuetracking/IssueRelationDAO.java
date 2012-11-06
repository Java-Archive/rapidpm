package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelationDAO extends GraphBaseDAO<IssueRelation> {
    private static final Logger logger = Logger.getLogger(IssueRelationDAO.class);

    public IssueRelationDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssueRelation.class, relDaoFactory);
    }

    public boolean delete(final IssueRelation entity) {
        return super.deleteRelations(entity);
    }
}
