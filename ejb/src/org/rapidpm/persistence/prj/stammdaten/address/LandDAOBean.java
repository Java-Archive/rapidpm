package org.rapidpm.persistence.prj.stammdaten.address;

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
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
 * User: sven.ruppert
 * Date: 03.03.11
 * Time: 21:28
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "LandDAOEJB")
@WebService(name = "LandDAOWS")
public class LandDAOBean {
    public LandDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private LandDAO getLandDAO() {
        return daoFactoryBean.getLandDAO();
    }

    private CRUDExecuter<FlatLand, Land, LandResult> crudExecuter = new CRUDExecuter<FlatLand, Land, LandResult>(LandResult.class) {
        @Override
        protected Land flatType2Type(final FlatLand flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Land l;
            if (id == null || id == -1 || id == 0) {
                l = new Land();
            } else {
                l = findByID(id);
            }

            l.setIsoCode(flatTypeEntity.getIsoCode());
            l.setName(flatTypeEntity.getName());
            return l;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Land findByID(final Long oid) {
            return daoFactoryBean.getLandDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadLandForIsoCode")
    @WebResult(name = "LandResult")
    LandResult loadLandForIsoCode(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "isoCode", mode = WebParam.Mode.IN) final String isoCode) {
        return create(getLandDAO().loadLandForIsoCode(isoCode));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "LandResult")
    LandResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatLand entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "LandResult")
    LandResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {

        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "LandResult")
    LandResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Land byID = daoFactoryBean.getLandDAO().findByID(oid);
        if (byID == null) {
            return new LandResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "LandResult")
    LandResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                               @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(daoFactoryBean.getLandDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "LandResult")
    LandResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(daoFactoryBean.getLandDAO().loadAllEntities());
    }


    private LandResult create(final Land... laender) {
        final LandResult result = new LandResult();
        for (Land land : laender) {
            result.getObjList().add(land);
        }
        return result;
    }

    private LandResult create(final Collection<Land> laender) {
        final LandResult result = new LandResult();
        for (Land land : laender) {
            result.getObjList().add(land);
        }
        return result;
    }

    public static class FlatLand extends BaseFlatEntity {
        private String isoCode;
        private String name;

        public String getIsoCode() {
            return isoCode;
        }

        public void setIsoCode(final String isoCode) {
            this.isoCode = isoCode;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

    }


    public static class LandResult extends BaseOrmResult<Land> {
        //        private List<Land> laender = new ArrayList<Land>();
        //
        //        public List<Land> getLaender(){
        //            return laender;
        //        }
        //
        //        public void setLaender(List<Land> laender){
        //            this.laender = laender;
        //        }

        public List<Land> getObjList() {
            return objList;
        }

        public void setObjList(final List<Land> objList) {
            this.objList = objList;
        }
    }

}
