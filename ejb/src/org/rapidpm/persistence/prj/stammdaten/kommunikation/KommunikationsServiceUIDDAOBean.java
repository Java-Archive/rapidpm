/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.data.LoggingResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 04.03.11
 * Time: 12:11
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
@Stateless(name = "KommunikationsServiceUIDDAOEJB")
@WebService(name = "KommunikationsServiceUIDDAOWS")
public class KommunikationsServiceUIDDAOBean {
    public KommunikationsServiceUIDDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private KommunikationsServiceUIDDAO getKommunikationServiceUIDDAO() {
        return daoFactoryBean.getKommunikationServiceUIDDAO();
    }

    private CRUDExecuter<FlatKommunikationsServiceUID, KommunikationsServiceUID, KommunikationsServiceUIDResult> crudExecuter = new CRUDExecuter<FlatKommunikationsServiceUID, KommunikationsServiceUID, KommunikationsServiceUIDResult>(
            KommunikationsServiceUIDResult.class) {
        @Override
        protected KommunikationsServiceUID flatType2Type(final FlatKommunikationsServiceUID flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final KommunikationsServiceUID ksuid;
            if (id == null || id == -1 || id == 0) {
                ksuid = new KommunikationsServiceUID();
            } else {
                ksuid = findByID(id);
            }

            //            ksuid.setCreated(flatTypeEntity.getCreated());
            //            ksuid.setModified(flatTypeEntity.getModified());
            ksuid.setService(daoFactoryBean.getKommunikationsServiceDAO().findByID(flatTypeEntity.getKommunikationsServiceOID()));
            ksuid.setKlassifizierung(daoFactoryBean.getKommunikationsServiceKlassifizierungDAO().findByID(flatTypeEntity.getKommunikationsServiceKlassifizierungOID()));
            ksuid.setUidparts(daoFactoryBean.getKommunikationServiceUIDPartDAO().loadWithOIDList(flatTypeEntity.getKommunikationsServiceUIDPartOIDs()));
            return ksuid;

        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected KommunikationsServiceUID findByID(final Long oid) {
            return daoFactoryBean.getKommunikationServiceUIDDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult saveOrUpdate(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatKommunikationsServiceUID entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);

    }

    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult remove(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {

        return crudExecuter.remove(sessionid, uid, oid);
    }

    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final KommunikationsServiceUID byID = getKommunikationServiceUIDDAO().findByID(oid);
        if (byID == null) {
            return new KommunikationsServiceUIDResult();
        } else {
            return create(byID);
        }
    }

    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getKommunikationServiceUIDDAO().loadWithOIDList(oids));
    }

    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationServiceUIDDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadServiceUIDsForOrganisationseinheit")
    @WebResult(name = "KommunikationsServiceUIDResult")
    KommunikationsServiceUIDResult loadServiceUIDsForOrganisationseinheit(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                          @WebParam(name = "organisationseinheitOID", mode = WebParam.Mode.IN) final Long organisationseinheitOID) {
        return create(getKommunikationServiceUIDDAO().loadServiceUIDsForOrganisationseinheit(organisationseinheitOID));
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "isEmailRegistered")
    @WebResult(name = "IsEmailRegisteredResult")
    IsEmailRegisteredResult isEmailRegistered(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "email", mode = WebParam.Mode.IN) final String email) {
        final IsEmailRegisteredResult result = new IsEmailRegisteredResult();
        result.setRegistered(getKommunikationServiceUIDDAO().isEmailRegistered(email));
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "isEmailRegisteredForMandantengruppeOID")
    @WebResult(name = "IsEmailRegisteredResult")
    IsEmailRegisteredResult isEmailRegistered(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "email", mode = WebParam.Mode.IN) final String email,
                                              @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long mandantengruppeOID) {
        final IsEmailRegisteredResult result = new IsEmailRegisteredResult();
        result.setRegistered(getKommunikationServiceUIDDAO().isEmailRegistered(email, mandantengruppeOID));
        return result;
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "isEmailRegisteredForMandantengruppe")
    @WebResult(name = "IsEmailRegisteredResult")
    IsEmailRegisteredResult isEmailRegistered(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "email", mode = WebParam.Mode.IN) final String email,
                                              @WebParam(name = "mandantengruppe", mode = WebParam.Mode.IN) final String mandantengruppe) {
        final IsEmailRegisteredResult result = new IsEmailRegisteredResult();
        result.setRegistered(getKommunikationServiceUIDDAO().isEmailRegistered(email, mandantengruppe));
        return result;
    }


    private KommunikationsServiceUIDDAOBean.KommunikationsServiceUIDResult create(final KommunikationsServiceUID... serviceUIDs) {
        final KommunikationsServiceUIDResult result = new KommunikationsServiceUIDDAOBean.KommunikationsServiceUIDResult();
        for (KommunikationsServiceUID klassifikation : serviceUIDs) {
            result.getObjList().add(convertType2FlatType(klassifikation));
        }
        return result;
    }

    private KommunikationsServiceUIDDAOBean.KommunikationsServiceUIDResult create(final Collection<KommunikationsServiceUID> uids) {
        final KommunikationsServiceUIDResult result = new KommunikationsServiceUIDDAOBean.KommunikationsServiceUIDResult();
        for (KommunikationsServiceUID klassifikation : uids) {
            result.getObjList().add(convertType2FlatType(klassifikation));
        }
        return result;
    }

    private FlatKommunikationsServiceUID convertType2FlatType(final KommunikationsServiceUID t) {
        final FlatKommunikationsServiceUID ft = new FlatKommunikationsServiceUID();
        ft.setId(t.getId());
        ft.setKommunikationsServiceKlassifizierungOID(t.getKlassifizierung().getId());
        ft.setKommunikationsServiceOID(t.getService().getId());


        final List<KommunikationsServiceUIDPart> uidparts = t.getUidparts();
        for (final KommunikationsServiceUIDPart uidpart : uidparts) {
            ft.getKommunikationsServiceUIDPartOIDs().add(uidpart.getId());
        }
        return ft;
    }


    public static class FlatKommunikationsServiceUID extends BaseFlatEntity {

        private List<Long> kommunikationsServiceUIDPartOIDs = new ArrayList<>();
        private Long kommunikationsServiceKlassifizierungOID;
        private Long kommunikationsServiceOID;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatKommunikationsServiceUID");
            sb.append("{kommunikationsServiceKlassifizierungOID=").append(kommunikationsServiceKlassifizierungOID);
            sb.append(", kommunikationsServiceUIDPartOIDs=").append(kommunikationsServiceUIDPartOIDs);
            sb.append(", kommunikationsServiceOID=").append(kommunikationsServiceOID);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FlatKommunikationsServiceUID)) return false;

            FlatKommunikationsServiceUID that = (FlatKommunikationsServiceUID) o;

            if (!kommunikationsServiceKlassifizierungOID.equals(that.kommunikationsServiceKlassifizierungOID))
                return false;
            if (!kommunikationsServiceOID.equals(that.kommunikationsServiceOID)) return false;
            if (!kommunikationsServiceUIDPartOIDs.equals(that.kommunikationsServiceUIDPartOIDs)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = kommunikationsServiceUIDPartOIDs.hashCode();
            result = 31 * result + kommunikationsServiceKlassifizierungOID.hashCode();
            result = 31 * result + kommunikationsServiceOID.hashCode();
            return result;
        }

        public Long getKommunikationsServiceKlassifizierungOID() {
            return kommunikationsServiceKlassifizierungOID;
        }

        public void setKommunikationsServiceKlassifizierungOID(Long kommunikationsServiceKlassifizierungOID) {
            this.kommunikationsServiceKlassifizierungOID = kommunikationsServiceKlassifizierungOID;
        }

        public Long getKommunikationsServiceOID() {
            return kommunikationsServiceOID;
        }

        public void setKommunikationsServiceOID(Long kommunikationsServiceOID) {
            this.kommunikationsServiceOID = kommunikationsServiceOID;
        }

        public List<Long> getKommunikationsServiceUIDPartOIDs() {
            return kommunikationsServiceUIDPartOIDs;
        }

        public void setKommunikationsServiceUIDPartOIDs(List<Long> kommunikationsServiceUIDPartOIDs) {
            this.kommunikationsServiceUIDPartOIDs = kommunikationsServiceUIDPartOIDs;
        }
    }

    public static class KommunikationsServiceUIDResult extends BaseOrmResult<FlatKommunikationsServiceUID> {
        //        private List<KommunikationsServiceUID> uids = new ArrayList<KommunikationsServiceUID>();
        //
        //        public List<KommunikationsServiceUID> getUids(){
        //            return uids;
        //        }
        //
        //        public void setUids(final List<KommunikationsServiceUID> uids){
        //            this.uids = uids;
        //        }

        public List<FlatKommunikationsServiceUID> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatKommunikationsServiceUID> objList) {
            this.objList = objList;
        }
    }

    public static class IsEmailRegisteredResult extends LoggingResult {
        private Boolean registered;

        public Boolean getRegistered() {
            return registered;
        }

        public void setRegistered(final Boolean registered) {
            this.registered = registered;
        }
    }


}
