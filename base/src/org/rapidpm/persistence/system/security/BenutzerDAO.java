package org.rapidpm.persistence.system.security;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * NeoScio
 * User: Manfred
 * Date: 22.02.2010
 * Time: 13:25:30
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class BenutzerDAO extends DAO<Long, Benutzer> {
    private static final Logger logger = Logger.getLogger(BenutzerDAO.class);

    public BenutzerDAO(final EntityManager entityManager) {
        super(entityManager, Benutzer.class);
    }


    public List<Benutzer> loadBenutzerForLogin(final String login) {
        return entityManager.createQuery("from Benutzer b where b.login=:login", Benutzer.class).setParameter("login", login).getResultList();
    }

    public Benutzer loadBenutzer(final String login, final String mandantengruppe) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery("from Benutzer b " + "where b.login=:login " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", Benutzer.class).setParameter("login", login).setParameter(
                "mandantengruppe",
                mandantengruppe);
        final Benutzer singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final Benutzer result = null;
        //        final ObjectSet<Benutzer> benutzers = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
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

    public Benutzer loadBenutzer(final String login, final Long mandantengruppe) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery("from Benutzer b " + "where b.login=:login " + "and b.mandantengruppe.id=:mandantengruppe", Benutzer.class).setParameter("login", login).setParameter("mandantengruppe",
                mandantengruppe);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final Benutzer result = null;
        //        final ObjectSet<Benutzer> benutzers = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
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


    public List<Benutzer> loadBenutzerByMandantenGruppe(final String mandantengruppe, final boolean hidden) {
        return entityManager.createQuery("from Benutzer b where b.mandantengruppe.mandantengruppe=:mandantengruppe and b.hidden=:hidden", Benutzer.class).setParameter("hidden", hidden).setParameter("mandantengruppe", mandantengruppe).getResultList();


        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("hidden", hidden)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.hidden='" + hidden + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();
        //        final ObjectSet<Benutzer> objSet = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                return b && b1;
        //            }
        //        });
        //        return objSet;
    }

    public List<Benutzer> loadBenutzerByMandantenGruppe(final Long mandantengruppeOID, final boolean hidden) {
        return entityManager.createQuery("from Benutzer b where b.mandantengruppe.id=:mandantengruppeOID and b.hidden=:hidden", Benutzer.class).setParameter("hidden", hidden).setParameter("mandantengruppeOID", mandantengruppeOID).getResultList();
    }

    public List<Benutzer> loadBenutzerByMandantenGruppe(final String mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from Benutzer b " + "where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", Benutzer.class).setParameter("hidden", hidden).setParameter("active",
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
        //        final ObjectSet<Benutzer> objSet = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                final boolean b2 = benutzer.getActive().equals(active);
        //                return b && b1 && b2;
        //            }
        //        });
        //        return objSet;
    }

    public List<Benutzer> loadBenutzerByMandantenGruppe(final Long mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from Benutzer b " + "where b.mandantengruppe.id=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", Benutzer.class).setParameter("hidden", hidden).setParameter("active", active).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();


    }

    public List<Benutzer> aktiveBenutzerByMandantenGruppe(final String mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from Benutzer b " + "where b.active=:aktiv " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", Benutzer.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv",
                aktiv).getResultList();
        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("active", aktiv)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.active='" + aktiv.toString() + "' " +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();

        //        final ObjectSet<Benutzer> objSet = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
        //                final String mandantenGrp = benutzer.getMandantengruppe().getMandantengruppe();
        //                final boolean b1 = mandantenGrp.equals(mandantengruppe);
        //                final Boolean b2 = benutzer.getActive();
        //                return b1 && (b2 && aktiv);
        //            }
        //        });
        //        return objSet;
    }

    public List<Benutzer> aktiveBenutzerByMandantenGruppe(final Long mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from Benutzer b " + "where b.active=:aktiv " + "and b.mandantengruppe.id=:mandantengruppe", Benutzer.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv", aktiv).getResultList();
    }

    public Benutzer loadBenutzer(final String login, final String passwd, final String webappName) {
        Benutzer benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen Benutzer");
            benutzer = loadAnonymousBenutzer(webappName);
        } else {
            final TypedQuery<Benutzer> typedQuery1 = entityManager.createQuery("from Benutzer b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd and b.benutzerWebapplikation.webappName=:webappName", Benutzer.class);
            final TypedQuery<Benutzer> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);

            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("hidden", false)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousBenutzer(webappName);
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
            //            final ObjectSet<Benutzer> objSet = entityManager.query(new Predicate<Benutzer>() {
            //                @Override
            //                public boolean match(final Benutzer benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getBenutzerWebapplikation().getWebappName().equals(webappName);
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
            //                benutzer = loadAnonymousBenutzer(webappName);
            //            }

        }
        return benutzer;
    }

    public Benutzer loadBenutzer(final String login, final String passwd, final Long webappOID) {
        Benutzer benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen Benutzer");
            benutzer = loadAnonymousBenutzer(webappOID);
        } else {
            final TypedQuery<Benutzer> typedQuery1 = entityManager.createQuery("from Benutzer b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", Benutzer.class);
            final TypedQuery<Benutzer> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappOID", webappOID);

            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousBenutzer(webappOID);
            }

            if (benutzer == null) {       //TODO Executer um alle Anfragen...
                benutzer = findByID(-1L);
            } else {

            }
        }
        return benutzer;
    }

    public Benutzer loadBenutzerInclSystemBenutzer(final String login, final String passwd, final String webappName) {
        Benutzer benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen Benutzer");
            benutzer = loadAnonymousBenutzer(webappName);
        } else {
            final TypedQuery<Benutzer> typedQuery = entityManager.createQuery("from Benutzer b " + "where b.login=:login " + "and b.active=true " + "and b.passwd=:passwd " + "and b.benutzerWebapplikation.webappName=:webappName",
                    Benutzer.class).setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);


            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousBenutzer(webappName);
            }
            //            final String sql = "select id from benutzer b where b.login = '" + login + "'" +
            ////                    SQLCreator.and("hidden", "false")+
            //                    SQLCreator.andValue("passwd", passwd) +
            //                    SQLCreator.andValue("active", "true") +
            //                    SQLCreator.and("b.benutzer_webapplikation_id", SQLCreator.webapplikationID(webappName));
            //            benutzer = createQuery(sql).findUnique();
            //            final ObjectSet<Benutzer> objSet = entityManager.query(new Predicate<Benutzer>() {
            //                @Override
            //                public boolean match(final Benutzer benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getBenutzerWebapplikation().getWebappName().equals(webappName);
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
            //                benutzer = loadAnonymousBenutzer(webappName);
            //            }
        }
        return benutzer;
    }

    public Benutzer loadBenutzerInclSystemBenutzer(final String login, final String passwd, final Long webappOID) {
        Benutzer benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen Benutzer");
            benutzer = loadAnonymousBenutzer(webappOID);
        } else {
            final TypedQuery<Benutzer> typedQuery = entityManager.createQuery("from Benutzer b where b.login=:login and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", Benutzer.class).setParameter("login",
                    login).setParameter(
                    "passwd",
                    passwd).setParameter("webappOID", webappOID);
            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousBenutzer(webappOID);
            }
        }
        return benutzer;
    }


    public Benutzer loadAnonymousBenutzer(final String webappName) {
        final String login = "Anonymous_" + webappName.replace("_App", "");
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: login:=" + login);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webApp:=" + webappName);
        }
        final TypedQuery<Benutzer> benutzerTypedQuery = entityManager.createQuery("from Benutzer b  where b.login=:login  " +
                "and b.benutzerWebapplikation.webappName=:webappName", Benutzer.class)
                .setParameter("login", login)
                .setParameter("webappName",
                        webappName);
        return getSingleResultOrNull(benutzerTypedQuery);
    }

    public Benutzer loadAnonymousBenutzer(final Long webappOID) {
        //        final String login = "Anonymous_" + webappName.replace("_App", "");

        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webappOID:=" + webappOID);
        }
        final TypedQuery<Benutzer> benutzerTypedQuery = entityManager.createQuery("from Benutzer b  where b.login like 'Anonymous_%' and b.benutzerWebapplikation.id=:webappOID", Benutzer.class)
                //                        .setParameter("login", login)
                .setParameter("webappOID", webappOID);
        return getSingleResultOrNull(benutzerTypedQuery);

    }


    public boolean checkIfBenutzerLoginIsAvailable(final String wishedLogin, final String mandantengruppe) {
        //        boolean loginAvailable = false;
        final Benutzer benutzer = loadBenutzer(wishedLogin, mandantengruppe);
        if (benutzer == null) {
            //prüefe ob registration vorhanden..
            //            final String sql = "select id from registration r \n" +
            //                    "where r.mandantengruppe_id=(" + SQLCreator.mandantengruppeID(mandantengruppe) + ")" +
            //                    "and r.login = '" + wishedLogin + "'";
            //            final Benutzer registration = createQuery(sql).findUnique();
//            final RegistrationDAO registrationDAO = new RegistrationDAO(entityManager);
//            return registrationDAO.checkIfLoginIsAvailable(wishedLogin, mandantengruppe);
            return true;
        } else {
            logger.info("Login wird schon aktiv verwendet : " + wishedLogin + " / " + mandantengruppe);
            return false;
        }

        //        final ObjectSet<Benutzer> objSetOne = entityManager.query(new Predicate<Benutzer>() {
        //            @Override
        //            public boolean match(final Benutzer benutzer) {
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

    public Benutzer loadBenutzerByEmail(final String email) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
                "select b from Benutzer" +
                        "  b where b.email=:email ",
                Benutzer.class)
                .setParameter("email", email);
        return getSingleResultOrNull(typedQuery);
    }

    public Benutzer loadBenutzerByEmail(final String email, final Long mandantengruppeOID) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
                "select b from Benutzer" +
                        "  b where b.email=:email " +
                        " and b.mandantengruppe.id=:mandantengruppeOID",
                Benutzer.class)
                .setParameter("email", email)
                .setParameter("mandantengruppeOID", mandantengruppeOID);
        return getSingleResultOrNull(typedQuery);
    }

    public Benutzer loadBenutzerByEmail(final String email, final String mandantengruppe) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
                "select b from Benutzer b where b.email=:email " +
                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
                Benutzer.class)
                .setParameter("email", email)
                .setParameter("mandantengruppe", mandantengruppe);
        return getSingleResultOrNull(typedQuery);
    }
//    public Benutzer loadBenutzerByEmail(final String email, final Long mandantengruppeOID) {
//        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.id=:mandantengruppeOID",
//                Benutzer.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppeOID", mandantengruppeOID);
//        return getSingleResultOrNull(typedQuery);
//    }
//
//    public Benutzer loadBenutzerByEmail(final String email, final String mandantengruppe) {
//        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
//                Benutzer.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppe", mandantengruppe);
//        return getSingleResultOrNull(typedQuery);
//    }

    public Benutzer authenticate(final String login, final String passwd) {
        final TypedQuery<Benutzer> typedQuery = entityManager.createQuery(
                "select b from Benutzer b where b.login=:login and b.passwd=:passwd", Benutzer.class)
                .setParameter("login", login)
                .setParameter("passwd", passwd);
        return getSingleResultOrNull(typedQuery);
    }
}
