package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 13:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */


@Stateless(name = "StundensaetzeScreenEJB")
@TransactionManagement
public class StundensaetzeScreenBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
