package org.rapidpm.persistence.prj.stammdaten.kommunikation;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class KommunikationsServiceDAO extends BaseDAO<Long, KommunikationsService> {
    private static final Logger logger = Logger.getLogger(KommunikationsServiceDAO.class);

    public KommunikationsServiceDAO(final EntityManager entityManager) {
        super(entityManager, KommunikationsService.class);
    }

    public KommunikationsService loadHandy() {
        return loadService("Handy");
        //        return (KommunikationsService) entityManager.createNamedQuery("LoadKommunikationsServiceHandy").getSingleResult();
    }


    public KommunikationsService loadEmail() {
        return loadService("Email");
        //        return (KommunikationsService) entityManager.createNamedQuery("LoadKommunikationsServiceEmail").getSingleResult();
    }

    public KommunikationsService loadTele() {
        return loadService("Tele");
        //        return (KommunikationsService) entityManager.createNamedQuery("LoadKommunikationsServiceTele").getSingleResult();
    }

    public KommunikationsService loadFax() {
        return loadService("Fax");
        //        return (KommunikationsService) entityManager.createNamedQuery("LoadKommunikationsServiceFax").getSingleResult();
    }

    private KommunikationsService loadService(final String serviceName) {
        final TypedQuery<KommunikationsService> typedQuery = entityManager.createQuery(
                "from KommunikationsService ks where ks.serviceName=:serviceName",
                KommunikationsService.class).setParameter("serviceName", serviceName);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause().eq("service_name", serviceName).findUnique();
    }

    public List<KommunikationsService> loadServicesForOrganisationseinheit(final Long organisationseinheitOID) {
        return entityManager.createQuery("select s" +
                " from Organisationseinheit org" +
                " inner join org.kommunikationsServiceUIDs komUID" +
                " inner join komUID.service s" +
                " where org.id=:organisationseinheitOID", KommunikationsService.class)
                .setParameter("organisationseinheitOID", organisationseinheitOID)
                .getResultList();
    }

}
