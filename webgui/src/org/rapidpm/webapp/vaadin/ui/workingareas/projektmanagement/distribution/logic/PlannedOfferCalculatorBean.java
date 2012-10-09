package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.logic;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 08.10.12
 * Time: 10:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Stateless(name = "PlannedOfferCalculatorEJB")
@TransactionManagement
public class PlannedOfferCalculatorBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
