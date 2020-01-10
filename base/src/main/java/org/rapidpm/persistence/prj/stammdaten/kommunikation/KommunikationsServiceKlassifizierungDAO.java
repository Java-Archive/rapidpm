package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 02.03.2010
 *        Time: 14:21:56
 */

public class KommunikationsServiceKlassifizierungDAO extends DAO<Long, KommunikationsServiceKlassifizierung> {

    public KommunikationsServiceKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, KommunikationsServiceKlassifizierung.class);
    }


    public KommunikationsServiceKlassifizierung load(final String klassifizierung) {
        final TypedQuery<KommunikationsServiceKlassifizierung> typedQuery = entityManager.createQuery(
                "from KommunikationsServiceKlassifizierung ksk where ksk.klassifizierung=:klassifizierung",
                KommunikationsServiceKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
        return getSingleResultOrNull(typedQuery);

    }

    public KommunikationsServiceKlassifizierung loadKlassifizierungPrivat() {
        return load("privat");
        //        return createWhereClause().eq("klassifizierung", "private").findUnique();

    }

    public KommunikationsServiceKlassifizierung loadKlassifizierungBeruflich() {
        return load("beruflich");
        //        return createWhereClause().eq("klassifizierung", "beruflich").findUnique();
    }


}
