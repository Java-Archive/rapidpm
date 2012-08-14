package org.rapidpm.orm.prj.projectmanagement.execution;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.12.11
 * Time: 22:22
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.orm.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.IssueCommentDAO;
import org.rapidpm.orm.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.orm.system.logging.LogLevelEnum;
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
import java.util.Date;
import java.util.List;

@Stateless(name = "IssueCommentDAOBean")
@WebService(name = "IssueCommentDAOWS")
public class IssueCommentDAOBean {
    //private static final Logger logger = Logger.getLogger(IssueCommentDAOBean.class);

    @Inject
    private transient Logger logger;


    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private
    CRUDExecuter<FlatIssueComment, IssueComment, IssueCommentResult>
            crudExecuter =
            new CRUDExecuter<FlatIssueComment, IssueComment, IssueCommentResult>(IssueCommentResult.class) {
                @Override
                protected IssueComment flatType2Type(final FlatIssueComment flatTypeEntity) {
                    final Long id = flatTypeEntity.getId();
                    final IssueComment typeObj;
                    if (id == null || id == -1) {
                        typeObj = new IssueComment();
                    } else {
                        typeObj = findByID(id);
                    }
                    typeObj.setTxt(flatTypeEntity.getTxt());
                    typeObj.setCreated(flatTypeEntity.getCreated());
                    typeObj.setCreator(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getIssueCreatorOID()));
                    return typeObj;
                }

                @Override
                protected DaoFactoryBean getDaoFactoryBean() {
                    return daoFactoryBean;
                }

                @Override
                protected IssueComment findByID(final Long oid) {
                    return getEntityDAO().findByID(oid);
                }

                @Override
                protected LogEventEntryWriterBean getLogger() {
                    return logEventEntryWriterBean;
                }
            };

    private IssueCommentDAO getEntityDAO() {
        return daoFactoryBean.getIssueCommentDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                      @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                      @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssueComment entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "entity", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return createResult(getEntityDAO().findByID(oid));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                       @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                       @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "getEntriesByIssue")
    @WebResult(name = "IssueCommentResult")
    IssueCommentResult getEntriesByIssue(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                         @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                         @WebParam(name = "issueOID", mode = WebParam.Mode.IN) final Long issueOID) {
        final IssueCommentResult result;
        final IssueBase issueBase = daoFactoryBean.getIssueBaseDAO().findByID(issueOID);
        if (issueBase != null) {
            result = createResult(issueBase.getComments());
            result.setValid(true);
        } else {
            result = createResult();
            result.setValid(false);
            result.getLoggingEventEntries().add(logEventEntryWriterBean.createLoggingEventEntry(LogLevelEnum.LOGIKINFO,
                    "getEntriesByIssue", sessionid, "Es wurde keine IssueBase mit der OID " + issueOID + " gefunden."));
        }
        return result;
    }

    private IssueCommentResult createResult(final IssueComment... typeObj) {
        final IssueCommentResult result = new IssueCommentResult();
        for (final IssueComment obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }

    private IssueCommentResult createResult(final Collection<IssueComment> typeObj) {
        final IssueCommentResult result = new IssueCommentResult();
        for (final IssueComment obj : typeObj) {
            result.getObjList().add(convertType2FlatType(obj));
        }
        return result;
    }


    //TODO anpassen
    private FlatIssueComment convertType2FlatType(final IssueComment t) {
        final FlatIssueComment ft = new FlatIssueComment();
        ft.setId(t.getId());
        ft.setTxt(t.getTxt());
        ft.setCreated(t.getCreated());
        ft.setIssueCreatorOID(t.getCreator().getId());

        return ft;
    }


    public static class FlatIssueComment extends BaseFlatEntity {
        private Long id;
        private String txt;
        private Date created;
        private Long issueCreatorOID;

        public Date getCreated() {
            return created;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Long getIssueCreatorOID() {
            return issueCreatorOID;
        }

        public void setIssueCreatorOID(Long issueCreatorOID) {
            this.issueCreatorOID = issueCreatorOID;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(final String txt) {
            this.txt = txt;
        }
    }

    public static class IssueCommentResult extends BaseOrmResult<FlatIssueComment> {
        //private List<IssueComment> objList = new ArrayList<IssueComment>();

        public List<FlatIssueComment> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatIssueComment> objList) {
            this.objList = objList;
        }
    }


}


