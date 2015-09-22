package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.gesellschaftsformen.Gesellschaftsform;

import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 09.03.2010
 * Time: 18:21:02
 */

public class GesellschaftsformDAO extends DAO<Long, Gesellschaftsform> {
  public static final String UNBEKANNT = "";
  private static final Logger logger = Logger.getLogger(GesellschaftsformDAO.class);

  public GesellschaftsformDAO(final OrientGraph orientDB) {
    super(orientDB, Gesellschaftsform.class);
  }

  @Override
  public Gesellschaftsform loadFull(Gesellschaftsform entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Gesellschaftsform createEntityFull(Gesellschaftsform entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
