package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedProjectDAO extends DAO<Long, PlannedProject> {

    public PlannedProjectDAO(final EntityManager entityManager) {
        super(entityManager, PlannedProject.class);
    }

    public PlannedProject loadFirstProject() {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
                "select p from PlannedProject p", PlannedProject.class)
                .setMaxResults(1);
        return getSingleResultOrNull(typedQuery);
    }

    public PlannedProject findByToken(final String token) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery("from PlannedProject p "
                + "where p.projektToken=:token ", PlannedProject.class).setParameter("token", token);
        final PlannedProject singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
    }
}
