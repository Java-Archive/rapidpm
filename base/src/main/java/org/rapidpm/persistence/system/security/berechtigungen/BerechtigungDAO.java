package org.rapidpm.persistence.system.security.berechtigungen;

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
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 19.09.2010
 * Time: 18:01:24
 */

public class BerechtigungDAO extends DAO<Long, Berechtigung> {
  private static final Logger logger = Logger.getLogger(BerechtigungDAO.class);

  public BerechtigungDAO(final OrientGraph orientDB) {
    super(orientDB, Berechtigung.class);
  }

  public Berechtigung loadBerechtigung(final String name) {
    if (logger.isInfoEnabled()) {
      logger.info("loadBerechtigung : " + name);
    }
//        final TypedQuery<Berechtigung> query = orientDB.createQuery("from Berechtigung  b where b.name=:name", Berechtigung.class)
//                .setParameter("name", name);
//        return getSingleResultOrNull(query);
    return null;
    //        return createWhereClause().eq("name", name).findUnique();

    //        final ObjectSet<Berechtigung> berechtigungObjectSet = orientDB.query(new Predicate<Berechtigung>() {
    //            @Override
    //            public boolean match(final Berechtigung berechtigung) {
    //                return berechtigung.getName().equals(name);
    //            }
    //        });
    //
    //        if (berechtigungObjectSet.size() == 1) {
    //            return berechtigungObjectSet.get(0);
    //        } else {
    //            return null;
    //        }
  }


  @Override
  public Berechtigung loadFull(Berechtigung entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Berechtigung createEntityFull(Berechtigung entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}