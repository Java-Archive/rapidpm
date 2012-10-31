package org.rapidpm.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.rapidpm.ejb3.EJBFactory;

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
    private DaoFactory relDaoFactory;
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
        GraphDBFactoryBean bean = EJBFactory.getEjbInstance(GraphDBFactoryBean.class);
        if (bean != null)
            relDaoFactory = bean.getDaoFactoryBean();
        else
            logger.fatal("Couldn't create DAOFactory for relational DB");
        graphDb = new EmbeddedGraphDatabase(DB_PATH);
        registerShutdownHook(graphDb);
    }

    public GraphDatabaseService getGraphDBService() {
        return graphDb;
    }

    public DaoFactory getRelDaoFactory() {
        return relDaoFactory;
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
