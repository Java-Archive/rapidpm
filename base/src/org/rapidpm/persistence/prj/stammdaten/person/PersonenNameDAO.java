package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 10:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;

public class PersonenNameDAO extends BaseDaoFactory.BaseDAO<Long, PersonenName> {
    private static final Logger logger = Logger.getLogger(PersonenNameDAO.class);


    public PersonenNameDAO(final EntityManager entityManager) {
        super(entityManager, PersonenName.class);
    }
}
