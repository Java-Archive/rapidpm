package org.rapidpm.persistence.prj.textelement;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * Created with IntelliJ IDEA.
 * User: marco
 * Date: 14.12.12
 * Time: 10:10
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class AbsatzDAO extends DAO<Long, Absatz> {
  private static final Logger logger = Logger.getLogger(AbsatzDAO.class);


  public AbsatzDAO(final OrientGraph orientDB) {
    super(orientDB, Absatz.class);
  }

  @Override
  public Absatz loadFull(Absatz entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Absatz createEntityFull(Absatz entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}