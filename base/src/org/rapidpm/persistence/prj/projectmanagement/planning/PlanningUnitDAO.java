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
public class PlanningUnitDAO extends BaseDAO<Long, PlanningUnit> {
    private static final Logger logger = Logger.getLogger(PlanningUnitDAO.class);

    public PlanningUnitDAO(final EntityManager entityManager) {
        super(entityManager, PlanningUnit.class);
    }


    public List<PlanningUnit> loadPlanningUnits() {
        return entityManager.createQuery("from PlanningUnit b", PlanningUnit.class).getResultList();
    }

    public PlanningUnit loadPlanningUnit(final String login, final String mandantengruppe) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit b " + "where b.login=:login " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", PlanningUnit.class).setParameter("login", login).setParameter(
                "mandantengruppe",
                mandantengruppe);
        final PlanningUnit singleResultOrNull = getSingleResultOrNull(typedQuery);
        return singleResultOrNull;
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final PlanningUnit result = null;
        //        final ObjectSet<PlanningUnit> benutzers = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
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

//    public PlanningUnit loadPlanningUnit(final PlanningUnit planningUnit) {
//        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit b " + "where b.login=:login " + "and b.mandantengruppe.id=:mandantengruppe", PlanningUnit.class).setParameter("login", login).setParameter("mandantengruppe",
//                mandantengruppe);
//        return getSingleResultOrNull(typedQuery);
//    }

    public PlanningUnit loadPlanningUnit(final String login, final Long mandantengruppe) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit b " + "where b.login=:login " + "and b.mandantengruppe.id=:mandantengruppe", PlanningUnit.class).setParameter("login", login).setParameter("mandantengruppe",
                mandantengruppe);
        return getSingleResultOrNull(typedQuery);
        //        return createWhereClause()
        //                .eq("login", login)
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe).findUnique();
        //        final String sql = "select id from benutzer b where b.login='" + login + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findUnique();

        //        final PlanningUnit result = null;
        //        final ObjectSet<PlanningUnit> benutzers = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
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


    public List<PlanningUnit> loadPlanningUnitByMandantenGruppe(final String mandantengruppe, final boolean hidden) {
        return entityManager.createQuery("from PlanningUnit b where b.mandantengruppe.mandantengruppe=:mandantengruppe and b.hidden=:hidden", PlanningUnit.class).setParameter("hidden", hidden).setParameter("mandantengruppe", mandantengruppe).getResultList();


        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("hidden", hidden)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.hidden='" + hidden + "'" +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();
        //        final ObjectSet<PlanningUnit> objSet = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                return b && b1;
        //            }
        //        });
        //        return objSet;
    }

    public List<PlanningUnit> loadPlanningUnitByMandantenGruppe(final Long mandantengruppeOID, final boolean hidden) {
        return entityManager.createQuery("from PlanningUnit b where b.mandantengruppe.id=:mandantengruppeOID and b.hidden=:hidden", PlanningUnit.class).setParameter("hidden", hidden).setParameter("mandantengruppeOID", mandantengruppeOID).getResultList();
    }

    public List<PlanningUnit> loadPlanningUnitByMandantenGruppe(final String mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from PlanningUnit b " + "where b.mandantengruppe.mandantengruppe=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", PlanningUnit.class).setParameter("hidden", hidden).setParameter("active",
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
        //        final ObjectSet<PlanningUnit> objSet = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
        //                final boolean b = benutzer.getMandantengruppe().getMandantengruppe().equals(mandantengruppe);
        //                final boolean b1 = benutzer.getHidden().equals(hidden);
        //                final boolean b2 = benutzer.getActive().equals(active);
        //                return b && b1 && b2;
        //            }
        //        });
        //        return objSet;
    }

    public List<PlanningUnit> loadPlanningUnitByMandantenGruppe(final Long mandantengruppe, final boolean hidden, final Boolean active) {
        return entityManager.createQuery("from PlanningUnit b " + "where b.mandantengruppe.id=:mandantengruppe " + "and b.hidden=:hidden and b.active=:active", PlanningUnit.class).setParameter("hidden", hidden).setParameter("active", active).setParameter(
                "mandantengruppe",
                mandantengruppe).getResultList();


    }

    public List<PlanningUnit> aktivePlanningUnitByMandantenGruppe(final String mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from PlanningUnit b " + "where b.active=:aktiv " + "and b.mandantengruppe.mandantengruppe=:mandantengruppe", PlanningUnit.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv",
                aktiv).getResultList();
        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("active", aktiv)
        //                .findList();
        //        final String sql = "select id from benutzer b where b.active='" + aktiv.toString() + "' " +
        //                SQLCreator.and("b.mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe));
        //        return createQuery(sql).findList();

        //        final ObjectSet<PlanningUnit> objSet = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
        //                final String mandantenGrp = benutzer.getMandantengruppe().getMandantengruppe();
        //                final boolean b1 = mandantenGrp.equals(mandantengruppe);
        //                final Boolean b2 = benutzer.getActive();
        //                return b1 && (b2 && aktiv);
        //            }
        //        });
        //        return objSet;
    }

    public List<PlanningUnit> aktivePlanningUnitByMandantenGruppe(final Long mandantengruppe, final Boolean aktiv) {
        return entityManager.createQuery("from PlanningUnit b " + "where b.active=:aktiv " + "and b.mandantengruppe.id=:mandantengruppe", PlanningUnit.class).setParameter("mandantengruppe", mandantengruppe).setParameter("aktiv", aktiv).getResultList();
    }

    public PlanningUnit loadPlanningUnit(final String login, final String passwd, final String webappName) {
        PlanningUnit benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlanningUnit");
            benutzer = loadAnonymousPlanningUnit(webappName);
        } else {
            final TypedQuery<PlanningUnit> typedQuery1 = entityManager.createQuery("from PlanningUnit b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd and b.benutzerWebapplikation.webappName=:webappName", PlanningUnit.class);
            final TypedQuery<PlanningUnit> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);

            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("hidden", false)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousPlanningUnit(webappName);
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
            //            final ObjectSet<PlanningUnit> objSet = entityManager.query(new Predicate<PlanningUnit>() {
            //                @Override
            //                public boolean match(final PlanningUnit benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getPlanningUnitWebapplikation().getWebappName().equals(webappName);
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
            //                benutzer = loadAnonymousPlanningUnit(webappName);
            //            }

        }
        return benutzer;
    }

    public PlanningUnit loadPlanningUnit(final String login, final String passwd, final Long webappOID) {
        PlanningUnit benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlanningUnit");
            benutzer = loadAnonymousPlanningUnit(webappOID);
        } else {
            final TypedQuery<PlanningUnit> typedQuery1 = entityManager.createQuery("from PlanningUnit b where b.login=:login and b.hidden=false and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", PlanningUnit.class);
            final TypedQuery<PlanningUnit> typedQuery = typedQuery1.setParameter("login", login).setParameter("passwd", passwd).setParameter("webappOID", webappOID);

            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousPlanningUnit(webappOID);
            }

            if (benutzer == null) {       //TODO Executer um alle Anfragen...
                benutzer = findByID(-1L);
            } else {

            }
        }
        return benutzer;
    }

    public PlanningUnit loadPlanningUnitInclSystemPlanningUnit(final String login, final String passwd, final String webappName) {
        PlanningUnit benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlanningUnit");
            benutzer = loadAnonymousPlanningUnit(webappName);
        } else {
            final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit b " + "where b.login=:login " + "and b.active=true " + "and b.passwd=:passwd " + "and b.benutzerWebapplikation.webappName=:webappName",
                    PlanningUnit.class).setParameter("login", login).setParameter("passwd", passwd).setParameter("webappName", webappName);
            benutzer = getSingleResultOrNull(typedQuery);


            //            benutzer = createWhereClause()
            //                    .eq("login", login)
            //                    .eq("passwd", passwd)
            //                    .eq("active", true)
            //                    .eq("benutzerWebapplikation.webappName", webappName).findUnique();
            if (benutzer == null) {
                benutzer = loadAnonymousPlanningUnit(webappName);
            }
            //            final String sql = "select id from benutzer b where b.login = '" + login + "'" +
            ////                    SQLCreator.and("hidden", "false")+
            //                    SQLCreator.andValue("passwd", passwd) +
            //                    SQLCreator.andValue("active", "true") +
            //                    SQLCreator.and("b.benutzer_webapplikation_id", SQLCreator.webapplikationID(webappName));
            //            benutzer = createQuery(sql).findUnique();
            //            final ObjectSet<PlanningUnit> objSet = entityManager.query(new Predicate<PlanningUnit>() {
            //                @Override
            //                public boolean match(final PlanningUnit benutzer) {
            //                    final boolean b1 = benutzer.getLogin().equals(login);
            //                    final Boolean b2 = benutzer.getActive();
            //                    final boolean b3 = benutzer.getPasswd().equals(passwd);
            //                    final boolean b4 = benutzer.getPlanningUnitWebapplikation().getWebappName().equals(webappName);
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
            //                benutzer = loadAnonymousPlanningUnit(webappName);
            //            }
        }
        return benutzer;
    }

    public PlanningUnit loadPlanningUnitInclSystemPlanningUnit(final String login, final String passwd, final Long webappOID) {
        PlanningUnit benutzer = null;
        if (login == null || login.isEmpty() || passwd == null || passwd.isEmpty()) {
            logger.warn("Login oder Passwd ist null, lade anonymen PlanningUnit");
            benutzer = loadAnonymousPlanningUnit(webappOID);
        } else {
            final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery("from PlanningUnit b where b.login=:login and b.active=true and b.passwd=:passwd " + "and b.benutzerWebapplikation.id=:webappOID", PlanningUnit.class).setParameter("login",
                    login).setParameter(
                    "passwd",
                    passwd).setParameter("webappOID", webappOID);
            benutzer = getSingleResultOrNull(typedQuery);
            if (benutzer == null) {
                benutzer = loadAnonymousPlanningUnit(webappOID);
            }
        }
        return benutzer;
    }


    public PlanningUnit loadAnonymousPlanningUnit(final String webappName) {
        final String login = "Anonymous_" + webappName.replace("_App", "");
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: login:=" + login);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webApp:=" + webappName);
        }
        final TypedQuery<PlanningUnit> benutzerTypedQuery = entityManager.createQuery("from PlanningUnit b  where b.login=:login  " +
                "and b.benutzerWebapplikation.webappName=:webappName", PlanningUnit.class)
                .setParameter("login", login)
                .setParameter("webappName",
                        webappName);
        return getSingleResultOrNull(benutzerTypedQuery);
    }

    public PlanningUnit loadAnonymousPlanningUnit(final Long webappOID) {
        //        final String login = "Anonymous_" + webappName.replace("_App", "");

        if (logger.isDebugEnabled()) {
            logger.debug("loadAnonymousUser: webappOID:=" + webappOID);
        }
        final TypedQuery<PlanningUnit> benutzerTypedQuery = entityManager.createQuery("from PlanningUnit b  where b.login like 'Anonymous_%' and b.benutzerWebapplikation.id=:webappOID", PlanningUnit.class)
                //                        .setParameter("login", login)
                .setParameter("webappOID", webappOID);
        return getSingleResultOrNull(benutzerTypedQuery);

    }


    public boolean checkIfPlanningUnitLoginIsAvailable(final String wishedLogin, final String mandantengruppe) {
        //        boolean loginAvailable = false;
        final PlanningUnit benutzer = loadPlanningUnit(wishedLogin, mandantengruppe);
        if (benutzer == null) {
            //prüefe ob registration vorhanden..
            //            final String sql = "select id from registration r \n" +
            //                    "where r.mandantengruppe_id=(" + SQLCreator.mandantengruppeID(mandantengruppe) + ")" +
            //                    "and r.login = '" + wishedLogin + "'";
            //            final PlanningUnit registration = createQuery(sql).findUnique();
//            final RegistrationDAO registrationDAO = new RegistrationDAO(entityManager);
//            return registrationDAO.checkIfLoginIsAvailable(wishedLogin, mandantengruppe);
            return true;
        } else {
            logger.info("Login wird schon aktiv verwendet : " + wishedLogin + " / " + mandantengruppe);
            return false;
        }

        //        final ObjectSet<PlanningUnit> objSetOne = entityManager.query(new Predicate<PlanningUnit>() {
        //            @Override
        //            public boolean match(final PlanningUnit benutzer) {
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

    public PlanningUnit loadPlanningUnitByEmail(final String email) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery(
                "select b from PlanningUnit" +
                        "  b where b.email=:email ",
                PlanningUnit.class)
                .setParameter("email", email);
        return getSingleResultOrNull(typedQuery);
    }

    public PlanningUnit loadPlanningUnitByEmail(final String email, final Long mandantengruppeOID) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery(
                "select b from PlanningUnit" +
                        "  b where b.email=:email " +
                        " and b.mandantengruppe.id=:mandantengruppeOID",
                PlanningUnit.class)
                .setParameter("email", email)
                .setParameter("mandantengruppeOID", mandantengruppeOID);
        return getSingleResultOrNull(typedQuery);
    }

    public PlanningUnit loadPlanningUnitByEmail(final String email, final String mandantengruppe) {
        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery(
                "select b from PlanningUnit b where b.email=:email " +
                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
                PlanningUnit.class)
                .setParameter("email", email)
                .setParameter("mandantengruppe", mandantengruppe);
        return getSingleResultOrNull(typedQuery);
    }
//    public PlanningUnit loadPlanningUnitByEmail(final String email, final Long mandantengruppeOID) {
//        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.id=:mandantengruppeOID",
//                PlanningUnit.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppeOID", mandantengruppeOID);
//        return getSingleResultOrNull(typedQuery);
//    }
//
//    public PlanningUnit loadPlanningUnitByEmail(final String email, final String mandantengruppe) {
//        final TypedQuery<PlanningUnit> typedQuery = entityManager.createQuery(
//                "select b" +
//                        " from Person p" +
//                        " inner join p.benutzer b" +
//                        " inner join p.kommunikationsServiceUIDs ksuid" +
//                        " inner join ksuid.uidparts part" +
//                        " where ksuid.service.serviceName='Email' and part.uidPart=:email" +
//                        " and b.mandantengruppe.mandantengruppe=:mandantengruppe",
//                PlanningUnit.class)
//                .setParameter("email", email)
//                .setParameter("mandantengruppe", mandantengruppe);
//        return getSingleResultOrNull(typedQuery);
//    }

}
