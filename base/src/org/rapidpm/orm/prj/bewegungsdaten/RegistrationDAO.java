package org.rapidpm.orm.prj.bewegungsdaten;

import org.apache.log4j.Logger;
import org.rapidpm.orm.BaseDAO;
import org.rapidpm.orm.system.security.Benutzer;
import org.rapidpm.orm.system.security.BenutzerDAO;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * NeoScio
 * User: Manfred
 * Date: 22.02.2010
 * Time: 18:33:22
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */

public class RegistrationDAO extends BaseDAO<Long, Registration> {
    private static final Logger logger = Logger.getLogger(RegistrationDAO.class);

    public RegistrationDAO(final EntityManager entityManager) {
        super(entityManager, Registration.class);
    }

    //TODO noch fehlerhaft da niccht die bestehenden Logins geprüft werden.
    public boolean checkIfLoginIsAvailable(final String login, final String mandantengruppe) {
        final List<Registration> resultList = entityManager.createQuery("from Registration  r where r.login=:login " + "and r.mandantengruppe.mandantengruppe=:mandantengruppe", Registration.class).setParameter("login", login).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();
        final Benutzer benutzer = new BenutzerDAO(entityManager).loadBenutzer(login, mandantengruppe);

        //        final List<Registration> registrationList = createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengrupe)
        //                .ne("registrationStatus.status", "Abgelehnt")
        //                .eq("login", login)
        //                .findList();
        final boolean b = resultList.isEmpty() && benutzer == null;
        return b;
    }

    public List<Registration> loadAllRegistrationForMandantengruppe(final String mandantengruppe) {
        //        final String sql = "select id from registration r where r.mandantengruppe_id='" + mandantengruppe + "'";
        //        return createQuery(sql).findList();
        return entityManager.createQuery("from Registration r where r.mandantengruppe.mandantengruppe=:mandantengruppe", Registration.class).setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        return entityManager.find(entityClass)
        //                .fetch("mandantengruppe", new FetchConfig().query())
        //                .where()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .findList();

        ////        List<Registration> resultList;
        //        final ObjectSet<Registration> objSet = entityManager.query(new Predicate<Registration>() {
        //            @Override
        //            public boolean match(final Registration registration) {
        //                return registration.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //            }
        //        });
        //        return objSet;
    }

