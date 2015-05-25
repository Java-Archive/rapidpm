package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 19.12.11
 * Time: 09:09
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;

public class OrganisationseinheitMetaDataDAO extends DAO<Long, OrganisationseinheitMetaData> {
    private static final Logger logger = Logger.getLogger(OrganisationseinheitMetaDataDAO.class);


    public OrganisationseinheitMetaDataDAO(final OrientGraph orientDB) {
        super(orientDB, OrganisationseinheitMetaData.class);
    }

    @Override
    public OrganisationseinheitMetaData loadFull(OrganisationseinheitMetaData entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
