package org.rapidpm.persistence.prj.stammdaten.web.webdomains; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.11.11
 * Time: 15:35
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainKlassifizierung;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainKlassifizierungDAO;

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

@Stateless(name = "WebDomainKlassifizierungDAOEJB")
@WebService(name = "WebDomainKlassifizierungDAOWS")
public class WebDomainKlassifizierungDAOBean {
    //private static final Logger logger = Logger.getLogger(WebDomainKlassifizierungDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatWebDomainKlassifizierung, WebDomainKlassifizierung, WebDomainKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatWebDomainKlassifizierung, WebDomainKlassifizierung, WebDomainKlassifizierungResult>(WebDomainKlassifizierungResult.class) {
                @Override
                protected WebDomainKlassifizierung flatType2Type(final FlatWebDomainKlassifizierung flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final WebDomainKlassifizierung typeObj;
                    if (id == null || id == -1) {
                        typeObj = new WebDomainKlassifizierung();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setKategorie(flatTypeEntity.getKlassifizierung());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected WebDomainKlassifizierung findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private WebDomainKlassifizierungDAO getEntityDAO() {
        return daoFactoryBean.getWebDomainKlassifizierungDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "WebDomainKlassifizierungResult")
    WebDomainKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                  @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                  @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatWebDomainKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "WebDomainKlassifizierungResult")
    WebDomainKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                            @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "WebDomainKlassifizierungResult")
    WebDomainKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                            @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "WebDomainKlassifizierungResult")
    WebDomainKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                   @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                   @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "WebDomainKlassifizierungResult")
    WebDomainKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                   @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private WebDomainKlassifizierungResult createResult(final WebDomainKlassifizierung... typeObj) {
        final WebDomainKlassifizierungResult result = new WebDomainKlassifizierungResult();
        for (final WebDomainKlassifizierung obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private WebDomainKlassifizierungResult createResult(final Collection<WebDomainKlassifizierung> typeObj) {
        final WebDomainKlassifizierungResult result = new WebDomainKlassifizierungResult();
        for (final WebDomainKlassifizierung obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    //TODO anpassen
    private FlatWebDomainKlassifizierung convertType2FlatType(final WebDomainKlassifizierung t) {
        final FlatWebDomainKlassifizierung ft = new FlatWebDomainKlassifizierung();
        ft.setId(t.getId());
        ft.setKlassifizierung(t.getKategorie());

        return ft;
    }


    public static class FlatWebDomainKlassifizierung extends BaseFlatEntity {
        private Long id;
        private String klassifizierung;

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }
    }

    public static class WebDomainKlassifizierungResult extends BaseOrmResult<FlatWebDomainKlassifizierung> {
        //private List<WebDomainKlassifizierung> objList = new ArrayList<WebDomainKlassifizierung>();

        public List<FlatWebDomainKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatWebDomainKlassifizierung> objList) {
            this.objList = objList;
        }
    }


}


