package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.information;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "InformationFieldGroupBeanEJB")
@TransactionManagement
public class PlanningInformationFieldGroupBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}