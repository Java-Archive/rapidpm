package org.rapidpm.orm.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 03:31
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
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.List;

@Stateless(name = "BrancheKlassifizierungDAOEJB")
@WebService(name = "BrancheKlassifizierungDAOWS")
public class BrancheKlassifizierungDAOBean {
    //private static final Logger logger = Logger.getLogger(BrancheKlassifizierungDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatBrancheKlassifizierung, BrancheKlassifizierung, BrancheKlassifizierungResult> crudExecuter = new CRUDExecuter<FlatBrancheKlassifizierung, BrancheKlassifizierung, BrancheKlassifizierungResult>(
            BrancheKlassifizierungResult.class) {
        @Override
        protected BrancheKlassifizierung flatType2Type(final FlatBrancheKlassifizierung flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final BrancheKlassifizierung typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new BrancheKlassifizierung();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setKlassifizierung(flatTypeEntity.getKlassifizierung());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected BrancheKlassifizierung findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private BranchenKlassifizierungDAO getEntityDAO() {
        return daoFactoryBean.getBranchenKlassifizierungDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BrancheKlassifizierungResult")
    BrancheKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBrancheKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BrancheKlassifizierungResult")
    BrancheKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BrancheKlassifizierungResult")
    BrancheKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BrancheKlassifizierung byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new BrancheKlassifizierungResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BrancheKlassifizierungResult")
    BrancheKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                 @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BrancheKlassifizierungResult")
    BrancheKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private BrancheKlassifizierungResult createResult(final BrancheKlassifizierung... typeObj) {
        final BrancheKlassifizierungResult result = new BrancheKlassifizierungResult();
        for (final BrancheKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private BrancheKlassifizierungResult createResult(final Collection<BrancheKlassifizierung> typeObj) {
        final BrancheKlassifizierungResult result = new BrancheKlassifizierungResult();
        for (final BrancheKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatBrancheKlassifizierung extends BaseFlatEntity {
        private String klassifizierung;

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }
    }

    public static class BrancheKlassifizierungResult extends BaseOrmResult<BrancheKlassifizierung> {
        //        private List<BranchenKlassifizierung> objList = new ArrayList<BranchenKlassifizierung>();
        //
        //        public List<BranchenKlassifizierung> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<BranchenKlassifizierung> objList){
        //            this.objList = objList;
        //        }

        public List<BrancheKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<BrancheKlassifizierung> objList) {
            this.objList = objList;
        }
    }


}


