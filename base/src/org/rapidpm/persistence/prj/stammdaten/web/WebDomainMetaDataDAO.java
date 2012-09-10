package org.rapidpm.persistence.prj.stammdaten.web; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.11.11
 * Time: 15:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;

public class WebDomainMetaDataDAO extends BaseDAO<Long, WebDomainMetaData> {
    private static final Logger logger = Logger.getLogger(WebDomainMetaDataDAO.class);


    public WebDomainMetaDataDAO(final EntityManager entityManager) {
        super(entityManager, WebDomainMetaData.class);
    }
}
