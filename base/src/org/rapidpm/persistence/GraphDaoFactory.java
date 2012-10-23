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

    private static final GraphDatabaseService graphDb = GraphDBFactory.getInstance().getGraphDBService();
    private static final DaoFactory daoFactory = GraphDBFactory.getInstance().getRelDaoFactory();

    private GraphDaoFactory() {
        //e,pty on purpose
    }

    public static IssueBaseDAO getIssueBaseDAO() {
        return new IssueBaseDAO(graphDb, daoFactory);
    }

    public static IssueStatusDAO getIssueStatusDAO() {
        return new IssueStatusDAO(graphDb, daoFactory);
    }

    public static IssuePriorityDAO getIssuePriorityDAO() {
        return new IssuePriorityDAO(graphDb, daoFactory);
    }

    public static IssueTypeDAO getIssueTypeDAO() {
        return new IssueTypeDAO(graphDb, daoFactory);
    }

    public static IssueComponentDAO getIssueComponentDAO() {
        return new IssueComponentDAO(graphDb, daoFactory);
    }

    public static IssueRelationDAO getIssueRelationDAO() {
        return new IssueRelationDAO(graphDb, daoFactory);
    }
}
