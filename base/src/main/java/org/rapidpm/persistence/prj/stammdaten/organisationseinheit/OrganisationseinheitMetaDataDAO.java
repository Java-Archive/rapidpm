package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 19.12.11
 * Time: 09:09
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class OrganisationseinheitMetaDataDAO extends DAO<Long, OrganisationseinheitMetaData> {

    public OrganisationseinheitMetaDataDAO(final EntityManager entityManager) {
        super(entityManager, OrganisationseinheitMetaData.class);
    }
}
