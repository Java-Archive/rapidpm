package org.rapidpm.persistence.prj.stammdaten.kommunikation;

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
 * User: svenruppert
 * Date: 04.03.11
 * Time: 10:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "KommunikationsServiceKlassifizierungDAOEJB")
@WebService(name = "KommunikationsServiceKlassifizierungDAOWS")
public class KommunikationsServiceKlassifizierungDAOBean {
    public KommunikationsServiceKlassifizierungDAOBean() {
    }

    @Inject
    private transient Logger logger;


    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private KommunikationsServiceKlassifizierungDAO getKommunikationsServiceKlassifizierungDAO() {
        return daoFactoryBean.getKommunikationsServiceKlassifizierungDAO();
    }

    private
    CRUDExecuter<FlatKommunikationsServiceKlassifizierung, KommunikationsServiceKlassifizierung, KommunikationsServiceKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatKommunikationsServiceKlassifizierung, KommunikationsServiceKlassifizierung, KommunikationsServiceKlassifizierungResult>(KommunikationsServiceKlassifizierungResult.class) {


                @Override
                protected KommunikationsServiceKlassifizierung flatType2Type(final FlatKommunikationsServiceKlassifizierung flatTypeEntity) {
                    final KommunikationsServiceKlassifizierung type = new KommunikationsServiceKlassifizierung();
                    type.setId(flatTypeEntity.getId());
                    type.setKlassifizierung(flatTypeEntity.getKlassifizierung());
                    return type;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected KommunikationsServiceKlassifizierung findByID(final Long oid) {
                    return getDaoFactoryBean().getKommunikationsServiceKlassifizierungDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "load")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult load(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                    @WebParam(name = "klassifizierung", mode = WebParam.Mode.IN) final String klassifizierung) {
        return create(getKommunikationsServiceKlassifizierungDAO().load(klassifizierung));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadKlassifizierungPrivat")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult loadKlassifizierungPrivat(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceKlassifizierungDAO().loadKlassifizierungPrivat());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadKlassifizierungBeruflich")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult loadKlassifizierungBeruflich(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceKlassifizierungDAO().loadKlassifizierungBeruflich());
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                              @WebParam(name = "entity", mode = WebParam.Mode.IN)
                                                              final FlatKommunikationsServiceKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                        @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final KommunikationsServiceKlassifizierung byID = getKommunikationsServiceKlassifizierungDAO().findByID(oid);
        if (byID == null) {
            return new KommunikationsServiceKlassifizierungResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                               @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getKommunikationsServiceKlassifizierungDAO().loadWithOIDList(oids));
    }

    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "KommunikationsServiceKlassifizierungResult")
    KommunikationsServiceKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceKlassifizierungDAO().loadAllEntities());
    }


    private KommunikationsServiceKlassifizierungResult create(final KommunikationsServiceKlassifizierung... klassifizierungen) {
        final KommunikationsServiceKlassifizierungResult result = new KommunikationsServiceKlassifizierungResult();
        for (KommunikationsServiceKlassifizierung klassifizierung : klassifizierungen) {
            result.getObjList().add(klassifizierung);
        }
        return result;
    }

    private KommunikationsServiceKlassifizierungResult create(final Collection<KommunikationsServiceKlassifizierung> klassifizierungen) {
        final KommunikationsServiceKlassifizierungResult result = new KommunikationsServiceKlassifizierungResult();
        for (KommunikationsServiceKlassifizierung klassifizierung : klassifizierungen) {
            result.getObjList().add(klassifizierung);
        }
        return result;
    }

    public static class FlatKommunikationsServiceKlassifizierung extends BaseFlatEntity {
        private String klassifizierung;

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatKommunikationsServiceKlassifizierung");
            sb.append("{klassifizierung='").append(klassifizierung).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatKommunikationsServiceKlassifizierung)) {
                return false;
            }

            final FlatKommunikationsServiceKlassifizierung that = (FlatKommunikationsServiceKlassifizierung) o;

            if (klassifizierung != null ? !klassifizierung.equals(that.klassifizierung) : that.klassifizierung != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return klassifizierung != null ? klassifizierung.hashCode() : 0;
        }
    }

    public static class KommunikationsServiceKlassifizierungResult extends BaseOrmResult<KommunikationsServiceKlassifizierung> {
        //        private List<KommunikationsServiceKlassifizierung> klassifizierungen = new ArrayList<KommunikationsServiceKlassifizierung>();
        //
        //        public List<KommunikationsServiceKlassifizierung> getKlassifizierungen(){
        //            return klassifizierungen;
        //        }
        //
        //        public void setKlassifizierungen(final List<KommunikationsServiceKlassifizierung> klassifizierungen){
        //            this.klassifizierungen = klassifizierungen;
        //        }

        public List<KommunikationsServiceKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<KommunikationsServiceKlassifizierung> objList) {
            this.objList = objList;
        }
    }

}
