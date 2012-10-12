package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class WirtschaftseinheitDAO extends DAO<Long, Wirtschaftseinheit> {
    private static final Logger logger = Logger.getLogger(WirtschaftseinheitDAO.class);


    public WirtschaftseinheitDAO(final EntityManager entityManager) {
        super(entityManager, Wirtschaftseinheit.class);
    }
}
