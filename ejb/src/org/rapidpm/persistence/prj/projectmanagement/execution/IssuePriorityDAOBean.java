//package org.rapidpm.persistence.prj.projectmanagement.execution; /**
// * RapidPM - www.rapidpm.org
// * User: svenruppert
// * Date: 29.04.11
// * Time: 01:00
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
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
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
//@Stateless(name = "IssuePriorityDAOEJB")
//@WebService(name = "IssuePriorityDAOWS")
//public class IssuePriorityDAOBean {
//    //private static final Logger logger = Logger.getLogger(IssuePriorityDAOBean.class);
//
//    @Inject @LoggerQualifier
//    private transient Logger logger;
//
//    @EJB(beanName = "DaoFactoryEJB")
//    private DaoFactoryBean daoFactoryBean;
//
//    @EJB(beanName = "LogEventEntryWriterEJB")
//    private LogEventEntryWriterBean logEventEntryWriterBean;
//
//    private CRUDExecuter<FlatIssuePriority, IssuePriority, IssuePriorityResult> crudExecuter = new CRUDExecuter<FlatIssuePriority, IssuePriority, IssuePriorityResult>(IssuePriorityResult.class) {
//        @Override
//        protected IssuePriority flatType2Type(final FlatIssuePriority flatTypeEntity) {
//            final Long id = flatTypeEntity.getId();
//            final IssuePriority typeObj;
//            if (id == null || id == -1) {
//                typeObj = new IssuePriority();
//            } else {
//                typeObj = findByID(id);
//            }
//            typeObj.setPriorityName(flatTypeEntity.getName());
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
//        protected IssuePriority findByID(final Long oid) {
//            return getEntityDAO().findByID(oid);
//        }
//
//        @Override
//        protected LogEventEntryWriterBean getLogger() {
//            return logEventEntryWriterBean;
//        }
//    };
//
//    private IssuePriorityDAO getEntityDAO() {
//        return daoFactoryBean.getIssuePriorityDAO();
//    }
//
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "saveOrUpdateTX")
//    @WebResult(name = "IssuePriorityResult")
//    IssuePriorityResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                       @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssuePriority entity) {
//
//        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "removeTX")
//    @WebResult(name = "IssuePriorityResult")
//    IssuePriorityResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
//        return crudExecuter.remove(sessionid, uid, oid);
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "findByID")
//    @WebResult(name = "IssuePriorityResult")
//    IssuePriorityResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
//        final IssuePriority byID = getEntityDAO().findByID(oid);
//        if (byID == null) {
//            return new IssuePriorityResult();
//        } else {
//            return createResult(byID);
//        }
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "loadWithOIDList")
//    @WebResult(name = "IssuePriorityResult")
//    IssuePriorityResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                        @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
//        return createResult(getEntityDAO().loadWithOIDList(oids));
//    }
//
//    @Interceptors(LoggingInterceptor.class)
//    public
//    @WebMethod(operationName = "loadAllEntities")
//    @WebResult(name = "IssuePriorityResult")
//    IssuePriorityResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
//        return createResult(getEntityDAO().loadAllEntities());
//    }
//
//    private IssuePriorityResult createResult(final IssuePriority... typeObj) {
//        final IssuePriorityResult result = new IssuePriorityResult();
//        for (final IssuePriority obj : typeObj) {
//            result.getObjList().add(obj);
//        }
//        return result;
//    }
//
//    private IssuePriorityResult createResult(final Collection<IssuePriority> typeObj) {
//        final IssuePriorityResult result = new IssuePriorityResult();
//        for (final IssuePriority obj : typeObj) {
//            result.getObjList().add(obj);
//        }
//        return result;
//    }
//
//
//    public static class FlatIssuePriority extends BaseFlatEntity {
//        private String name;
//
//        @Override
//        public boolean equals(final Object o) {
//            if (this == o) {
//                return true;
//            }
//            if (!(o instanceof FlatIssuePriority)) {
//                return false;
//            }
//
//            final FlatIssuePriority that = (FlatIssuePriority) o;
//
//            if (name != null ? !name.equals(that.name) : that.name != null) {
//                return false;
//            }
//
//            return true;
//        }
//
//        @Override
//        public int hashCode() {
//            return name != null ? name.hashCode() : 0;
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder();
//            sb.append("FlatIssuePriority");
//            sb.append("{name='").append(name).append('\'');
//            sb.append('}');
//            return sb.toString();
//        }
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
//    public static class IssuePriorityResult extends BaseOrmResult<IssuePriority> {
//        //        private List<IssuePriority> objList = new ArrayList<IssuePriority>();
//        //
//        //        public List<IssuePriority> getObjList(){
//        //            return objList;
//        //        }
//        //
//        //        public void setObjList(final List<IssuePriority> objList){
//        //            this.objList = objList;
//        //        }
//
//        public List<IssuePriority> getObjList() {
//            return objList;
//        }
//
//        public void setObjList(final List<IssuePriority> objList) {
//            this.objList = objList;
//        }
//    }
//
//
//}
//
//
