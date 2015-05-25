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
 *        Time: 20:52:12
 */

public class BranchenKlassifizierungDAO extends DAO<Long, BrancheKlassifizierung> {
    private static final Logger logger = Logger.getLogger(BranchenKlassifizierungDAO.class);

    public BranchenKlassifizierungDAO(final OrientGraph orientDB) {
        super(orientDB, BrancheKlassifizierung.class);
    }


    public BrancheKlassifizierung loadBranchenKlassifizierung(final String klassifizierung) {
//        final TypedQuery<BrancheKlassifizierung> query = orientDB.createQuery(
//                "from BrancheKlassifizierung  bk where bk.klassifizierung=:klassifizierung",
//                BrancheKlassifizierung.class);
//        final TypedQuery<BrancheKlassifizierung> typedQuery = query.setParameter("klassifizierung", klassifizierung);
//        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();
        //        BranchenKlassifizierung result = null;
        //        final ObjectSet<BranchenKlassifizierung> objSet = orientDB.query(new Predicate<BranchenKlassifizierung>() {
        //            @Override
        //            public boolean match(final BranchenKlassifizierung branchenKlassifizierung) {
        //                return branchenKlassifizierung.getKlassifizierung().equals(klassifizierung);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }

        //        try {
        //            result = (BranchenKlassifizierung) orientDB.createQuery("select bk from BranchenKlassifizierung bk where bk.klassifizierung='" + klassifizierung + '\'')
        //                    .getSingleResult();
        //        } catch (Exception e) {
        //            logger.error("BranchenKlassifizierung konnte nicht geladen werden: " + e);
        //        }
        //        return result;
        return null;
    }


    @Override
    public BrancheKlassifizierung loadFull(BrancheKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
