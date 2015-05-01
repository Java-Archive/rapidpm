package org.rapidpm.persistence.system.security;

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
 * @since 14.03.2010
 *        Time: 17:27:05
 */

public class NewPasswdRequestDAO extends DAO<Long, NewPasswdRequest> {
    private static final Logger logger = Logger.getLogger(NewPasswdRequestDAO.class);

    public NewPasswdRequestDAO(final OrientGraph orientDB) {
        super(orientDB, NewPasswdRequest.class);
    }


}