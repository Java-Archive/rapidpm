package org.rapidpm.orm.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class AusbildungseinheitDAO extends BaseDaoFactory.BaseDAO<Long, Ausbildungseinheit> {
    private static final Logger logger = Logger.getLogger(AusbildungseinheitDAO.class);


    public AusbildungseinheitDAO(final EntityManager entityManager) {
        super(entityManager, Ausbildungseinheit.class);
    }
}
