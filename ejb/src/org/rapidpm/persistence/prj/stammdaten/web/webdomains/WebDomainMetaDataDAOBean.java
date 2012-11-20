package org.rapidpm.persistence.prj.stammdaten.web.webdomains; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.11.11
 * Time: 15:31
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainMetaData;
import org.rapidpm.persistence.prj.stammdaten.web.WebDomainMetaDataDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "WebDomainMetaDataDAOEJB")
@WebService(name = "WebDomainMetaDataDAOWS")
public class WebDomainMetaDataDAOBean {
    //private static final Logger logger = Logger.getLogger(WebDomainMetaDataDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatWebDomainMetaData, WebDomainMetaData, WebDomainMetaDataResult>
            crudExecuter =
            new CRUDExecuter<FlatWebDomainMetaData, WebDomainMetaData, WebDomainMetaDataResult>(WebDomainMetaDataResult.class) {
                @Override
                protected WebDomainMetaData flatType2Type(final FlatWebDomainMetaData flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final WebDomainMetaData typeObj;
                    if (id == null || id == -1) {
                        typeObj = new WebDomainMetaData();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setPropertyname(flatTypeEntity.getPropertyname());
                    typeObj.setPropertyvalue(flatTypeEntity.getPropertyvalue());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected WebDomainMetaData findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private WebDomainMetaDataDAO getEntityDAO() {
        return daoFactoryBean.getWebDomainMetaDataDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "WebDomainMetaDataResult")
    WebDomainMetaDataResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                           @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                           @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatWebDomainMetaData entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "WebDomainMetaDataResult")
    WebDomainMetaDataResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                     @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                     @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "WebDomainMetaDataResult")
    WebDomainMetaDataResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                     @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                     @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "WebDomainMetaDataResult")
    WebDomainMetaDataResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                            @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                            @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "WebDomainMetaDataResult")
    WebDomainMetaDataResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                            @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private WebDomainMetaDataResult createResult(final WebDomainMetaData... typeObj) {
        final WebDomainMetaDataResult result = new WebDomainMetaDataResult();
        for (final WebDomainMetaData obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private WebDomainMetaDataResult createResult(final Collection<WebDomainMetaData> typeObj) {
        final WebDomainMetaDataResult result = new WebDomainMetaDataResult();
        for (final WebDomainMetaData obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    //TODO anpassen
    private FlatWebDomainMetaData convertType2FlatType(final WebDomainMetaData t) {
        final FlatWebDomainMetaData ft = new FlatWebDomainMetaData();
        ft.setId(t.getId());
        ft.setPropertyname(t.getPropertyname());
        ft.setPropertyvalue(t.getPropertyvalue());

        return ft;
    }


    public static class FlatWebDomainMetaData extends BaseFlatEntity {
        private Long id;
        private String propertyname;
        private String propertyvalue;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatWebDomainMetaData");
            sb.append("{id=").append(id);
            sb.append(", propertyname='").append(propertyname).append('\'');
            sb.append(", propertyvalue='").append(propertyvalue).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getPropertyvalue() {
            return propertyvalue;
        }

        public void setPropertyvalue(String propertyvalue) {
            this.propertyvalue = propertyvalue;
        }

        public String getPropertyname() {
            return propertyname;
        }

        public void setPropertyname(final String propertyname) {
            this.propertyname = propertyname;
        }
    }

    public static class WebDomainMetaDataResult extends BaseOrmResult<FlatWebDomainMetaData> {
        //private List<WebDomainMetaData> objList = new ArrayList<WebDomainMetaData>();

        public List<FlatWebDomainMetaData> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatWebDomainMetaData> objList) {
            this.objList = objList;
        }
    }


}


