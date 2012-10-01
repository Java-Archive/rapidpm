package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.ressources;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

@Stateless(name = "PlanningRessourcesFormLayoutEJB")
@TransactionManagement
public class PlanningRessourcesMyFormLayoutBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}