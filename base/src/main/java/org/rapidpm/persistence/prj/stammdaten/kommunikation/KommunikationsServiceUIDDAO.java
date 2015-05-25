package org.rapidpm.persistence.prj.stammdaten.kommunikation;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 04.03.11
 * Time: 12:02
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.InvalidKeyException;
import java.util.List;

public class KommunikationsServiceUIDDAO extends DAO<Long, KommunikationsServiceUID> {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceUIDDAO.class);


    public KommunikationsServiceUIDDAO(final OrientGraph orientDB) {
        super(orientDB, KommunikationsServiceUID.class);
    }

    public List<KommunikationsServiceUID> loadServiceUIDsForOrganisationseinheit(final Long organisationseinheitOID) {
//        return orientDB.createQuery("select suid" +
//                " from Organisationseinheit org" +
//                " inner join org.kommunikationsServiceUIDs suid" +
//                " where org.id=:organisationseinheitOID",
//                KommunikationsServiceUID.class)
//                .setParameter("organisationseinheitOID", organisationseinheitOID)
//                .getResultList();
        return null;
    }

    public boolean isEmailRegistered(final String email) {
//        final TypedQuery<KommunikationsServiceUID> typedQuery = orientDB.createQuery(
//                "select ksuid" +
//                        " from KommunikationsServiceUID ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName ='Email' and part.uidPart=:email",
//                KommunikationsServiceUID.class)
//                .setParameter("email", email);
//        return getSingleResultOrNull(typedQuery) != null;
        return true;
    }

    public boolean isEmailRegistered(final String email, final Long mandantengruppeOID) {
//        final TypedQuery<KommunikationsServiceUID> typedQuery = orientDB.createQuery(
//                "select ksuid" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where b.mandantengruppe.id=:mandantengruppeOID and ksuid.service.serviceName ='Email' and part.uidPart=:email",
//                KommunikationsServiceUID.class)
//                .setParameter("mandantengruppeOID", mandantengruppeOID)
//                .setParameter("email", email);
//        return getSingleResultOrNull(typedQuery) != null;
        return true;
    }

    public boolean isEmailRegistered(final String email, final String mandantengruppe) {
//        final TypedQuery<KommunikationsServiceUID> typedQuery = orientDB.createQuery(
//                "select ksuid" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where b.mandantengruppe.mandantengruppe=:mandantengruppe and ksuid.service.serviceName ='Email' and part.uidPart=:email",
//                KommunikationsServiceUID.class)
//                .setParameter("mandantengruppe", mandantengruppe)
//                .setParameter("email", email);
//        return getSingleResultOrNull(typedQuery) != null;
        return true;
    }

    @Override
    public KommunikationsServiceUID loadFull(KommunikationsServiceUID entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
