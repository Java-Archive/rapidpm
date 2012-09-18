package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "ProjektmanagementScreensEJB")
@TransactionManagement
public class ProjektmanagementScreensBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}