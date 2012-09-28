package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

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
@Stateless(name = "AufwandProjInitScreenEJB")
@TransactionManagement
public class AufwandProjInitScreenBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
