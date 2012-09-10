package org.rapidpm.orm.prj.book;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

public class BuchDAO extends BaseDAO<Long, Buch> {
    private static final Logger logger = Logger.getLogger(BuchDAO.class);


    public BuchDAO(final EntityManager entityManager) {
        super(entityManager, Buch.class);
    }


}
