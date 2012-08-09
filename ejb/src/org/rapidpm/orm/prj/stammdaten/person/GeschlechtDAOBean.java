/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.person;

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
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 20.12.11
 * Time: 14:14
 */
@Stateless(name = "GeschlechtDAOEJB")
@WebService(name = "GeschlechtDAOWS")
public class GeschlechtDAOBean {
    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatGeschlecht, Geschlecht, GeschlechtResult> crudExecuter = new CRUDExecuter<FlatGeschlecht, Geschlecht, GeschlechtResult>(GeschlechtResult.class) {
        @Override
        protected Geschlecht flatType2Type(final FlatGeschlecht flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final Geschlecht typeObj;
            if (id == null || id == -1) {
                typeObj = new Geschlecht();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setGeschlecht(flatTypeEntity.getGeschlecht());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected Geschlecht findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private GeschlechtDAO getEntityDAO() {
        return daoFactoryBean.getGeschlechtDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                    @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                    @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatGeschlecht entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                              @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                              @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                              @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                     @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                     @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                     @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "load")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult load(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                          @WebParam(name = "geschlecht", mode = WebParam.Mode.IN) final String geschlecht) {
        return createResult(getEntityDAO().load(geschlecht));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadGeschlechtMaennlich")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult loadGeschlechtMaennlich(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                             @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadGeschlechtMaennlich());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadGeschlechtWeiblich")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult loadGeschlechtWeiblich(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                            @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadGeschlechtWeiblich());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadGeschlechtNothing")
    @WebResult(name = "GeschlechtResult")
    GeschlechtResult loadGeschlechtNothing(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                           @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadGeschlechtNothing());
    }

    private GeschlechtResult createResult(final Geschlecht... typeObj) {
        final GeschlechtResult result = new GeschlechtResult();
        for (final Geschlecht obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private GeschlechtResult createResult(final Collection<Geschlecht> typeObj) {
        final GeschlechtResult result = new GeschlechtResult();
        for (final Geschlecht obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    private FlatGeschlecht convertType2FlatType(final Geschlecht t) {
        final FlatGeschlecht ft = new FlatGeschlecht();
        ft.setId(t.getId());
        ft.setGeschlecht(t.getGeschlecht());

        return ft;
    }


    public static class FlatGeschlecht extends BaseFlatEntity {
        private String geschlecht;

        public String getGeschlecht() {
            return geschlecht;
        }

        public void setGeschlecht(final String geschlecht) {
            this.geschlecht = geschlecht;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final FlatGeschlecht that = (FlatGeschlecht) o;

            if (geschlecht != null ? !geschlecht.equals(that.geschlecht) : that.geschlecht != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return geschlecht != null ? geschlecht.hashCode() : 0;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatGeschlecht");
            sb.append("{geschlecht='").append(geschlecht).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static class GeschlechtResult extends BaseOrmResult<FlatGeschlecht> {
        //private List<Geschlecht> objList = new ArrayList<Geschlecht>();

        public List<FlatGeschlecht> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatGeschlecht> objList) {
            this.objList = objList;
        }
    }


}


