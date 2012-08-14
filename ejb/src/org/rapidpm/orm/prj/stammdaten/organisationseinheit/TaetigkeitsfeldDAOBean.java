package org.rapidpm.orm.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 04:02
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

@Stateless(name = "TaetigkeitsfeldDAOEJB")
@WebService(name = "TaetigkeitsfeldDAOWS")
public class TaetigkeitsfeldDAOBean {
    //private static final Logger logger = Logger.getLogger(TaetigkeitsfeldDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatTaetigkeitsfeld, Taetigkeitsfeld, TaetigkeitsfeldResult> crudExecuter = new CRUDExecuter<FlatTaetigkeitsfeld, Taetigkeitsfeld, TaetigkeitsfeldResult>(TaetigkeitsfeldResult.class) {
        @Override
        protected Taetigkeitsfeld flatType2Type(final FlatTaetigkeitsfeld flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Taetigkeitsfeld typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Taetigkeitsfeld();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setBeschreibung(flatTypeEntity.getBeschreibung());
            //            typeObj.setCreated(flatTypeEntity.getCreated());
            //            typeObj.setModified(flatTypeEntity.getModified());
            typeObj.setNamen(flatTypeEntity.getNamen());


            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Taetigkeitsfeld findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private TaetigkeitsfeldDAO getEntityDAO() {
        return daoFactoryBean.getTaetigkeitsfeldDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                         @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatTaetigkeitsfeld entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Taetigkeitsfeld byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new TaetigkeitsfeldResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadTaetigkeitsfelderForMandantengruppe")
    @WebResult(name = "TaetigkeitsfeldResult")
    TaetigkeitsfeldResult loadTaetigkeitsfelderForMandantengruppe(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                  @WebParam(name = "mandantengruppeOID", mode = WebParam.Mode.IN) final Long oid) {
        final List<Taetigkeitsfeld> taetigkeitsfeldListe = getEntityDAO().loadTaetigkeitsfelderForMandantengruppe(daoFactoryBean.getMandantengruppeDAO().findByID(oid));
        return createResult(taetigkeitsfeldListe);
    }


    private TaetigkeitsfeldResult createResult(final Taetigkeitsfeld... typeObj) {
        final TaetigkeitsfeldResult result = new TaetigkeitsfeldResult();
        for (final Taetigkeitsfeld obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private TaetigkeitsfeldResult createResult(final Collection<Taetigkeitsfeld> typeObj) {
        final TaetigkeitsfeldResult result = new TaetigkeitsfeldResult();
        for (final Taetigkeitsfeld obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatTaetigkeitsfeld extends BaseFlatEntity {
        //        private String created;
        //        private String modified;
        private String namen;
        private String beschreibung;

        //        public String getCreated(){
        //            return created;
        //        }
        //
        //        public void setCreated(final String created){
        //            this.created = created;
        //        }
        //
        //        public String getModified(){
        //            return modified;
        //        }
        //
        //        public void setModified(final String modified){
        //            this.modified = modified;
        //        }

        public String getNamen() {
            return namen;
        }

        public void setNamen(final String namen) {
            this.namen = namen;
        }

        public String getBeschreibung() {
            return beschreibung;
        }

        public void setBeschreibung(final String beschreibung) {
            this.beschreibung = beschreibung;
        }
    }


    public static class TaetigkeitsfeldResult extends BaseOrmResult<Taetigkeitsfeld> {
        //        private List<Taetigkeitsfeld> objList = new ArrayList<Taetigkeitsfeld>();
        //
        //        public List<Taetigkeitsfeld> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<Taetigkeitsfeld> objList){
        //            this.objList = objList;
        //        }

        public List<Taetigkeitsfeld> getObjList() {
            return objList;
        }

        public void setObjList(final List<Taetigkeitsfeld> objList) {
            this.objList = objList;
        }
    }


}


