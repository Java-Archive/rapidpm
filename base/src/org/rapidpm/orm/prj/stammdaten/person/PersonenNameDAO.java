package org.rapidpm.orm.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 10:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;

public class PersonenNameDAO extends BaseDAO<Long, PersonenName> {
    private static final Logger logger = Logger.getLogger(PersonenNameDAO.class);


    public PersonenNameDAO(final EntityManager entityManager) {
        super(entityManager, PersonenName.class);
    }
}
