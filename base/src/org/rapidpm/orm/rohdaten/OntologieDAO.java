package org.rapidpm.orm.rohdaten;

import org.rapidpm.orm.BaseDaoFactory;
import org.rapidpm.orm.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

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
 * @since 25.10.2010
 *        Time: 00:25:37
 */

public class OntologieDAO extends BaseDaoFactory.BaseDAO<Long, Ontologie> {
    private static final Logger logger = Logger.getLogger(OntologieDAO.class);

    public OntologieDAO(final EntityManager entityManager) {
        super(entityManager, Ontologie.class);
    }

    public List<Ontologie> loadAllForMandantengrupe(final Mandantengruppe mandantengruppe) {
        return loadAllForMandantengrupe(mandantengruppe.getMandantengruppe());
        //        final String sql = "select id from ontologie o where o.mandantengruppe_id = " + SQLCreator.mandantengruppeID(mandantengruppe);
        //        return createQuery(sql).findList();
        //        final ObjectSet<Ontologie> ontologieListe = entityManager.query(new Predicate<Ontologie>() {
        //            @Override
        //            public boolean match(final Ontologie ontologie) {
        //                final Mandantengruppe mgrp = ontologie.getMandantengruppe();
        //                if (mgrp.equals(mandantengruppe)) {
        //                    return true;
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        return ontologieListe;
    }

    public List<Ontologie> loadAllForMandantengrupe(final String mandantengruppe) {
        return entityManager.createQuery("from Ontologie  o where o.mandantengruppe.mandantengruppe=:mandantengruppe", Ontologie.class).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();
        //        return createWhereClause().eq("mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        final String sql = "select id from ontologie o where o.mandantengruppe_id = " + SQLCreator.mandantengruppeID(mandantengruppe);
        //        return createQuery(sql).findList();
        //        final ObjectSet<Ontologie> ontologieListe = entityManager.query(new Predicate<Ontologie>() {
        //            @Override
        //            public boolean match(final Ontologie ontologie) {
        //                final Mandantengruppe mgrp = ontologie.getMandantengruppe();
        //                if (mgrp.getMandantengruppe().equals(mandantengruppe)) {
        //                    return true;
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        return ontologieListe;
    }


    public Ontologie loadOntologie(final String mandantengruppe, final String symbolicName) {
        final
        TypedQuery<Ontologie>
                typedQuery =
                entityManager.createQuery("from Ontologie  o " + "where o.mandantengruppe.mandantengruppe=:mandantengruppe " + "and o.symbolischerName=:symbolicName",
                        Ontologie.class).setParameter("mandantengruppe", mandantengruppe).setParameter("symbolicName",
                        symbolicName);
        return getSingleResultOrNull(typedQuery);

        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("symbolischerName", symbolicName)
        //                .findUnique();
        //        final String sql = "select id from ontologie o where o.mandantengruppe_id = " + SQLCreator.mandantengruppeID(mandantengruppe) +
        //                SQLCreator.and("symbolischer_name", symbolicName);
        //        return createQuery(sql).findUnique();

        //        final ObjectSet<Ontologie> ontologies = entityManager.query(new Predicate<Ontologie>() {
        //            @Override
        //            public boolean match(final Ontologie ontologie) {
        //                final Mandantengruppe mgrp = ontologie.getMandantengruppe();
        //                if (mgrp.getMandantengruppe().equals(mandantengruppe)) {
        //                    final String symbolischerName = ontologie.getSymbolischerName();
        //                    if (symbolischerName.equals(symbolicName)) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        if(ontologies.size() != 1){
        //            return null;
        //        }else{
        //            return ontologies.get(0);
        //        }
    }

}
