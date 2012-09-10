package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 31.08.2010
 *        Time: 13:00:32
 */

public class IssueBaseDAO extends BaseDAO<Long, IssueBase> {
    private static final Logger logger = Logger.getLogger(IssueBaseDAO.class);

    //    private SimpleDateFormat format = new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.GERMAN);

    public IssueBaseDAO(final EntityManager entityManager) {
        super(entityManager, IssueBase.class);
    }

    public IssueBase loadIssueFor(final long issueNr) {
        final TypedQuery<IssueBase> typedQuery = entityManager.createQuery("from IssueBase i where i.id=:issueNr", IssueBase.class).setParameter("issueNr", issueNr);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("id", issueNr).findUnique();
    }

    //    public List<IssueBase> loadAllIssuesFor(final String mandantengruppe, final String project, final String assignee, final String status, final Date version){
    //        return entityManager.createQuery("from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.project.projectName=:project " + "and b.assignee.login=:assignee " + "and b.status.name=:status " + "and b.version=:version", IssueBase.class).setParameter("mandantengruppe", mandantengruppe).setParameter("project", project).setParameter("assignee", assignee).setParameter("status", status).setParameter("version", version).getResultList();
    //        //        final String sql = "select id from base_issue bi \n" +
    //        //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
    //        //                and("bi.assignee_id", benutzerID(assignee, mandantengruppe)) +
    //        //                and("bi.project_id", projectID(mandantengruppe, project)) +
    //        //                and("bi.status_id", statusID(status)) +
    //        //                and("bi.version", version);
    //        //        return createQuery(sql).findList();
    //
    //        //        addEq("mandantengruppe.mandantengruppe", mandantengruppe);
    //        //        addEq("assignee.login", assignee);
    //        //        addEq("project.mandantengruppe.mandantengruppe", mandantengruppe);
    //        //        addEq("project.projectName", project);
    //        //        addEq("version", version);
    //        //        addEq("status.name", status);
    //        //        return findListForExpressionList();
    //    }

    //    public List<IssueBase> loadAllIssuesFor(final String mandantengruppe, final String project, final String assignee, final String status, final Date version, final String priority){
    //
    //        String sql = "from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe ";
    //        if(project != null && !project.isEmpty()){
    //            sql = sql + "and b.project.projectName=:project ";
    //        }
    //        if(assignee != null && !assignee.isEmpty()){
    //            sql = sql + "and b.assignee.login=:assignee ";
    //        }
    //
    //        if(status != null && !status.isEmpty()){
    //            sql = sql + "and b.status.name=:status ";
    //        }
    //
    //        if(version != null){
    //            sql = sql +  "and b.version>=:version ";
    //        }
    //
    //        TypedQuery<IssueBase> query = entityManager.createQuery(sql, IssueBase.class).setParameter("mandantengruppe", mandantengruppe);
    //        if( project != null && !project.isEmpty() ){
    //            query = query.setParameter("project", project);
    //        }
    //
    //        if( assignee != null && !assignee.isEmpty() ){
    //            query = query.setParameter("assignee", assignee);
    //        }
    //
    //        if( status != null && !status.isEmpty() ){
    //            query = query.setParameter("status", status);
    //        }
    //        if( versionVon != null  ){
    //            query = query.setParameter("versionVon", versionVon);
    //        }
    //
    //        if( versionBis != null  ){
    //            query = query.setParameter("versionBis", versionBis);
    //        }
    //        return query.getResultList();
    //

    //        return entityManager.createQuery("from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.project.projectName=:project " + "and b.assignee.login=:assignee " + "and b.status.name=:status " + "and b.priority.name=:priority " + "and b.version=:version", IssueBase.class)
    //                .setParameter("mandantengruppe", mandantengruppe)
    //                .setParameter("project", project)
    //                .setParameter("assignee", assignee)
    //                .setParameter("status", status)
    //                .setParameter("version", version)
    //                .setParameter("priority", priority)
    //                .getResultList();

