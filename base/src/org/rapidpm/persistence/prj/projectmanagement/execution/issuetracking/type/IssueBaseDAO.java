package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;


import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.GraphBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */

public class IssueBaseDAO extends GraphBaseDAO {

    public IssueBaseDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueBase.class);
    }


}
