package org.rapidpm.orm.prj.stammdaten.organisationseinheit;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 19.12.11
 * Time: 09:41
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "OrganisationseinheitMetaDataDAOEJB")
@WebService(name = "OrganisationseinheitMetaDataDAOWS")
public class OrganisationseinheitMetaDataDAOBean {
    private static final Logger logger = Logger.getLogger(OrganisationseinheitMetaDataDAOBean.class);

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatOrganisationseinheitMetaData, OrganisationseinheitMetaData, OrganisationseinheitMetaDataResult>
            crudExecuter =
            new CRUDExecuter<FlatOrganisationseinheitMetaData, OrganisationseinheitMetaData, OrganisationseinheitMetaDataResult>(OrganisationseinheitMetaDataResult.class) {
                @Override
                protected OrganisationseinheitMetaData flatType2Type(final FlatOrganisationseinheitMetaData flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final OrganisationseinheitMetaData typeObj;
                    if (id == null || id == -1) {
                        typeObj = new OrganisationseinheitMetaData();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setMetaDataName(flatTypeEntity.getMetaDataName());
                    typeObj.setMetaDataValue(flatTypeEntity.getMetaDataValue());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected OrganisationseinheitMetaData findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private OrganisationseinheitMetaDataDAO getEntityDAO() {
        return daoFactoryBean.getOrganisationseinheitMetaDataDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "OrganisationseinheitMetaDataResult")
    OrganisationseinheitMetaDataResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                      @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatOrganisationseinheitMetaData entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "OrganisationseinheitMetaDataResult")
    OrganisationseinheitMetaDataResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "OrganisationseinheitMetaDataResult")
    OrganisationseinheitMetaDataResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "OrganisationseinheitMetaDataResult")
    OrganisationseinheitMetaDataResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "OrganisationseinheitMetaDataResult")
    OrganisationseinheitMetaDataResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private OrganisationseinheitMetaDataResult createResult(final OrganisationseinheitMetaData... typeObj) {
        final OrganisationseinheitMetaDataResult result = new OrganisationseinheitMetaDataResult();
        for (final OrganisationseinheitMetaData obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private OrganisationseinheitMetaDataResult createResult(final Collection<OrganisationseinheitMetaData> typeObj) {
        final OrganisationseinheitMetaDataResult result = new OrganisationseinheitMetaDataResult();
        for (final OrganisationseinheitMetaData obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    private FlatOrganisationseinheitMetaData convertType2FlatType(final OrganisationseinheitMetaData t) {
        final FlatOrganisationseinheitMetaData ft = new FlatOrganisationseinheitMetaData();
        ft.setId(t.getId());
        ft.setMetaDataName(t.getMetaDataName());
        ft.setMetaDataValue(t.getMetaDataValue());
        return ft;
    }


    public static class FlatOrganisationseinheitMetaData extends BaseFlatEntity {
        private Long id;
        private String metaDataName;
        private String metaDataValue;

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatOrganisationseinheitMetaData");
            sb.append("{id=").append(id);
            sb.append(", metaDataName='").append(metaDataName).append('\'');
            sb.append(", metaDataValue='").append(metaDataValue).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getMetaDataValue() {
            return metaDataValue;
        }

        public void setMetaDataValue(String metaDataValue) {
            this.metaDataValue = metaDataValue;
        }

        public String getMetaDataName() {
            return metaDataName;
        }

        public void setMetaDataName(final String metaDataName) {
            this.metaDataName = metaDataName;
        }
    }

    public static class OrganisationseinheitMetaDataResult extends BaseOrmResult<FlatOrganisationseinheitMetaData> {
        //private List<OrganisationseinheitMetaData> objList = new ArrayList<OrganisationseinheitMetaData>();

        public List<FlatOrganisationseinheitMetaData> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatOrganisationseinheitMetaData> objList) {
            this.objList = objList;
        }
    }


}


