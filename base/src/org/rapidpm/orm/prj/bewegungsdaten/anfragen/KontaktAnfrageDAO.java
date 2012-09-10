package org.rapidpm.orm.prj.bewegungsdaten.anfragen;

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

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

public class KontaktAnfrageDAO extends BaseDAO<Long, KontaktAnfrage> {
    private static final Logger logger = Logger.getLogger(KontaktAnfrageDAO.class);

    public KontaktAnfrageDAO(final EntityManager entityManager) {
        super(entityManager, KontaktAnfrage.class);
    }


}
