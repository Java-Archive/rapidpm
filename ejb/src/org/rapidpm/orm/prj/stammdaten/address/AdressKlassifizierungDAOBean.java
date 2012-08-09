package org.rapidpm.orm.prj.stammdaten.address;

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
 * User: sven.ruppert
 * Date: 03.03.11
 * Time: 21:01
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
@Stateless(name = "AdressKlassifizierungDAOEJB")
@WebService(name = "AdressKlassifizierungDAOWS")
public class AdressKlassifizierungDAOBean {

    @Inject
    private transient Logger logger;


    public AdressKlassifizierungDAOBean() {
    }

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private AddressKlassifizierungDAO getAddressKlassifizierungDAO() {
        return daoFactoryBean.getAddressKlassifizierungDAO();
    }

    private
    CRUDExecuter<FlatAdressKlassifizierung, AdressKlassifizierung, AdressKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatAdressKlassifizierung, AdressKlassifizierung, AdressKlassifizierungResult>(AdressKlassifizierungResult.class) {


                @Override
                protected AdressKlassifizierung flatType2Type(final FlatAdressKlassifizierung flatTypeEntity) {
                    final AdressKlassifizierung type = new AdressKlassifizierung();
                    type.setId(flatTypeEntity.getId());
                    type.setKlassifizierung(flatTypeEntity.getKlassifizierung());
                    return type;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected AdressKlassifizierung findByID(final Long oid) {
                    return daoFactoryBean.getAddressKlassifizierungDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(action = "loadAdressKlassifizierung")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult loadAdressKlassifizierung(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                          @WebParam(name = "klassifizierung", mode = WebParam.Mode.IN) final String klassifizierung) {
        final AdressKlassifizierung adressKlassifizierung = getAddressKlassifizierungDAO().loadAdressKlassifizierung(klassifizierung);
        return create(adressKlassifizierung);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(action = "loadKlassifizierungPrivat")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult loadKlassifizierungPrivat(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getAddressKlassifizierungDAO().loadKlassifizierungPrivat());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(action = "loadKlassifizierungBeruflich")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult loadKlassifizierungBeruflich(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                                             @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getAddressKlassifizierungDAO().loadKlassifizierungBeruflich());
    }


    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                               @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatAdressKlassifizierung entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final AdressKlassifizierung byID = getAddressKlassifizierungDAO().findByID(oid);
        if (byID == null) {
            return new AdressKlassifizierungResult();
        } else {
            return create(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                                @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return create(getAddressKlassifizierungDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "AdressKlassifizierungResult")
    AdressKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return create(getAddressKlassifizierungDAO().loadAllEntities());
    }


    private AdressKlassifizierungResult create(final AdressKlassifizierung... adressKlassifizierungen) {
        final AdressKlassifizierungResult result = new AdressKlassifizierungResult();
        for (AdressKlassifizierung klassifizierung : adressKlassifizierungen) {
            result.getObjList().add(klassifizierung);
        }
        return result;
    }

    private AdressKlassifizierungResult create(final Collection<AdressKlassifizierung> adressKlassifizierungen) {
        final AdressKlassifizierungResult result = new AdressKlassifizierungResult();
        for (AdressKlassifizierung adressKlassifizierung : adressKlassifizierungen) {
            result.getObjList().add(adressKlassifizierung);
        }
        return result;
    }

    public static class FlatAdressKlassifizierung extends BaseFlatEntity {
        private String klassifizierung;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatAdressKlassifizierung");
            sb.append("{klassifizierung='").append(klassifizierung).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatAdressKlassifizierung)) {
                return false;
            }

            final FlatAdressKlassifizierung that = (FlatAdressKlassifizierung) o;

            if (klassifizierung != null ? !klassifizierung.equals(that.klassifizierung) : that.klassifizierung != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return klassifizierung != null ? klassifizierung.hashCode() : 0;
        }

        public String getKlassifizierung() {
            return klassifizierung;
        }

        public void setKlassifizierung(final String klassifizierung) {
            this.klassifizierung = klassifizierung;
        }
    }

    public static class AdressKlassifizierungResult extends BaseOrmResult<AdressKlassifizierung> {
        //        private List<AdressKlassifizierung> klassifizierungen = new ArrayList<AdressKlassifizierung>();
        //
        //
        //        public List<AdressKlassifizierung> getKlassifizierungen(){
        //            return klassifizierungen;
        //        }
        //
        //        public void setKlassifizierungen(List<AdressKlassifizierung> klassifizierungen){
        //            this.klassifizierungen = klassifizierungen;
        //        }

        public List<AdressKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<AdressKlassifizierung> objList) {
            this.objList = objList;
        }
    }
}
