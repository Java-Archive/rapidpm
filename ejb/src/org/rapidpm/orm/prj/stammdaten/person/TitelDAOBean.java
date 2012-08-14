package org.rapidpm.orm.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 23.03.11
 * Time: 21:11
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

@Stateless(name = "TitelDAOEJB")
@WebService(name = "TitelDAOWS")
public class TitelDAOBean {
    //private static final Logger logger = Logger.getLogger(TitelDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatTitel, Titel, TitelResult> crudExecuter = new CRUDExecuter<FlatTitel, Titel, TitelResult>(TitelResult.class) {
        @Override
        protected Titel flatType2Type(final FlatTitel flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Titel typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Titel();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setTitel(flatTypeEntity.getTitel());
            typeObj.setTitelNr(flatTypeEntity.getReihenfolge());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Titel findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private TitelDAO getEntityDAO() {
        return daoFactoryBean.getTitelDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "TitelResult")
    TitelResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                               @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatTitel entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "TitelResult")
    TitelResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "TitelResult")
    TitelResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Titel byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new TitelResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "TitelResult")
    TitelResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "TitelResult")
    TitelResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private TitelResult createResult(final Titel... typeObj) {
        final TitelResult result = new TitelResult();
        for (final Titel obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private TitelResult createResult(final Collection<Titel> typeObj) {
        final TitelResult result = new TitelResult();
        for (final Titel obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatTitel extends BaseFlatEntity {
        private String titel;
        private int reihenfolge;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatTitel");
            sb.append("{reihenfolge=").append(reihenfolge);
            sb.append(", titel='").append(titel).append('\'');
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatTitel)) {
                return false;
            }

            final FlatTitel flatTitel = (FlatTitel) o;

            if (reihenfolge != flatTitel.reihenfolge) {
                return false;
            }
            if (titel != null ? !titel.equals(flatTitel.titel) : flatTitel.titel != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = titel != null ? titel.hashCode() : 0;
            result = 31 * result + reihenfolge;
            return result;
        }

        public int getReihenfolge() {
            return reihenfolge;
        }

        public void setReihenfolge(final int reihenfolge) {
            this.reihenfolge = reihenfolge;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(final String titel) {
            this.titel = titel;
        }
    }

    public static class TitelResult extends BaseOrmResult<Titel> {
        //        private List<Titel> objList = new ArrayList<Titel>();
        //
        //        public List<Titel> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<Titel> objList){
        //            this.objList = objList;
        //        }

        public List<Titel> getObjList() {
            return objList;
        }

        public void setObjList(final List<Titel> objList) {
            this.objList = objList;
        }
    }


}


