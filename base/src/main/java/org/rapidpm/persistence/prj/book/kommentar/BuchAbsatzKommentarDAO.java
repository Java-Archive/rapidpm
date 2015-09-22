package org.rapidpm.persistence.prj.book.kommentar;
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

public class BuchAbsatzKommentarDAO extends DAO<Long, BuchAbsatzKommentar> {
  private static final Logger logger = Logger.getLogger(BuchAbsatzKommentarDAO.class);


  public BuchAbsatzKommentarDAO(final OrientGraph orientDB) {
    super(orientDB, BuchAbsatzKommentar.class);
  }

  @Override
  public BuchAbsatzKommentar loadFull(BuchAbsatzKommentar entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public BuchAbsatzKommentar createEntityFull(BuchAbsatzKommentar entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
