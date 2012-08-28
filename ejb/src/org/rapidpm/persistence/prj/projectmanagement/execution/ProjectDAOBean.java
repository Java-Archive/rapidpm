/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.persistence.prj.projectmanagement.execution; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 29.04.11
 * Time: 00:20
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.data.BaseFlatEntity;
import org.rapidpm.data.BaseOrmResult;
import org.rapidpm.ejb3.CRUDExecuter;
import org.rapidpm.ejb3.interceptor.LoggingInterceptor;
import org.rapidpm.logging.LogEventEntryWriterBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectName;
import org.rapidpm.persistence.prj.stammdaten.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.ProjectDAO;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;
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

@Stateless(name = "ProjectDAOEJB")
@WebService(name = "ProjectDAOWS")
public class ProjectDAOBean {
    //private static final Logger logger = Logger.getLogger(ProjectDAOBean.class);

    @Inject
    private transient Logger logger;

    @EJB(beanName = "DaoFactoryEJB")
    private DaoFactoryBean daoFactoryBean;

    @EJB(beanName = "LogEventEntryWriterEJB")
    private LogEventEntryWriterBean logEventEntryWriterBean;

    private CRUDExecuter<FlatProject, PlannedProject, ProjectResult> crudExecuter = new CRUDExecuter<FlatProject, PlannedProject, ProjectResult>(ProjectResult.class) {
        @Override
        protected PlannedProject flatType2Type(final FlatProject flatTypeEntity) {
            final Long id = flatTypeEntity.getId();
            final PlannedProject typeObj;
            if (id == null || id == -1) {
                typeObj = new PlannedProject();
            } else {
                typeObj = findByID(id);
            }
            typeObj.setCreator(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getCreatorOID()));
            typeObj.setResponsiblePerson(daoFactoryBean.getBenutzerDAO().findByID(flatTypeEntity.getResponsiblePersonOID()));

            typeObj.setActive(flatTypeEntity.getActive());
            //            typeObj.setCreated(flatTypeEntity.getCreated());
//            typeObj.setIssues(daoFactoryBean.getIssueBaseDAO().loadWithOIDList(flatTypeEntity.getIssueOIDs()));
            typeObj.setFakturierbar(flatTypeEntity.getFakturierbar());
            typeObj.setId(flatTypeEntity.getId());
            typeObj.setInfo(flatTypeEntity.getInfo());
            typeObj.setMandantengruppe(daoFactoryBean.getMandantengruppeDAO().findByID(flatTypeEntity.getMandantengruppeIOD()));
            typeObj.setPlannedProjectName(flatTypeEntity.getProjectName());

            return typeObj;
        }

        @Override
        protected DaoFactoryBean getDaoFactoryBean() {
            return daoFactoryBean;
        }

        @Override
        protected PlannedProject findByID(final Long oid) {
            return getEntityDAO().findByID(oid);
        }

