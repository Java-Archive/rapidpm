package org.rapidpm.persistence;

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
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
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
    public void setEntityManager(final EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
//    private EntityManager em;

//    private BaseDAO daoFactory;

//    @Deprecated
//    public BaseDAO getDaoFactoryFactory() {
//        return this;
//    }
//

    @PostConstruct
    void postConstruct() {
//        daoFactory = new BaseDAO();
//        setEntityManager(em);
    }

    @PostActivate
    private void postActivate() {
//        daoFactory = new BaseDAO();
//        setEntityManager(em);

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
