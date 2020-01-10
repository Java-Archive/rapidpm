package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 06.07.11
 * Time: 10:15
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class PersonenNameDAO extends DAO<Long, PersonenName> {

    public PersonenNameDAO(final EntityManager entityManager) {
        super(entityManager, PersonenName.class);
    }
}
