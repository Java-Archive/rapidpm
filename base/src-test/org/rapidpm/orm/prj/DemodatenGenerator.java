package org.rapidpm.orm.prj; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 10.02.12
 * Time: 15:16
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@rapidpm.org
 */

import org.rapidpm.orm.DaoFactory;
import org.rapidpm.orm.system.security.BenutzerGruppe;
import org.rapidpm.orm.system.security.BenutzerWebapplikation;
import org.rapidpm.orm.system.security.Mandantengruppe;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DemodatenGenerator extends BaseDAOTest {
    private static final Logger logger = Logger.getLogger(DemodatenGenerator.class);

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("baseormNeoMetaJDBCbeta");
    private static EntityManager entityManager = emf.createEntityManager();
    private static DaoFactory daoFactory = new DaoFactory();

    public static void main(String[] args) {
        daoFactory.setEm(entityManager);

        final Mandantengruppe mandantengruppe = new Mandantengruppe();
        mandantengruppe.setMandantengruppe("NeoMeta");
        daoFactory.saveOrUpdate(mandantengruppe);

        final BenutzerWebapplikation benutzerWebapplikation = new BenutzerWebapplikation();
        benutzerWebapplikation.setWebappName("NeoMeta_App");
        daoFactory.saveOrUpdate(benutzerWebapplikation);

        final String gruppenname = "admin";
        final BenutzerGruppe benutzerGruppe = new BenutzerGruppe();
        benutzerGruppe.setGruppenname(gruppenname);
        daoFactory.saveOrUpdate(benutzerGruppe);


    }


}
