package org.rapidpm.orm.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12.01.11
 * Time: 12:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class KommunikationsServiceUIDPartKlassifikationDAO extends BaseDaoFactory.BaseDAO<Long, KommunikationsServiceUIDPartKlassifikation> {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDPartKlassifikationDAO.class);


    public KommunikationsServiceUIDPartKlassifikationDAO(final EntityManager entityManager) {
        super(entityManager, KommunikationsServiceUIDPartKlassifikation.class);
    }

    public KommunikationsServiceUIDPartKlassifikation loadComplete() {
        return load("UID");

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


    public KommunikationsServiceUIDPartKlassifikation load(final String klassifizierung) {
        final TypedQuery<KommunikationsServiceUIDPartKlassifikation> typedQuery = entityManager.createQuery(
                "from KommunikationsServiceUIDPartKlassifikation k where k.bezeichnung=:bezeichnung",
                KommunikationsServiceUIDPartKlassifikation.class).setParameter("bezeichnung", klassifizierung);
        return getSingleResultOrNull(typedQuery);

    }


}
