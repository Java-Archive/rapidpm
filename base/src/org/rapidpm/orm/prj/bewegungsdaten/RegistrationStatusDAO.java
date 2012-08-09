package org.rapidpm.orm.prj.bewegungsdaten;

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * NeoScio
 * User: Manfred
 * Date: 22.02.2010
 * Time: 18:39:09
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class RegistrationStatusDAO extends BaseDaoFactory.BaseDAO<Long, RegistrationStatus> {
    private static final Logger logger = Logger.getLogger(RegistrationStatusDAO.class);

    //    public List<RegistrationStatus> loadAllEntities() {
    //        return super.loadAllEntities(RegistrationStatus.class);
    //    }

    public RegistrationStatusDAO(final EntityManager entityManager) {
        super(entityManager, RegistrationStatus.class);
    }

    public RegistrationStatus loadRegistrationStatus_Zur_Pruefung() {
        return load("Zur_Prüfung");


        //        try {
        //            final Query query = entityManager.createNamedQuery("LoadRegistrationStatus_Zur_Pruefung");
        //            result = (RegistrationStatus) query.getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
    }

    public RegistrationStatus load(final String txt) {
        final TypedQuery<RegistrationStatus> typedQuery = entityManager.createQuery("from RegistrationStatus  rs where rs.status=:txt",
                RegistrationStatus.class).setParameter("txt", txt);
        return getSingleResultOrNull(typedQuery);

        //        return createWhereClause().eq("status", txt).findUnique();
        //        final RegistrationStatus result;
        //        final ObjectSet<RegistrationStatus> objSet = entityManager.query(new Predicate<RegistrationStatus>() {
        //            @Override
        //            public boolean match(final RegistrationStatus registrationStatus) {
        //                final String status = registrationStatus.getIssueStatus();
        //                return status.equals(txt);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
    }

    public RegistrationStatus loadRegistrationStatus_abgelehnt() {
        return load("Abgelehnt");
        //        RegistrationStatus result;
        //        try {
        //            final Query query = entityManager.createNamedQuery("LoadRegistrationStatus_abgelehnt");
        //            result = (RegistrationStatus) query.getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
    }

    public RegistrationStatus loadRegistrationStatus_freigeschaltet() {
        return load("freigeschaltet");
        //        RegistrationStatus result;
        //        try {
        //            final Query query = entityManager.createNamedQuery("LoadRegistrationStatus_freigeschaltet");
        //            result = (RegistrationStatus) query.getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
    }
}
