package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 10.03.2010
 * Time: 08:24:08
 */

public class TaetigkeitsfeldKlassifizierungDAO extends DAO<Long, TaetigkeitsfeldKlassifizierung> {
  private static final Logger logger = Logger.getLogger(TaetigkeitsfeldKlassifizierungDAO.class);

  public TaetigkeitsfeldKlassifizierungDAO(final OrientGraph orientDB) {
    super(orientDB, TaetigkeitsfeldKlassifizierung.class);
  }

  public TaetigkeitsfeldKlassifizierung loadTaetigkeitsKlassifizierung(final String klassifizierung) {
    return load(klassifizierung);
    //        try {
    //            taetigkeitsfeldKlassifizierung = (TaetigkeitsfeldKlassifizierung) orientDB.createNamedQuery("LoadTaetigkeitsfKlassifizierung")
    //                    .setParameter("klassifizierung", klassifizierung)
    //                    .getSingleResult();
    //        } catch (Exception e) {
    //            logger.info("Geforderte TaetigkeitsKlassifizierung nicht im System enthalten.. " + klassifizierung);
    //            logger.info("Geforderte TaetigkeitsKlassifizierung nicht im System enthalten.. setzte Klassifizierung Unbekannt");
    //            taetigkeitsfeldKlassifizierung = (TaetigkeitsfeldKlassifizierung) orientDB.createNamedQuery("LoadTaetigkeitsfKlassifizierungUnbekannt")
    //                    .getSingleResult();
    //        }
    //        return taetigkeitsfeldKlassifizierung;
  }

  private TaetigkeitsfeldKlassifizierung load(final String klassifizierung) {
//        final TypedQuery<TaetigkeitsfeldKlassifizierung> typedQuery = orientDB.createQuery(
//                "from TaetigkeitsfeldKlassifizierung t where t.klassifizierung=:klassifizierung",
//                TaetigkeitsfeldKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
//        return getSingleResultOrNull(typedQuery);
    return null;
    //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();

    //        final TaetigkeitsfeldKlassifizierung taetigkeitsfeldKlassifizierung;
    //        final ObjectSet<TaetigkeitsfeldKlassifizierung> objSet = orientDB.query(new Predicate<TaetigkeitsfeldKlassifizierung>() {
    //            @Override
    //            public boolean match(final TaetigkeitsfeldKlassifizierung taetigkeitsfeldKlassifizierung) {
    //                return taetigkeitsfeldKlassifizierung.getKlassifizierung().equals(klassifizierung);
    //            }
    //        });
    //        final int size = objSet.size();
    //        if(size==1){
    //            taetigkeitsfeldKlassifizierung = objSet.get(0);
    //        }else{
    //            taetigkeitsfeldKlassifizierung = null;
    //        }
    //        return taetigkeitsfeldKlassifizierung;
  }


  public TaetigkeitsfeldKlassifizierung loadTaetigkeitsfeldKlassifizierungHptTaetigkeit() {
    return load("Haupttätigkeit");
    //        return (TaetigkeitsfeldKlassifizierung) orientDB.createNamedQuery("LoadTaetigkeitsfKlassifizierungHptTaetigkeit").getSingleResult();
  }

  public TaetigkeitsfeldKlassifizierung loadTaetigkeitsfeldKlassifizierungNebenTaetigkeit() {
    return load("Nebentätigkeit");
    //        return (TaetigkeitsfeldKlassifizierung) orientDB.createNamedQuery("LoadTaetigkeitsfKlassifizierungNebenTaetigkeit").getSingleResult();
  }

  public TaetigkeitsfeldKlassifizierung loadTaetigkeitsfeldKlassifizierungUnbekannt() {
    return load("Unbekannt");
    //        return (TaetigkeitsfeldKlassifizierung) orientDB.createNamedQuery("LoadTaetigkeitsfKlassifizierungUnbekannt").getSingleResult();
  }


  @Override
  public TaetigkeitsfeldKlassifizierung loadFull(TaetigkeitsfeldKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public TaetigkeitsfeldKlassifizierung createEntityFull(TaetigkeitsfeldKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
