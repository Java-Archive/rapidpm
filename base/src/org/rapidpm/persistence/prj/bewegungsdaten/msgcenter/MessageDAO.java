package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter;

import org.apache.log4j.Logger;
import org.rapidpm.persistence.DAO;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * NeoScio
 * User: Manfred
 * Date: 22.02.2010
 * Time: 17:40:07
 * This Source Code is part of the RapidPM - www.rapidpm.org project.
 * please contact sven.ruppert@me.com
 */
public class MessageDAO extends DAO<Long, Message> {
    private static final Logger logger = Logger.getLogger(MessageDAO.class);


    public MessageDAO(final EntityManager entityManager) {
        super(entityManager, Message.class);
    }

    public List<Message> loadMessagesFor(final String webappName, final String mandantengruppe, final String klassifizierung) {
        return entityManager.createQuery("from Message  m where m.benutzerWebapplikation.webappName=:webappName " + "and m.mandantengruppe.mandantengruppe=:mandantengruppe " + "and m.messageKlassifizierung.name=:klassifizierung",
                Message.class).setParameter("webappName", webappName).setParameter("mandantengruppe", mandantengruppe).setParameter("klassifizierung", klassifizierung).getResultList();
        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("messageKlassifizierung.name", klassifizierung)
        //                .eq("benutzerWebapplikation.webappName", webappName)
        //                .findList();
        //        final String sql = "select id from message where benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webappName) +
        //                SQLCreator.and("mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe)) +
        //                SQLCreator.and("message_klassifizierung_id", SQLCreator.messageklassifizierungID(klassifizierung));
        //        return createQuery(sql).findList();
    }

    public List<Message> loadMessagesFor(final String webappName, final String mandantengruppe, final String klassifizierung, final String login) {
        return entityManager.createQuery("from Message  m where m.benutzerWebapplikation.webappName=:webappName " + "and m.mandantengruppe.mandantengruppe=:mandantengruppe " + "and m.messageKlassifizierung.name=:klassifizierung " + "and m.creator.login=:login",
                Message.class).setParameter("webappName", webappName).setParameter("mandantengruppe", mandantengruppe).setParameter("klassifizierung", klassifizierung).setParameter("login", login).getResultList();

        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("messageKlassifizierung.name", klassifizierung)
        //                .eq("benutzerWebapplikation.webappName", webappName)
        //                .eq("benutzer.login", login)
        //                .findList();

        //        final String sql = "select id from message where benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webappName) +
        //                SQLCreator.and("mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe)) +
        //                SQLCreator.and("message_klassifizierung_id", SQLCreator.messageklassifizierungID(klassifizierung)) +
        //                SQLCreator.and("benutzer_id", SQLCreator.benutzerID(login, mandantengruppe));
        //        return createQuery(sql).findList();

    }

    public List<Message> loadMessagesForMandantengruppe(final String mandantengruppe) {
        return entityManager.createQuery("from Message m where m.mandantengruppe.mandantengruppe=:mandantengruppe", Message.class).setParameter("mandantengruppe", mandantengruppe).getResultList();

        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .findList();
        //        final String sql = "select id from message where mandantengruppe_id=" + SQLCreator.mandantengruppeID(mandantengruppe);
        //        return createQuery(sql).findList();
        //        final ObjectSet<Message> objSet = entityManager.query(new Predicate<Message>() {
        //            @Override
        //            public boolean match(final Message message) {
        //                return chkMandantengruppe(message, mandantengruppe);
        //            }
        //        });
        //        return objSet;

    }

    public List<Message> loadMessagesForUsers(final String webappName, final String mandantengruppe, final String klassifizierung, final String benutzergruppenName) {
        return entityManager.createQuery("from Message  m where m.benutzerWebapplikation.webappName=:webappName " + "and m.mandantengruppe.mandantengruppe=:mandantengruppe " + "and m.messageKlassifizierung.name=:klassifizierung " + "and m.benutzerGruppe.gruppenname=:benutzergruppenName",
                Message.class)

                .setParameter("mandantengruppe", mandantengruppe).setParameter("klassifizierung", klassifizierung).setParameter("benutzergruppenName", benutzergruppenName).getResultList();


        //        return createWhereClause()
        //                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
        //                .eq("messageKlassifizierung.name", klassifizierung)
        //                .eq("benutzerWebapplikation.webappName", webappName)
        //                .eq("benutzerGruppe.gruppenname", benutzergruppenName)
        //                .findList();


        //        final String sql = "select id from message where benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webappName) +
        //                SQLCreator.and("mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe)) +
        //                SQLCreator.and("message_klassifizierung_id", SQLCreator.messageklassifizierungID(klassifizierung)) +
        //                SQLCreator.and("benutzer_gruppe_id", SQLCreator.benutzerGruppeID(benutzergruppenName));
        //        return createQuery(sql).findList();
        //        final ObjectSet<Message> objSet = entityManager.query(new Predicate<Message>() {
        //            @Override
        //            public boolean match(final Message message) {
        //                final boolean b0 = chkMandantengruppe(message, mandantengruppe);
        //                final boolean b1a = chkBenutzerGruppe(message, "anonymous");
        //                final boolean b1b = chkBenutzerGruppe(message, "user");
        //                final boolean b2 = chkKlassifizierung(message, klassifizierung);
        //                final boolean b3 = chkWebAppName(message, webappName);
        //                return b0 && (b1a || b1b) && b2 && b3;
        //            }
        //        });
        //        return objSet;

    }
    //TODO wo ist er Unterschied ??

