package org.rapidpm.persistence.system.security;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * NeoScio
 * User: Manfred
 * Date: 26.02.2010
 * Time: 12:17:07
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class MandantengruppeDAO extends BaseDAO<Long, Mandantengruppe> {
    private static final Logger logger = Logger.getLogger(MandantengruppeDAO.class);

    public MandantengruppeDAO(final EntityManager entityManager) {
        super(entityManager, Mandantengruppe.class);
    }


    //    public List<Mandantengruppe> loadAllEntities() {
    //        return super.loadAllEntities(Mandantengruppe.class);
    //    }

    //    public Mandantengruppe loadMandantengruppeByBenutzerID(long id) {
    //        entityManager.query(new Predicate<Benutzer>(){
    //            @Override
    //            public boolean match(Benutzer mandantengruppe) {
    //
    //            }
    //        })
    //        final Query query = entityManager.createNamedQuery("LoadMandantengruppeByBenutzerID")
    //                .setParameter("id", id);
    //        Mandantengruppe m;
    //        try {
    //            m = (Mandantengruppe) query.getSingleResult();
    //        } catch (Exception e) {
    //            logger.error(e);
    //            m = null;
    //        }
    //        return m;
    //    }


    public Mandantengruppe loadMandantengruppe(final Mandantengruppe mandantengruppe) {
        return loadMandantengruppe(mandantengruppe.getMandantengruppe());
        //        return createWhereClause().eq("mandantengruppe", mandantengruppenName.getMandantengruppe()).findUnique();
    }

    public Mandantengruppe loadMandantengruppe(final String mandantengruppenName) {
        final TypedQuery<Mandantengruppe> typedQuery = entityManager.createQuery(
                "from Mandantengruppe  m where m.mandantengruppe=:mandantengruppenName",
                Mandantengruppe.class).setParameter("mandantengruppenName", mandantengruppenName);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("mandantengruppe", mandantengruppenName).findUnique();
        //        Mandantengruppe mandantengruppe = null;
        //        final ObjectSet<Mandantengruppe> objSet = entityManager.query(new Predicate<Mandantengruppe>() {
        //            @Override
        //            public boolean match(final Mandantengruppe mandantengruppe) {
        //                return mandantengruppe.getMandantengruppe().equals(mandantengruppenName);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            mandantengruppe = objSet.get(0);
        //        }else{
        //            mandantengruppe = null;
        //        }
        //
        ////        try {
        ////            mandantengruppe = (Mandantengruppe) entityManager.createNamedQuery("LoadMandantengruppe")
        ////                    .setParameter("mandantengruppe", mandantengruppenName)
        ////                    .getSingleResult();
        ////        } catch (Exception e) {
        ////            logger.warn("Konnten mandantengruppe nicht laden " + e);
        ////        }
        //        return mandantengruppe;
    }
}