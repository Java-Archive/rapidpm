package org.rapidpm.orm.system.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

public class LogginEntityActionDAO extends BaseDAO<Long, LoggingEntityAction> {
    private static final Logger logger = Logger.getLogger(LogginEntityActionDAO.class);


    public LogginEntityActionDAO(final EntityManager entityManager) {
        super(entityManager, LoggingEntityAction.class);
    }


    public LoggingEntityAction loadCreated() {
        return entityManager.createQuery("from LoggingEntityAction lea where lea.action='created'", LoggingEntityAction.class).getSingleResult();
    }

    public LoggingEntityAction loadModified() {
        return entityManager.createQuery("from LoggingEntityAction lea where lea.action='modified'", LoggingEntityAction.class).getSingleResult();
    }

    public LoggingEntityAction loadDeleted() {
        return entityManager.createQuery("from LoggingEntityAction lea where lea.action='deleted'", LoggingEntityAction.class).getSingleResult();
    }


}