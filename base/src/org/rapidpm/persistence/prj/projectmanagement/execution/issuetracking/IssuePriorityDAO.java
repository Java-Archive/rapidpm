package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.GraphBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 17.10.12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class IssuePriorityDAO extends GraphBaseDAO<IssuePriority> {

    public IssuePriorityDAO(GraphDatabaseService graphDb, DaoFactory relDaoFactory) {
        super(graphDb, IssuePriority.class, relDaoFactory);
    }
}
