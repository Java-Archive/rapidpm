package org.rapidpm.persistence.security;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.BenutzerWebapplikationDAO;
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
 * Date: 21.02.11
 * Time: 17:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateless(name = "BenutzerWebapplikationDAOEJB")
@WebService(name = "BenutzerWebapplikationDAOWS")
public class BenutzerWebapplikationDAOBean {
    public BenutzerWebapplikationDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;
    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatBenutzerWebapplikation, BenutzerWebapplikation, BenutzerWebapplikationResult>
            crudExecuter =
            new CRUDExecuter<FlatBenutzerWebapplikation, BenutzerWebapplikation, BenutzerWebapplikationResult>(BenutzerWebapplikationResult.class) {

                @Override
                protected BenutzerWebapplikation flatType2Type(final FlatBenutzerWebapplikation flatTypeEntity) {
                    final BenutzerWebapplikation type = new BenutzerWebapplikation();
                    type.setId(flatTypeEntity.getId());
                    type.setWebappName(flatTypeEntity.getWebappName());

                    return type;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected BenutzerWebapplikation findByID(final Long oid) {
                    return daoFactoryBean.getBenutzerWebapplikationDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private BenutzerWebapplikationResult createResult(final BenutzerWebapplikation benutzerWebapplikation) {
        final BenutzerWebapplikationResult result = new BenutzerWebapplikationResult();
        if (benutzerWebapplikation != null) {
            result.getObjList().add(benutzerWebapplikation);
        } else {

        }
        return result;
    }

    private BenutzerWebapplikationResult createResult(final Collection<BenutzerWebapplikation> liste) {
        final BenutzerWebapplikationResult result = new BenutzerWebapplikationResult();
        result.getObjList().addAll(liste);
        return result;
    }

    private BenutzerWebapplikationDAO getBenutzerWebapplikationDAO() {
        return daoFactoryBean.getBenutzerWebapplikationDAO();
    }


    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<BenutzerWebapplikation> list = getBenutzerWebapplikationDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @WebMethod(operationName = "loadBenutzerWebapplikation")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult loadBenutzerWebapplikation(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                            @WebParam(name = "name", mode = WebParam.Mode.IN) final String name) {
        return createResult(getBenutzerWebapplikationDAO().loadBenutzerWebapplikation(name));
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBenutzerWebapplikation entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {

        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BenutzerWebapplikation byID = getBenutzerWebapplikationDAO().findByID(oid);
        if (byID == null) {
            return new BenutzerWebapplikationResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getBenutzerWebapplikationDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BenutzerWebapplikationResult")
    BenutzerWebapplikationResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getBenutzerWebapplikationDAO().loadAllEntities());
    }

    public static class FlatBenutzerWebapplikation extends BaseFlatEntity {
        private String webappName;


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatBenutzerWebapplikation");
            sb.append("{webAppname='").append(webappName).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getWebappName() {
            return webappName;
        }

        public void setWebappName(final String webappName) {
            this.webappName = webappName;
        }
    }

    public static class BenutzerWebapplikationResult extends BaseOrmResult<BenutzerWebapplikation> {

        //        private List<BenutzerWebapplikation> benutzerWebapplikationen = new ArrayList<BenutzerWebapplikation>();
        //
        //        public List<BenutzerWebapplikation> getBenutzerWebapplikationen(){
        //            return benutzerWebapplikationen;
        //        }
        //
        //        public void setBenutzerWebapplikationen(List<BenutzerWebapplikation> benutzerWebapplikationen){
        //            this.benutzerWebapplikationen = benutzerWebapplikationen;
        //        }

        public List<BenutzerWebapplikation> getObjList() {
            return objList;
        }

        public void setObjList(final List<BenutzerWebapplikation> objList) {
            this.objList = objList;
        }
    }

}
