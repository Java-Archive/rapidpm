package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "PlanningUnitFieldGroupEJB")
@TransactionManagement
public class PlanningUnitFieldGroupBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}