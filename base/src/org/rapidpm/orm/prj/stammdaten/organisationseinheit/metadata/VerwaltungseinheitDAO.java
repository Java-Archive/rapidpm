package org.rapidpm.orm.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

public class VerwaltungseinheitDAO extends BaseDAO<Long, Verwaltungseinheit> {
    private static final Logger logger = Logger.getLogger(VerwaltungseinheitDAO.class);


    public VerwaltungseinheitDAO(final EntityManager entityManager) {
        super(entityManager, Verwaltungseinheit.class);
    }
}
