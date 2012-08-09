package org.rapidpm.orm.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 04:08
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

@Stateless(name = "TaetigkeitsfeldKlassifizierungDAOEJB")
@WebService(name = "TaetigkeitsfeldKlassifizierungDAOWS")
public class TaetigkeitsfeldKlassifizierungDAOBean {
    //private static final Logger logger = Logger.getLogger(TaetigkeitsfeldKlassifizierungDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatTaetigkeitsfeldKlassifizierung, TaetigkeitsfeldKlassifizierung, TaetigkeitsfeldKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatTaetigkeitsfeldKlassifizierung, TaetigkeitsfeldKlassifizierung, TaetigkeitsfeldKlassifizierungResult>(TaetigkeitsfeldKlassifizierungResult.class) {
                @Override
                protected TaetigkeitsfeldKlassifizierung flatType2Type(final FlatTaetigkeitsfeldKlassifizierung flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final TaetigkeitsfeldKlassifizierung typeObj;
                    if (id == null || id == -1 || id == 0) {
                        typeObj = new TaetigkeitsfeldKlassifizierung();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setBeschreibung(flatTypeEntity.getBeschreibung());
                    //                    typeObj.setCreated(flatTypeEntity.getCreated());
                    typeObj.setKlassifizierung(flatTypeEntity.getKlassifizierung());
                    //                    typeObj.setModified(flatTypeEntity.getModified());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected TaetigkeitsfeldKlassifizierung findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private TaetigkeitsfeldKlassifizierungDAO getEntityDAO() {
        return daoFactoryBean.getTaetigkeitsklassifizierungDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "TaetigkeitsfeldKlassifizierungResult")
    TaetigkeitsfeldKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "entity", mode = WebParam.Mode.IN)
                                                        final FlatTaetigkeitsfeldKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "TaetigkeitsfeldKlassifizierungResult")
    TaetigkeitsfeldKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                  @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "TaetigkeitsfeldKlassifizierungResult")
    TaetigkeitsfeldKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                  @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final TaetigkeitsfeldKlassifizierung byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new TaetigkeitsfeldKlassifizierungResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "TaetigkeitsfeldKlassifizierungResult")
    TaetigkeitsfeldKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                         @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "TaetigkeitsfeldKlassifizierungResult")
    TaetigkeitsfeldKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private TaetigkeitsfeldKlassifizierungResult createResult(final TaetigkeitsfeldKlassifizierung... typeObj) {
        final TaetigkeitsfeldKlassifizierungResult result = new TaetigkeitsfeldKlassifizierungResult();
        for (final TaetigkeitsfeldKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private TaetigkeitsfeldKlassifizierungResult createResult(final Collection<TaetigkeitsfeldKlassifizierung> typeObj) {
        final TaetigkeitsfeldKlassifizierungResult result = new TaetigkeitsfeldKlassifizierungResult();
        for (final TaetigkeitsfeldKlassifizierung obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatTaetigkeitsfeldKlassifizierung extends BaseFlatEntity {
        private String klassifizierung;
        private String beschreibung;
        //        private String created;
        //        private String modified;

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }

        public String getBeschreibung() {
            return beschreibung;
        }

        public void setBeschreibung(final String beschreibung) {
            this.beschreibung = beschreibung;
        }

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
    }

    public static class TaetigkeitsfeldKlassifizierungResult extends BaseOrmResult<TaetigkeitsfeldKlassifizierung> {
        //        private List<TaetigkeitsfeldKlassifizierung> objList = new ArrayList<TaetigkeitsfeldKlassifizierung>();
        //
        //        public List<TaetigkeitsfeldKlassifizierung> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<TaetigkeitsfeldKlassifizierung> objList){
        //            this.objList = objList;
        //        }

        public List<TaetigkeitsfeldKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<TaetigkeitsfeldKlassifizierung> objList) {
            this.objList = objList;
        }
    }


}


