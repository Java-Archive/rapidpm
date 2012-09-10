package org.rapidpm.persistence.prj; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 10.02.12
 * Time: 15:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.system.security.BenutzerGruppe;
import org.rapidpm.persistence.system.security.BenutzerWebapplikation;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DemodatenGenerator extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(DemodatenGenerator.class);

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERSISTENCE_UNIT_NAME_TEST);
    private static EntityManager entityManager = emf.createEntityManager();
    private static DaoFactory daoFactoryFactory = new DaoFactory();

    public static void main(String[] args) {
        daoFactoryFactory.setEntityManager(entityManager);

        final Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe("NeoMeta");
        daoFactoryFactory.getMandantengruppeDAO().saveOrUpdate(mandantengruppe);

        final BenutzerWebapplikation benutzerWebapplikation = new BenutzerWebapplikation();
        benutzerWebapplikation.setWebappName("NeoMeta_App");
        daoFactoryFactory.getBenutzerWebapplikationDAO().saveOrUpdate(benutzerWebapplikation);

        final String gruppenname = "admin";
        final BenutzerGruppe benutzerGruppe = new BenutzerGruppe();
        benutzerGruppe.setGruppenname(gruppenname);
        daoFactoryFactory.getBenutzerGruppeDAO().saveOrUpdate(benutzerGruppe);


    }


}
