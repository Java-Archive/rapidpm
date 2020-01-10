package org.rapidpm.persistence.system.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:58
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class LogginEntityActionDAO extends DAO<Long, LoggingEntityAction> {

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