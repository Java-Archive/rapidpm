package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;

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
}
