package org.rapidpm.persistence.rohdaten;

import org.rapidpm.persistence.BaseDaoFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11/21/10
 * Time: 5:39 PM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */
public class OntologieEntryDAO extends BaseDaoFactory.BaseDAO<Long, OntologieEntry> {
    public OntologieEntryDAO(final EntityManager entityManager) {
        super(entityManager, OntologieEntry.class);
    }


    public List<OntologieEntry> loadOntologieEntries(final String ontologie, final String mandantengruppe) {
        return entityManager.createQuery("select o.entryListe from Ontologie o inner join o.entryListe oe " + "where o.symbolischerName=:ontologie  " + "and o.mandantengruppe.mandantengruppe=:mandantengruppe ", OntologieEntry.class)
                // "and o.mandantengruppe.mandantengruppe=:mandantengruppe ").setParameter("ontologie", ontologie)
                .setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        return createWhereClause()
        //                .eq("ontologie.symbolischerName", ontologie)
        //                .eq("ontologie.mandantengruppe.mandantengruppe", mandantengruppe)
        //                .findList();
    }

    public List<OntologieEntry> loadOntologieEntry(final String ontologie, final String value, final String mandantengruppe) {
        return entityManager.createQuery("select o.entryListe from Ontologie o inner join o.entryListe oe " + "where o.symbolischerName=:ontologie  " + "and o.mandantengruppe.mandantengruppe=:mandantengruppe " + "and oe.value=:value ", OntologieEntry.class)
                //"and oe.value=:value ")
                .setParameter("ontologie", ontologie).setParameter("value", value).setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        return createWhereClause()
        //                .eq("ontologie.symbolischerName", ontologie)
        //                .eq("ontologie.mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("value", value)
        //                .findList();
    }

}
