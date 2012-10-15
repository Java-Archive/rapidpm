package org.rapidpm.persistence.prj.projectmanagement.planning;

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
public class PlanningUnitDAO extends DAO<Long, PlanningUnit> {
    private static final Logger logger = Logger.getLogger(PlanningUnitDAO.class);

    public PlanningUnitDAO(final EntityManager entityManager) {
        super(entityManager, PlanningUnit.class);
    }

    public PlanningUnit loadPlanningUnitByName(final String planningUnitName) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit pu "
                + "where pu.planningUnitName=:planningUnitName ", PlanningUnit.class).setParameter("planningUnitName", planningUnitName);
        final PlanningUnit singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
    }




}
