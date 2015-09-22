package org.rapidpm.persistence.prj.book.kommentar;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class BuchKapitelKommentarDAO extends DAO<Long, BuchKapitelKommentar> {
  private static final Logger logger = Logger.getLogger(BuchKapitelKommentarDAO.class);


  public BuchKapitelKommentarDAO(final OrientGraph orientDB) {
    super(orientDB, BuchKapitelKommentar.class);
  }

  @Override
  public BuchKapitelKommentar loadFull(BuchKapitelKommentar entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public BuchKapitelKommentar createEntityFull(BuchKapitelKommentar entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
