package org.rapidpm.persistence.prj.stammdaten.address;

import org.rapidpm.persistence.BaseDaoFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 02.03.2010
 *        Time: 15:53:13
 */

public class AddressKlassifizierungDAO extends BaseDaoFactory.BaseDAO<Long, AdressKlassifizierung> {
    private static final Logger logger = Logger.getLogger(AddressKlassifizierungDAO.class);


    public AddressKlassifizierungDAO(final EntityManager entityManager) {
        super(entityManager, AdressKlassifizierung.class);
    }

    public AdressKlassifizierung loadAdressKlassifizierung(final String klassifizierung) {

        //        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        //        final CriteriaQuery<AdressKlassifizierung> criteriaQuery = builder.createQuery(AdressKlassifizierung.class);
        //        final Root<AdressKlassifizierung> akAlias = criteriaQuery.from(AdressKlassifizierung.class);
        //        criteriaQuery.where(builder.equal(akAlias.get("klassifizierung"), klassifizierung));
        //        final AdressKlassifizierung adressKlassifizierung = entityManager.createQuery(criteriaQuery).getSingleResult();
        //        return adressKlassifizierung;
        final TypedQuery<AdressKlassifizierung> typedQuery = entityManager.createQuery(
                "from AdressKlassifizierung ak where ak.klassifizierung=:klassifizierung ",
                AdressKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
        return getSingleResultOrNull(typedQuery);

        //        final TypedQuery<AdressKlassifizierung> query = entityManager.createQuery("from AdressKlassifizierung a where a.klassifizierung=:klassifizierung", AdressKlassifizierung.class);
        //        query.setParameter(klassifizierung, klassifizierung);
        //        return query.getSingleResult();
        //        return createWhereClause().eq("klassifizierung", klassifizierung).findUnique();
    }

    public AdressKlassifizierung loadKlassifizierungPrivat() {
        return loadAdressKlassifizierung("privat");
    }

    public AdressKlassifizierung loadKlassifizierungBeruflich() {
        return loadAdressKlassifizierung("beruflich");
    }

}
