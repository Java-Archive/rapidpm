package org.rapidpm.persistence.prj.textelement.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TextElementKommentarDAO extends DAO<Long, TextElementKommentar> {


    public TextElementKommentarDAO(final EntityManager entityManager) {
        super(entityManager, TextElementKommentar.class);
    }
}
