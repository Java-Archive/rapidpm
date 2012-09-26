package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "ProjektplanungScreenBeanEJB")
@TransactionManagement
public class ProjektPlanungScreenBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}