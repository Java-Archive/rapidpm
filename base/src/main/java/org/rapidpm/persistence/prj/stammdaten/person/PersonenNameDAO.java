package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 10:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;

public class PersonenNameDAO extends DAO<Long, PersonenName> {
    private static final Logger logger = Logger.getLogger(PersonenNameDAO.class);


    public PersonenNameDAO(final OrientGraph orientDB) {
        super(orientDB, PersonenName.class);
    }

    @Override
    public PersonenName loadFull(PersonenName entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