    public List<Registration> loadAllRegistrationForMandantengruppe(final Long mandantengruppe) {
        return entityManager.createQuery("from Registration r where r.mandantengruppe.id=:mandantengruppe", Registration.class).setParameter("mandantengruppe", mandantengruppe).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final String webApp) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp", Registration.class).setParameter("webApp", webApp).getResultList();
        //        final String sql = "select id from registration r where r.benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webApp);
        //        return createQuery(sql).findList();
        //        return entityManager.find(entityClass)
        //                .fetch("benutzerWebapplikation", new FetchConfig().query())
        //                .where().eq("benutzerWebapplikation.webappName", webApp)
        //                .findList();
        //        List<Registration> resultList;
        //        final ObjectSet<Registration> objSet = entityManager.query(new Predicate<Registration>() {
        //            @Override
        //            public boolean match(final Registration registration) {
        //                final BenutzerWebapplikation webapplikation = registration.getBenutzerWebapplikation();
        //                return webapplikation.getWebappName().equals(webApp);
        //            }
        //        });
        //        return objSet;

    }

    public List<Registration> loadAllRegistrationForWebApp(final Long webApp) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.id=:webApp", Registration.class).setParameter("webApp", webApp).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final String webApp, final String status) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp " + "and  r.registrationStatus.status=:status", Registration.class).setParameter("webApp", webApp).setParameter("status",
                status).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final Long webApp, final String status) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.id=:webApp " + "and  r.registrationStatus.status=:status", Registration.class).setParameter("webApp", webApp).setParameter("status",
                status).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final String webApp, final Long status) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp " + "and  r.registrationStatus.id=:status", Registration.class).setParameter("webApp", webApp).setParameter("status",
                status).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final Long webApp, final Long status) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.id=:webApp " + "and  r.registrationStatus.id=:status", Registration.class).setParameter("webApp", webApp).setParameter("status", status).getResultList();
    }

    //    public List<Registration> loadAllRegistrationForWebApp(final String webApp, final List<String> statusListe){
    //        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp " + "and  r.registrationStatus.status in (:statusListe)",
    //                                         Registration.class).setParameter("webApp", webApp).setParameter("statusListe", statusListe).getResultList();
    //    }

    public List<Registration> loadAllRegistrationForWebApp(final String webApp, final List<Long> statusListe) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp " + "and  r.registrationStatus.id in (:statusListe)", Registration.class).setParameter("webApp", webApp).setParameter("statusListe",
                statusListe).getResultList();
    }

    public List<Registration> loadAllRegistrationForWebApp(final Long webApp, final List<Long> statusListe) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.id=:webApp " + "and  r.registrationStatus.id in (:statusListe)", Registration.class).setParameter("webApp", webApp).setParameter("statusListe",
                statusListe).getResultList();
    }


    public List<Registration> loadAllRegistrationForWebAppWithoutAccepted(final String webApp) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.webappName=:webApp and not  r.registrationStatus.status='Akzeptiert'", Registration.class).setParameter("webApp", webApp).getResultList();

        //        final String sql = "select id from registration r where r.benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webApp) +
        //                SQLCreator.andNot("r.registration_status_id", SQLCreator.registrationStatusID("Akzeptiert"));
        //        return createQuery(sql).findList();
        //        return entityManager.find(entityClass)
        //                .fetch("benutzerWebapplikation", new FetchConfig().query())
        //                .fetch("registrationStatus", new FetchConfig().query())
        //                .where()
        //                .ne("registrationStatus.status", "Akzeptiert")
        //                .eq("benutzerWebapplikation.webappName", webApp)
        //                .findList();
        //        final ObjectSet<Registration> objSet = entityManager.query(new Predicate<Registration>() {
        //            @Override
        //            public boolean match(final Registration registration) {
        //                final BenutzerWebapplikation webapplikation = registration.getBenutzerWebapplikation();
        //                final boolean b = webapplikation.getWebappName().equals(webApp);
        //
        //                if (b) {
        //                    final RegistrationStatus status = registration.getRegistrationStatus();
        //                    final String statusTxt = status.getIssueStatus();
        //                    final boolean b1 = statusTxt.equals("freigeschaltet");
        //                    final boolean b2 = statusTxt.equals("Abgelehnt");
        //                    return !(b1 || b2);
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        return objSet;

    }

    public List<Registration> loadAllRegistrationForWebAppWithoutAccepted(final Long webApp) {
        return entityManager.createQuery("from Registration  r where r.benutzerWebapplikation.id=:webApp and not  r.registrationStatus.status='Akzeptiert'", Registration.class).setParameter("webApp", webApp).getResultList();

    }

    public List<Registration> loadAllRegistrationForWebAppAndAproval(final String webApp) {
        return loadAllRegistrationForWebApp(webApp, "Zur_Prüfung");

        //        return entityManager.find(entityClass)
        //                .fetch("benutzerWebapplikation", new FetchConfig().query())
        //                .fetch("registrationStatus", new FetchConfig().query())
        //                .where()
        //                .eq("registrationStatus.status", "Zur_Prüfung")
        //                .eq("benutzerWebapplikation.webappName", webApp)
        //                .findList();
    }

    public List<Registration> loadAllRegistrationForWebAppAndAproval(final Long webApp) {
        return loadAllRegistrationForWebApp(webApp, "Zur_Prüfung");
    }

    public List<Registration> loadAllRegistrationWithoutAccepted() {
        return entityManager.createQuery("from Registration  r where not r.registrationStatus.status='Akzeptiert'", Registration.class).getResultList();

        //        final String sql = "select id from registration r where r.registration_status_id=" + SQLCreator.registrationStatusID("Akzeptiert");
        //        return createQuery(sql).findList();
        //        return entityManager.find(entityClass)
        //                .fetch("benutzerWebapplikation", new FetchConfig().query())
        //                .fetch("registrationStatus", new FetchConfig().query())
        //                .where()
        //                .ne("registrationStatus.status", "Akzeptiert")
        //                .findList();
        //        final ObjectSet<Registration> objSet = entityManager.query(new Predicate<Registration>() {
        //            @Override
        //            public boolean match(final Registration registration) {
        ////                final BenutzerWebapplikation webapplikation = registration.getBenutzerWebapplikation();
        ////                final boolean b = webapplikation.getWebappName().equals(webApp);
        ////                if(b){
        //                final RegistrationStatus status = registration.getRegistrationStatus();
        //                final String statusTxt = status.getIssueStatus();
        //                final boolean b1 = statusTxt.equals("freigeschaltet");
        //                final boolean b2 = statusTxt.equals("Abgelehnt");
        //                return !(b1 || b2);
        ////                }else{
        ////                    return false;
        ////                }
        //            }
        //        });
        //        return objSet;
    }

    public List<Registration> loadAllRegistrationForAproval() {

        return entityManager.createQuery("from Registration  r where r.registrationStatus.status='Zur_Prüfung'", Registration.class).getResultList();
        //
        //        return entityManager.find(entityClass)
        //                .fetch("benutzerWebapplikation", new FetchConfig().query())
        //                .fetch("registrationStatus", new FetchConfig().query())
        //                .where()
        //                .eq("registrationStatus.status", "Zur_Prüfung")
        //                .findList();


    }

}
