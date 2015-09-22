package org.rapidpm.persistence.prj.stammdaten.organisationseinheit; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 18.03.11
 * Time: 19:53
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;
import java.util.List;

public class PositionDAO extends DAO<Long, Position> {
  private static final Logger logger = Logger.getLogger(PositionDAO.class);


  public PositionDAO(final OrientGraph orientDB) {
    super(orientDB, Position.class);
  }

  public List<Position> loadPositionForOrgeinheit(final Long orgOID) {
//        final TypedQuery<Position> typedQuery = orientDB.createQuery("from Position p where  p.organisationseinheit.id=:orgOID", Position.class)
//                .setParameter("orgOID", orgOID);
//        return typedQuery.getResultList();
    return null;
  }


  @Override
  public Position loadFull(Position entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Position createEntityFull(Position entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
