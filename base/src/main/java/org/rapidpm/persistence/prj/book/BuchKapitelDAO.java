package org.rapidpm.persistence.prj.book;
/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 14.02.11
 * Time: 18:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class BuchKapitelDAO extends DAO<Long, BuchKapitel> {
  private static final Logger logger = Logger.getLogger(BuchKapitelDAO.class);


  public BuchKapitelDAO(final OrientGraph orientDB) {
    super(orientDB, BuchKapitel.class);
  }

  @Override
  public BuchKapitel loadFull(BuchKapitel entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public BuchKapitel createEntityFull(BuchKapitel entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
