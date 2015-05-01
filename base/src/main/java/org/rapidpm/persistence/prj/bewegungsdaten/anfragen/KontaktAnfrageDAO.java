package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(KontaktAnfrageDAO.class);

    public KontaktAnfrageDAO(final OrientGraph orientDB) {
        super(orientDB, KontaktAnfrage.class);
    }


}
