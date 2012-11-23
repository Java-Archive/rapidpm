//package org.rapidpm.persistence;
//
//
//import org.apache.log4j.Logger;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
//
//
///**
// * Created with IntelliJ IDEA.
// * User: Alvin
// * Date: 16.10.12
// * Time: 18:39
// * To change this template use File | Settings | File Templates.
// */
//public class GraphDaoFactory {
//    private static final Logger logger = Logger.getLogger(GraphDaoFactory.class);
//
//    private static final GraphDatabaseService graphDb = GraphDBFactory.getInstance().getGraphDBService();
//    private static final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//    private static GraphDaoFactory instance;
//
//    public static GraphDaoFactory getInstance() {
//        if (instance == null) {
//            if (logger.isDebugEnabled())
//                logger.debug("Create new GraphDaoFactory instance");
//            instance = new GraphDaoFactory();
//        }
//        return instance;
//    }
//
//    public IssueBaseDAO getIssueBaseDAO(final Long projectId) {
//        return new IssueBaseDAO(graphDb, daoFactory, projectId);
//    }
//
//    public IssueStatusDAO getIssueStatusDAO() {
//        return new IssueStatusDAO(graphDb, daoFactory);
//    }
//
//    public IssuePriorityDAO getIssuePriorityDAO() {
//        return new IssuePriorityDAO(graphDb, daoFactory);
//    }
//
//    public IssueTypeDAO getIssueTypeDAO() {
//        return new IssueTypeDAO(graphDb, daoFactory);
//    }
//
//    public IssueComponentDAO getIssueComponentDAO() {
//        return new IssueComponentDAO(graphDb, daoFactory);
//    }
//
//    public IssueRelationDAO getIssueRelationDAO() {
//        return new IssueRelationDAO(graphDb, daoFactory);
//    }
//
//    public IssueVersionDAO getIssueVersionDAO() {
//        return new IssueVersionDAO(graphDb, daoFactory);
//    }
//
//    public IssueStoryPointDAO getIssueStoryPointDAO() {
//        return new IssueStoryPointDAO(graphDb, daoFactory);
//    }
//}
