package org.rapidpm.persistence.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 12:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;

public class KommunikationsServiceUIDPartKlassifikationDAO extends DAO<Long, KommunikationsServiceUIDPartKlassifikation> {
  private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDPartKlassifikationDAO.class);


  public KommunikationsServiceUIDPartKlassifikationDAO(final OrientGraph orientDB) {
    super(orientDB, KommunikationsServiceUIDPartKlassifikation.class);
  }

  public KommunikationsServiceUIDPartKlassifikation loadComplete() {
    return load("UID");

  }

  public KommunikationsServiceUIDPartKlassifikation load(final String klassifizierung) {
//        final TypedQuery<KommunikationsServiceUIDPartKlassifikation> typedQuery = orientDB.createQuery(
//                "from KommunikationsServiceUIDPartKlassifikation k where k.bezeichnung=:bezeichnung",
//                KommunikationsServiceUIDPartKlassifikation.class).setParameter("bezeichnung", klassifizierung);
//        return getSingleResultOrNull(typedQuery);
    return null;
  }

  public KommunikationsServiceUIDPartKlassifikation loadLandeskennziffer() {
    return load("Landeskennziffer");
  }

  public KommunikationsServiceUIDPartKlassifikation loadVorwahl() {
    return load("Vorwahl");
  }

  public KommunikationsServiceUIDPartKlassifikation loadNummer() {
    return load("Nummer");
  }

  public KommunikationsServiceUIDPartKlassifikation loadDurchwahl() {
    return load("Durchwahl");
  }

  @Override
  public KommunikationsServiceUIDPartKlassifikation loadFull(KommunikationsServiceUIDPartKlassifikation entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public KommunikationsServiceUIDPartKlassifikation createEntityFull(KommunikationsServiceUIDPartKlassifikation entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }
}
