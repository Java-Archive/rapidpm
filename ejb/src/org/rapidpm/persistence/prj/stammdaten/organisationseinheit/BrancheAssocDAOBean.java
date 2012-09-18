package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 03:27
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

@Stateless(name = "BrancheAssocDAOEJB")
@WebService(name = "BrancheAssocDAOWS")
public class BrancheAssocDAOBean {
    //private static final Logger logger = Logger.getLogger(BrancheAssocDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatBrancheAssoc, BrancheAssoc, BrancheAssocResult> crudExecuter = new CRUDExecuter<FlatBrancheAssoc, BrancheAssoc, BrancheAssocResult>(BrancheAssocResult.class) {
        @Override
        protected BrancheAssoc flatType2Type(final FlatBrancheAssoc flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final BrancheAssoc typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new BrancheAssoc();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setBranche(daoFactoryBean.getBrancheDAO().findByID(flatTypeEntity.getBrancheOID()));
            typeObj.setBrancheKlassifizierung(daoFactoryBean.getBranchenKlassifizierungDAO().findByID(flatTypeEntity.getBrancheKlassifizierungOID()));

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected BrancheAssoc findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private BrancheAssocDAO getEntityDAO() {
        return daoFactoryBean.getBranchenAssocDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "BrancheAssocResult")
    BrancheAssocResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatBrancheAssoc entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "BrancheAssocResult")
    BrancheAssocResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "BrancheAssocResult")
    BrancheAssocResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final BrancheAssoc byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new BrancheAssocResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "BrancheAssocResult")
    BrancheAssocResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "BrancheAssocResult")
    BrancheAssocResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private BrancheAssocResult createResult(final BrancheAssoc... typeObj) {
        final BrancheAssocResult result = new BrancheAssocResult();
        for (final BrancheAssoc obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private BrancheAssocResult createResult(final Collection<BrancheAssoc> typeObj) {
        final BrancheAssocResult result = new BrancheAssocResult();
        for (final BrancheAssoc obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private FlatBrancheAssoc type2FlatType(final BrancheAssoc t) {
        final FlatBrancheAssoc ft = new FlatBrancheAssoc();
        ft.setBrancheOID(t.getBranche().getId());
        ft.setBrancheKlassifizierungOID(t.getBrancheKlassifizierung().getId());
        ft.setId(t.getId());


        return ft;
    }

    public static class FlatBrancheAssoc extends BaseFlatEntity {
        private Long brancheOID;
        private Long brancheKlassifizierungOID;

        public Long getBrancheOID() {
            return brancheOID;
        }

        public void setBrancheOID(final Long brancheOID) {
            this.brancheOID = brancheOID;
        }

        public Long getBrancheKlassifizierungOID() {
            return brancheKlassifizierungOID;
        }

        public void setBrancheKlassifizierungOID(final Long brancheKlassifizierungOID) {
            this.brancheKlassifizierungOID = brancheKlassifizierungOID;
        }
    }

    public static class BrancheAssocResult extends BaseOrmResult<FlatBrancheAssoc> {
        public List<FlatBrancheAssoc> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatBrancheAssoc> objList) {
            this.objList = objList;
        }
    }


}


