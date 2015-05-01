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

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NamensKlassifizierungDAO extends DAO<Long, NamensKlassifizierung> {
    private static final Logger logger = Logger.getLogger(NamensKlassifizierungDAO.class);

    public NamensKlassifizierungDAO(final OrientGraph orientDB) {
        super(orientDB, NamensKlassifizierung.class);
    }

    public NamensKlassifizierung loadNamensKlassifizierungVorname() {
        return load("Vorname");
        //        return (NamensKlassifizierung) orientDB.createNamedQuery("LoadNamensKlassifizierungVorname").getSingleResult();
    }

    private NamensKlassifizierung load(final String klassifizierung) {
//        final TypedQuery<NamensKlassifizierung> typedQuery = orientDB.createQuery(
//                "from NamensKlassifizierung nk where nk.klassifizierung=:klassifizierung",
//                NamensKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
//        return getSingleResultOrNull(typedQuery);
        return null;
        //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();
        //        final NamensKlassifizierung result;
        //        final ObjectSet<NamensKlassifizierung> objSet = orientDB.query(new Predicate<NamensKlassifizierung>() {
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
