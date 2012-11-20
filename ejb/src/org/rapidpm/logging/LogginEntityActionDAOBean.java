package org.rapidpm.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 18:01
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.logging.LogginEntityActionDAO;
import org.rapidpm.persistence.system.logging.LoggingEntityAction;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless(name = "LogginEntityActionDAOEJB")
@WebService(name = "LogginEntityActionDAOWS")
public class LogginEntityActionDAOBean {
    //private static final Logger logger = Logger.getLogger(LogginEntityActionDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;


    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatLogginEntityAction, LoggingEntityAction, LogginEntityActionResult> crudExecuter = new CRUDExecuter<FlatLogginEntityAction, LoggingEntityAction, LogginEntityActionResult>(LogginEntityActionResult.class) {
        @Override
        protected LoggingEntityAction flatType2Type(final FlatLogginEntityAction flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final LoggingEntityAction typeObj;
            if (id == null || id == -1) {
                typeObj = new LoggingEntityAction();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setAction(flatTypeEntity.getAction());
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected LoggingEntityAction findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private LogginEntityActionDAO getEntityDAO() {
        return daoFactoryBean.getLogginEntityActionDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "LogginEntityActionResult")
    LogginEntityActionResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatLogginEntityAction entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    @WebResult(name = "LogginEntityActionResult")
    @WebMethod(operationName = "removeTX")
    public
    LogginEntityActionResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "LogginEntityActionResult")
    LogginEntityActionResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final LoggingEntityAction byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new LogginEntityActionResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "LogginEntityActionResult")
    LogginEntityActionResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "LogginEntityActionResult")
    LogginEntityActionResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private LogginEntityActionResult createResult(final LoggingEntityAction... typeObj) {
        final LogginEntityActionResult result = new LogginEntityActionResult();
        for (final LoggingEntityAction obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private LogginEntityActionResult createResult(final Collection<LoggingEntityAction> typeObj) {
        final LogginEntityActionResult result = new LogginEntityActionResult();
        for (final LoggingEntityAction obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatLogginEntityAction extends BaseFlatEntity {
        private String action;

        public String getAction() {
            return action;
        }

        public void setAction(final String action) {
            this.action = action;
        }
    }

    public static class LogginEntityActionResult extends BaseOrmResult<LoggingEntityAction> {
        private List<LoggingEntityAction> objList = new ArrayList<>();

        public List<LoggingEntityAction> getObjList() {
            return objList;
        }

        public void setObjList(final List<LoggingEntityAction> objList) {
            this.objList = objList;
        }
    }


}


