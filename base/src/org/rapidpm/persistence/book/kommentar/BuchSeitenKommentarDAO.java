package org.rapidpm.persistence.book.kommentar;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:07
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class BuchSeitenKommentarDAO extends BaseDaoFactory.BaseDAO<Long, BuchSeitenKommentar> {
    private static final Logger logger = Logger.getLogger(BuchSeitenKommentarDAO.class);


    public BuchSeitenKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchSeitenKommentar.class);
    }
}
