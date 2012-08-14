package org.rapidpm.orm.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 03:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
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
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "BrancheDAOEJB")
@WebService(name = "BrancheDAOWS")
public class BrancheDAOBean {
    //private static final Logger logger = Logger.getLogger(BrancheDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatBranche, Branche, BrancheResult> crudExecuter = new CRUDExecuter<FlatBranche, Branche, BrancheResult>(BrancheResult.class) {
        @Override
        protected Branche flatType2Type(final FlatBranche flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Branche typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Branche();

            } else {
                typeObj = findByID(id);
            }
            typeObj.setBeschreibung(flatTypeEntity.getBeschreibung());
            typeObj.setBranchenSchluessel(flatTypeEntity.getBranchenSchluessel());
            typeObj.setWZVersion(flatTypeEntity.getWZVersion());
            typeObj.setISIC(flatTypeEntity.getISIC());
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Branche findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private BrancheDAO getEntityDAO() {
        return daoFactoryBean.getBrancheDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BrancheResult")
    BrancheResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBranche entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BrancheResult")
    BrancheResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BrancheResult")
    BrancheResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Branche byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new BrancheResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BrancheResult")
    BrancheResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BrancheResult")
    BrancheResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private BrancheResult createResult(final Branche... typeObj) {
        final BrancheResult result = new BrancheResult();
        for (final Branche obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private BrancheResult createResult(final Collection<Branche> typeObj) {
        final BrancheResult result = new BrancheResult();
        for (final Branche obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatBranche extends BaseFlatEntity {
        private String branchenSchluessel;
        private String WZVersion;
        private String beschreibung;
        private String ISIC;

        public String getBranchenSchluessel() {
            return branchenSchluessel;
        }

        public void setBranchenSchluessel(final String branchenSchluessel) {
            this.branchenSchluessel = branchenSchluessel;
        }

        public String getWZVersion() {
            return WZVersion;
        }

        public void setWZVersion(final String WZVersion) {
            this.WZVersion = WZVersion;
        }

        public String getBeschreibung() {
            return beschreibung;
        }

        public void setBeschreibung(final String beschreibung) {
            this.beschreibung = beschreibung;
        }

        public String getISIC() {
            return ISIC;
        }

        public void setISIC(final String ISIC) {
            this.ISIC = ISIC;
        }
    }

    public static class BrancheResult extends BaseOrmResult<Branche> {
        //        private List<Branche> objList = new ArrayList<Branche>();
        //
        //        public List<Branche> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<Branche> objList){
        //            this.objList = objList;
        //        }

        public List<Branche> getObjList() {
            return objList;
        }

        public void setObjList(final List<Branche> objList) {
            this.objList = objList;
        }
    }


}


