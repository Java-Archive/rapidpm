package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchSeiteDAO extends DAO<Long, BuchSeite> {
    private static final Logger logger = Logger.getLogger(BuchSeiteDAO.class);


    public BuchSeiteDAO(final EntityManager entityManager) {
        super(entityManager, BuchSeite.class);
    }
}
