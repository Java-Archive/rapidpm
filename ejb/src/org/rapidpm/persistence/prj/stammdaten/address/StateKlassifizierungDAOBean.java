package org.rapidpm.persistence.prj.stammdaten.address; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 09.11.11
 * Time: 01:11
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

@Stateless(name = "StateKlassifizierungDAOEJB")
@WebService(name = "StateKlassifizierungDAOWS")
public class StateKlassifizierungDAOBean {
    //private static final Logger logger = Logger.getLogger(StateKlassifizierungDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatStateKlassifizierung, StateKlassifizierung, StateKlassifizierungResult>
            crudExecuter =
            new CRUDExecuter<FlatStateKlassifizierung, StateKlassifizierung, StateKlassifizierungResult>(StateKlassifizierungResult.class) {
                @Override
                protected StateKlassifizierung flatType2Type(final FlatStateKlassifizierung flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final StateKlassifizierung typeObj;
                    if (id == null || id == -1) {
                        typeObj = new StateKlassifizierung();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setBezeichnung(flatTypeEntity.getBezeichnung());

                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected StateKlassifizierung findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private StateKlassifizierungDAO getEntityDAO() {
        return daoFactoryBean.getStateKlassifizierungDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "StateKlassifizierungResult")
    StateKlassifizierungResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                              @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatStateKlassifizierung entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "StateKlassifizierungResult")
    StateKlassifizierungResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "StateKlassifizierungResult")
    StateKlassifizierungResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "StateKlassifizierungResult")
    StateKlassifizierungResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                               @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                               @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "StateKlassifizierungResult")
    StateKlassifizierungResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                               @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private StateKlassifizierungResult createResult(final StateKlassifizierung... typeObj) {
        final StateKlassifizierungResult result = new StateKlassifizierungResult();
        for (final StateKlassifizierung obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private StateKlassifizierungResult createResult(final Collection<StateKlassifizierung> typeObj) {
        final StateKlassifizierungResult result = new StateKlassifizierungResult();
        for (final StateKlassifizierung obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    //TODO anpassen
    private FlatStateKlassifizierung convertType2FlatType(final StateKlassifizierung t) {
        final FlatStateKlassifizierung ft = new FlatStateKlassifizierung();
        ft.setId(t.getId());
        ft.setBezeichnung(t.getBezeichnung());
        return ft;
    }


    public static class FlatStateKlassifizierung extends BaseFlatEntity {
        private Long id;
        private String bezeichnung;

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getBezeichnung() {
            return bezeichnung;
        }

        public void setBezeichnung(final String bezeichnung) {
            this.bezeichnung = bezeichnung;
        }
    }

    public static class StateKlassifizierungResult extends BaseOrmResult<FlatStateKlassifizierung> {
        //private List<StateKlassifizierung> objList = new ArrayList<StateKlassifizierung>();

        public List<FlatStateKlassifizierung> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatStateKlassifizierung> objList) {
            this.objList = objList;
        }
    }


}


