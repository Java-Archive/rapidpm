package org.rapidpm.webapp.vaadin;


import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 11.09.12
 * Time: 12:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

@Stateless(name = "LoginEJB")
@TransactionManagement
public class LoginBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
