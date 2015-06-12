package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 11:56
 */
public class ProjektanfrageDAO extends DAO<Long, Projektanfrage> {

    public ProjektanfrageDAO(final OrientGraph orientDB) {
        super(orientDB, Projektanfrage.class);
    }

    @Override
    public Projektanfrage loadFull(Projektanfrage entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public Projektanfrage createEntityFull(Projektanfrage entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        return null;
    }
}