    //    public List<Message> loadMessagesForAdmin(final String webappName, final String mandantengruppe, final String klassifizierung) {
    //        return  entityManager.createQuery("from Message  m where m.benutzerWebapplikation.webappName=:webappName " +
    //                                                  "and m.mandantengruppe.mandantengruppe=:mandantengruppe " +
    //                                                  "and m.messageKlassifizierung.name=:klassifizierung " +
    //                                                  , Message.class).getResultList();
    //
    ////        return createWhereClause()
    ////                .eq("mandantengruppe.mandantengruppe", mandantengruppe)
    ////                .eq("messageKlassifizierung.name", klassifizierung)
    ////                .eq("benutzerWebapplikation.webappName", webappName)
    ////                .findList();
    //
    ////        final String sql = "select id from message where benutzer_webapplikation_id=" + SQLCreator.webapplikationID(webappName) +
    ////                SQLCreator.and("mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe)) +
    ////                SQLCreator.and("message_klassifizierung_id",  SQLCreator.messageklassifizierungID(klassifizierung));
    ////        return createQuery(sql).findList();
    ////        final ObjectSet<Message> objSet = entityManager.query(new Predicate<Message>() {
    ////            @Override
    ////            public boolean match(final Message message) {
    ////                final boolean b0 = chkMandantengruppe(message, mandantengruppe);
    ////                final boolean b1a = chkBenutzerGruppe(message, "anonymous");
    ////                final boolean b1b = chkBenutzerGruppe(message, "user");
    ////                final boolean b1c = chkBenutzerGruppe(message, "admin");
    ////                final boolean b2 = chkKlassifizierung(message, klassifizierung);
    ////                final boolean b3 = chkWebAppName(message, webappName);
    ////                return b0 && (b1a || b1b || b1c) && b2 && b3;
    ////            }
    ////        });
    ////        return objSet;
    //    }


    //        final ObjectSet<Message> objSet = entityManager.query(new Predicate<Message>() {
    //            @Override
    //            public boolean match(final Message message) {
    //                final boolean b0 = chkWebAppName(message, webappName);
    //                final boolean b1 = chkMandantengruppe(message, mandantengruppe);
    //                final boolean b2 = chkKlassifizierung(message, klassifizierung);
    //                final boolean b3 = chkBenutzer(message, login);
    //                return b0 && b1 && b2 && b3;
    //            }
    //        });
    //        return objSet;

}

//    private boolean chkMandantengruppe(final Message message, final String mandantengruppe) {
//        boolean result = false;
//        final Mandantengruppe mandantengruppeObj = message.getMandantengruppe();
//        if (mandantengruppeObj.getMandantengruppe().equals(mandantengruppe)) {
//            result = true;
//        } else {
//            //falsche Mandantengruppe
//        }
//        return result;
//    }
//
//    private boolean chkBenutzerGruppe(final Message message, final String benutzerGrp) {
//        boolean result = false;
//        final BenutzerGruppe benutzerGruppe = message.getBenutzerGruppe();
////        final BenutzerWebapplikation webapplikation = message.getBenutzerWebapplikation();
//        if (benutzerGruppe.getGruppenName().equals(benutzerGrp)) {
//            result = true;
//        } else {
//            //falsche WebApp
//        }
//        return  result;
//    }
//
//    private boolean chkBenutzer(final Message message, final String login) {
//        boolean result = false;
//        final Benutzer benutzerObj = message.getBenutzer();
//        if (benutzerObj.getLastLogin().equals(login)) {
//            result = true;
//        } else {
//            //falsche WebApp
//        }
//        return  result;
//    }
//
//    private boolean chkWebAppName(final Message message, final String webappName) {
//        boolean result = false;
//        final BenutzerWebapplikation webapplikation = message.getBenutzerWebapplikation();
//        if (webapplikation.getWebappName().equals(webappName)) {
//            result = true;
//        } else {
//            //falsche WebApp
//        }
//        return  result;
//    }
//
//    private boolean chkKlassifizierung(final Message message, final String klassifizierung) {
//        boolean result = false;
//        final MessageKlassifizierung msgKlassifizierung = message.getMessageKlassifizierung();
//        if (msgKlassifizierung.getName().equals(klassifizierung)) {
//            result = true;
//        } else {
//            //falsche Klassifizierung
//        }
//        return result;
//    }


//    public List<Message> loadMessagesForAnonymus(final String webappName, final String mandantengruppe, final String klassifizierung) {
//        final String sql = "select id from message where benutzer_webapplikation_id="+SQLCreator.webapplikationID(webappName)+
//        SQLCreator.and("mandantengruppe_id", SQLCreator.mandantengruppeID(mandantengruppe))+
//        SQLCreator.and("message_klassifizierung_id", klassifizierung)+
//                SQLCreator.and("benutzer_id", "");
//return createQuery(sql).findList();

//        final ObjectSet<Message> objSet = entityManager.query(new Predicate<Message>() {
//            @Override
//            public boolean match(final Message message) {
//                final boolean b0 = chkMandantengruppe(message, mandantengruppe);
//                final boolean b1 = chkBenutzerGruppe(message, "anonymous");
//                final boolean b2 = chkKlassifizierung(message, klassifizierung);
//                final boolean b3 = chkWebAppName(message, webappName);
//                return b0 && b1 && b2 && b3;
//            }
//        });
//        return objSet;
//    }

