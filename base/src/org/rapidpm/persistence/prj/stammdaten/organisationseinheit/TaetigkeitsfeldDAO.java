package org.rapidpm.persistence.prj.stammdaten.organisationseinheit;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 14.03.2010
 *        Time: 12:48:29
 */

public class TaetigkeitsfeldDAO extends DAO<Long, Taetigkeitsfeld> {
    private static final Logger logger = Logger.getLogger(TaetigkeitsfeldDAO.class);


    public TaetigkeitsfeldDAO(final EntityManager entityManager) {
        super(entityManager, Taetigkeitsfeld.class);
    }

    public Taetigkeitsfeld loadTaetigkeitsfeldFor(final String taetigkeitsfeldName) {
        final Taetigkeitsfeld taetigkeitsfeld;
        if (taetigkeitsfeldName == null || taetigkeitsfeldName.isEmpty()) {
            final TypedQuery<Taetigkeitsfeld> typedQuery = entityManager.createQuery("from Taetigkeitsfeld  t where t.namen='Nicht eingeordnet'", Taetigkeitsfeld.class);
            taetigkeitsfeld = getSingleResultOrNull(typedQuery);
        } else {
            try {
                final TypedQuery<Taetigkeitsfeld> typedQuery = entityManager.createQuery("from Taetigkeitsfeld  t where t.namen=:taetigkeitsfeldName", Taetigkeitsfeld.class).setParameter("taetigkeitsfeldName", taetigkeitsfeldName);
                taetigkeitsfeld = getSingleResultOrNull(typedQuery);
            } catch (Exception e) {
                logger.error(e);
                final TypedQuery<Taetigkeitsfeld> typedQuery = entityManager.createQuery("from Taetigkeitsfeld  t where t.namen='Nicht eingeordnet'", Taetigkeitsfeld.class);
                return getSingleResultOrNull(typedQuery);
            }
        }
        return taetigkeitsfeld;
        //        return createWhereClause().eq("namen", taetigkeitsfeldName).findUnique();
        //        final Taetigkeitsfeld result;
        //
        //        final ObjectSet<Taetigkeitsfeld> objSet = entityManager.query(new Predicate<Taetigkeitsfeld>() {
        //            @Override
        //            public boolean match(final Taetigkeitsfeld taetigkeitsfeld) {
        //                return taetigkeitsfeld.getNamen().equals(taetigkeitsfeldName);
        //            }
        //        });
        //        final int size = objSet.size();
        //        if(size==1){
        //            result = objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
        //        Taetigkeitsfeld result = (Taetigkeitsfeld) entityManager.createNamedQuery("LoadTaetigkeitsfeldFor")
        //                .setParameter("taetigkeitsfeld", taetigkeitsfeld).getSingleResult();
        //        if (result == null) {
        //            logger.warn("Das angeforderte Taetigkeitsfeld ist nicht in der DB : " + taetigkeitsfeld + " lade Taetigkeitsfeld Unbekannt");
        //            result = (Taetigkeitsfeld) entityManager.createNamedQuery("LoadTaetigkeitsfeld_NichtEingeordnet").getSingleResult();
        //        }
        //        return result;
    }

    //TODO refactoring performance 

    public List<Taetigkeitsfeld> loadTaetigkeitsfelderForMandantengruppe(final Mandantengruppe mandantengruppe) {
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        final List<Taetigkeitsfeld> resultList = entityManager.createQuery("from Taetigkeitsfeld t where t.id in ( select distinct(ta.taetigkeitsfeld.id) from TaetigkeitsfeldAssoc ta  where ta.mandantengruppe.id=:mandantengruppe)",
                Taetigkeitsfeld.class).setParameter("mandantengruppe", mandantengruppe.getId()).getResultList();

        if (resultList.isEmpty()) {
            resultList.add(loadTaetigkeitsfeldNichtEingeordnet());
        } else {
            //
        }
        return resultList;
    }

