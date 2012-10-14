package org.rapidpm.persistence.prj.projectmanagement.planning.finance;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 08.10.2012
 * Time: 10:53
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedOfferDAO extends DAO<Long, PlannedOffer> {
    private static final Logger logger = Logger.getLogger(PlannedOfferDAO.class);

    public PlannedOfferDAO(final EntityManager entityManager) {
        super(entityManager, PlannedOffer.class);
    }




}
