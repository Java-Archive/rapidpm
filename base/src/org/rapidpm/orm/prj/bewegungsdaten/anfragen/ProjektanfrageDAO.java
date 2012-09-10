package org.rapidpm.orm.prj.bewegungsdaten.anfragen;

import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 11:56
 */
public class ProjektanfrageDAO extends BaseDAO<Long, Projektanfrage> {

    public ProjektanfrageDAO(final EntityManager entityManager) {
        super(entityManager, Projektanfrage.class);
    }

}
