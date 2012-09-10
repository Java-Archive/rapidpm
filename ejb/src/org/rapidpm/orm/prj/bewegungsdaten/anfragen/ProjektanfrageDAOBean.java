package org.rapidpm.orm.prj.bewegungsdaten.anfragen;

import org.apache.log4j.Logger;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.DaoFactoryBean;

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
