package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;

public class AusbildungseinheitDAO extends DAO<Long, Ausbildungseinheit> {
    private static final Logger logger = Logger.getLogger(AusbildungseinheitDAO.class);


    public AusbildungseinheitDAO(final OrientGraph orientDB) {
        super(orientDB, Ausbildungseinheit.class);
    }

    @Override
    public Ausbildungseinheit loadFull(Ausbildungseinheit entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
