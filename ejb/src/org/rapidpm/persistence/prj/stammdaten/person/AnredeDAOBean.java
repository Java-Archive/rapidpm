package org.rapidpm.persistence.prj.stammdaten.person; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 23.03.11
 * Time: 21:09
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

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

@Stateless(name = "AnredeDAOEJB")
@WebService(name = "AnredeDAOWS")
public class AnredeDAOBean {
    //private static final Logger logger = Logger.getLogger(AnredeDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatAnrede, Anrede, AnredeResult> crudExecuter = new CRUDExecuter<FlatAnrede, Anrede, AnredeResult>(AnredeResult.class) {
        @Override
        protected Anrede flatType2Type(final FlatAnrede flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Anrede typeObj;
            if (id == null || id == -1 || id == 0) {
                typeObj = new Anrede();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setAnrede(flatTypeEntity.getAnrede());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Anrede findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private AnredeDAO getEntityDAO() {
        return daoFactoryBean.getAnredeDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "AnredeResult")
    AnredeResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatAnrede entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "AnredeResult")
    AnredeResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "AnredeResult")
    AnredeResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final Anrede byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new AnredeResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "AnredeResult")
    AnredeResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "AnredeResult")
    AnredeResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private AnredeResult createResult(final Anrede... typeObj) {
        final AnredeResult result = new AnredeResult();
        for (final Anrede obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private AnredeResult createResult(final Collection<Anrede> typeObj) {
        final AnredeResult result = new AnredeResult();
        for (final Anrede obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatAnrede extends BaseFlatEntity {
        private String anrede;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatAnrede)) {
                return false;
            }

            final FlatAnrede that = (FlatAnrede) o;

            if (anrede != null ? !anrede.equals(that.anrede) : that.anrede != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return anrede != null ? anrede.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatAnrede");
            sb.append("{anrede='").append(anrede).append('\'');
            sb.append('}');
            return sb.toString();
        }

        public String getAnrede() {
            return anrede;
        }

        public void setAnrede(final String anrede) {
            this.anrede = anrede;
        }
    }

    public static class AnredeResult extends BaseOrmResult<Anrede> {
        //        private List<Anrede> objList = new ArrayList<Anrede>();
        //
        //        public List<Anrede> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<Anrede> objList){
        //            this.objList = objList;
        //        }

        public List<Anrede> getObjList() {
            return objList;
        }

        public void setObjList(final List<Anrede> objList) {
            this.objList = objList;
        }
    }


}


