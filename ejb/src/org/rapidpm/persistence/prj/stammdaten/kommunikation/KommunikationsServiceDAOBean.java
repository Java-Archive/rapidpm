package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;

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
 * Date: 04.03.11
 * Time: 10:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "KommunikationsServiceDAOEJB")
@WebService(name = "KommunikationsServiceDAOWS")
public class KommunikationsServiceDAOBean {
    public KommunikationsServiceDAOBean() {
    }

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private KommunikationsServiceDAO getKommunikationsServiceDAO() {
        return daoFactoryBean.getKommunikationsServiceDAO();
    }

    private
    CRUDExecuter<FlatKommunikationsService, KommunikationsService, KommunikationsServiceResult>
            crudExecuter =
            new CRUDExecuter<FlatKommunikationsService, KommunikationsService, KommunikationsServiceResult>(KommunikationsServiceResult.class) {


                @Override
                protected KommunikationsService flatType2Type(final FlatKommunikationsService flatTypeEntity) {
                    final KommunikationsService type = new KommunikationsService();
                    type.setId(flatTypeEntity.getId());
                    type.setServiceName(flatTypeEntity.getServiceName());
                    return type;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected KommunikationsService findByID(final Long oid) {
                    return daoFactoryBean.getKommunikationsServiceDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadHandy")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadHandy(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceDAO().loadHandy());
    }

    public
    @WebMethod(operationName = "loadEmail")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadEmail(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceDAO().loadEmail());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadTele")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadTele(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceDAO().loadTele());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadFax")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadFax(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceDAO().loadFax());
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                               @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatKommunikationsService entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final KommunikationsService byID = getKommunikationsServiceDAO().findByID(oid);
        if (byID == null) {
            return new KommunikationsServiceResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getKommunikationsServiceDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadServicesForOrganisationseinheit")
    @WebResult(name = "KommunikationsServiceResult")
    KommunikationsServiceResult loadServicesForOrganisationseinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                                    @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                    @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID) {
        return create(getKommunikationsServiceDAO().loadServicesForOrganisationseinheit(organisationseinheitOID));
    }


    private KommunikationsServiceResult create(final KommunikationsService... services) {
        final KommunikationsServiceResult result = new KommunikationsServiceResult();
        for (KommunikationsService service : services) {
            result.getObjList().add(service);
        }
        return result;
    }

    private KommunikationsServiceResult create(final Collection<KommunikationsService> services) {
        final KommunikationsServiceResult result = new KommunikationsServiceResult();
        for (KommunikationsService service : services) {
            result.getObjList().add(service);
        }
        return result;
    }

    public static class FlatKommunikationsService extends BaseFlatEntity {
        private String serviceName;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatKommunikationsService)) {
                return false;
            }

            final FlatKommunikationsService that = (FlatKommunikationsService) o;

            if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return serviceName != null ? serviceName.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatKommunikationsService");
            sb.append("{serviceName='").append(serviceName).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(final String serviceName) {
            this.serviceName = serviceName;
        }
    }

    public static class KommunikationsServiceResult extends BaseOrmResult<KommunikationsService> {
        //        private List<KommunikationsService> services = new ArrayList<KommunikationsService>();
        //
        //        public List<KommunikationsService> getServices(){
        //            return services;
        //        }
        //
        //        public void setServices(final List<KommunikationsService> services){
        //            this.services = services;
        //        }

        public List<KommunikationsService> getObjList() {
            return objList;
        }

        public void setObjList(final List<KommunikationsService> objList) {
            this.objList = objList;
        }
    }


}
