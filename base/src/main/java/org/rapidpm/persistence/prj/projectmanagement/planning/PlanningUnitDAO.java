package org.rapidpm.persistence.prj.projectmanagement.planning;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(PlanningUnitDAO.class);

    public PlanningUnitDAO(final OrientGraph orientDB) {
        super(orientDB, PlanningUnit.class);
    }

    public PlanningUnit loadPlanningUnitByName(final String planningUnitName) {
//        final TypedQuery<PlanningUnit> typedQuery = orientDB.createQuery("from PlanningUnit pu "
//                + "where pu.planningUnitName=:planningUnitName ", PlanningUnit.class).setParameter("planningUnitName", planningUnitName);
//        final PlanningUnit singleResultOrNull = getSingleResultOrNull(typedQuery);
//        return singleResultOrNull;
        return null;
    }

    public List<PlanningUnit> loadAllPlanningUnitsWithoutParents() {
//        final TypedQuery<PlanningUnit> typedQuery = orientDB.createQuery("from PlanningUnit pu "
//                + "where pu.parent=null ", PlanningUnit.class);
//        final List<PlanningUnit> resultList = typedQuery.getResultList();
//        return resultList;
        return null;
    }




}
