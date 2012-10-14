package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


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

    public BranchenKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, BrancheKlassifizierung.class);
    }


    public BrancheKlassifizierung loadBranchenKlassifizierung(final String klassifizierung) {
        final TypedQuery<BrancheKlassifizierung> query = entityManager.createQuery(
                "from BrancheKlassifizierung  bk where bk.klassifizierung=:klassifizierung",
                BrancheKlassifizierung.class);
        final TypedQuery<BrancheKlassifizierung> typedQuery = query.setParameter("klassifizierung", klassifizierung);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();
        //        BranchenKlassifizierung result = null;
        //        final ObjectSet<BranchenKlassifizierung> objSet = entityManager.query(new Predicate<BranchenKlassifizierung>() {
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
        //            result = (BranchenKlassifizierung) entityManager.createQuery("select bk from BranchenKlassifizierung bk where bk.klassifizierung='" + klassifizierung + '\'')
        //                    .getSingleResult();
        //        } catch (Exception e) {
        //            logger.error("BranchenKlassifizierung konnte nicht geladen werden: " + e);
        //        }
        //        return result;
    }


}
