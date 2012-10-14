package org.rapidpm.persistence.prj.stammdaten.person;
/**
 * NeoScio
 * User: svenruppert
 * Date: 04.03.2010
 * Time: 17:34:02
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NamensKlassifizierungDAO extends DAO<Long, NamensKlassifizierung> {
    private static final Logger logger = Logger.getLogger(NamensKlassifizierungDAO.class);

    public NamensKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, NamensKlassifizierung.class);
    }

    public NamensKlassifizierung loadNamensKlassifizierungVorname() {
        return load("Vorname");
        //        return (NamensKlassifizierung) entityManager.createNamedQuery("LoadNamensKlassifizierungVorname").getSingleResult();
    }

    private NamensKlassifizierung load(final String klassifizierung) {
        final TypedQuery<NamensKlassifizierung> typedQuery = entityManager.createQuery(
                "from NamensKlassifizierung nk where nk.klassifizierung=:klassifizierung",
                NamensKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();
        //        final NamensKlassifizierung result;
        //        final ObjectSet<NamensKlassifizierung> objSet = entityManager.query(new Predicate<NamensKlassifizierung>() {
        //            @Override
        //            public boolean match(final NamensKlassifizierung namensKlassifizierung) {
        //                return namensKlassifizierung.getKlassifizierung().equals(vorname);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
    }

    public NamensKlassifizierung loadNamensKlassifizierungNachname() {
        return load("Nachname");
    }

    public NamensKlassifizierung loadNamensKlassifizierungGeburtsname() {
        return load("Geburtsname");
    }


}
