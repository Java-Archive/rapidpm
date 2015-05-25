package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupDAO extends DAO<Long, RessourceGroup> {
    private static final Logger logger = Logger.getLogger(RessourceGroupDAO.class);

    public RessourceGroupDAO(final OrientGraph orientDB) {
        super(orientDB, RessourceGroup.class);
    }

    public RessourceGroup loadRessourceGroupByName(final String name) {
//        final TypedQuery<RessourceGroup> typedQuery = orientDB.createQuery("from RessourceGroup rg "
//                + "where rg.name=:name ", RessourceGroup.class).setParameter("name", name);
//        final RessourceGroup singleResultOrNull = getSingleResultOrNull(typedQuery);
//        return singleResultOrNull;
        return null;
    }

    @Override
    public RessourceGroup loadFull(RessourceGroup entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
