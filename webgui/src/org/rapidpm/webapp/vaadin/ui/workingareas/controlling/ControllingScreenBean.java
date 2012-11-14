package org.rapidpm.webapp.vaadin.ui.workingareas.controlling;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * User: donnie
 * Date: 14.11.12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "ControllingScreenBeanEJB")
@TransactionManagement
public class ControllingScreenBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
