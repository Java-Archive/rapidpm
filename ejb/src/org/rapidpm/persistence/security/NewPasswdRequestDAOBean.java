/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.security; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 03:17
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.system.security.NewPasswdRequest;
import org.rapidpm.persistence.system.security.NewPasswdRequestDAO;

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

@Stateless(name = "NewPasswdRequestDAOEJB")
@WebService(name = "NewPasswdRequestDAOWS")
public class NewPasswdRequestDAOBean {
    //private static final Logger logger = Logger.getLogger(NewPasswdRequestDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatNewPasswdRequest, NewPasswdRequest, NewPasswdRequestResult> crudExecuter = new CRUDExecuter<FlatNewPasswdRequest, NewPasswdRequest, NewPasswdRequestResult>(NewPasswdRequestResult.class) {
        @Override
        protected NewPasswdRequest flatType2Type(final FlatNewPasswdRequest flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final NewPasswdRequest typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new NewPasswdRequest();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setMandantengruppe(daoFactoryBean.getMandantengruppeDAO().findByID(flatTypeEntity.getMandantengruppeOID()));
            typeObj.setEmail(flatTypeEntity.getEmail());
            typeObj.setNachname(flatTypeEntity.getNachname());
            typeObj.setVorname(flatTypeEntity.getVorname());
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected NewPasswdRequest findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private NewPasswdRequestDAO getEntityDAO() {
        return daoFactoryBean.getNewPasswdRequestDAO();
    }

    @WebMethod(operationName = "loadRevisionFor")
    @Interceptors(LoggingInterceptor.class)
    public
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult loadRevisionFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                           @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final List<NewPasswdRequest> list = getEntityDAO().loadAllRevisionsFor(oid);
        return createResult(list);
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatNewPasswdRequest entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final NewPasswdRequest byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new NewPasswdRequestResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                           @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "NewPasswdRequestResult")
    NewPasswdRequestResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private NewPasswdRequestResult createResult(final NewPasswdRequest... typeObj) {
        final NewPasswdRequestResult result = new NewPasswdRequestResult();
        for (final NewPasswdRequest obj : typeObj) {
            final FlatNewPasswdRequest flatNewPasswdRequest = type2FlatType(obj);
            result.getObjList().add(flatNewPasswdRequest);
        }
        return result;
    }

    private NewPasswdRequestResult createResult(final Collection<NewPasswdRequest> typeObj) {
        final NewPasswdRequestResult result = new NewPasswdRequestResult();
        for (final NewPasswdRequest obj : typeObj) {
            final FlatNewPasswdRequest flatNewPasswdRequest = type2FlatType(obj);
            result.getObjList().add(flatNewPasswdRequest);
        }
        return result;
    }

    private FlatNewPasswdRequest type2FlatType(final NewPasswdRequest n) {
        final FlatNewPasswdRequest f = new FlatNewPasswdRequest();
        f.setEmail(n.getEmail());
        f.setMandantengruppeOID(n.getMandantengruppe().getId());
        f.setId(n.getId());
        f.setNachname(n.getNachname());
        f.setVorname(n.getVorname());

        //        final List<PasswdRequestHistory> passwdRequestHistories = n.getPasswdRequestHistories();
        //        for(final PasswdRequestHistory passwdRequestHistory : passwdRequestHistories){
        //            f.getPasswdRequestHistorieOIDs().add(passwdRequestHistory.getId());
        //        }

        return f;
    }


    public static class FlatNewPasswdRequest extends BaseFlatEntity {
        private String vorname;
        private String nachname;
        private String email;
        //        private List<Long> passwdRequestHistorieOIDs = new ArrayList<Long>();
        private Long mandantengruppeOID;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatNewPasswdRequest");
            sb.append("{email='").append(email).append('\'');
            sb.append(", vorname='").append(vorname).append('\'');
            sb.append(", nachname='").append(nachname).append('\'');
            //            sb.append(", passwdRequestHistorieOIDs=").append(passwdRequestHistorieOIDs);
            sb.append(", mandantengruppeOID=").append(mandantengruppeOID);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatNewPasswdRequest)) {
                return false;
            }

            final FlatNewPasswdRequest that = (FlatNewPasswdRequest) o;

            if (email != null ? !email.equals(that.email) : that.email != null) {
                return false;
            }
            if (mandantengruppeOID != null ? !mandantengruppeOID.equals(that.mandantengruppeOID) : that.mandantengruppeOID != null) {
                return false;
            }
            if (nachname != null ? !nachname.equals(that.nachname) : that.nachname != null) {
                return false;
            }
            //            if(passwdRequestHistorieOIDs != null ? !passwdRequestHistorieOIDs.equals(that.passwdRequestHistorieOIDs) : that.passwdRequestHistorieOIDs != null){
            //                return false;
            //            }
            if (vorname != null ? !vorname.equals(that.vorname) : that.vorname != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = vorname != null ? vorname.hashCode() : 0;
            result = 31 * result + (nachname != null ? nachname.hashCode() : 0);
            result = 31 * result + (email != null ? email.hashCode() : 0);
            //            result = 31 * result + (passwdRequestHistorieOIDs != null ? passwdRequestHistorieOIDs.hashCode() : 0);
            result = 31 * result + (mandantengruppeOID != null ? mandantengruppeOID.hashCode() : 0);
            return result;
        }

        //        public List<Long> getPasswdRequestHistorieOIDs(){
        //            return passwdRequestHistorieOIDs;
        //        }
        //
        //        public void setPasswdRequestHistorieOIDs(final List<Long> passwdRequestHistorieOIDs){
        //            this.passwdRequestHistorieOIDs = passwdRequestHistorieOIDs;
        //        }

        public String getVorname() {
            return vorname;
        }

        public void setVorname(final String vorname) {
            this.vorname = vorname;
        }

        public String getNachname() {
            return nachname;
        }

        public void setNachname(final String nachname) {
            this.nachname = nachname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(final String email) {
            this.email = email;
        }

        public Long getMandantengruppeOID() {
            return mandantengruppeOID;
        }

        public void setMandantengruppeOID(final Long mandantengruppeOID) {
            this.mandantengruppeOID = mandantengruppeOID;
        }
    }

    public static class NewPasswdRequestResult extends BaseOrmResult<FlatNewPasswdRequest> {
        //        private List<FlatNewPasswdRequest> objList = new ArrayList<FlatNewPasswdRequest>();
        //
        //        public List<FlatNewPasswdRequest> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<FlatNewPasswdRequest> objList){
        //            this.objList = objList;
        //        }

        public List<FlatNewPasswdRequest> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatNewPasswdRequest> objList) {
            this.objList = objList;
        }
    }


}


