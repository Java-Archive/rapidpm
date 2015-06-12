package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class BuchKommentarDAO extends DAO<Long, BuchKommentar> {
    private static final Logger logger = Logger.getLogger(BuchKommentarDAO.class);


    public BuchKommentarDAO(final OrientGraph orientDB) {
        super(orientDB, BuchKommentar.class);
    }

    @Override
    public BuchKommentar loadFull(BuchKommentar entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public BuchKommentar createEntityFull(BuchKommentar entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        throw new NotYetImplementedException();
    }
}
