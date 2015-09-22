package org.rapidpm.persistence.prj.stammdaten.address;
/**
 * NeoScio
 * User: svenruppert
 * Date: 06.03.2010
 * Time: 18:45:27
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class LandDAO extends DAO<Long, Land> {
  private static final Logger logger = Logger.getLogger(LandDAO.class);

  public LandDAO(final OrientGraph orientDB) {
    super(orientDB, Land.class);
  }


  public Land loadLandForIsoCode(final String isoCode) {
//        final TypedQuery<Land> typedQuery = orientDB.createQuery("from Land l where l.isoCode=:isoCode", Land.class).setParameter("isoCode",
//                isoCode);
//        return getSingleResultOrNull(typedQuery);
    //        return createWhereClause().eq("isoCode", isoCode).findUnique();

    //        Land result = null;
    //        final ObjectSet<Land> laender = orientDB.query(new Predicate<Land>() {
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
    return null;
  }

  @Override
  public Land loadFull(Land entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Land createEntityFull(Land entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    return null;
  }

  //    public Land loadLandForIsoCode(final String isoCode) {
  //        Land result = null;
  //        try {
  //            result = (Land) orientDB.createNamedQuery("LoadLandForIsoCode").setParameter("isoCode", isoCode).getSingleResult();
  //        } catch (Exception e) {
  //            logger.info("Es wurde kein Land für den angegebenen ISO Code gefunden: " + isoCode);
  //
  //        }
  //        return result;
  //    }


}