    //        addEq("mandantengruppe.mandantengruppe", mandantengruppe);
    //        addEq("assignee.login", assignee);
    //        addEq("project.mandantengruppe.mandantengruppe", mandantengruppe);
    //        addEq("project.projectName", project);
    //        addEq("version", version);
    //        addEq("priority.name", priority);
    //        addEq("status.name", status);
    //        return findListForExpressionList();

    //        final String sql = "select id from base_issue bi \n" +
    //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
    //                and("bi.assignee_id", benutzerID(assignee, mandantengruppe)) +
    //                and("bi.project_id", projectID(mandantengruppe, project)) +
    //                and("bi.status_id", statusID(status)) +
    //                and("bi.priority_id", priorityID(priority)) +
    //                and("bi.version", version);
    //        return createQuery(sql).findList();
    //    }

    public List<IssueBase> loadAllIssuesFromVersionToVersion(final String mandantengruppe, final String project, final String assignee, final String status, final Date versionVon, final Date versionBis, final IssuePriority issuePriority) {

        String sql = "from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe ";
        if (project != null && !project.isEmpty()) {
            sql = sql + "and b.project.projectName=:project ";
        }
        if (assignee != null && !assignee.isEmpty()) {
            sql = sql + "and b.assignee.login=:assignee ";
        }

        if (status != null && !status.isEmpty()) {
            sql = sql + "and b.status.name=:status ";
        }

        if (versionVon != null && versionBis != null) {
            sql = sql + "and (b.version>=:versionVon and b.version<=:versionBis )";
        }
        if (versionVon != null && versionBis == null) {
            sql = sql + "and b.version>=:versionVon ";
        }
        if (versionVon == null && versionBis != null) {
            sql = sql + "and b.version<=:versionBis ";
        }

        if (issuePriority != null) {
            sql = sql + "and b.priority=:priority ";
        }


        TypedQuery<IssueBase> query = entityManager.createQuery(sql, IssueBase.class).setParameter("mandantengruppe", mandantengruppe);
        if (project != null && !project.isEmpty()) {
            query = query.setParameter("project", project);
        }

        if (assignee != null && !assignee.isEmpty()) {
            query = query.setParameter("assignee", assignee);
        }

        if (status != null && !status.isEmpty()) {
            query = query.setParameter("status", status);
        }
        if (versionVon != null) {
            query = query.setParameter("versionVon", versionVon);
        }

        if (versionBis != null) {
            query = query.setParameter("versionBis", versionBis);
        }
        if (issuePriority != null) {
            query = query.setParameter("issuePriority", issuePriority);
        }
        return query.getResultList();


        //        addEq("mandantengruppe.mandantengruppe", mandantengruppe);
        //        addEq("assignee.login", assignee);
        //        addEq("project.projectName", project);
        //        addEq("status.name", status);
        //
        //        final ExpressionList<IssueBase> expressionList = getExpressionList();
        //
        //        setExpressionList(expressionList.between("version", versionVon, versionBis));

        //        addGe("version", versionVon);
        //        addLe("version", versionBis);


        //        return findListForExpressionList();
        //
        //        final String sql = "select id from base_issue bi \n" +
        //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
        //                and("bi.assignee_id", benutzerID(assignee, mandantengruppe)) +
        //                and("bi.project_id", projectID(mandantengruppe, project)) +
        //                and("bi.status_id", statusID(status)) +
        //                andGreaterOrEQ("bi.version", versionVon) +
        //                andSmallerOrEQ("bi.version", versionBis);
        //        return createQuery(sql).findList();
    }


    public List<IssueBase> loadAllIssuesForBenutzer(final String assignee) {
        return entityManager.createQuery("from IssueBase b where b.isssueAssignee.login=:assignee", IssueBase.class).setParameter("assignee", assignee).getResultList();
    }

    public List<IssueBase> loadAllIssuesForBenutzer(final Long assigneeOID) {
        return entityManager.createQuery("from IssueBase b where b.isssueAssignee.id=:assigneeOID", IssueBase.class).setParameter("assigneeOID", assigneeOID).getResultList();
    }

