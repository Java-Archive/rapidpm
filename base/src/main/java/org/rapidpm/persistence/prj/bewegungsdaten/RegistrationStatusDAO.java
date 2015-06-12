package org.rapidpm.persistence.prj.bewegungsdaten;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * NeoScio
 * User: Manfred
 * Date: 22.02.2010
 * Time: 18:39:09
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class RegistrationStatusDAO extends DAO<Long, RegistrationStatus> {
    private static final Logger logger = Logger.getLogger(RegistrationStatusDAO.class);

    //    public List<RegistrationStatus> loadAllEntities() {
    //        return super.loadAllEntities(RegistrationStatus.class);
    //    }

    public RegistrationStatusDAO(final OrientGraph orientDB) {
        super(orientDB, RegistrationStatus.class);
    }

    public RegistrationStatus loadRegistrationStatus_Zur_Pruefung() {
        return load("Zur_Prüfung");


        //        try {
        //            final Query query = orientDB.createNamedQuery("LoadRegistrationStatus_Zur_Pruefung");
        //            result = (RegistrationStatus) query.getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
    }

    public RegistrationStatus load(final String txt) {
//        final TypedQuery<RegistrationStatus> typedQuery = orientDB.createQuery("from RegistrationStatus  rs where rs.status=:txt",
//                RegistrationStatus.class).setParameter("txt", txt);
//        return getSingleResultOrNull(typedQuery);
        return null;
        //        return createWhereClause().eq("status", txt).findUnique();
        //        final RegistrationStatus result;
        //        final ObjectSet<RegistrationStatus> objSet = orientDB.query(new Predicate<RegistrationStatus>() {
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
        //            final Query query = orientDB.createNamedQuery("LoadRegistrationStatus_abgelehnt");
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
        //            final Query query = orientDB.createNamedQuery("LoadRegistrationStatus_freigeschaltet");
        //            result = (RegistrationStatus) query.getSingleResult();
        //        } catch (Exception e) {
        //            logger.error(e);
        //            result = null;
        //        }
        //        return result;
    }

    @Override
    public RegistrationStatus loadFull(RegistrationStatus entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }

    @Override
    public RegistrationStatus createEntityFull(RegistrationStatus entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
        return null;
    }
}
