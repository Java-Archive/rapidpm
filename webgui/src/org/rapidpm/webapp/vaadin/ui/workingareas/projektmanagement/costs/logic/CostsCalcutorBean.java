package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "CostsCalcutorCostsEJB")
@TransactionManagement
public class CostsCalcutorBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}