    public List<IssueBase> loadAllIssuesForProject(final String project, final String mandantengruppe) {
        return entityManager.createQuery("from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.project.projectName=:project", IssueBase.class).setParameter("project", project).setParameter("mandantengruppe",
                mandantengruppe).getResultList();

        //        addEq("mandantengruppe.mandantengruppe", mandantengruppe);
        //        addEq("project.projectName", project);
        //        return findListForExpressionList();

        //        final String sql = "select id from base_issue bi \n" +
        //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
        //                and("bi.project_id", projectID(mandantengruppe, project));
        //        return createQuery(sql).findList();
    }

    public List<IssueBase> loadAllIssuesForMandantengruppe(final String mandantengruppe) {
        return entityManager.createQuery("from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe", IssueBase.class).setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //                .eq("project.mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        final String sql = "select id from base_issue bi \n" +
        //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n";
        //        return createQuery(sql).findList();
    }

    public List<IssueBase> loadAllIssuesForStatus(final IssueStatus issueStatus, final String mandantengruppe) {
        return entityManager.createQuery("from IssueBase b where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.status.name=:status", IssueBase.class).setParameter("status", issueStatus).setParameter("mandantengruppe",
                mandantengruppe).getResultList();
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("project.mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("issueStatus.name", issueStatus).findList();
        //        final String sql = "select id from base_issue bi \n" +
        //                " where bi.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
        //                and("bi.status_id", statusID(issueStatus.name()));
        //        return createQuery(sql).findList();
    }

    //    //refac generischer schreiben
    //    public void createSupportIssueForRegistration(final String webAppName, final Benutzer assigne, final Benutzer reporter){
    //        final Mandantengruppe mandantengruppeNeoScio = new MandantengruppeDAO(entityManager).loadMandantengruppe("NeoScioPortal");
    //        final IssueBase issueBase = new IssueBase();
    //        issueBase.setAssignee(assigne);
    //        issueBase.setMandantengruppe(mandantengruppeNeoScio);
    //        final IssuePriority issuePriority = new IssuePriorityDAO(entityManager).loadPriorityDringend_u_Wichtig();
    //        issueBase.setIssuePriority(issuePriority);
    //        issueBase.setReporter(reporter);
    //        issueBase.setSummary("Registration prüfen " + webAppName);
    //        issueBase.setVersion(new DateTime(DateUtil.createNextVersion()).toDate());
    //        issueBase.setText("Registration auf Vollständigkeit prüfen und falls gewünscht freischalten. IssueStatus: freigeschaltet");
    //        issueBase.setProject(new ProjectDAO(entityManager).loadProjectRegistrationen());
    //        issueBase.setIssueStatus(new IssueStatusDAO(entityManager).loadStatusOpen());
    //        issueBase.setDueDate_planned(new Date());
    //        persist(issueBase);
    //    }
    //
    //    public void createSupportIssueForNewPasswdRequest(final String webAppName, final String infoTxt, final Benutzer assigne, final Benutzer reporter){
    //        final Mandantengruppe mandantengruppeNeoScio = new MandantengruppeDAO(entityManager).loadMandantengruppe("NeoScioPortal");
    //        final IssueBase issueBase = new IssueBase();
    //        issueBase.setAssignee(assigne);
    //        issueBase.setMandantengruppe(mandantengruppeNeoScio);
    //        final IssuePriority issuePriority = new IssuePriorityDAO(entityManager).loadPriorityDringend_u_Wichtig();
    //        issueBase.setIssuePriority(issuePriority);
    //        issueBase.setReporter(reporter);
    //        issueBase.setSummary("neues Passwd vergeben " + webAppName);
    //        issueBase.setVersion(new DateTime(DateUtil.createNextVersion()).toDate());
    //        issueBase.setText("Passwd erzeugen und per mail zusenden. " + infoTxt);
    //        issueBase.setProject(new ProjectDAO(entityManager).loadProjectRegistrationen());
    //        issueBase.setIssueStatus(new IssueStatusDAO(entityManager).loadStatusOpen());
    //        issueBase.setDueDate_planned(new Date());
    //        persist(issueBase);
    //    }
    //
    //    public void createSupportIssueForKontaktAnfrage(final String webAppName, final String infoTxt, final Benutzer assigne, final Benutzer reporter){
    //        final Mandantengruppe mandantengruppeNeoScio = new MandantengruppeDAO(entityManager).loadMandantengruppe("NeoScioPortal");
    //        final IssueBase issueBase = new IssueBase();
    //        issueBase.setAssignee(assigne);
    //        issueBase.setMandantengruppe(mandantengruppeNeoScio);
    //        final IssuePriority issuePriority = new IssuePriorityDAO(entityManager).loadPriorityDringend_u_Wichtig();
    //        issueBase.setIssuePriority(issuePriority);
    //        issueBase.setReporter(reporter);
    //        issueBase.setSummary("Kontaktanfrage " + webAppName);
    //        issueBase.setVersion(new DateTime(DateUtil.createNextVersion()).toDate());
    //        issueBase.setText("Kontaktanfrage " + infoTxt);
    //        issueBase.setProject(new ProjectDAO(entityManager).loadProjectRegistrationen());
    //        issueBase.setIssueStatus(new IssueStatusDAO(entityManager).loadStatusOpen());
    //        issueBase.setDueDate_planned(new Date());
    //        persist(issueBase);
    //    }


    //    private boolean isAssignee(final String assignee, final IssueBase issue) {
    //        if (assignee != null && !assignee.isEmpty()) {
    //            final Benutzer issueAssignee = issue.getAssignee();
    //            final boolean b = issueAssignee.getLogin().equals(assignee);
    //            if (b) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //
    //        } else {
    //            return true;
    //        }
    //    }
    //
    //    private boolean isStatus(final String status, final IssueBase issue) {
    //        if (status != null && !status.isEmpty()) {
    //            final IssueStatus issueStatus = issue.getIssueStatus();
    //            final boolean b = issueStatus.name().equals(status);
    //            if (b) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //
    //        } else {
    //            return true;
    //        }
    //    }
    //
    //    private boolean isPriority(final String priority, final IssueBase issue) {
    //        if (priority != null && !priority.isEmpty()) {
    //            final IssuePriority issuePriority = issue.getIssuePriority();
    //            final boolean b = issuePriority.name().equals(priority);
    //            if (b) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //
    //        } else {
    //            return true;
    //        }
    //    }
    //
    //    private boolean isVersion(final String version, final IssueBase issue) {
    //        if (version != null && !version.isEmpty()) {
    //            final Date date = issue.getVersion();
    //            final String issueVersion = format.format(date);
    //            final boolean b = issueVersion.equals(version);
    //            if (b) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        } else {
    //            return true;
    //        }
    //    }

    //    private boolean isVersion(final String versionVon, final String versionBis, final IssueBase issue) {
    //        if ((versionVon != null && !versionVon.isEmpty()) && (versionBis != null && !versionBis.isEmpty())) {
    //            final Date date = issue.getVersion();
    ////            final String issueVersion = format.format(date);
    //            final DateTime issueVersion = new DateTime(date.getTime());
    //            final DateTime von = new DateTime(versionVon);
    //            final DateTime bis = new DateTime(versionBis);
    //
    //            final boolean before = von.isBefore(issueVersion);
    //            final boolean after = bis.isAfter(issueVersion);
    //
    ////            final boolean b = issueVersion.equals(version);
    //            if (before && after) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        } else {
    //            return true;
    //        }
    //    }

    //
    //    private boolean isProject(final String projectName, final IssueBase issue) {
    //        final PlannedProject projectObj = issue.getProject();
    //        final String projectObjName = projectObj.getPlannedProjectName();
    //        if (projectName != null && !projectName.isEmpty()) {
    //            if (projectObjName.equals(projectName)) {
    //                return true;
    //            } else {
    //                return false;
    //            }
    //        } else {
    //            return true;
    //        }
    //    }


}
