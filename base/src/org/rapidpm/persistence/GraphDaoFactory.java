package org.rapidpm.persistence;


import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class GraphDaoFactory {
    private static final Logger logger = Logger.getLogger(GraphDaoFactory.class);

    private static final GraphDatabaseService graphDb = GraphDBFactory.getInstance().getGraphDBService();
    private static final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

    private GraphDaoFactory() {
        //empty on purpose
    }

    public static IssueBaseDAO getIssueBaseDAO(final Long projectId) {
        return new IssueBaseDAO(graphDb, daoFactory, projectId);
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

    public static IssueVersionDAO getIssueVersionDAO() {
        return new IssueVersionDAO(graphDb, daoFactory);
    }

    public static IssueStoryPointDAO getIssueStoryPointDAO() {
        return new IssueStoryPointDAO(graphDb, daoFactory);
    }
}
