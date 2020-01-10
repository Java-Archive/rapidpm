package org.rapidpm.persistence.prj.book.kommentar;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:07
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class BuchSeitenKommentarDAO extends DAO<Long, BuchSeitenKommentar> {

    public BuchSeitenKommentarDAO(final EntityManager entityManager) {
        super(entityManager, BuchSeitenKommentar.class);
    }
}
