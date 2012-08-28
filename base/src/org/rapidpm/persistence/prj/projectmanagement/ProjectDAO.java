package org.rapidpm.persistence.prj.projectmanagement;

import org.rapidpm.persistence.BaseDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
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
 *        Time: 21:18:43
 */

public class ProjectDAO extends BaseDaoFactory.BaseDAO<Long, PlannedProject> {

    private static final Logger logger = Logger.getLogger(ProjectDAO.class);


    public ProjectDAO(final EntityManager entityManager) {
        super(entityManager, PlannedProject.class);
    }

    public List<PlannedProject> loadProjectsFor(final Boolean active, final String mandantengruppe) {
        return entityManager.createQuery("from Project  p where p.active=:active and p.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("active", active).setParameter("mandantengruppe", mandantengruppe).getResultList();
        //        return createWhereClause().eq("active", active).eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        final String sql = "select id from project p \n" +
        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
        //                and("bi.active", active.toString());
        //        return createQuery(sql).findList();

    }

    public List<PlannedProject> loadProjectsFor(final String mandantengruppe) {
        return entityManager.createQuery("from Project  p where p.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).getResultList();
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();

        //        final String sql = "select id from project p \n" +
        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n";
        //        return createQuery(sql).findList();
    }

    //    public PlannedProject loadProjectFor(final String mandantengruppe, final String prjName){
    //        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
    //                "from PlannedProject  p where p.mandantengruppe.mandantengruppe=:mandantengruppe and p.projectName=:prjName",
    //                PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).setParameter("prjName", prjName);
    //        return getSingleResultOrNull(typedQuery);
    //        //        return createWhereClause().eq("projectName", prjName).eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
    //        //        final String sql = "select id from project p \n" +
    //        //                " where p.mandantengruppe_id = (" + mandantengruppeID(mandantengruppe) + ") \n" +
    //        //                and("bi.project_name", prjName);
    //        //        return createQuery(sql).findUnique();
    //
    //    }

}
