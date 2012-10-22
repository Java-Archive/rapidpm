package org.rapidpm.persistence;


import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class GraphDaoFactory {

    private final GraphDatabaseService graphDb;
    private static GraphDaoFactory instance;

    public static GraphDaoFactory getInstance() {
        if (instance == null)
            instance = new GraphDaoFactory();
        return instance;
    }

    public GraphDaoFactory() {
        graphDb = GraphDBFactory.getInstance().getGraphDBService();
    }

    public IssueBaseDAO getIssueBaseDAO() {
        return new IssueBaseDAO(graphDb);
    }

    public IssueStatusDAO getIssueStatusDAO() {
        return new IssueStatusDAO(graphDb);
    }

    public IssuePriorityDAO getIssuePriorityDAO() {
        return new IssuePriorityDAO(graphDb);
    }

    public IssueTypeDAO getIssueTypeDAO() {
        return new IssueTypeDAO(graphDb);
    }

    public IssueComponentDAO getIssueComponentDAO() {
        return new IssueComponentDAO(graphDb);
    }

    public IssueCommentDAO getIssueCommentDAO() {
        return new IssueCommentDAO(graphDb);
    }

    public IssueRelationDAO getIssueRelationDAO() {
        return new IssueRelationDAO(graphDb);
    }
}
