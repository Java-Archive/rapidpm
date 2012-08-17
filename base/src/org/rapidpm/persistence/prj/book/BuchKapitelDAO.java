package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class BuchKapitelDAO extends BaseDaoFactory.BaseDAO<Long, BuchKapitel> {
    private static final Logger logger = Logger.getLogger(BuchKapitelDAO.class);


    public BuchKapitelDAO(final EntityManager entityManager) {
        super(entityManager, BuchKapitel.class);
    }
}
