package org.rapidpm.persistence.prj.projectmanagement.execution; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 01:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.ProjectNameDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;

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

@Stateless(name = "ProjectNameDAOEJB")
@WebService(name = "ProjectNameDAOWS")
public class ProjectNameDAOBean {
    //private static final Logger logger = Logger.getLogger(ProjectNameDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;


    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatProjectName, PlannedProjectName, ProjectNameResult> crudExecuter = new CRUDExecuter<FlatProjectName, PlannedProjectName, ProjectNameResult>(ProjectNameResult.class) {
        @Override
        protected PlannedProjectName flatType2Type(final FlatProjectName flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final PlannedProjectName typeObj;
            if (id == null || id == -1) {
                typeObj = new PlannedProjectName();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setNamepart(flatTypeEntity.getNamepart());
            typeObj.setOrdernr(flatTypeEntity.getOrdernr());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected PlannedProjectName findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private ProjectNameDAO getEntityDAO() {
        return daoFactoryBean.getProjectNameDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "ProjectNameResult")
    ProjectNameResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                     @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatProjectName entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "ProjectNameResult")
    ProjectNameResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "ProjectNameResult")
    ProjectNameResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final PlannedProjectName byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new ProjectNameResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "ProjectNameResult")
    ProjectNameResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "ProjectNameResult")
    ProjectNameResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private ProjectNameResult createResult(final PlannedProjectName... typeObj) {
        final ProjectNameResult result = new ProjectNameResult();
        for (final PlannedProjectName obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private ProjectNameResult createResult(final Collection<PlannedProjectName> typeObj) {
        final ProjectNameResult result = new ProjectNameResult();
        for (final PlannedProjectName obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatProjectName extends BaseFlatEntity {
        private String namepart;
        private int ordernr;

        public String getNamepart() {
            return namepart;
        }

        public void setNamepart(final String namepart) {
            this.namepart = namepart;
        }

        public int getOrdernr() {
            return ordernr;
        }

        public void setOrdernr(final int ordernr) {
            this.ordernr = ordernr;
        }
    }

    public static class ProjectNameResult extends BaseOrmResult<PlannedProjectName> {
        //        private List<PlannedProjectName> objList = new ArrayList<PlannedProjectName>();
        //
        //        public List<PlannedProjectName> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<PlannedProjectName> objList){
        //            this.objList = objList;
        //        }

        public List<PlannedProjectName> getObjList() {
            return objList;
        }

        public void setObjList(final List<PlannedProjectName> objList) {
            this.objList = objList;
        }
    }


}


