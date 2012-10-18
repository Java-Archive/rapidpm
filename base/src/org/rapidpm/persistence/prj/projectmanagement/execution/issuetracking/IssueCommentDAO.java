package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;


import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.GraphBaseDAO;

import javax.persistence.EntityManager;
/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 18.10.12
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */
public class IssueCommentDAO extends GraphBaseDAO<IssueComment> {

    public IssueCommentDAO(GraphDatabaseService graphDb) {
        super(graphDb, IssueComment.class);
    }
}
