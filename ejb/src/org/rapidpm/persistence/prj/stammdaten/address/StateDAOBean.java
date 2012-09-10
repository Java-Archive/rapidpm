package org.rapidpm.persistence.prj.stammdaten.address; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.11.11
 * Time: 01:03
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

@Stateless(name = "StateDAOEJB")
@WebService(name = "StateDAOWS")
public class StateDAOBean {
    //private static final Logger logger = Logger.getLogger(StateDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatState, State, StateResult>
            crudExecuter =
            new CRUDExecuter<FlatState, State, StateResult>(StateResult.class) {
                @Override
                protected State flatType2Type(final FlatState flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final State typeObj;
                    if (id == null || id == -1) {
                        typeObj = new State();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setName(flatTypeEntity.getName());
                    typeObj.setKurzName(flatTypeEntity.getKurzName());
                    typeObj.setLand(daoFactoryBean.getLandDAO().findByID(flatTypeEntity.getLandOID()));
                    typeObj.setStateklassifizierung(daoFactoryBean.getStateKlassifizierungDAO().findByID(flatTypeEntity.getStateKlassifizierungOID()));
                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected State findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private StateDAO getEntityDAO() {
        return daoFactoryBean.getStateDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "StateResult")
    StateResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                               @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                               @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatState entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "StateResult")
    StateResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                         @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "StateResult")
    StateResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                         @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "StateResult")
    StateResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "StateResult")
    StateResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private StateResult createResult(final State... typeObj) {
        final StateResult result = new StateResult();
        for (final State obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private StateResult createResult(final Collection<State> typeObj) {
        final StateResult result = new StateResult();
        for (final State obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    //TODO anpassen
    private FlatState convertType2FlatType(final State t) {
        final FlatState ft = new FlatState();
        ft.setId(t.getId());
        ft.setName(t.getName());
        ft.setKurzName(t.getKurzName());
        ft.setLandOID(t.getLand().getId());
        ft.setStateKlassifizierungOID(t.getStateklassifizierung().getId());
        return ft;
    }


    public static class FlatState extends BaseFlatEntity {
        private Long id;
        private String name;
        private String kurzName;
        private Long landOID;
        private Long stateKlassifizierungOID;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatState");
            sb.append("{id=").append(id);
            sb.append(", name='").append(name).append('\'');
            sb.append(", kurzName='").append(kurzName).append('\'');
            sb.append(", landOID=").append(landOID);
            sb.append(", stateKlassifizierungOID=").append(stateKlassifizierungOID);
            sb.append('}');
            return sb.toString();
        }

        public String getKurzName() {
            return kurzName;
        }

        public void setKurzName(String kurzName) {
            this.kurzName = kurzName;
        }

        public Long getLandOID() {
            return landOID;
        }

        public void setLandOID(Long landOID) {
            this.landOID = landOID;
        }

        public Long getStateKlassifizierungOID() {
            return stateKlassifizierungOID;
        }

        public void setStateKlassifizierungOID(Long stateKlassifizierungOID) {
            this.stateKlassifizierungOID = stateKlassifizierungOID;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }
    }

    public static class StateResult extends BaseOrmResult<FlatState> {
        //private List<State> objList = new ArrayList<State>();

        public List<FlatState> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatState> objList) {
            this.objList = objList;
        }
    }


}


