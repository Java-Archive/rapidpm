package org.rapidpm.persistence.prj.stammdaten.person;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.MissingNonOptionalPropertyException;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import java.security.InvalidKeyException;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: sven.ruppert
 * Date: 23.04.2010
 * Time: 11:28:38
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */
public class PersonDAO extends DAO<Long, Person> {
  private static final Logger logger = Logger.getLogger(PersonDAO.class);

  public PersonDAO(final OrientGraph orientDB) {
    super(orientDB, Person.class);
  }

  public List<Person> loadPersonenByOrgeinheit(final Long orgeinheitOID) {
    //return orientDB.createQuery("select po.personen from Position po inner join po.organisationseinheit o where o.id=:orgeinheitOID ", Person.class)
//        return orientDB.createQuery("select p from Position po inner join po.person p where po.id in (select pos.id from Position pos inner join pos.organisationseinheit o where o.id=:orgeinheitOID) ", Person.class)
//                .setParameter("orgeinheitOID", orgeinheitOID).getResultList();
    return null;

  }

  //REFAC nicht alle Personen haben Benutzer
  public List<Person> loadPersonByMandantengruppe(final String mandantengruppe) {
//        return orientDB.createQuery("from Person p inner join p.benutzer b " + "where b in ( select b from Benutzer b inner join b.mandantengruppe m " + "where m.mandantengruppe=:mandantengruppe)", Person.class)
//                .setParameter("mandantengruppe", mandantengruppe).getResultList();
    return null;

  }

  //REFAC nicht alle Personen haben Benutzer
  public List<Person> loadPersonByMandantengruppe(final Long mandantengruppeOID) {
//        return orientDB.createQuery("from Person p inner join p.benutzer b " + "where b in ( select b from Benutzer b inner join b.mandantengruppe m " + "where m.id=:mandantengruppeOID)", Person.class)
//                .setParameter("mandantengruppeOID", mandantengruppeOID).getResultList();
    return null;
  }

  public Person loadPersonByBenutzer(final Long benutzerOID) {
//        final TypedQuery<Person> personTypedQuery = orientDB.createQuery("select p from Person  p inner join p.benutzer b where b.id=:benutzer_id", Person.class)
//                .setParameter("benutzer_id", benutzerOID);
//        try {
//            return personTypedQuery.getSingleResult();
//        } catch (Exception e) {
//            if (logger.isInfoEnabled()) {
//                logger.info(e);
//            }
//            return null;
//        }
    return null;
  }

  public List<Person> loadPersonByEmail(final String email) {
//        final TypedQuery<Person> typedQuery = orientDB.createQuery(
//                "select p" +
//                        " from Person p " +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email",
//                Person.class)
//                .setParameter("email", email);
//        return typedQuery.getResultList();
    return null;
  }

  @Override
  public Person loadFull(Person entity) throws InvalidKeyException, NotYetImplementedException {
    throw new NotYetImplementedException();
  }

  @Override
  public Person createEntityFull(Person entity) throws InvalidKeyException, NotYetImplementedException, MissingNonOptionalPropertyException {
    throw new NotYetImplementedException();
  }

//    public List<Person> loadPersonByEmail(final String email, final Long mandantengruppeOID){
//        final TypedQuery<Person> typedQuery = orientDB.createQuery(
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
