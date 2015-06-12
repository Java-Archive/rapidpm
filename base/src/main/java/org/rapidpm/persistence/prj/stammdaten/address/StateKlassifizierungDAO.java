package org.rapidpm.persistence.prj.stammdaten.address; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.11.11
 * Time: 01:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class StateKlassifizierungDAO extends DAO<Long, StateKlassifizierung> {
    private static final Logger logger = Logger.getLogger(StateKlassifizierungDAO.class);


    public StateKlassifizierungDAO(final OrientGraph orientDB) {
        super(orientDB, StateKlassifizierung.class);
    }

    @Override
    public StateKlassifizierung loadFull(StateKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public StateKlassifizierung createEntityFull(StateKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        throw new NotYetImplementedException();
    }
}
