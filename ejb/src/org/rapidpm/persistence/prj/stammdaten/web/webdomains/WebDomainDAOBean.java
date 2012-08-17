package org.rapidpm.persistence.prj.stammdaten.web.webdomains;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 14:33
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomain;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainDAO;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainKlassifizierung;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainMetaData;
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

@Stateless(name = "WebDomainDAOEJB")
@WebService(name = "WebDomainDAOWS")
public class WebDomainDAOBean {
    //private static final Logger logger = Logger.getLogger(WebDomainDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;


    private CRUDExecuter<FlatWebDomain, WebDomain, WebDomainResult> crudExecuter = new CRUDExecuter<FlatWebDomain, WebDomain, WebDomainResult>(WebDomainResult.class) {
        @Override
        protected WebDomain flatType2Type(final FlatWebDomain flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final WebDomain typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new WebDomain();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setActive(flatTypeEntity.isActive());
            typeObj.setBlocked(flatTypeEntity.isBlocked());
            //            typeObj.setCreated(flatTypeEntity.getCreated());
            typeObj.setWebDomainKlassifizierung(daoFactoryBean.getWebDomainKlassifizierungDAO().findByID(flatTypeEntity.getWebDomainKlassifizierungOID()));
            typeObj.setDomainName(flatTypeEntity.getDomainName());


            final List<Long> webDomainMetaDataOIDs = flatTypeEntity.getWebDomainMetaDataOIDs();
            typeObj.setWebDomainMetaDatas(daoFactoryBean.getWebDomainMetaDataDAO().loadWithOIDList(webDomainMetaDataOIDs));

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected WebDomain findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private WebDomainDAO getEntityDAO() {
        return daoFactoryBean.getWebDomainDAO();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWebDomainsForSuchmaschinenmodulAndTaetigkeitsfeld")
    @WebResult(name = "WebDomainResult")
    WebDomainResult loadWebDomainsFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "suchmaschinenmodul", mode = WebParam.Mode.IN) final String suchmaschinenmodul, @WebParam(name = "taetigkeitsfeld", mode = WebParam.Mode.IN) final String taetigkeitsfeld) {
        return createResult(getEntityDAO().loadWebDomainsFor(suchmaschinenmodul, taetigkeitsfeld));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWebDomainsForSuchmaschinenmodulName")
    @WebResult(name = "WebDomainResult")
    WebDomainResult loadWebDomainsFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "suchmaschinenmodul", mode = WebParam.Mode.IN) final String suchmaschinenmodul) {
        return createResult(getEntityDAO().loadWebDomainsFor(suchmaschinenmodul));
    }

//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "loadWebDomainsForSuchmaschinenmodul")
//    @WebResult(name = "WebDomainResult")
//    WebDomainResult loadWebDomainsFor(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                      @WebParam(name = "suchmaschinenmodulOID", mode = WebParam.Mode.IN) final Long suchmaschinenmodulOID) {
//
//
//        final Suchmodul byID = daoFactoryBean.getSuchmodulDAO().findByID(suchmaschinenmodulOID);     //REFAC DAO anpassen um mit der OID zu suchen
//        return createResult(getEntityDAO().loadWebDomainsFor(byID.getSuchmodulname()));
//    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "WebDomainResult")
    WebDomainResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                   @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatWebDomain entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "WebDomainResult")
    WebDomainResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "WebDomainResult")
    WebDomainResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final WebDomain byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new WebDomainResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "WebDomainResult")
    WebDomainResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                    @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "WebDomainResult")
    WebDomainResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private WebDomainResult createResult(final WebDomain... typeObj) {
        final WebDomainResult result = new WebDomainResult();
        for (final WebDomain obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private WebDomainResult createResult(final Collection<WebDomain> typeObj) {
        final WebDomainResult result = new WebDomainResult();
        for (final WebDomain obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private FlatWebDomain convertType2FlatType(final WebDomain t) {
        final FlatWebDomain ft = new FlatWebDomain();
        ft.setActive(t.getActive());
        ft.setBlocked(t.getBlocked());
        ft.setDomainName(t.getDomainName());
        ft.setId(t.getId());
        final WebDomainKlassifizierung webDomainKlassifizierung = t.getWebDomainKlassifizierung();
        if (webDomainKlassifizierung != null) {
            ft.setWebDomainKlassifizierungOID(webDomainKlassifizierung.getId());
        } else {
        }
        final List<WebDomainMetaData> webDomainMetaDatas = t.getWebDomainMetaDatas();
        if (webDomainMetaDatas != null) {
            for (final WebDomainMetaData webDomainMetaData : webDomainMetaDatas) {
                ft.getWebDomainMetaDataOIDs().add(webDomainMetaData.getId());
            }
        } else {
        }
        return ft;
    }


    public static class FlatWebDomain extends BaseFlatEntity {
        private String domainName;
        private boolean active;
        private boolean blocked;
        //        private Date created;
        //        private Date modified;
        private Long webDomainKlassifizierungOID;
        private List<Long> webDomainMetaDataOIDs = new ArrayList<>();

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(final String domainName) {
            this.domainName = domainName;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(final boolean active) {
            this.active = active;
        }

        public boolean isBlocked() {
            return blocked;
        }

        public void setBlocked(final boolean blocked) {
            this.blocked = blocked;
        }


        public Long getWebDomainKlassifizierungOID() {
            return webDomainKlassifizierungOID;
        }

        public void setWebDomainKlassifizierungOID(final Long webDomainKlassifizierungOID) {
            this.webDomainKlassifizierungOID = webDomainKlassifizierungOID;
        }

        public List<Long> getWebDomainMetaDataOIDs() {
            return webDomainMetaDataOIDs;
        }

        public void setWebDomainMetaDataOIDs(final List<Long> webDomainMetaDataOIDs) {
            this.webDomainMetaDataOIDs = webDomainMetaDataOIDs;
        }
    }

    public static class WebDomainResult extends BaseOrmResult<FlatWebDomain> {
        public List<FlatWebDomain> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatWebDomain> objList) {
            this.objList = objList;
        }
    }


}


