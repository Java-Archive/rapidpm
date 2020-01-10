package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchAbsatzKommentarDAO extends DAO<Long, BuchAbsatzKommentar> {

    public BuchAbsatzKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchAbsatzKommentar.class);
    }
}
