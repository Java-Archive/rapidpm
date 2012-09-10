package org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 01.09.2010
 *        Time: 16:58:15
 */

public class IssueTimeUnitDAO extends BaseDAO<Long, IssueTimeUnit> {
    private static final Logger logger = Logger.getLogger(IssueTimeUnitDAO.class);

    public IssueTimeUnitDAO(final EntityManager entityManager) {
        super(entityManager, IssueTimeUnit.class);
    }

    //    public List<IssueTimeUnit> loadTimeUnitsForIssue(final long issueNr){
    //        return entityManager.createQuery("from IssueTimeUnit t where t.issueNr=:issueNr", IssueTimeUnit.class).setParameter("issueNr", issueNr).getResultList();
    //        //        return createWhereClause().eq("issue_nr", issueNr).findList();
    //    }


    /**
     * select * from time_unit tu
     * where tu.worker_id = (select id from benutzer b where b.login='sven.ruppert' and
     * b.mandantengruppe_id = (select id from mandantengruppe m where m.mandantengruppe='NeoScioPortal'))
     * and datum >= '2010-11-23'
     * and datum <= '2010-11-30'
     * and issue_nr in (select id from base_issue bi where bi.project_id = (select id from project p where p.project_name='NeoScio - intern - allgemeines'))
     * and issue_nr in (select id from base_issue bi where bi.status_id = (select id from status s where s.name='open'))
     *
     * @param mandantengruppe
     * @param login
     * @param versionVon
     * @param versionBis
     * @param status
     * @return
     */
    public List<IssueTimeUnit> loadTimeUnitsFor(final String mandantengruppe, final String login, final String versionVon, final String versionBis, final String status, final String project) {

        //        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //        final CriteriaQuery<IssueTimeUnit> timeUnitCriteriaQuery = builder.createQuery(IssueTimeUnit.class);
        //        final Root<IssueTimeUnit> timeUnitRoot = timeUnitCriteriaQuery.from(IssueTimeUnit.class);

        //        final CriteriaQuery<AdressKlassifizierung> criteriaQuery = builder.createQuery(AdressKlassifizierung.class);
        //        final Root<AdressKlassifizierung> akAlias = criteriaQuery.from(AdressKlassifizierung.class);


        //        final Path<Object> objectPath = akAlias.get("klassifizierung");
        //        final Predicate predicate = builder.equal(objectPath, objectPath);
        //        final CriteriaQuery<AdressKlassifizierung> where = criteriaQuery.where(predicate);
        //
        //        final AdressKlassifizierung adressKlassifizierung = entityManager.createQuery(criteriaQuery).getSingleResult();
        //        return adressKlassifizierung;

        //        final CriteriaQuery<IssueTimeUnit> selectMain = timeUnitCriteriaQuery.select(timeUnitRoot);
        //
        //        boolean first = true;
        //        if (login != null && !login.isEmpty()) {
        //            final TypedQuery<IssueTimeUnit> queryLogin = entityManager.createQuery("from IssueTimeUnit t where  t.worker.login='" + login + "' ", IssueTimeUnit.class);
        //            final Path<Benutzer> worker = timeUnitRoot.<Benutzer>get("worker");
        //            final Path<String> pathWorkerLogin = worker.<String>get("login");
        //            selectMain.where(pathWorkerLogin)
        //            tim
        //            final CriteriaQuery<IssueTimeUnit> select = timeUnitCriteriaQuery.select(pathWorkerLogin);
        //            builder.equal(timeUnitRoot.get(""))
        //        }
        //
        //        String sql = "select id from time_unit ";
        //
        //        if (login != null && !login.isEmpty()) {
        //            sql = sql + " where worker_id = (select id from benutzer b where b.login='" + login + "' and\n" +
        //                    "      b.mandantengruppe_id = (select id from mandantengruppe m where m.mandantengruppe='" + mandantengruppe + "'))";
        //            first = false;
        //        }
        //
        //
        //        if (versionVon != null && !versionVon.isEmpty()) {
        //            if (!first) {
        //                sql = sql + " and ";
        //            } else {
        //                sql = sql + " where ";
        //            }
        //            sql = sql + " datum >= '" + versionVon + "'";
        //            first = false;
        //        }
        //        if (versionBis != null && !versionBis.isEmpty()) {
        //            if (!first) {
        //                sql = sql + " and ";
        //            } else {
        //                sql = sql + " where ";
        //            }
        //
        //            sql = sql + " datum <= '" + versionBis + "'";
        //            first = false;
        //        }
        //        if (project != null && !project.isEmpty()) {
        //            if (!first) {
        //                sql = sql + " and ";
        //            } else {
        //                sql = sql + " where ";
        //            }
        //
        //            sql = sql + " issue_nr in (select id from base_issue bi where bi.project_id = (select id from project p where p.project_name='" + project + "'))";
        //            first = false;
        //        }
        //        if (status != null && !status.isEmpty()) {
        //            if (!first) {
        //                sql = sql + " and ";
        //            } else {
        //                sql = sql + " where ";
        //            }
        //
        //            sql = sql + " issue_nr in (select id from base_issue bi where bi.status_id = (select id from status s where s.name='" + status + "'))";
        //            first = false;
        //        }
        //        //create RAWL SQL
        //        return createQuery(sql).findList();
        return Collections.emptyList();
    }
}
