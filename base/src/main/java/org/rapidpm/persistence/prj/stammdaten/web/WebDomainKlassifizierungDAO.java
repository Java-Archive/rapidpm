package org.rapidpm.persistence.prj.stammdaten.web;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.InvalidKeyException;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 02.03.2010
 *        Time: 15:09:34
 */

public class WebDomainKlassifizierungDAO extends DAO<Long, WebDomainKlassifizierung> {
    private static final Logger logger = Logger.getLogger(WebDomainKlassifizierungDAO.class);


    public WebDomainKlassifizierungDAO(final OrientGraph orientDB) {
        super(orientDB, WebDomainKlassifizierung.class);
    }

    public WebDomainKlassifizierung loadGeschaeftlich() {
//        final
//        TypedQuery<WebDomainKlassifizierung>
//                typedQuery =
//                orientDB.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung='geschäftlich'",
//                        WebDomainKlassifizierung.class);
//        return getSingleResultOrNull(typedQuery);
        return null;
        //        return createWhereClause().eq("kategorie", "geschäftlich").findUnique();
        //        final ObjectSet<WebDomainKlassifizierung> objSet = orientDB.query(new Predicate<WebDomainKlassifizierung>() {
        //            @Override
        //            public boolean match(final WebDomainKlassifizierung webDomainKlassifizierung) {
        //                return webDomainKlassifizierung.getWebDomainKlassifizierung().equals("geschäftlich");
        //            }
        //        });
        //        final int size = objSet.size();
        //        final WebDomainKlassifizierung result;
        //        if(size == 1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
        //        return (WebDomainKlassifizierung) orientDB.createNamedQuery("LoadWebDomainKlassifizierungGeschaeftlich").getSingleResult();
    }

    public WebDomainKlassifizierung loadPrivat() {
//        final
//        TypedQuery<WebDomainKlassifizierung>
//                typedQuery =
//                orientDB.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung='privat'", WebDomainKlassifizierung.class);
//        return getSingleResultOrNull(typedQuery);
        return null;

        //        return createWhereClause().eq("kategorie", "privat").findUnique();
        //        final ObjectSet<WebDomainKlassifizierung> objSet = orientDB.query(new Predicate<WebDomainKlassifizierung>() {
        //            @Override
        //            public boolean match(final WebDomainKlassifizierung webDomainKlassifizierung) {
        //                return webDomainKlassifizierung.getWebDomainKlassifizierung().equals("privat");
        //            }
        //        });
        //        final int size = objSet.size();
        //        final WebDomainKlassifizierung result;
        //        if(size == 1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;


        //        return (WebDomainKlassifizierung) orientDB.createNamedQuery("LoadWebDomainKlassifizierungPrivat").getSingleResult();
    }

    public WebDomainKlassifizierung load(final String name) {
//        final
//        TypedQuery<WebDomainKlassifizierung>
//                typedQuery =
//                orientDB.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung=:name",
//                        WebDomainKlassifizierung.class).setParameter("name", name);
//        return getSingleResultOrNull(typedQuery);

        return null;
    }


    @Override
    public WebDomainKlassifizierung loadFull(WebDomainKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
