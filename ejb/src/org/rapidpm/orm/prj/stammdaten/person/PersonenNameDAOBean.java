package org.rapidpm.orm.prj.stammdaten.person;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.07.11
 * Time: 09:04
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

@Stateless(name = "PersonenNameDAOEJB")
@WebService(name = "PersonenNameDAOWS")
public class PersonenNameDAOBean {
    //private static final Logger logger = Logger.getLogger(PersonenNameDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatPersonenName, PersonenName, PersonenNameResult> crudExecuter = new CRUDExecuter<FlatPersonenName, PersonenName, PersonenNameResult>(PersonenNameResult.class) {
        @Override
        protected PersonenName flatType2Type(final FlatPersonenName flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final PersonenName typeObj;
            if (id == null || id == -1) {
                typeObj = new PersonenName();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setName(flatTypeEntity.getName());
            typeObj.setNamensKlassifizierung(daoFactoryBean.getNamensKlassifizierungDAO().findByID(flatTypeEntity.getNamensKlassifizierungOID()));
            typeObj.setReihenfolge(flatTypeEntity.getReihenfolge());
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected PersonenName findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private PersonenNameDAO getEntityDAO() {
        return daoFactoryBean.getPersonenNameDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "PersonenNameResult")
    PersonenNameResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatPersonenName entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "PersonenNameResult")
    PersonenNameResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "PersonenNameResult")
    PersonenNameResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final PersonenName byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new PersonenNameResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "PersonenNameResult")
    PersonenNameResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "PersonenNameResult")
    PersonenNameResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private PersonenNameResult createResult(final PersonenName... typeObj) {
        final PersonenNameResult result = new PersonenNameResult();
        for (final PersonenName obj : typeObj) {
            final FlatPersonenName ft = new FlatPersonenName();
            ft.setId(obj.getId());
            ft.setName(obj.getName());
            ft.setNamensKlassifizierungOID(obj.getNamensKlassifizierung().getId());
            ft.setReihenfolge(obj.getReihenfolge());
            result.getObjList().add(ft);
        }
        return result;
    }

    private PersonenNameResult createResult(final Collection<PersonenName> typeObj) {
        final PersonenNameResult result = new PersonenNameResult();
        for (final PersonenName obj : typeObj) {
            final FlatPersonenName ft = new FlatPersonenName();
            ft.setId(obj.getId());
            ft.setName(obj.getName());
            ft.setNamensKlassifizierungOID(obj.getNamensKlassifizierung().getId());
            ft.setReihenfolge(obj.getReihenfolge());
            result.getObjList().add(ft);
        }
        return result;
    }


    public static class FlatPersonenName extends BaseFlatEntity {
        private Long id;
        private String name;
        private Integer reihenfolge;
        private Long namensKlassifizierungOID;

        public Long getNamensKlassifizierungOID() {
            return namensKlassifizierungOID;
        }

        public void setNamensKlassifizierungOID(final Long namensKlassifizierungOID) {
            this.namensKlassifizierungOID = namensKlassifizierungOID;
        }

        public Integer getReihenfolge() {
            return reihenfolge;
        }

        public void setReihenfolge(final Integer reihenfolge) {
            this.reihenfolge = reihenfolge;
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

    public static class PersonenNameResult extends BaseOrmResult<FlatPersonenName> {
        // private List<PersonenName> objList = new ArrayList<PersonenName>();

        public List<FlatPersonenName> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatPersonenName> objList) {
            this.objList = objList;
        }
    }


}


