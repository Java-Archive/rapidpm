package org.rapidpm.persistence.system.security;

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

    public NewPasswdRequestDAO(final EntityManager entityManager) {
        super(entityManager, NewPasswdRequest.class);
    }


}