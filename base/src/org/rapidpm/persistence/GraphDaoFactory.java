package org.rapidpm.persistence;

import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatusDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTypeDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class GraphDaoFactory {
    private static final GraphDatabaseService graphDb = GraphDBFactory.getGraphDBService();

    public static IssueBaseDAO getIssueBaseDAO() {
        return new IssueBaseDAO(graphDb);
    }

    public static IssueStatusDAO getIssueStatusDAO() {
        return new IssueStatusDAO(graphDb);
    }

    public static IssuePriorityDAO getIssuePriorityDAO() {
        return new IssuePriorityDAO(graphDb);
    }

    public static IssueTypeDAO getIssueTypeDAO() {
        return new IssueTypeDAO(graphDb);
    }
}
