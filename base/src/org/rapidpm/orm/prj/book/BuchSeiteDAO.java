package org.rapidpm.orm.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class BuchSeiteDAO extends BaseDaoFactory.BaseDAO<Long, BuchSeite> {
    private static final Logger logger = Logger.getLogger(BuchSeiteDAO.class);


    public BuchSeiteDAO(final EntityManager entityManager) {
        super(entityManager, BuchSeite.class);
    }
}
