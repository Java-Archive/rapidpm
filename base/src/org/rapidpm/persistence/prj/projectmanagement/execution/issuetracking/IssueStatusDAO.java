package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.GraphBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class IssueStatusDAO extends GraphBaseDAO<IssueStatus> {

    public IssueStatusDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueStatus.class);
    }
}
