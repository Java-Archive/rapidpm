package org.rapidpm.orm.security.berechtigungen;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.orm.system.security.berechtigungen.Berechtigung;
import org.rapidpm.orm.system.security.berechtigungen.BerechtigungDAO;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 02:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateless(name = "BerechtigungDAOEJB")
@WebService(name = "BerechtigungDAOWS")
public class BerechtigungDAOBean {
    public BerechtigungDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;


    private CRUDExecuter<FlatBerechtigung, Berechtigung, BerechtigungResult> crudExecuter = new CRUDExecuter<FlatBerechtigung, Berechtigung, BerechtigungResult>(BerechtigungResult.class) {
        @Override
        protected Berechtigung flatType2Type(final FlatBerechtigung flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Berechtigung typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Berechtigung();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setName(flatTypeEntity.getName());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Berechtigung findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };


    private BerechtigungDAO getEntityDAO() {
        return daoFactoryBean.getBerechtigungDAO();
    }

    //    @Interceptors(LoggingInterceptor.class)
    //    public
    //    @WebMethod(operationName = "saveOrUpdateTX")
    //    @WebResult(name = "BerechtigungResult")
    //    BerechtigungResult loadBerechtigung(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
    //                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
    //                                        @WebParam(name = "name", mode = WebParam.Mode.IN) final String name){
    //        return createResult(getEntityDAO().loadBerechtigung(name));
    //    }

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<Berechtigung> list = getEntityDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBerechtigung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Berechtigung byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new BerechtigungResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BerechtigungResult")
    BerechtigungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private BerechtigungResult createResult(final Berechtigung... typeObj) {
        final BerechtigungResult result = new BerechtigungResult();
        for (final Berechtigung berechtigung : typeObj) {
            result.getObjList().add(berechtigung);
        }
        return result;
    }

    private BerechtigungResult createResult(final Collection<Berechtigung> typeObj) {
        final BerechtigungResult result = new BerechtigungResult();
        for (final Berechtigung berechtigung : typeObj) {
            result.getObjList().add(berechtigung);
        }
        return result;
    }


    public static class FlatBerechtigung extends BaseFlatEntity {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    public static class BerechtigungResult extends BaseOrmResult<Berechtigung> {
        //        private List<Berechtigung> berechtigungList = new ArrayList<Berechtigung>();
        //
        //        public List<Berechtigung> getBerechtigungList(){
        //            return berechtigungList;
        //        }
        //
        //        public void setBerechtigungList(final List<Berechtigung> berechtigungList){
        //            this.berechtigungList = berechtigungList;
        //        }

        public List<Berechtigung> getObjList() {
            return objList;
        }

        public void setObjList(final List<Berechtigung> objList) {
            this.objList = objList;
        }
    }

}
