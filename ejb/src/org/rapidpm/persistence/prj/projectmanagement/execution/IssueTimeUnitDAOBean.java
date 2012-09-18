package org.rapidpm.persistence.prj.projectmanagement.execution; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 01:03
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
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnitDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.logging.LogLevelEnum;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless(name = "IssueTimeUnitDAOEJB")
@WebService(name = "IssueTimeUnitDAOWS")
public class IssueTimeUnitDAOBean {
    //private static final Logger logger = Logger.getLogger(IssueTimeUnitDAOBean.class);

    @Inject @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatIssueTimeUnit, IssueTimeUnit, IssueTimeUnitResult> crudExecuter = new CRUDExecuter<FlatIssueTimeUnit, IssueTimeUnit, IssueTimeUnitResult>(IssueTimeUnitResult.class) {
        @Override
        protected IssueTimeUnit flatType2Type(final FlatIssueTimeUnit flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final IssueTimeUnit typeObj;
            if (id == null || id == -1) {
                typeObj = new IssueTimeUnit();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setComment(flatTypeEntity.getComment());
            typeObj.setDatum(flatTypeEntity.getDatum());
            //            typeObj.setDays(flatTypeEntity.getDays());
            //            typeObj.setHours(flatTypeEntity.getHours());
            typeObj.setMinutes(flatTypeEntity.getMinutes());
            //            typeObj.setWeeks(flatTypeEntity.getWeeks());
            typeObj.setWorker(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getWorker()));
            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected IssueTimeUnit findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private IssueTimeUnitDAO getEntityDAO() {
        return daoFactoryBean.getIssueTimeUnitDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "IssueTimeUnitResult")
    IssueTimeUnitResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssueTimeUnit entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "IssueTimeUnitResult")
    IssueTimeUnitResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "IssueTimeUnitResult")
    IssueTimeUnitResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final IssueTimeUnit byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new IssueTimeUnitResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "IssueTimeUnitResult")
    IssueTimeUnitResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "IssueTimeUnitResult")
    IssueTimeUnitResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "getEntriesByIssue")
    @WebResult(name = "IssueCommentResult")
    IssueTimeUnitResult getEntriesByIssue(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                          @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                          @WebParam(name = "issueOID", mode = WebParam.Mode.IN) final Long issueOID) {
        final IssueTimeUnitResult result;
        final IssueBase issueBase = daoFactoryBean.getIssueBaseDAO().findByID(issueOID);
        if (issueBase != null) {
            result = createResult(issueBase.getIssueTimeUnitsUsed());
            result.setValid(true);
        } else {
            result = createResult();
            result.setValid(false);
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    "getEntriesByIssue", sessionid, "Es wurde keine IssueBase mit der OID " + issueOID + " gefunden."));
        }
        return result;
    }

    private IssueTimeUnitResult createResult(final IssueTimeUnit... typeObj) {
        final IssueTimeUnitResult result = new IssueTimeUnitResult();
        for (final IssueTimeUnit obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }

    private IssueTimeUnitResult createResult(final Collection<IssueTimeUnit> typeObj) {
        final IssueTimeUnitResult result = new IssueTimeUnitResult();
        for (final IssueTimeUnit obj : typeObj) {
            result.getObjList().add(obj);
        }
        return result;
    }


    public static class FlatIssueTimeUnit extends BaseFlatEntity {
        private Date datum;

        private int minutes;
        private String comment;
        private Long worker;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("FlatIssueTimeUnit");
            sb.append("{comment='").append(comment).append('\'');
            sb.append(", datum=").append(datum);
            sb.append(", minutes=").append(minutes);
            sb.append(", worker=").append(worker);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FlatIssueTimeUnit)) {
                return false;
            }

            final FlatIssueTimeUnit that = (FlatIssueTimeUnit) o;

            if (minutes != that.minutes) {
                return false;
            }
            if (comment != null ? !comment.equals(that.comment) : that.comment != null) {
                return false;
            }
            if (datum != null ? !datum.equals(that.datum) : that.datum != null) {
                return false;
            }
            if (worker != null ? !worker.equals(that.worker) : that.worker != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = datum != null ? datum.hashCode() : 0;
            result = 31 * result + minutes;
            result = 31 * result + (comment != null ? comment.hashCode() : 0);
            result = 31 * result + (worker != null ? worker.hashCode() : 0);
            return result;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(final String comment) {
            this.comment = comment;
        }

        public Date getDatum() {
            return datum;
        }

        public void setDatum(final Date datum) {
            this.datum = datum;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(final int minutes) {
            this.minutes = minutes;
        }

        public Long getWorker() {
            return worker;
        }

        public void setWorker(final Long worker) {
            this.worker = worker;
        }
    }

    public static class IssueTimeUnitResult extends BaseOrmResult<IssueTimeUnit> {
        //        private List<IssueTimeUnit> objList = new ArrayList<IssueTimeUnit>();
        //
        //        public List<IssueTimeUnit> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<IssueTimeUnit> objList){
        //            this.objList = objList;
        //        }

        public List<IssueTimeUnit> getObjList() {
            return objList;
        }

        public void setObjList(final List<IssueTimeUnit> objList) {
            this.objList = objList;
        }
    }


}


