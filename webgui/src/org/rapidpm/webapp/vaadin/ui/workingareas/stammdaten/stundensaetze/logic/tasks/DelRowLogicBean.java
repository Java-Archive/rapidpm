package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tasks;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 23.09.12
 * Time: 13:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Stateless(name = "StundensaetzeDeleteEJB")
@TransactionManagement
public class DelRowLogicBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
