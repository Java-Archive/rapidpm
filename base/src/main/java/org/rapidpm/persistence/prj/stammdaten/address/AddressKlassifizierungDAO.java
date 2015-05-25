package org.rapidpm.persistence.prj.stammdaten.address;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.exception.NotYetImplementedException;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.InvalidKeyException;

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

public class AddressKlassifizierungDAO extends DAO<Long, AdressKlassifizierung> {
    private static final Logger logger = Logger.getLogger(AddressKlassifizierungDAO.class);


    public AddressKlassifizierungDAO(final OrientGraph orientDB) {
        super(orientDB, AdressKlassifizierung.class);
    }

    public AdressKlassifizierung loadAdressKlassifizierung(final String klassifizierung) {

        //        final CriteriaBuilder builder = orientDB.getCriteriaBuilder();
        //        final CriteriaQuery<AdressKlassifizierung> criteriaQuery = builder.createQuery(AdressKlassifizierung.class);
        //        final Root<AdressKlassifizierung> akAlias = criteriaQuery.from(AdressKlassifizierung.class);
        //        criteriaQuery.where(builder.equal(akAlias.get("klassifizierung"), klassifizierung));
        //        final AdressKlassifizierung adressKlassifizierung = orientDB.createQuery(criteriaQuery).getSingleResult();
        //        return adressKlassifizierung;
//        final TypedQuery<AdressKlassifizierung> typedQuery = orientDB.createQuery(
//                "from AdressKlassifizierung ak where ak.klassifizierung=:klassifizierung ",
//                AdressKlassifizierung.class).setParameter("klassifizierung", klassifizierung);
//        return getSingleResultOrNull(typedQuery);
        return null;

        //        final TypedQuery<AdressKlassifizierung> query = orientDB.createQuery("from AdressKlassifizierung a where a.klassifizierung=:klassifizierung", AdressKlassifizierung.class);
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

    @Override
    public AdressKlassifizierung loadFull(AdressKlassifizierung entity) throws InvalidKeyException, NotYetImplementedException {
        throw new NotYetImplementedException();
    }
}
