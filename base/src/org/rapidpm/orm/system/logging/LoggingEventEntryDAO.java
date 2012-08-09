package org.rapidpm.orm.system.logging;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.02.11
 * Time: 09:14
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class LoggingEventEntryDAO extends BaseDaoFactory.BaseDAO<Long, LoggingEventEntry> {
    private static final Logger logger = Logger.getLogger(LoggingEventEntryDAO.class);


    public LoggingEventEntryDAO(final EntityManager entityManager) {
        super(entityManager, LoggingEventEntry.class);
    }


}