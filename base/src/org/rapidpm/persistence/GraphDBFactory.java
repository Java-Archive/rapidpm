package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.rapidpm.Constants;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 16.10.12
 * Time: 10:47
 * To change this template use File | Settings | File Templates.
 */
public class GraphDBFactory {
    private static final Logger logger = Logger.getLogger(GraphDBFactory.class);

    public static final String DB_PATH = Constants.DB_PATH;
    private final GraphDatabaseService graphDb;
    private static GraphDBFactory instance;

    public static GraphDBFactory getInstance() {
        if (instance == null) {
            if (logger.isDebugEnabled())
                logger.debug("Create new GraphDBFactory instance");
            instance = new GraphDBFactory();
            if (!instance.initDb())
                throw new IllegalStateException("GraphDB couldn't be created/initialized.");
        }
        return instance;
    }

    private GraphDBFactory() {
        graphDb = new EmbeddedGraphDatabase(DB_PATH);
        registerShutdownHook(graphDb);
    }

    public boolean initDb() {
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
                for (final Class clazz : classList) {
                    final RelationshipType rel = GraphRelationFactory.getRootToClassRootRelType(clazz);
                    final Node class_root = graphDb.createNode();
                    class_root.setProperty(GraphRelationFactory.getRelationAttributeName(), rel.name());
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
