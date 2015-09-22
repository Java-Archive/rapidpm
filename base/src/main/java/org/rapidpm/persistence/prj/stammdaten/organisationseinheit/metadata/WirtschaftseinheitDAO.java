package org.rapidpm.persistence.prj.stammdaten.organisationseinheit.metadata; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 15.12.11
 * Time: 21:19
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class WirtschaftseinheitDAO extends DAO<Long, Wirtschaftseinheit> {
  private static final Logger logger = Logger.getLogger(WirtschaftseinheitDAO.class);


  public WirtschaftseinheitDAO(final OrientGraph orientDB) {
    super(orientDB, Wirtschaftseinheit.class);
  }

  @Override
  public Wirtschaftseinheit loadFull(Wirtschaftseinheit entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Wirtschaftseinheit createEntityFull(Wirtschaftseinheit entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
