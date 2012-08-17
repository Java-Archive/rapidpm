package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class BuchKommentarDAO extends BaseDaoFactory.BaseDAO<Long, BuchKommentar> {
    private static final Logger logger = Logger.getLogger(BuchKommentarDAO.class);


    public BuchKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchKommentar.class);
    }
}
