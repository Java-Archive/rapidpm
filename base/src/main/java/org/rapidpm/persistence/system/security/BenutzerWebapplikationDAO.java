package org.rapidpm.persistence.system.security;

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
 * @since 23.07.2010
 * Time: 20:59:59
 */

public class BenutzerWebapplikationDAO extends DAO<Long, BenutzerWebapplikation> {
  private static final Logger logger = Logger.getLogger(BenutzerWebapplikationDAO.class);

  public BenutzerWebapplikationDAO(final OrientGraph orientDB) {
    super(orientDB, BenutzerWebapplikation.class);
  }

  //    public List<BenutzerWebapplikation> loadAllEntities() {
  //        return loadAllEntities(BenutzerWebapplikation.class);
  //    }

  public BenutzerWebapplikation loadBenutzerWebapplikation(final String name) {
//        final TypedQuery<BenutzerWebapplikation> typedQuery = orientDB.createQuery("from BenutzerWebapplikation  bw where bw.webappName=:name",
//                BenutzerWebapplikation.class).setParameter("name", name);
//        return getSingleResultOrNull(typedQuery);
    return null;
    //        return createWhereClause().eq("webapp_name", name).findUnique();
    //        BenutzerWebapplikation webapplikation = null;
    //        final ObjectSet<BenutzerWebapplikation> objSet = orientDB.query(new Predicate<BenutzerWebapplikation>() {
    //            @Override
    //            public boolean match(final BenutzerWebapplikation webapplikation) {
    //                return webapplikation.getWebappName().equals(name);
    //            }
    //        });
    //        final int size = objSet.size();
    //        if (size == 1) {
    //            webapplikation = objSet.get(0);
    //        } else {
    //            webapplikation = null;
    //        }
    //        return webapplikation;
  }


  @Override
  public BenutzerWebapplikation loadFull(BenutzerWebapplikation entity) throws InvalidKeyException, NotYetImplementedException {
    return findByID(entity.getId(), false);
  }

  @Override
  public BenutzerWebapplikation createEntityFull(BenutzerWebapplikation entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    return createEntityFlat(entity);
  }
}