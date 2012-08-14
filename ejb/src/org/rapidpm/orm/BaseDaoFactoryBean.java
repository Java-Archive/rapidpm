package org.rapidpm.orm;

import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.Serializable;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 07.02.11
 * Time: 23:44
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateful(name = "BaseDaoFactoryEJB")
@TransactionManagement
public class BaseDaoFactoryBean extends BaseDaoFactory implements Serializable {

    @Inject
    private transient Logger logger;


    public BaseDaoFactoryBean() {
    }

    //    @Resource(shareable = false)
    //    private transient UserTransaction userTransaction;

    @PersistenceContext(unitName = "baseorm", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

//    private BaseDaoFactory daoFactory;

//    @Deprecated
//    public BaseDaoFactory getDaoFactory() {
//        return this;
//    }
//

    @PostConstruct
    void postConstruct() {
//        daoFactory = new BaseDaoFactory();
        setEm(em);
    }

    @PostActivate
    private void postActivate() {
//        daoFactory = new BaseDaoFactory();
        setEm(em);

    }

    @PrePassivate
    private void prePassivate() {
//        daoFactory = null;
    }

    @PreDestroy
    private void preDestroy() {
//        daoFactory = null;
    }


}
