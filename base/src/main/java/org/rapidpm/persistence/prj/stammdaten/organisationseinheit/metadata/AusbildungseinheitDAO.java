package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:18
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class AusbildungseinheitDAO extends DAO<Long, Ausbildungseinheit> {

    public AusbildungseinheitDAO(final EntityManager entityManager) {
        super(entityManager, Ausbildungseinheit.class);
    }
}
