package org.rapidpm.persistence.prj.stammdaten.address;
/**
 * NeoScio
 * User: svenruppert
 * Date: 06.03.2010
 * Time: 18:45:43
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.security.InvalidKeyException;
import java.util.List;

public class AdresseDAO extends DAO<Long, Adresse> {
    private static final Logger logger = Logger.getLogger(AdresseDAO.class);

    public AdresseDAO(final OrientGraph orientDB) {
        super(orientDB, Adresse.class);
    }

    public List<Adresse> getAdressenForPerson(final Long personOID) {
//        return orientDB.createQuery("select adr from Person p inner join p.adressen adr where p.id=:pid", Adresse.class)
//                .setParameter("pid", personOID)
//                .getResultList();
        return null;
    }

    @Override
    public Adresse loadFull(Adresse entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
