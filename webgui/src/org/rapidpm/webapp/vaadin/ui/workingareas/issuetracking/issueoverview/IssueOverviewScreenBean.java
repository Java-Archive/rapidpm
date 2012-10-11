package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview;

import org.rapidpm.persistence.DaoFactoryBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 11.10.12
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "IssueOverviewScreenBeanEJB")
@TransactionManagement
public class IssueOverviewScreenBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}