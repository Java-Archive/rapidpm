package org.rapidpm.persistence.prj.projectmanagement.execution;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 00:39
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.logging.LoggerQualifier;
import org.rapidpm.persistence.AbstractOneToManyConnectExecutor;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTimeUnit;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.persistence.system.security.Benutzer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Stateless(name = "IssueBaseDAOEJB")
@WebService(name = "IssueBaseDAOWS")
public class IssueBaseDAOBean {

    @Inject
    @LoggerQualifier
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatIssueBase, IssueBase, IssueBaseResult> crudExecuter = new CRUDExecuter<FlatIssueBase, IssueBase, IssueBaseResult>(IssueBaseResult.class) {
        @Override
        protected IssueBase flatType2Type(final FlatIssueBase flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final IssueBase typeObj;
            if (id == null || id == -1) {
                typeObj = new IssueBase();
            } else {
                typeObj = findByID(id);
            }

            typeObj.setAssignee(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getIssueAssigneeOID()));

            final List<Long> commentsOIDs = flatTypeEntity.getIssueCommentOIDs();
            typeObj.setComments(daoFactoryBean.getIssueCommentDAO().loadWithOIDList(commentsOIDs));
            typeObj.setDueDate_closed(flatTypeEntity.getDueDate_closed());
            typeObj.setDueDate_planned(flatTypeEntity.getDueDate_planned());
            typeObj.setDueDate_resolved(flatTypeEntity.getDueDate_resolved());

//            typeObj.setFakturierbar(flatTypeEntity.getFakturierbar());

            typeObj.setId(flatTypeEntity.getId());

            final Long issuePriorityOID = flatTypeEntity.getIssuePriorityOID();
            typeObj.setPriority(daoFactoryBean.getIssuePriorityDAO().findByID(issuePriorityOID));

            final Long issueStatusOID = flatTypeEntity.getIssueStatusOID();
            typeObj.setStatus(daoFactoryBean.getIssueStatusDAO().findByID(issueStatusOID));

//            final Long issueTimeUnitEstimatedOID = flatTypeEntity.getIssueTimeUnitEstimatedOID();
//            typeObj.setIssueTimeUnitEstimated(daoFactoryBean.getIssueTimeUnitDAO().findByID(issueTimeUnitEstimatedOID));
//
//            final List<Long> issueTimeUnitsUsedOIDs = flatTypeEntity.getIssueTimeUnitsUsedOIDs();
//            typeObj.setIssueTimeUnitsUsed(daoFactoryBean.getIssueTimeUnitDAO().loadWithOIDList(issueTimeUnitsUsedOIDs));

            typeObj.setReporter(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getIssueReporterOID()));
            typeObj.setSummary(flatTypeEntity.getSummary());
            typeObj.setText(flatTypeEntity.getText());
            typeObj.setVersion(flatTypeEntity.getVersion());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected IssueBase findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    public IssueBaseDAOBean() {

    }

    private IssueBaseDAO getEntityDAO() {
        return daoFactoryBean.getIssueBaseDAO();
    }



    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                   @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssueBase entity) {
        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

//
//    public
//    @WebMethod(operationName = "addNewIssueToProject")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult addNewIssueToProject(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                         @WebParam(name = "projectOID", mode = WebParam.Mode.IN) final Long projectOID, @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatIssueBase entity) {
//
//        final IssueBaseResult result = crudExecuter.saveOrUpdate(sessionid, uid, entity);
//        final Long lastOID = crudExecuter.getLastOID();
//        final IssueBase byID = daoFactoryBean.getIssueBaseDAO().findByID(lastOID);
//
//        final ProjectDAO projectDAO = daoFactoryBean.getProjectDAO();
//        final PlannedProject project = projectDAO.findByID(projectOID);
//        project.getIssues().add(byID);
//        daoFactoryBean.saveOrUpdate(project);
//        return result;
//    }

//
//    public
//    @WebMethod(operationName = "addIssueToProject")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult addIssueToProject(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                      @WebParam(name = "projectOID", mode = WebParam.Mode.IN) final Long projectOID, @WebParam(name = "issueOID", mode = WebParam.Mode.IN) final Long issueOID) {
//
//        final IssueBase byID = daoFactoryBean.getIssueBaseDAO().findByID(issueOID);
//        final ProjectDAO projectDAO = daoFactoryBean.getProjectDAO();
//        final PlannedProject project = projectDAO.findByID(projectOID);
//        project.getIssues().add(byID);
//        daoFactoryBean.saveOrUpdate(project);
//        return new IssueBaseResult(); //TODO Logging
//    }

//
//    public
//    @WebMethod(operationName = "removeIssueFromProject")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult removeIssueFromProject(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                           @WebParam(name = "projectOID", mode = WebParam.Mode.IN) final Long projectOID, @WebParam(name = "issueOID", mode = WebParam.Mode.IN) final Long issueOID) {
//        final IssueBase byID = daoFactoryBean.getIssueBaseDAO().findByID(issueOID);
//        final ProjectDAO projectDAO = daoFactoryBean.getProjectDAO();
//        final PlannedProject project = projectDAO.findByID(projectOID);
//        project.getIssues().remove(byID);
//        daoFactoryBean.saveOrUpdate(project);
//        return new IssueBaseResult(); //TODO Logging
//    }

//
//    public
//    @WebMethod(operationName = "moveIssueFromProjectToProject")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult moveIssueFromProjectToProject(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                                  @WebParam(name = "projectOIDFrom", mode = WebParam.Mode.IN) final Long projectFromOID, @WebParam(name = "projectOIDTo", mode = WebParam.Mode.IN) final Long projectToOID,
//                                                  @WebParam(name = "issueOID", mode = WebParam.Mode.IN) final Long issueOID) {
//        final IssueBase byID = daoFactoryBean.getIssueBaseDAO().findByID(issueOID);
//        final ProjectDAO projectDAO = daoFactoryBean.getProjectDAO();
//        final PlannedProject projectFrom = projectDAO.findByID(projectFromOID);
//        final PlannedProject projectTo = projectDAO.findByID(projectToOID);
//
//        projectFrom.getIssues().remove(byID);
//        projectTo.getIssues().add(byID);
//        daoFactoryBean.saveOrUpdate(projectFrom);
//        daoFactoryBean.saveOrUpdate(projectTo);
//        return new IssueBaseResult(); //TODO Logging
//    }


    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }


    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final IssueBase byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new IssueBaseResult();
        } else {
            return createResult(byID);
        }
    }


    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                    @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }


    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

