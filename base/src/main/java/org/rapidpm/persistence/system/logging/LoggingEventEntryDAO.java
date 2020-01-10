package org.rapidpm.persistence.system.logging;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.02.11
 * Time: 09:14
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class LoggingEventEntryDAO extends DAO<Long, LoggingEventEntry> {

    public LoggingEventEntryDAO(final EntityManager entityManager) {
        super(entityManager, LoggingEventEntry.class);
    }


}