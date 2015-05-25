package org.rapidpm.persistence.system.security.berechtigungen;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * User: Alexander Vos
 * Date: 27.11.12
 * Time: 14:21
 */
public class RolleDAO extends DAO<Long, Rolle> {
    private static final Logger logger = Logger.getLogger(RolleDAO.class);

    public RolleDAO(final OrientGraph orientDB) {
        super(orientDB, Rolle.class);
    }

    public Rolle loadRolle(final String name) {
        if (logger.isInfoEnabled()) {
            logger.info("loadRolle : " + name);
        }
//        final TypedQuery<Rolle> query = orientDB.createQuery("from Rolle r where r.name=:name", Rolle.class)
//                .setParameter("name", name);
//        return getSingleResultOrNull(query);
        return null;
    }

    @Override
    public Rolle loadFull(Rolle entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
