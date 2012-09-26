package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitDAO extends BaseDAO<Long, PlanningUnit> {
    private static final Logger logger = Logger.getLogger(PlanningUnitDAO.class);

    public PlanningUnitDAO(final EntityManager entityManager) {
        super(entityManager, PlanningUnit.class);
    }


}
