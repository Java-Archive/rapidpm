package org.rapidpm.persistence.system.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class LogginEntityActionDAO extends DAO<Long, LoggingEntityAction> {
    private static final Logger logger = Logger.getLogger(LogginEntityActionDAO.class);


    public LogginEntityActionDAO(final OrientGraph orientDB) {
        super(orientDB, LoggingEntityAction.class);
    }


    public LoggingEntityAction loadCreated() {
//        return orientDB.createQuery("from LoggingEntityAction lea where lea.action='created'", LoggingEntityAction.class).getSingleResult();
    return null;
    }

    public LoggingEntityAction loadModified() {
//        return orientDB.createQuery("from LoggingEntityAction lea where lea.action='modified'", LoggingEntityAction.class).getSingleResult();
        return null;
    }

    public LoggingEntityAction loadDeleted() {
//        return orientDB.createQuery("from LoggingEntityAction lea where lea.action='deleted'", LoggingEntityAction.class).getSingleResult();
        return null;
    }


}