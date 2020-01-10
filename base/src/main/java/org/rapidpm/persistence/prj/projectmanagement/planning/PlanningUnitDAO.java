package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitDAO extends DAO<Long, PlanningUnit> {

    public PlanningUnitDAO(final EntityManager entityManager) {
        super(entityManager, PlanningUnit.class);
    }

    public PlanningUnit loadPlanningUnitByName(final String planningUnitName) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit pu "
                + "where pu.planningUnitName=:planningUnitName ", PlanningUnit.class).setParameter("planningUnitName", planningUnitName);
        final PlanningUnit singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
    }

    public List<PlanningUnit> loadAllPlanningUnitsWithoutParents() {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit pu "
                + "where pu.parent=null ", PlanningUnit.class);
        final List<PlanningUnit> resultList = typedQuery.getResultList();
        return resultList;
    }




}
