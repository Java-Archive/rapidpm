package org.rapidpm.orm.security;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.DaoFactoryBean;
import org.rapidpm.orm.prj.bewegungsdaten.RegistrationStatus;
import org.rapidpm.orm.prj.bewegungsdaten.RegistrationStatusDAO;

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
 * Date: 07.03.11
 * Time: 12:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "RegistrationStatusDAOEJB")
@WebService(name = "RegistrationStatusDAOWS")
public class RegistrationStatusDAOBean {
    public RegistrationStatusDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private RegistrationStatusDAO getRegistrationStatusDAO() {
        return daoFactoryBean.getRegistrationStatusDAO();
    }

    final CRUDExecuter<FlatRegistrationsStatus, RegistrationStatus, RegistrationStatusResult> executer = new CRUDExecuter<FlatRegistrationsStatus, RegistrationStatus, RegistrationStatusResult>(RegistrationStatusResult.class) {
        @Override
        protected RegistrationStatus flatType2Type(final FlatRegistrationsStatus flatTypeEntity) {
            final RegistrationStatus s = new RegistrationStatus();
            s.setId(flatTypeEntity.getId());
            s.setStatus(flatTypeEntity.getStatus());
            return s;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected RegistrationStatus findByID(final Long oid) {
            return daoFactoryBean.getRegistrationStatusDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<RegistrationStatus> list = getRegistrationStatusDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadRegistrationStatus_Zur_Pruefung")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadRegistrationStatus_Zur_Pruefung(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationStatusDAO().loadRegistrationStatus_Zur_Pruefung());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "load")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult load(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "status", mode = WebParam.Mode.IN) final String txt) {
        return createResult(getRegistrationStatusDAO().load(txt));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadRegistrationStatus_abgelehnt")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadRegistrationStatus_abgelehnt(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationStatusDAO().loadRegistrationStatus_abgelehnt());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadRegistrationStatus_freigeschaltet")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadRegistrationStatus_freigeschaltet(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationStatusDAO().loadRegistrationStatus_freigeschaltet());
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatRegistrationsStatus entity) {
        //        final RegistrationStatusResult result = new RegistrationStatusResult();
        //        daoFactoryBean.saveOrUpdate(entity);
        //        final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "saveOrUpdateTX", sessionid, "save OK");
        //        result.getLoggingEventEntries().add(eventEntry);
        //        return result;
        return executer.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        //        final RegistrationStatusResult result = new RegistrationStatusResult();
        //        final Registration byID = daoFactoryBean.getRegistrationDAO().findByID(oid);
        //        if(byID != null){
        //            daoFactoryBean.remove(byID);
        //            final LoggingEventEntry eventEntry = logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove OK");
        //            result.getLoggingEventEntries().add(eventEntry);
        //        }else{
        //
        //            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "oid falsch"));
        //            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO, "removeTX", sessionid, "remove FAILED"));
        //        }
        //        return result;
        return executer.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final RegistrationStatus byID = getRegistrationStatusDAO().findByID(oid);
        if (byID == null) {
            return new RegistrationStatusResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getRegistrationStatusDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "RegistrationStatusResult")
    RegistrationStatusResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getRegistrationStatusDAO().loadAllEntities());
    }

    private RegistrationStatusResult createResult(final RegistrationStatus rs) {
        final RegistrationStatusResult result = new RegistrationStatusResult();
        if (rs != null) {
            result.getObjList().add(rs);
        } else {

        }
        return result;
    }

    private RegistrationStatusResult createResult(final Collection<RegistrationStatus> statusliste) {
        final RegistrationStatusResult result = new RegistrationStatusResult();
        result.getObjList().addAll(statusliste);
        return result;
    }

    public static class FlatRegistrationsStatus extends BaseFlatEntity {
        private String status;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatRegistrationsStatus)) {
                return false;
            }

            final FlatRegistrationsStatus that = (FlatRegistrationsStatus) o;

            if (status != null ? !status.equals(that.status) : that.status != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return status != null ? status.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatRegistrationsStatus");
            sb.append("{status='").append(status).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(final String status) {
            this.status = status;
        }
    }


    public static class RegistrationStatusResult extends BaseOrmResult<RegistrationStatus> {
        //        private List<RegistrationStatus> statusliste = new ArrayList<RegistrationStatus>();
        //
        //        public List<RegistrationStatus> getStatusliste(){
        //            return statusliste;
        //        }
        //
        //        public void setStatusliste(final List<RegistrationStatus> statusliste){
        //            this.statusliste = statusliste;
        //        }

        public List<RegistrationStatus> getObjList() {
            return objList;
        }

        public void setObjList(final List<RegistrationStatus> objList) {
            this.objList = objList;
        }
    }
}
