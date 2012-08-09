package org.rapidpm.orm.prj.stammdaten.address;
/**
 * NeoScio
 * User: svenruppert
 * Date: 06.03.2010
 * Time: 18:45:27
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class LandDAO extends BaseDaoFactory.BaseDAO<Long, Land> {
    private static final Logger logger = Logger.getLogger(LandDAO.class);

    public LandDAO(final EntityManager entityManager) {
        super(entityManager, Land.class);
    }


    public Land loadLandForIsoCode(final String isoCode) {
        final TypedQuery<Land> typedQuery = entityManager.createQuery("from Land l where l.isoCode=:isoCode", Land.class).setParameter("isoCode",
                isoCode);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("isoCode", isoCode).findUnique();

        //        Land result = null;
        //        final ObjectSet<Land> laender = entityManager.query(new Predicate<Land>() {
        //            @Override
        //            public boolean match(final Land land) {
        //                final String iso = land.getIsoCode();
        //                return iso.equals(isoCode);
        //            }
        //        });
        //        final int size = laender.size();
        //        if(size == 1){
        //            result = laender.get(0);
        //        }else{
        //          result = null;
        //        }
        //        return result;
    }

    //    public Land loadLandForIsoCode(final String isoCode) {
    //        Land result = null;
    //        try {
    //            result = (Land) entityManager.createNamedQuery("LoadLandForIsoCode").setParameter("isoCode", isoCode).getSingleResult();
    //        } catch (Exception e) {
    //            logger.info("Es wurde kein Land für den angegebenen ISO Code gefunden: " + isoCode);
    //
    //        }
    //        return result;
    //    }


}
