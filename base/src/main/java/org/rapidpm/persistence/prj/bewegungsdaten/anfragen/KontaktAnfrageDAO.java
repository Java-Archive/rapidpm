package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 15.03.2010
 *        Time: 15:29:02
 */

public class KontaktAnfrageDAO extends DAO<Long, KontaktAnfrage> {

    public KontaktAnfrageDAO(final EntityManager entityManager) {
        super(entityManager, KontaktAnfrage.class);
    }


}
