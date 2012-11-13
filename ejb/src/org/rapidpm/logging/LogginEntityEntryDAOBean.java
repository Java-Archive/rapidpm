package org.rapidpm.logging; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 30.05.11
 * Time: 17:40
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.logging.LogginEntityEntryDAO;
import org.rapidpm.persistence.system.logging.LoggingEntityEntry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "LogginEntityEntryDAOEJB")
@WebService(name = "LogginEntityEntryDAOWS")
public class LogginEntityEntryDAOBean {
    //private static final Logger logger = Logger.getLogger(LogginEntityEntryDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatLogginEntityEntry, LoggingEntityEntry, LogginEntityEntryResult> crudExecuter = new CRUDExecuter<FlatLogginEntityEntry, LoggingEntityEntry, LogginEntityEntryResult>(LogginEntityEntryResult.class) {
        @Override
        protected LoggingEntityEntry flatType2Type(final FlatLogginEntityEntry flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final LoggingEntityEntry typeObj;
            if (id == null || id == -1) {
                typeObj = new LoggingEntityEntry();
            } else {
                typeObj = findByID(id);
            }
            //            typeObj.setMetaDataName(flatTypeEntity.getMetaDataName());
            throw new IllegalStateException("Save darf im LogginEntityEntryWS nicht verwendet werden..");
            //            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected LoggingEntityEntry findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private LogginEntityEntryDAO getEntityDAO() {
        return daoFactoryBean.getLogginEntityEntryDAO();
    }


    //
    //    public
    //    @WebMethod(operationName = "saveOrUpdateTX")
    //    @WebResult(name = "LogginEntityEntryResult")
    //    LogginEntityEntryResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
    //                                           @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatLogginEntityEntry entity){
    //
    //        return crudExecuter.saveOrUpdate(sessionid, entity);
    //    }

    //
    //    public
    //    @WebMethod(operationName = "removeTX")
    //    @WebResult(name = "LogginEntityEntryResult")
    //    LogginEntityEntryResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid){
    //        return crudExecuter.remove(sessionid, oid);
    //    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "LogginEntityEntryResult")
    LogginEntityEntryResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final LoggingEntityEntry byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new LogginEntityEntryResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "LogginEntityEntryResult")
    LogginEntityEntryResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "LogginEntityEntryResult")
    LogginEntityEntryResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private LogginEntityEntryResult createResult(final LoggingEntityEntry... typeObj) {
        final LogginEntityEntryResult result = new LogginEntityEntryResult();
        for (final LoggingEntityEntry obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private LogginEntityEntryResult createResult(final Collection<LoggingEntityEntry> typeObj) {
        final LogginEntityEntryResult result = new LogginEntityEntryResult();
        for (final LoggingEntityEntry obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatLogginEntityEntry extends BaseFlatEntity {

    }

    public static class LogginEntityEntryResult extends BaseOrmResult<LoggingEntityEntry> {
        //        private List<LoggingEntityEntry> objList = new ArrayList<LoggingEntityEntry>();
        //
        //        public List<LoggingEntityEntry> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<LoggingEntityEntry> objList){
        //            this.objList = objList;
        //        }

        public List<LoggingEntityEntry> getObjList() {
            return objList;
        }

        public void setObjList(final List<LoggingEntityEntry> objList) {
            this.objList = objList;
        }
    }


}


