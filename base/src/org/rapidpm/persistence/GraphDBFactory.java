package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 16.10.12
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class GraphDBFactory {
    private static final Logger logger = Logger.getLogger(GraphDBFactory.class);

    public static final String DB_PATH = "C:/out/graphDB";
    private final GraphDatabaseService graphDb;
    private static GraphDBFactory instance;

    public static GraphDBFactory getInstance() {
        if (instance == null) {
            if (logger.isDebugEnabled())
                logger.debug("Create new GrapgDBFactory instance");
            instance = new GraphDBFactory();
        }
        return instance;
    }

    private GraphDBFactory() {
        graphDb = new EmbeddedGraphDatabase(DB_PATH);
        registerShutdownHook(graphDb);
        if (!createDB())
            throw new IllegalStateException("GraphDB couldn't be created/initialized.");
    }

    private boolean createDB() {
        boolean success = true;
        final Node root_node = graphDb.getNodeById(0);
        if (!root_node.hasRelationship()) {
            success= false;
            final List<Class> classList = new ArrayList<>();
            classList.add(IssueBase.class);
            classList.add(IssueStatus.class);
            classList.add(IssuePriority.class);
            classList.add(IssueType.class);
            classList.add(IssueComponent.class);
            classList.add(IssueRelation.class);
            classList.add(IssueVersion.class);
            classList.add(IssueStoryPoint.class);

            final Transaction tx = graphDb.beginTx();
            try{
                for (Class clazz : classList) {
                    final RelationshipType rel = GraphRelationRegistry.getRootToClassRootRelType(clazz);
                    Node class_root = graphDb.createNode();
                    class_root.setProperty(GraphRelationRegistry.getRelationAttributeName(), rel.name());
                    root_node.createRelationshipTo(class_root, rel);
                }
                tx.success();
                success = true;
            } finally {
                tx.finish();
            }
        }
        return success;
    }


//    private boolean initializeBasicAttributes() {
//        boolean success = false;
//        try {
//            final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
//
//            final IssueStatus status = new IssueStatus();
//            status.setStatusName("Open");
//            status.setStatusFileName("status_open.gif");
//            daoFactory.getIssueStatusDAO().persist(status);
//
//            final IssuePriority priority = new IssuePriority();
//            priority.setPriorityName("Trivial");
//            priority.setPriorityFileName("priority_trivial.gif");
//            priority.setPrio(0);
//            daoFactory.getIssuePriorityDAO().persist(priority);
//
//            final IssueType type = new IssueType("Bug");
//            daoFactory.getIssueTypeDAO().persist(type);
//
//            final IssueVersion version = new IssueVersion(" - ");
//            daoFactory.getIssueVersionDAO().persist(version);
//
//            final IssueStoryPoint storypoint = new IssueStoryPoint(1);
//            daoFactory.getIssueStoryPointDAO().persist(storypoint);
//
//            final IssueRelation relation = new IssueRelation();
//            relation.setRelationName("Duplicate");
//            relation.setOutgoingName("duplicates");
//            relation.setIncomingName("is duplicated by");
//            daoFactory.getIssueRelationDAO().persist(relation);
//
//            final IssueComponent component = new IssueComponent("Documentation");
//            daoFactory.getIssueComponentDAO().persist(component);
//            success = true;
//        } finally {
//
//        }
//        return success;
//    }

    public GraphDatabaseService getGraphDBService() {
        return graphDb;
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
}
