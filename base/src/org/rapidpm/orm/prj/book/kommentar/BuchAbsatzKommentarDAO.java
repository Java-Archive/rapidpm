package org.rapidpm.orm.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

public class BuchAbsatzKommentarDAO extends BaseDAO<Long, BuchAbsatzKommentar> {
    private static final Logger logger = Logger.getLogger(BuchAbsatzKommentarDAO.class);


    public BuchAbsatzKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchAbsatzKommentar.class);
    }
}
