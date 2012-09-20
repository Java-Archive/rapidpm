package org.rapidpm.persistence.prj.projectmanagement.planning;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.BaseDAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 13:24
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlannedProjectDAO extends BaseDAO<Long, PlannedProject> {
    private static final Logger logger = Logger.getLogger(PlannedProjectDAO.class);

    public PlannedProjectDAO(final EntityManager entityManager) {
        super(entityManager, PlannedProject.class);
    }


    public List<PlannedProject> loadPlannedProjects() {
        return entityManager.createQuery("from PlannedProject b", PlannedProject.class).getResultList();
    }

    public PlannedProject loadPlannedProject(final String login, final String mandantengruppe) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery("from PlannedProject b " + "where b.login=:login " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("login", login).setParameter(
                "mandantengruppe",
                mandantengruppe);
        final PlannedProject singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final PlannedProject result = null;
        //        final ObjectSet<PlannedProject> benutzers = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final String loginUser = benutzer.getLogin();
        //                if (loginUser.equals(login)) {
        //                    final Mandantengruppe mandantengruppe1 = benutzer.getMandantengruppe();
        //                    final String mandantengruppenName = mandantengruppe1.getMandantengruppe();
        //                    if (mandantengruppenName.equals(mandantengruppe)) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        if(benutzers.size() == 1){
        //            return  benutzers.get(0);
        //        }else{
        //            return null;
        //        }
    }

    public PlannedProject loadPlannedProject(final String login, final Long mandantengruppe) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery("from PlannedProject b " + "where b.login=:login " + "and b.mandantengruppe.id=:mandantengruppe", PlannedProject.class).setParameter("login", login).setParameter("mandantengruppe",
                mandantengruppe);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final PlannedProject result = null;
        //        final ObjectSet<PlannedProject> benutzers = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final String loginUser = benutzer.getLogin();
        //                if (loginUser.equals(login)) {
        //                    final Mandantengruppe mandantengruppe1 = benutzer.getMandantengruppe();
        //                    final String mandantengruppenName = mandantengruppe1.getMandantengruppe();
        //                    if (mandantengruppenName.equals(mandantengruppe)) {
        //                        return true;
        //                    } else {
        //                        return false;
        //                    }
        //                } else {
        //                    return false;
        //                }
        //            }
        //        });
        //        if(benutzers.size() == 1){
        //            return  benutzers.get(0);
        //        }else{
        //            return null;
        //        }
    }


    public List<PlannedProject> loadPlannedProjectByMandantenGruppe(final String mandantengruppe, final boolean hidden) {
        return entityManager.createQuery("from PlannedProject b where b.mandantengruppe.mandantengruppe=:mandantengruppe and b.hidden=:hidden", PlannedProject.class).setParameter("hidden", hidden).setParameter("mandantengruppe", mandantengruppe).getResultList();


        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("hidden", hidden)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.hidden='" + hidden + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();
        //        final ObjectSet<PlannedProject> objSet = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                return b && b1;
        //            }
        //        });
        //        return objSet;
    }

    public List<PlannedProject> loadPlannedProjectByMandantenGruppe(final Long mandantengruppeOID, final boolean hidden) {
        return entityManager.createQuery("from PlannedProject b where b.mandantengruppe.id=:mandantengruppeOID and b.hidden=:hidden", PlannedProject.class).setParameter("hidden", hidden).setParameter("mandantengruppeOID", mandantengruppeOID).getResultList();
    }

    public List<PlannedProject> loadPlannedProjectByMandantenGruppe(final String mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from PlannedProject b " + "where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", PlannedProject.class).setParameter("hidden", hidden).setParameter("active",
                active).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();


        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("hidden", hidden)
        //                .eq("active", active)
        //                .findList();

        //        final String sql = "select id from benutzer b where b.hidden='" + hidden + "' " +
        //                SQLCreator.and("active", active.toString()) +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();
        //        final ObjectSet<PlannedProject> objSet = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                final boolean b2 = benutzer.getActive().equals(active);
        //                return b && b1 && b2;
        //            }
        //        });
        //        return objSet;
    }

    public List<PlannedProject> loadPlannedProjectByMandantenGruppe(final Long mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from PlannedProject b " + "where b.mandantengruppe.id=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", PlannedProject.class).setParameter("hidden", hidden).setParameter("active", active).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();


    }

    public List<PlannedProject> aktivePlannedProjectByMandantenGruppe(final String mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from PlannedProject b " + "where b.active=:aktiv " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv",
                aktiv).getResultList();
        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("active", aktiv)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.active='" + aktiv.toString() + "' " +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();

        //        final ObjectSet<PlannedProject> objSet = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final String mandantenGrp = benutzer.getMandantengruppe().getMandantengruppe();
        //                final boolean b1 = mandantenGrp.equals(mandantengruppe);
        //                final Boolean b2 = benutzer.getActive();
        //                return b1 && (b2 && aktiv);
        //            }
        //        });
        //        return objSet;
    }

    public List<PlannedProject> aktivePlannedProjectByMandantenGruppe(final Long mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from PlannedProject b " + "where b.active=:aktiv " + "and b.mandantengruppe.id=:mandantengruppe", PlannedProject.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv", aktiv).getResultList();
    }

    public PlannedProject loadPlannedProject(final String login, final String passwd, final String webappName) {
        PlannedProject benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlannedProject");
            benutzer = loadAnonymousPlannedProject(webappName);
        } else {
            final TypedQuery<PlannedProject> typedQuery1 = entityManager.createQuery("from PlannedProject b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd and b.benutzerWebapplikation.webappName=:webappName", PlannedProject.class);
            final TypedQuery<PlannedProject> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);

            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("hidden", false)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousPlannedProject(webappName);
            }

            if (benutzer == null) {       //TODO Executer um alle Anfragen...
                benutzer = findByID(-1L);
            } else {

            }

            //            final String sql = "select id from benutzer b where b.login = '" + login + "'" +
            //                    SQLCreator.and("hidden", "false") +
            //                    SQLCreator.andValue("passwd", passwd) +
            //                    SQLCreator.and("active", "true") +
            //                    SQLCreator.and("b.webapp_id", SQLCreator.webapplikationID(webappName));
            //            benutzer = createQuery(sql).findUnique();
            //            final ObjectSet<PlannedProject> objSet = entityManager.query(new Predicate<PlannedProject>() {
            //                @Override
            //                public boolean match(final PlannedProject benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getPlannedProjectWebapplikation().getWebappName().equals(webappName);
            //                    final Boolean b5 = benutzer.getHidden();
            //                    return b1 && b2 && b3 && b4 && !b5;
            //                }
            //            });
            //            final int size = objSet.size();
            //            if (size == 1) {
            //                benutzer = objSet.get(0);
            //                benutzer.setLastLogin(new Date());
            //                entityManager.store(benutzer);
            //                entityManager.commit();
            //            } else {
            //                logger.warn("Lade User Anonymous . ");
            //                benutzer = loadAnonymousPlannedProject(webappName);
            //            }

        }
        return benutzer;
    }

    public PlannedProject loadPlannedProject(final String login, final String passwd, final Long webappOID) {
        PlannedProject benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlannedProject");
            benutzer = loadAnonymousPlannedProject(webappOID);
        } else {
            final TypedQuery<PlannedProject> typedQuery1 = entityManager.createQuery("from PlannedProject b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", PlannedProject.class);
            final TypedQuery<PlannedProject> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappOID", webappOID);

            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousPlannedProject(webappOID);
            }

            if (benutzer == null) {       //TODO Executer um alle Anfragen...
                benutzer = findByID(-1L);
            } else {

            }
        }
        return benutzer;
    }

    public PlannedProject loadPlannedProjectInclSystemPlannedProject(final String login, final String passwd, final String webappName) {
        PlannedProject benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlannedProject");
            benutzer = loadAnonymousPlannedProject(webappName);
        } else {
            final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery("from PlannedProject b " + "where b.login=:login " + "and b.active=true " + "and b.passwd=:passwd " + "and b.benutzerWebapplikation.webappName=:webappName",
                    PlannedProject.class).setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);


            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousPlannedProject(webappName);
            }
            //            final String sql = "select id from benutzer b where b.login = '" + login + "'" +
            ////                    SQLCreator.and("hidden", "false")+
            //                    SQLCreator.andValue("passwd", passwd) +
            //                    SQLCreator.andValue("active", "true") +
            //                    SQLCreator.and("b.benutzer_webapplikation_id", SQLCreator.webapplikationID(webappName));
            //            benutzer = createQuery(sql).findUnique();
            //            final ObjectSet<PlannedProject> objSet = entityManager.query(new Predicate<PlannedProject>() {
            //                @Override
            //                public boolean match(final PlannedProject benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getPlannedProjectWebapplikation().getWebappName().equals(webappName);
            ////                    final Boolean b5 = benutzer.getHidden();
            //                    return b1 && b2 && b3 && b4;
            //                }
            //            });
            //            final int size = objSet.size();
            //            if (size == 1) {
            //                benutzer = objSet.get(0);
            //                benutzer.setLastLogin(new Date());
            //                entityManager.store(benutzer);
            //                entityManager.commit();
            //            } else {
            //                logger.warn("Lade User Anonymous . ");
            //                benutzer = loadAnonymousPlannedProject(webappName);
            //            }
        }
        return benutzer;
    }

    public PlannedProject loadPlannedProjectInclSystemPlannedProject(final String login, final String passwd, final Long webappOID) {
        PlannedProject benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlannedProject");
            benutzer = loadAnonymousPlannedProject(webappOID);
        } else {
            final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery("from PlannedProject b where b.login=:login and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", PlannedProject.class).setParameter("login",
                    login).setParameter(
                    "passwd",
                    passwd).setParameter("webappOID", webappOID);
            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousPlannedProject(webappOID);
            }
        }
        return benutzer;
    }


    public PlannedProject loadAnonymousPlannedProject(final String webappName) {
        final String login = "Anonymous_" + webappName.replace("_App", "");
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: login:=" + login);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webApp:=" + webappName);
        }
        final TypedQuery<PlannedProject> benutzerTypedQuery = entityManager.createQuery("from PlannedProject b  where b.login=:login  " +
                "and b.benutzerWebapplikation.webappName=:webappName", PlannedProject.class)
                .setParameter("login", login)
                .setParameter("webappName",
                        webappName);
        return getSingleResultOrNull(benutzerTypedQuery);
    }

    public PlannedProject loadAnonymousPlannedProject(final Long webappOID) {
        //        final String login = "Anonymous_" + webappName.replace("_App", "");

        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webappOID:=" + webappOID);
        }
        final TypedQuery<PlannedProject> benutzerTypedQuery = entityManager.createQuery("from PlannedProject b  where b.login like 'Anonymous_%' and b.benutzerWebapplikation.id=:webappOID", PlannedProject.class)
                //                        .setParameter("login", login)
                .setParameter("webappOID", webappOID);
        return getSingleResultOrNull(benutzerTypedQuery);

    }


    public boolean checkIfPlannedProjectLoginIsAvailable(final String wishedLogin, final String mandantengruppe) {
        //        boolean loginAvailable = false;
        final PlannedProject benutzer = loadPlannedProject(wishedLogin, mandantengruppe);
        if (benutzer == null) {
            //prüefe ob registration vorhanden..
            //            final String sql = "select id from registration r \n" +
            //                    "where r.mandantengruppe_id=(" + SQLCreator.mandantengruppeID(mandantengruppe) + ")" +
            //                    "and r.login = '" + wishedLogin + "'";
            //            final PlannedProject registration = createQuery(sql).findUnique();
//            final RegistrationDAO registrationDAO = new RegistrationDAO(entityManager);
//            return registrationDAO.checkIfLoginIsAvailable(wishedLogin, mandantengruppe);
            return true;
        } else {
            logger.info("Login wird schon aktiv verwendet : " + wishedLogin + " / " + mandantengruppe);
            return false;
        }

        //        final ObjectSet<PlannedProject> objSetOne = entityManager.query(new Predicate<PlannedProject>() {
        //            @Override
        //            public boolean match(final PlannedProject benutzer) {
        //                final boolean b1 = benutzer.getLogin().equals(wishedLogin);
        //                final boolean b2 = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                return b1 && b2;
        //            }
        //        });
        //        final int i = objSetOne.size();
        //        if (i == 0) {
        //            final ObjectSet<Registration> objSetTwo = entityManager.query(new Predicate<Registration>() {
        //                @Override
        //                public boolean match(final Registration registration) {
        //                    final boolean b1 = registration.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                    final boolean b2 = registration.getLogin().equals(wishedLogin);
        //                    return b1 && b2;
        //                }
        //            });
        //            final int j = objSetTwo.size();
        //            if (j == 0) {
        //                //alles ok registrieren
        //                logger.info("WishedLogin ist frei..." + wishedLogin);
        //                loginAvailable = true;
        //            } else {
        //                //Login schon vorhanden
        //                logger.info("Login ist noch frei wurde aber schon in einem Request angefragt.. " + wishedLogin + " / " + mandantengruppe);
        //            }
        //        } else {
        //            logger.info("Login wird schon aktiv verwendet : " + wishedLogin + " / " + mandantengruppe);
        //        }
        //        return loginAvailable;
    }

    public PlannedProject loadPlannedProjectByEmail(final String email) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
                "select b from PlannedProject" +
                        "  b where b.email=:email ",
                PlannedProject.class)
                .setParameter("email", email);
        return getSingleResultOrNull(typedQuery);
    }

    public PlannedProject loadPlannedProjectByEmail(final String email, final Long mandantengruppeOID) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
                "select b from PlannedProject" +
                        "  b where b.email=:email " +
                        " and b.mandantengruppe.id=:mandantengruppeOID",
                PlannedProject.class)
                .setParameter("email", email)
                .setParameter("mandantengruppeOID", mandantengruppeOID);
        return getSingleResultOrNull(typedQuery);
    }

    public PlannedProject loadPlannedProjectByEmail(final String email, final String mandantengruppe) {
        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
                "select b from PlannedProject b where b.email=:email " +
                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
                PlannedProject.class)
                .setParameter("email", email)
                .setParameter("mandantengruppe", mandantengruppe);
        return getSingleResultOrNull(typedQuery);
    }
//    public PlannedProject loadPlannedProjectByEmail(final String email, final Long mandantengruppeOID) {
//        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.id=:mandantengruppeOID",
//                PlannedProject.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppeOID", mandantengruppeOID);
//        return getSingleResultOrNull(typedQuery);
//    }
//
//    public PlannedProject loadPlannedProjectByEmail(final String email, final String mandantengruppe) {
//        final TypedQuery<PlannedProject> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
//                PlannedProject.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppe", mandantengruppe);
//        return getSingleResultOrNull(typedQuery);
//    }

}
