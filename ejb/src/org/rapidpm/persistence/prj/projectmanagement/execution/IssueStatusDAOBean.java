//package org.rapidpm.persistence.prj.projectmanagement.execution; /**
// * RapidPM - www.rapidpm.org
// * User: svenruppert
// * Date: 29.04.11
// * Time: 01:01
// * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
// */
//
//import org.apache.log4j.Logger;
//import org.rapidpm.data.BaseFlatEntity;
//import org.rapidpm.data.BaseOrmResult;
//import org.rapidpm.ejb3.CRUDExecuter;
//import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
//import org.rapidpm.logging.LogEventEntryWriterBean;
//import org.rapidpm.logging.LoggerQualifier;
//import org.rapidpm.persistence.DaoFactoryBean;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatusDAO;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.interceptor.Interceptors;
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebResult;
//import javax.jws.WebService;
//import java.util.Collection;
//import java.util.List;
//
//@Stateless(name = "IssueStatusDAOEJB")
//@WebService(name = "IssueStatusDAOWS")
//public class IssueStatusDAOBean {
//    //private static final Logger logger = Logger.getLogger(IssueStatusDAOBean.class);
//
//    @Inject @LoggerQualifier
//    private transient Logger logger;
//
//
//    @EJB(beanName = "DaoFactoryEJB")
//    private DaoFactoryBean daoFactoryBean;
//
//    @EJB(beanName = "LogEventEntryWriterEJB")
//    private LogEventEntryWriterBean logEventEntryWriterBean;
//
//    private CRUDExecuter<FlatIssueStatus, IssueStatus, IssueStatusResult> crudExecuter = new CRUDExecuter<FlatIssueStatus, IssueStatus, IssueStatusResult>(IssueStatusResult.class) {
//        @Override
//        protected IssueStatus flatType2Type(final FlatIssueStatus flatTypeEntity) {
//            final Long id = flatTypeEntity.getId();
//            final IssueStatus typeObj;
//            if (id == null || id == -1) {
//                typeObj = new IssueStatus();
//            } else {
//                typeObj = findByID(id);
//            }
//            typeObj.setStatusName(flatTypeEntity.getName());
//
//            return typeObj;
//        }
//
//        @Override
//        protected DaoFactoryBean getDaoFactoryBean() {
//            return daoFactoryBean;
//        }
//
//        @Override
//        protected IssueStatus findByID(final Long oid) {
//            return getEntityDAO().findByID(oid);
//        }
//
//        @Override
//        protected LogEventEntryWriterBean getLogger() {
//            return logEventEntryWriterBean;
//        }
//    };
//
//    private IssueStatusDAO getEntityDAO() {
//        return daoFactoryBean.getIssueStatusDAO();
//    }
//
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "saveOrUpdateTX")
//    @WebResult(name = "IssueStatusResult")
//    IssueStatusResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                     @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssueStatus entity) {
//
//        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "removeTX")
//    @WebResult(name = "IssueStatusResult")
//    IssueStatusResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
//        return crudExecuter.remove(sessionid, uid, oid);
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "findByID")
//    @WebResult(name = "IssueStatusResult")
//    IssueStatusResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
//        final IssueStatus byID = getEntityDAO().findByID(oid);
//        if (byID == null) {
//            return new IssueStatusResult();
//        } else {
//            return createResult(byID);
//        }
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "loadWithOIDList")
//    @WebResult(name = "IssueStatusResult")
//    IssueStatusResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                      @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
//        return createResult(getEntityDAO().loadWithOIDList(oids));
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "loadAllEntities")
//    @WebResult(name = "IssueStatusResult")
//    IssueStatusResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
//        return createResult(getEntityDAO().loadAllEntities());
//    }
//
//    private IssueStatusResult createResult(final IssueStatus... typeObj) {
//        final IssueStatusResult result = new IssueStatusResult();
//        for (final IssueStatus obj : typeObj) {
//            result.getObjList().add(obj);
//        }
//        return result;
//    }
//
//    private IssueStatusResult createResult(final Collection<IssueStatus> typeObj) {
//        final IssueStatusResult result = new IssueStatusResult();
//        for (final IssueStatus obj : typeObj) {
//            result.getObjList().add(obj);
//        }
//        return result;
//    }
//
//
//    public static class FlatIssueStatus extends BaseFlatEntity {
//        private String name;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(final String name) {
//            this.name = name;
//        }
//    }
//
//    public static class IssueStatusResult extends BaseOrmResult<IssueStatus> {
//        //        private List<IssueStatus> objList = new ArrayList<IssueStatus>();
//        //
//        //        public List<IssueStatus> getObjList(){
//        //            return objList;
//        //        }
//        //
//        //        public void setObjList(final List<IssueStatus> objList){
//        //            this.objList = objList;
//        //        }
//
//        public List<IssueStatus> getObjList() {
//            return objList;
//        }
//
//        public void setObjList(final List<IssueStatus> objList) {
//            this.objList = objList;
//        }
//    }
//
//
//}
//
//
