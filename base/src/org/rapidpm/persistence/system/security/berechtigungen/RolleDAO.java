package org.rapidpm.persistence.system.security.berechtigungen;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * User: Alexander Vos
 * Date: 27.11.12
 * Time: 14:21
 */
public class RolleDAO extends DAO<Long, Rolle> {
    private static final Logger logger = Logger.getLogger(RolleDAO.class);

    public RolleDAO(final EntityManager entityManager) {
        super(entityManager, Rolle.class);
    }

    public Rolle loadRolle(final String name) {
        if (logger.isInfoEnabled()) {
            logger.info("loadRolle : " + name);
        }
        final TypedQuery<Rolle> query = entityManager.createQuery("from Rolle r where r.name=:name", Rolle.class)
                .setParameter("name", name);
        return getSingleResultOrNull(query);
    }
}
