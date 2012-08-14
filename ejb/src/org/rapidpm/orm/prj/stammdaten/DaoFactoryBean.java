package org.rapidpm.orm.prj.stammdaten;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 13.01.12
 * Time: 13:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.orm.DaoFactory;
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

@Stateful(name = "DaoFactoryEJB")
@TransactionManagement
public class DaoFactoryBean extends DaoFactory implements Serializable {

    @Inject
    private transient Logger logger;


    public DaoFactoryBean() {
    }

    //    @Resource(shareable = false)
    //    private transient UserTransaction userTransaction;

    @PersistenceContext(unitName = "baseorm", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    private DaoFactory daoFactory;

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }


    @PostConstruct
    void postConstruct() {
        daoFactory = new DaoFactory();
        daoFactory.setEm(em);
    }

    @PostActivate
    private void postActivate() {
        daoFactory = new DaoFactory();
        daoFactory.setEm(em);

    }

    @PrePassivate
    private void prePassivate() {
        daoFactory = null;
    }

    @PreDestroy
    private void preDestroy() {
        daoFactory = null;
    }


}
