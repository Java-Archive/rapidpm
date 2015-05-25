package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 03.03.2010
 *        Time: 17:21:25
 */

public class BrancheDAO extends DAO<Long, Branche> {
    private static final Logger logger = Logger.getLogger(BrancheDAO.class);


    public BrancheDAO(final OrientGraph orientDB) {
        super(orientDB, Branche.class);
    }

    //    protected List<Branche> loadAllEntities() {
    //        return loadAllEntities(Branche.class);
    //    }

    public Branche loadBrancheFor(final String branchenSchluessel) {
//        final TypedQuery<Branche> typedQuery = orientDB.createQuery("from Branche b where b.branchenSchluessel=:branchenSchluessel",
//                Branche.class).setParameter("branchenSchluessel", branchenSchluessel);
//        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("branchen_schluessel", branchenSchluessel).findUnique();

        //        final Branche branche = null;
        //        final ObjectSet<Branche> brancheObjectSet = orientDB.query(new Predicate<Branche>() {
        //            @Override
        //            public boolean match(final Branche branche) {
        //                return branche.getBranchenSchluessel().equals(branchenSchluessel);
        //            }
        //        });

        //        try {
        //            final List<Branche> resultList = orientDB.createNamedQuery("LoadBrancheWZ2008")
        //                    .setParameter("branchenSchluessel", branchenSchluessel)
        //                    .getResultList();
        //
        //            if (resultList.size() == 1) {
        //                branche = resultList.get(0);
        //            } else {
        //                logger.warn("Die gewünschte Branche ist nicht im System verfügbar. " + branchenSchluessel);
        //            }
        //        } catch (Exception e) {
        //            logger.error("Konnte Branche nicht laden " + e);
        //            logger.error("BranchenSchluessel " + branchenSchluessel);
        //        }
        //        return branche;
        return null;
    }

    @Override
    public Branche loadFull(Branche entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
