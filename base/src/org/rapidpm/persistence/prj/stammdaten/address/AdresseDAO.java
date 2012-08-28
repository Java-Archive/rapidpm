package org.rapidpm.persistence.prj.stammdaten.address;
/**
 * RapidPM
 * User: svenruppert
 * Date: 06.03.2010
 * Time: 18:45:43
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@web.de
 *
 */

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class AdresseDAO extends BaseDaoFactory.BaseDAO<Long, Adresse> {
    private static final Logger logger = Logger.getLogger(AdresseDAO.class);

    public AdresseDAO(final EntityManager entityManager) {
        super(entityManager, Adresse.class);
    }

    public List<Adresse> getAdressenForPerson(final Long personOID) {
        return entityManager.createQuery("select adr from Person p inner join p.adressen adr where p.id=:pid", Adresse.class)
                .setParameter("pid", personOID)
                .getResultList();
    }
}
