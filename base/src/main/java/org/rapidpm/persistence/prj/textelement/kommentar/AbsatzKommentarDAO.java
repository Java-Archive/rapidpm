package org.rapidpm.persistence.prj.textelement.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:04
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class AbsatzKommentarDAO extends DAO<Long, AbsatzKommentar> {
  private static final Logger logger = Logger.getLogger(AbsatzKommentarDAO.class);


  public AbsatzKommentarDAO(final OrientGraph orientDB) {
    super(orientDB, AbsatzKommentar.class);
  }

  @Override
  public AbsatzKommentar loadFull(AbsatzKommentar entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public AbsatzKommentar createEntityFull(AbsatzKommentar entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