        @Override
        protected LogEventEntryWriterBean getLogger() {
            return logEventEntryWriterBean;
        }
    };

    private ProjectDAO getEntityDAO() {
        return daoFactoryBean.getProjectDAO();
    }


    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "saveOrUpdateTX")
    @WebResult(name = "ProjectResult")
    ProjectResult saveOrUpdateTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                 @WebParam(name = "entity", mode = WebParam.Mode.IN) final FlatProject entity) {

        return crudExecuter.saveOrUpdate(sessionid, uid, entity);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "removeTX")
    @WebResult(name = "ProjectResult")
    ProjectResult removeTX(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        return crudExecuter.remove(sessionid, uid, oid);
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "findByID")
    @WebResult(name = "ProjectResult")
    ProjectResult findByID(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid, @WebParam(name = "oid", mode = WebParam.Mode.IN) final Long oid) {
        final PlannedProject byID = getEntityDAO().findByID(oid);
        if (byID == null) {
            return new ProjectResult();
        } else {
            return createResult(byID);
        }
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadWithOIDList")
    @WebResult(name = "ProjectResult")
    ProjectResult loadWithOIDList(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid,
                                  @WebParam(name = "oidliste", mode = WebParam.Mode.IN) final List<Long> oids) {
        return createResult(getEntityDAO().loadWithOIDList(oids));
    }

    @Interceptors(LoggingInterceptor.class)
    public
    @WebMethod(operationName = "loadAllEntities")
    @WebResult(name = "ProjectResult")
    ProjectResult loadAllEntities(@WebParam(name = "sessionID", mode = WebParam.Mode.IN) final String sessionid, @WebParam(name = "UID", mode = WebParam.Mode.IN) final Long uid) {
        return createResult(getEntityDAO().loadAllEntities());
    }

    private PlannedProject loadAllData(final PlannedProject prj) {
//        final List<IssueBase> issues = prj.getIssues();
//        if (issues != null) {
//            issues.size();
//        } else {
//
//        }
        final Benutzer creator = prj.getCreator();
        if (logger.isDebugEnabled()) {
            logger.debug("creator " + creator.getLogin());
        }
        System.out.println("creator = " + creator);
        creator.getBerechtigungen().size();
        prj.getPlannedProjectName().size();
        final Benutzer responsiblePerson = prj.getResponsiblePerson();
        if (logger.isDebugEnabled()) {
            logger.debug("responsible Person " + responsiblePerson.getLogin());
        }
        final List<Berechtigung> berechtigungen = responsiblePerson.getBerechtigungen();
        berechtigungen.size();
        return prj;
    }

    private ProjectResult createResult(final PlannedProject... typeObj) {
        final ProjectResult result = new ProjectResult();
        for (final PlannedProject obj : typeObj) {
            final PlannedProject project = loadAllData(obj);
            System.out.println("project = " + project);
            result.getObjList().add(project);
        }
        return result;
    }

    private ProjectResult createResult(final Collection<PlannedProject> typeObj) {
        final ProjectResult result = new ProjectResult();
        for (final PlannedProject obj : typeObj) {
            final PlannedProject project = loadAllData(obj);
            System.out.println("project = " + project);
            result.getObjList().add(project);
        }
        return result;
    }


    public static class FlatProject extends BaseFlatEntity {
        private Long mandantengruppeIOD;
        private List<PlannedProjectName> projectName;
        private boolean active;
        private boolean fakturierbar;
        private Long creatorOID;
        private Long responsiblePersonOID;
        //        private Date created;
        private String info;
        private List<Long> issueOIDs;

        public boolean getActive() {
            return active;
        }

        public void setActive(final boolean active) {
            this.active = active;
        }

        //        public Date getCreated(){
        //            return created;
        //        }
        //
        //        public void setCreated(final Date created){
        //            this.created = created;
        //        }

        public Long getCreatorOID() {
            return creatorOID;
        }

        public void setCreatorOID(final Long creatorOID) {
            this.creatorOID = creatorOID;
        }

        public boolean getFakturierbar() {
            return fakturierbar;
        }

        public void setFakturierbar(final boolean fakturierbar) {
            this.fakturierbar = fakturierbar;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(final String info) {
            this.info = info;
        }

        public List<Long> getIssueOIDs() {
            return issueOIDs;
        }

        public void setIssueOIDs(final List<Long> issueOIDs) {
            this.issueOIDs = issueOIDs;
        }

        public Long getMandantengruppeIOD() {
            return mandantengruppeIOD;
        }

        public void setMandantengruppeIOD(final Long mandantengruppeIOD) {
            this.mandantengruppeIOD = mandantengruppeIOD;
        }

        public List<PlannedProjectName> getProjectName() {
            return projectName;
        }

        public void setProjectName(final List<PlannedProjectName> projectName) {
            this.projectName = projectName;
        }

        public Long getResponsiblePersonOID() {
            return responsiblePersonOID;
        }

        public void setResponsiblePersonOID(final Long responsiblePersonOID) {
            this.responsiblePersonOID = responsiblePersonOID;
        }
    }

    public static class ProjectResult extends BaseOrmResult<PlannedProject> {
        //        private List<PlannedProject> objList = new ArrayList<PlannedProject>();
        //
        //        public List<PlannedProject> getObjList(){
        //            return objList;
        //        }
        //
        //        public void setObjList(final List<PlannedProject> objList){
        //            this.objList = objList;
        //        }

        public List<PlannedProject> getObjList() {
            return objList;
        }

        public void setObjList(final List<PlannedProject> objList) {
            this.objList = objList;
        }
    }


}


