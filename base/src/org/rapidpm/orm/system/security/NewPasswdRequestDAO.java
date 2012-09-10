package org.rapidpm.orm.system.security;

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
 * @since 14.03.2010
 *        Time: 17:27:05
 */

public class NewPasswdRequestDAO extends BaseDAO<Long, NewPasswdRequest> {
    private static final Logger logger = Logger.getLogger(NewPasswdRequestDAO.class);

    public NewPasswdRequestDAO(final EntityManager entityManager) {
        super(entityManager, NewPasswdRequest.class);
    }


}