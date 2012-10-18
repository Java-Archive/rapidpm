package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.GraphBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */
public class IssueRelationDAO extends GraphBaseDAO<IssueRelation> {

    public IssueRelationDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueRelation.class);
    }
}
