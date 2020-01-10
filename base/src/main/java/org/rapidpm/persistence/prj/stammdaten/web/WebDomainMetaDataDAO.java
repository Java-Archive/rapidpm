package org.rapidpm.persistence.prj.stammdaten.web; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.11.11
 * Time: 15:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class WebDomainMetaDataDAO extends DAO<Long, WebDomainMetaData> {

    public WebDomainMetaDataDAO(final EntityManager entityManager) {
        super(entityManager, WebDomainMetaData.class);
    }
}
