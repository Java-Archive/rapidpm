package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 24.05.12
 * Time: 11:00
 */
@Stateless(name = "TreeTableDataSourceFillerInitEJB")
@TransactionManagement
public class TreeTableDataSourceFillerBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
