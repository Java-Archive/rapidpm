package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 11:56
 */
public class ProjektanfrageDAO extends DAO<Long, Projektanfrage> {

    public ProjektanfrageDAO(final EntityManager entityManager) {
        super(entityManager, Projektanfrage.class);
    }

}
