package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class KommunikationsServiceDAO extends DAO<Long, KommunikationsService> {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceDAO.class);

    public KommunikationsServiceDAO(final OrientGraph orientDB) {
        super(orientDB, KommunikationsService.class);
    }

    public KommunikationsService loadHandy() {
        return loadService("Handy");
        //        return (KommunikationsService) orientDB.createNamedQuery("LoadKommunikationsServiceHandy").getSingleResult();
    }


    public KommunikationsService loadEmail() {
        return loadService("Email");
        //        return (KommunikationsService) orientDB.createNamedQuery("LoadKommunikationsServiceEmail").getSingleResult();
    }

    public KommunikationsService loadTele() {
        return loadService("Tele");
        //        return (KommunikationsService) orientDB.createNamedQuery("LoadKommunikationsServiceTele").getSingleResult();
    }

    public KommunikationsService loadFax() {
        return loadService("Fax");
        //        return (KommunikationsService) orientDB.createNamedQuery("LoadKommunikationsServiceFax").getSingleResult();
    }

    private KommunikationsService loadService(final String serviceName) {
//        final TypedQuery<KommunikationsService> typedQuery = orientDB.createQuery(
//                "from KommunikationsService ks where ks.serviceName=:serviceName",
//                KommunikationsService.class).setParameter("serviceName", serviceName);
//        return getSingleResultOrNull(typedQuery);
        return null;
        //        return createWhereClause().eq("service_name", serviceName).findUnique();
    }

    public List<KommunikationsService> loadServicesForOrganisationseinheit(final Long organisationseinheitOID) {
//        return orientDB.createQuery("select s" +
//                " from Organisationseinheit org" +
//                " inner join org.kommunikationsServiceUIDs komUID" +
//                " inner join komUID.service s" +
//                " where org.id=:organisationseinheitOID", KommunikationsService.class)
//                .setParameter("organisationseinheitOID", organisationseinheitOID)
//                .getResultList();
        return null;
    }

}