//
//    public
//    @WebMethod(operationName = "loadAllIssuesForBenutzer")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
//                                    @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                    @WebParam(name = "assignee") final String assignee) {
//        return createResult(getEntityDAO().loadAllIssuesForBenutzer(assignee));
//    }
//
//
//    public
//    @WebMethod(operationName = "loadAllIssuesForBenutzerOID")
//    @WebResult(name = "IssueBaseResult")
//    IssueBaseResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
//                                    @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
//                                    @WebParam(name = "issueAssigneeOID") final Long assigneeOID) {
//        return createResult(getEntityDAO().loadAllIssuesForBenutzer(assigneeOID));
//    }


    public
    @WebMethod(operationName = "connectIssueComment")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult connectIssueComment(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                        @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                        @WebParam(name = "issueBaseOID") final Long issueBaseOID,
                                        @WebParam(name = "issueCommentOID") final Long issueCommentOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, IssueComment, IssueBase, IssueBaseResult>(
                daoFactoryBean, daoFactoryBean.getIssueCommentDAO(), daoFactoryBean.getIssueBaseDAO()) {

            @Override
            public List<IssueComment> getCollection(final IssueBase issueBase) {
                return issueBase.getComments();
            }

            @Override
            public IssueBaseResult getResult(final IssueBase issueBase) {
                return issueBase != null ? createResult(issueBase) : createResult();
            }

        }.execute("connectIssueComment", logEventEntryWriterBean, sessionid, issueBaseOID, issueCommentOID);
    }


    public
    @WebMethod(operationName = "connectIssueTimeUnitUsed")
    @WebResult(name = "IssueBaseResult")
    IssueBaseResult connectIssueTimeUnitUsed(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid,
                                             @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                             @WebParam(name = "issueBaseOID") final Long issueBaseOID,
                                             @WebParam(name = "issueTimeUnitUsedOID") final Long issueTimeUnitUsedOID) {
        return new AbstractOneToManyConnectExecutor<DaoFactory, IssueTimeUnit, IssueBase, IssueBaseResult>(
                daoFactoryBean, daoFactoryBean.getIssueTimeUnitDAO(), daoFactoryBean.getIssueBaseDAO()) {

            @Override
            public List<IssueTimeUnit> getCollection(final IssueBase issueBase) {
                return null;//issueBase.getIssueTimeUnitsUsed();
            }

            @Override
            public IssueBaseResult getResult(final IssueBase issueBase) {
                return issueBase != null ? createResult(issueBase) : createResult();
            }
        }.execute("connectIssueTimeUnitUsed", logEventEntryWriterBean, sessionid, issueBaseOID, issueTimeUnitUsedOID);
    }

    private IssueBaseResult createResult(final IssueBase... typeObj) {
        final IssueBaseResult result = new IssueBaseResult();
        for (final IssueBase obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private IssueBaseResult createResult(final Collection<IssueBase> typeObj) {
        final IssueBaseResult result = new IssueBaseResult();
        for (final IssueBase obj : typeObj) {
            result.getObjList().add(type2FlatType(obj));
        }
        return result;
    }

    private FlatIssueBase type2FlatType(final IssueBase t) {
        final FlatIssueBase ft = new FlatIssueBase();
        ft.setIssueAssigneeOID(t.getAssignee().getId());
        final List<IssueComment> comments = t.getComments();
        if (comments != null) {
            ft.setIssueCommentOIDs(new ArrayList<Long>());
            for (final IssueComment comment : comments) {
                ft.getIssueCommentOIDs().add(comment.getId());
            }
        }
        ft.setDueDate_closed(t.getDueDate_closed());
        ft.setDueDate_planned(t.getDueDate_planned());
        ft.setDueDate_resolved(t.getDueDate_resolved());
        ft.setFakturierbar(false);
        ft.setIssuePriorityOID(t.getPriority().getId());
        ft.setIssueStatusOID(t.getPriority().getId());

//        final IssueTimeUnit issueTimeUnitEstimated = t.getIssueTimeUnitEstimated();
//        if (issueTimeUnitEstimated != null) {
//            ft.setIssueTimeUnitEstimatedOID(issueTimeUnitEstimated.getId());
//        }
//        final List<IssueTimeUnit> issueTimeUnitsUsed = t.getIssueTimeUnitsUsed();
//        if (issueTimeUnitsUsed != null) {
//            ft.setIssueTimeUnitsUsedOIDs(new ArrayList<Long>());
//            for (final IssueTimeUnit issueTimeUnit : issueTimeUnitsUsed) {
//                ft.getIssueTimeUnitsUsedOIDs().add(issueTimeUnit.getId());
//            }
//        }

        final Benutzer reporter = t.getReporter();
        if (reporter != null) {
            ft.setIssueReporterOID(reporter.getId());
        }
        ft.setSummary(t.getSummary());
        ft.setText(t.getText());
        ft.setVersion(t.getVersion());
        ft.setId(t.getId());


        return ft;
    }


    public static class FlatIssueBase extends BaseFlatEntity {
        private String summary;
        private String text;
        private boolean fakturierbar;
        private Long issuePriorityOID;
        private Long issueStatusOID;
        private Long issueTimeUnitEstimatedOID;
        private List<Long> issueTimeUnitsUsedOIDs;
        private Long issueReporterOID;
        private Long issueAssigneeOID;
        //        private float euro;
        private String version;
        private Date dueDate_planned;
        private Date dueDate_resolved;
        private Date dueDate_closed;
        private List<Long> issueCommentOIDs;

        public Long getIssueAssigneeOID() {
            return issueAssigneeOID;
        }

        public void setIssueAssigneeOID(Long issueAssigneeOID) {
            this.issueAssigneeOID = issueAssigneeOID;
        }

        public Date getDueDate_closed() {
            return dueDate_closed;
        }

        public void setDueDate_closed(Date dueDate_closed) {
            this.dueDate_closed = dueDate_closed;
        }

        public Date getDueDate_planned() {
            return dueDate_planned;
        }

        public void setDueDate_planned(Date dueDate_planned) {
            this.dueDate_planned = dueDate_planned;
        }

        public Date getDueDate_resolved() {
            return dueDate_resolved;
        }

        public void setDueDate_resolved(Date dueDate_resolved) {
            this.dueDate_resolved = dueDate_resolved;
        }

        public boolean getFakturierbar() {
            return fakturierbar;
        }

        public void setFakturierbar(boolean fakturierbar) {
            this.fakturierbar = fakturierbar;
        }

        public List<Long> getIssueCommentOIDs() {
            return issueCommentOIDs;
        }

        public void setIssueCommentOIDs(List<Long> issueCommentOIDs) {
            this.issueCommentOIDs = issueCommentOIDs;
        }

        public Long getIssuePriorityOID() {
            return issuePriorityOID;
        }

        public void setIssuePriorityOID(Long issuePriorityOID) {
            this.issuePriorityOID = issuePriorityOID;
        }

        public Long getIssueStatusOID() {
            return issueStatusOID;
        }

        public void setIssueStatusOID(Long issueStatusOID) {
            this.issueStatusOID = issueStatusOID;
        }

        public Long getIssueTimeUnitEstimatedOID() {
            return issueTimeUnitEstimatedOID;
        }

        public void setIssueTimeUnitEstimatedOID(Long issueTimeUnitEstimatedOID) {
            this.issueTimeUnitEstimatedOID = issueTimeUnitEstimatedOID;
        }

        public List<Long> getIssueTimeUnitsUsedOIDs() {
            return issueTimeUnitsUsedOIDs;
        }

        public void setIssueTimeUnitsUsedOIDs(List<Long> issueTimeUnitsUsedOIDs) {
            this.issueTimeUnitsUsedOIDs = issueTimeUnitsUsedOIDs;
        }

        public Long getIssueReporterOID() {
            return issueReporterOID;
        }

        public void setIssueReporterOID(Long issueReporterOID) {
            this.issueReporterOID = issueReporterOID;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static class IssueBaseResult extends BaseOrmResult<FlatIssueBase> {
        public List<FlatIssueBase> getObjList() {
            return objList;
        }

        public void setObjList(final List<FlatIssueBase> objList) {
            this.objList = objList;
        }
    }


}


