package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 20:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class TaetigkeitsfeldAssocDAO extends DAO<Long, TaetigkeitsfeldAssoc> {

    public TaetigkeitsfeldAssocDAO(final EntityManager entityManager) {
        super(entityManager, TaetigkeitsfeldAssoc.class);
    }
}
