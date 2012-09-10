package org.rapidpm.orm.prj.stammdaten.person;

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: sven.ruppert
 * Date: 23.04.2010
 * Time: 11:28:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class PersonDAO extends BaseDAO<Long, Person> {
    private static final Logger logger = Logger.getLogger(PersonDAO.class);

    public PersonDAO(final EntityManager entityManager) {
        super(entityManager, Person.class);
    }

    public List<Person> loadPersonenByOrgeinheit(final Long orgeinheitOID) {
        //return entityManager.createQuery("select po.personen from Position po inner join po.organisationseinheit o where o.id=:orgeinheitOID ", Person.class)
        return entityManager.createQuery("select p from Position po inner join po.person p where po.id in (select pos.id from Position pos inner join pos.organisationseinheit o where o.id=:orgeinheitOID) ", Person.class)
                .setParameter("orgeinheitOID", orgeinheitOID).getResultList();

    }

    //REFAC nicht alle Personen haben Benutzer
    public List<Person> loadPersonByMandantengruppe(final String mandantengruppe) {
        return entityManager.createQuery("from Person p inner join p.benutzer b " + "where b in ( select b from Benutzer b inner join b.mandantengruppe m " + "where m.mandantengruppe=:mandantengruppe)", Person.class)
                .setParameter("mandantengruppe", mandantengruppe).getResultList();

    }

    //REFAC nicht alle Personen haben Benutzer
    public List<Person> loadPersonByMandantengruppe(final Long mandantengruppeOID) {
        return entityManager.createQuery("from Person p inner join p.benutzer b " + "where b in ( select b from Benutzer b inner join b.mandantengruppe m " + "where m.id=:mandantengruppeOID)", Person.class)
                .setParameter("mandantengruppeOID", mandantengruppeOID).getResultList();
    }

    public Person loadPersonByBenutzer(final Long benutzerOID) {
        final TypedQuery<Person> personTypedQuery = entityManager.createQuery("select p from Person  p inner join p.benutzer b where b.id=:benutzer_id", Person.class)
                .setParameter("benutzer_id", benutzerOID);
        try {
            return personTypedQuery.getSingleResult();
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
            return null;
        }
    }

    public List<Person> loadPersonByEmail(final String email) {
        final TypedQuery<Person> typedQuery = entityManager.createQuery(
                "select p" +
                        " from Person p " +
                        " inner join p.kommunikationsServiceUIDs ksuid" +
                        " inner join ksuid.uidparts part" +
                        " where ksuid.service.serviceName='Email' and part.uidPart=:email",
                Person.class)
                .setParameter("email", email);
        return typedQuery.getResultList();
    }

//    public List<Person> loadPersonByEmail(final String email, final Long mandantengruppeOID){
//        final TypedQuery<Person> typedQuery = entityManager.createQuery(
//                "select p" +
//                        " from Person p " +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.id=:mandantengruppeOID",
//                Person.class)
//                .setParameter("email", email);
//        return typedQuery.getResultList();
//    }


}
