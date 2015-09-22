/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence.prj.stammdaten.person;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.rapidpm.Constants;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 20.12.11
 * Time: 15:14
 */
public class GeschlechtDAO extends DAO<Long, Geschlecht> {

  public GeschlechtDAO(final OrientGraph orientDB) {
    super(orientDB, org.rapidpm.persistence.prj.stammdaten.person.Geschlecht.class);
  }

  public Geschlecht loadGeschlechtMaennlich() {
    return load(Constants.GESCHLECHT_M);
  }

  public Geschlecht load(final String geschlechtTxt) {
//        final TypedQuery<Geschlecht> typedQuery = orientDB.createQuery("from Geschlecht g where g.geschlecht=:geschlechtTxt",
//                Geschlecht.class).setParameter("geschlechtTxt", geschlechtTxt);
//        return getSingleResultOrNull(typedQuery);
    return null;
  }

  public Geschlecht loadGeschlechtWeiblich() {
    return load(Constants.GESCHLECHT_W);
  }

  public Geschlecht loadGeschlechtNothing() {
    return load("Nothing");
  }

  @Override
  public Geschlecht loadFull(Geschlecht entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Geschlecht createEntityFull(Geschlecht entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
