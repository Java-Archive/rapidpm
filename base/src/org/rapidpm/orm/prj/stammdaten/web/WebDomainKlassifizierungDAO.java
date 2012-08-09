package org.rapidpm.orm.prj.stammdaten.web;

import org.rapidpm.orm.BaseDaoFactory;
import org.apache.log4j.Logger;

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
 *        Time: 15:09:34
 */

public class WebDomainKlassifizierungDAO extends BaseDaoFactory.BaseDAO<Long, WebDomainKlassifizierung> {
    private static final Logger logger = Logger.getLogger(WebDomainKlassifizierungDAO.class);


    public WebDomainKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, WebDomainKlassifizierung.class);
    }

    public WebDomainKlassifizierung loadGeschaeftlich() {
        final
        TypedQuery<WebDomainKlassifizierung>
                typedQuery =
                entityManager.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung='geschäftlich'",
                        WebDomainKlassifizierung.class);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("kategorie", "geschäftlich").findUnique();
        //        final ObjectSet<WebDomainKlassifizierung> objSet = entityManager.query(new Predicate<WebDomainKlassifizierung>() {
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
        //        return (WebDomainKlassifizierung) entityManager.createNamedQuery("LoadWebDomainKlassifizierungGeschaeftlich").getSingleResult();
    }

    public WebDomainKlassifizierung loadPrivat() {
        final
        TypedQuery<WebDomainKlassifizierung>
                typedQuery =
                entityManager.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung='privat'", WebDomainKlassifizierung.class);
        return getSingleResultOrNull(typedQuery);

        //        return createWhereClause().eq("kategorie", "privat").findUnique();
        //        final ObjectSet<WebDomainKlassifizierung> objSet = entityManager.query(new Predicate<WebDomainKlassifizierung>() {
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


        //        return (WebDomainKlassifizierung) entityManager.createNamedQuery("LoadWebDomainKlassifizierungPrivat").getSingleResult();
    }

    public WebDomainKlassifizierung load(final String name) {
        final
        TypedQuery<WebDomainKlassifizierung>
                typedQuery =
                entityManager.createQuery("from WebDomainKlassifizierung wdk " + "where wdk.klassifizierung=:name",
                        WebDomainKlassifizierung.class).setParameter("name", name);
        return getSingleResultOrNull(typedQuery);

    }


}
