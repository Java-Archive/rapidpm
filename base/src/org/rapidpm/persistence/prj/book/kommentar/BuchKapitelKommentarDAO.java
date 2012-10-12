package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchKapitelKommentarDAO extends DAO<Long, BuchKapitelKommentar> {
    private static final Logger logger = Logger.getLogger(BuchKapitelKommentarDAO.class);


    public BuchKapitelKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchKapitelKommentar.class);
    }
}