    public List<Taetigkeitsfeld> loadTaetigkeitsfelderForMandantengruppe(final String mandantengruppe) {
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        return entityManager.createQuery("from Taetigkeitsfeld t where t.id in ( select distinct(ta.taetigkeitsfeld.id) from TaetigkeitsfeldAssoc ta " + "where ta.mandantengruppe.mandantengruppe=:mandantengruppe)",
                Taetigkeitsfeld.class).setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        final String sql = "select id from taetigkeitsfeld t where t.id in ( \n" +
        //                "select distinct(taetigkeitsfeld_id)   from taetigkeitsfeld_assoc ta \n" +
        //                "where ta.mandantengruppe_id = " + SQLCreator.mandantengruppeID(mandantengruppe) + "\n" +
        //                ")";
        //        return createQuery(sql).findList();


        //        final ObjectSet<Organisationseinheit> objSetOrgEinheiten = entityManager.query(new Predicate<Organisationseinheit>() {
        //            @Override
        //            public boolean match(final Organisationseinheit organisationseinheiten) {
        //                boolean result = false;
        //                final Set<Mandantengruppe> mandantengruppenSet = organisationseinheiten.getMandantengruppen();
        //                for (final Mandantengruppe mandantengrpObj : mandantengruppenSet) {
        //                    final boolean b1 = mandantengrpObj.getMandantengruppe().equals(mandantengruppe);
        //                    if (b1) {
        //                        result = true;
        //                        break;
        //                    } else {
        //                        //
        //                    }
        //                }
        //                return result;
        //            }
        //        });
        //
        //        final Set<Taetigkeitsfeld> tFeldSet = new HashSet<Taetigkeitsfeld>();
        //        final Iterator<Organisationseinheit> organisationseinheitIterator = objSetOrgEinheiten.iterator();
        //        while (organisationseinheitIterator.hasNext()) {
        //            final Organisationseinheit organisationseinheiten = organisationseinheitIterator.next();
        //            final Collection<TaetigkeitsfeldAssoc> assocCollection = organisationseinheiten.getTaetigkeitsfeldAssocs();
        //            for (final TaetigkeitsfeldAssoc taetigkeitsfeldAssoc : assocCollection) {
        //                final boolean b1 = taetigkeitsfeldAssoc.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                if(b1){
        //                    final Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldAssoc.getTaetigkeitsfeld();
        //                    tFeldSet.add(taetigkeitsfeld);
        //                    break;
        //                }else{
        //                    //
        //                }
        //            }
        //        }
        //        final List<Taetigkeitsfeld> liste = new ArrayList<Taetigkeitsfeld>();
        //        liste.addAll(tFeldSet);
        //
        //        return liste;
        //        return (List<Taetigkeitsfeld>) entityManager.createNamedQuery("LoadTaetigkeitsfeldListeForMandantengruppe").setParameter(1, mandantengruppe).getResultList();
    }


    public Taetigkeitsfeld loadTaetigkeitsfeldNichtEingeordnet() {
        final TypedQuery<Taetigkeitsfeld> typedQuery = entityManager.createQuery("from Taetigkeitsfeld  t where t.namen='Nicht eingeordnet'", Taetigkeitsfeld.class);
        return getSingleResultOrNull(typedQuery);

        //        return createWhereClause().eq("namen", "Nicht eingeordnet").findUnique();
        //        final ObjectSet<Taetigkeitsfeld> objSet = entityManager.query(new Predicate<Taetigkeitsfeld>() {
        //            @Override
        //            public boolean match(final Taetigkeitsfeld taetigkeitsfeld) {
        //                return taetigkeitsfeld.getNamen().equals("Nicht eingeordnet");
        //            }
        //        });
        //        final int size = objSet.size();
        //        final Taetigkeitsfeld result;
        //        if(size==1){
        //            result =  objSet.get(0);
        //        }else{
        //            result = null;
        //        }
        //        return result;
        //        return (Taetigkeitsfeld) entityManager.createNamedQuery("LoadTaetigkeitsfeld_NichtEingeordnet").getSingleResult();
    }
}
