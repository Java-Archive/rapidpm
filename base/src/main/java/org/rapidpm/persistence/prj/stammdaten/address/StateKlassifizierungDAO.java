package org.rapidpm.persistence.prj.stammdaten.address; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.11.11
 * Time: 01:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

public class StateKlassifizierungDAO extends DAO<Long, StateKlassifizierung> {

    public StateKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, StateKlassifizierung.class);
    }
}
