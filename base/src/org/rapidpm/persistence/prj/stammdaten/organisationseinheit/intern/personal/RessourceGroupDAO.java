package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RessourceGroupDAO extends DAO<Long, RessourceGroup> {
    private static final Logger logger = Logger.getLogger(RessourceGroupDAO.class);

    public RessourceGroupDAO(final EntityManager entityManager) {
        super(entityManager, RessourceGroup.class);
    }

    public RessourceGroup loadRessourceGroupByName(final String name) {
        final TypedQuery<RessourceGroup> typedQuery = entityManager.createQuery("from RessourceGroup rg "
                + "where rg.name=:name ", RessourceGroup.class).setParameter("name", name);
        final RessourceGroup singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
    }
}
