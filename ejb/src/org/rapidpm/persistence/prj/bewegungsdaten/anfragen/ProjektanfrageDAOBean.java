package org.rapidpm.persistence.prj.bewegungsdaten.anfragen;

import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 26.04.12
 * Time: 12:03
 */
@Stateless(name = "ProjektanfrageDAOEJB")
public class ProjektanfrageDAOBean {

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;


    private ProjektanfrageDAO getEntityDAO() {
        return daoFactoryBean.getProjektanfrageDAO();
    }

}
