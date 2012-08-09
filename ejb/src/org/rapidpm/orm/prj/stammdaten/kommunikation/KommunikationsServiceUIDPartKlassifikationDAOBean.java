package org.rapidpm.orm.prj.stammdaten.kommunikation;

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

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 04.03.11
 * Time: 10:37
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "KommunikationsServiceUIDPartKlassifikationDAOEJB")
@WebService(name = "KommunikationsServiceUIDPartKlassifikationDAOWS")
public class KommunikationsServiceUIDPartKlassifikationDAOBean {

    public KommunikationsServiceUIDPartKlassifikationDAOBean() {
    }

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;


    private
    CRUDExecuter<FlatKommunikationsServiceUIDPartKlassifikation, KommunikationsServiceUIDPartKlassifikation, KommunikationsServiceUIDPartKlassifikationResult>
            crudExecuter =
            new CRUDExecuter<FlatKommunikationsServiceUIDPartKlassifikation, KommunikationsServiceUIDPartKlassifikation, KommunikationsServiceUIDPartKlassifikationResult>(KommunikationsServiceUIDPartKlassifikationResult.class) {

                @Override
                protected KommunikationsServiceUIDPartKlassifikation flatType2Type(final FlatKommunikationsServiceUIDPartKlassifikation flatTypeEntity) {
                    final KommunikationsServiceUIDPartKlassifikation type = new KommunikationsServiceUIDPartKlassifikation();
                    type.setId(flatTypeEntity.getId());
                    type.setBezeichnung(flatTypeEntity.getBezeichnung());
                    return type;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected KommunikationsServiceUIDPartKlassifikation findByID(final Long oid) {
                    return daoFactoryBean.getKommunikationsServiceUIDPartKlassifikationDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };


    private KommunikationsServiceUIDPartKlassifikationDAO getKommunikationsServiceUIDPartKlassifikationDAO() {
        return daoFactoryBean.getKommunikationsServiceUIDPartKlassifikationDAO();
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadComplete")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadComplete(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadComplete());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadLandeskennziffer")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadLandeskennziffer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadLandeskennziffer());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadVorwahl")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadVorwahl(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadVorwahl());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadNummer")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadNummer(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadNummer());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadDurchwahl")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadDurchwahl(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadDurchwahl());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "load")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult load(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                          @WebParam(name = "klassifizierung", mode = WebParam.Mode.IN) final String klassifizierung) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().load(klassifizierung));
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                    @WebParam(name = "entity", mode = WebParam.Mode.IN)
                                                                    final FlatKommunikationsServiceUIDPartKlassifikation entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                              @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);

    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                              @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final KommunikationsServiceUIDPartKlassifikation byID = getKommunikationsServiceUIDPartKlassifikationDAO().findByID(oid);
        if (byID == null) {
            return new KommunikationsServiceUIDPartKlassifikationResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                                     @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "KommunikationsServiceUIDPartKlassifikationResult")
    KommunikationsServiceUIDPartKlassifikationResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getKommunikationsServiceUIDPartKlassifikationDAO().loadAllEntities());
    }


    private KommunikationsServiceUIDPartKlassifikationResult create(final KommunikationsServiceUIDPartKlassifikation... klassifikationen) {
        final KommunikationsServiceUIDPartKlassifikationResult result = new KommunikationsServiceUIDPartKlassifikationResult();
        for (KommunikationsServiceUIDPartKlassifikation klassifikation : klassifikationen) {
            result.getObjList().add(klassifikation);
        }
        return result;
    }

    private KommunikationsServiceUIDPartKlassifikationResult create(final Collection<KommunikationsServiceUIDPartKlassifikation> klassifikationen) {
        final KommunikationsServiceUIDPartKlassifikationResult result = new KommunikationsServiceUIDPartKlassifikationResult();
        for (KommunikationsServiceUIDPartKlassifikation klassifikation : klassifikationen) {
            result.getObjList().add(klassifikation);
        }
        return result;
    }

    public static class FlatKommunikationsServiceUIDPartKlassifikation extends BaseFlatEntity {
        private String bezeichnung;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatKommunikationsServiceUIDPartKlassifikation");
            sb.append("{bezeichnung='").append(bezeichnung).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatKommunikationsServiceUIDPartKlassifikation)) {
                return false;
            }

            final FlatKommunikationsServiceUIDPartKlassifikation that = (FlatKommunikationsServiceUIDPartKlassifikation) o;

            if (bezeichnung != null ? !bezeichnung.equals(that.bezeichnung) : that.bezeichnung != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return bezeichnung != null ? bezeichnung.hashCode() : 0;
        }

        public String getBezeichnung() {
            return bezeichnung;
        }

        public void setBezeichnung(final String bezeichnung) {
            this.bezeichnung = bezeichnung;
        }
    }

    public static class KommunikationsServiceUIDPartKlassifikationResult extends BaseOrmResult<KommunikationsServiceUIDPartKlassifikation> {
        //        private List<KommunikationsServiceUIDPartKlassifikation> klassifizierungen = new ArrayList<KommunikationsServiceUIDPartKlassifikation>();
        //
        //        public List<KommunikationsServiceUIDPartKlassifikation> getKlassifizierungen(){
        //            return klassifizierungen;
        //        }
        //
        //        public void setKlassifizierungen(final List<KommunikationsServiceUIDPartKlassifikation> klassifizierungen){
        //            this.klassifizierungen = klassifizierungen;
        //        }

        public List<KommunikationsServiceUIDPartKlassifikation> getObjList() {
            return objList;
        }

        public void setObjList(final List<KommunikationsServiceUIDPartKlassifikation> objList) {
            this.objList = objList;
        }
    }


}
