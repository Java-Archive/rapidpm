package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 19:53
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PositionDAO extends DAO<Long, Position> {
    private static final Logger logger = Logger.getLogger(PositionDAO.class);


    public PositionDAO(final EntityManager entityManager) {
        super(entityManager, Position.class);
    }

    public List<Position> loadPositionForOrgeinheit(final Long orgOID) {
        final TypedQuery<Position> typedQuery = entityManager.createQuery("from Position p where  p.organisationseinheit.id=:orgOID", Position.class)
                .setParameter("orgOID", orgOID);
        return typedQuery.getResultList();
    }


}
