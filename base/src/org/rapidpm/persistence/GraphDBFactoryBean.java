package org.rapidpm.persistence;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 23.10.12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "GraphDBFactoryBeanEJB")
@TransactionManagement
public class GraphDBFactoryBean {

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean baseDaoFactoryBean;

    public DaoFactoryBean getDaoFactoryBean() {
        return baseDaoFactoryBean;
    }
}
