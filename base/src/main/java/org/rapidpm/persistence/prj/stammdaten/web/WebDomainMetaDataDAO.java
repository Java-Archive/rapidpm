package org.rapidpm.persistence.prj.stammdaten.web; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.11.11
 * Time: 15:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;

public class WebDomainMetaDataDAO extends DAO<Long, WebDomainMetaData> {
    private static final Logger logger = Logger.getLogger(WebDomainMetaDataDAO.class);


    public WebDomainMetaDataDAO(final OrientGraph orientDB) {
        super(orientDB, WebDomainMetaData.class);
    }

    @Override
    public WebDomainMetaData loadFull(WebDomainMetaData entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
