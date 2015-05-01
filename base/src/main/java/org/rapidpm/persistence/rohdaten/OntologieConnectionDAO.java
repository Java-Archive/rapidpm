package org.rapidpm.persistence.rohdaten;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 11/21/10
 * Time: 5:35 PM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class OntologieConnectionDAO extends DAO<Long, OntologieConnection> {
    public OntologieConnectionDAO(final OrientGraph orientDB) {
        super(orientDB, OntologieConnection.class);
    }

    public OntologieConnection loadOntologieConnection(final String ontologieConnection) {
//        return orientDB.createQuery("from OntologieConnection  oc where oc.name=:ontologieConnection", OntologieConnection.class).setParameter("ontologieConnection", ontologieConnection).getSingleResult();
        //        return createWhereClause().eq("name", ontologieConnection).findUnique();
        return null;
    }

}
