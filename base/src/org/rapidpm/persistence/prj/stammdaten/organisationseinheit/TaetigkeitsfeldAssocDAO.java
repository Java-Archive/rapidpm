package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 20:00
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;

public class TaetigkeitsfeldAssocDAO extends BaseDAO<Long, TaetigkeitsfeldAssoc> {
    private static final Logger logger = Logger.getLogger(TaetigkeitsfeldAssocDAO.class);


    public TaetigkeitsfeldAssocDAO(final EntityManager entityManager) {
        super(entityManager, TaetigkeitsfeldAssoc.class);
    }
}
