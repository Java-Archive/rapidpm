package org.rapidpm.persistence.prj.bewegungsdaten.msgcenter.msg;

import org.rapidpm.persistence.BaseDaoFactory;
import org.rapidpm.persistence.system.security.Benutzer;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 *
 * @author Sven Ruppert
 * @version 0.1
 *          <p/>
 *          This Source Code is part of the RapidPM - www.rapidpm.org project.
 *          please contact sven.ruppert@me.com
 * @since 21.09.2010
 *        Time: 16:34:25
 */

public class PersonalMessageDAO extends BaseDaoFactory.BaseDAO<Long, PersonalMessage> {
    private static final Logger logger = Logger.getLogger(PersonalMessageDAO.class);

    public PersonalMessageDAO(final EntityManager entityManager) {
        super(entityManager, PersonalMessage.class);
    }


    public List<PersonalMessage> loadUnreadMessagesForEmpfaenger(final Benutzer benutzer) {
        //        final String mandantengruppe = benutzer.getMandantengruppe().getMandantengruppe();
        return entityManager.createQuery("from PersonalMessage  pm where pm.empfaenger=:benutzer and pm.unread=true", PersonalMessage.class).setParameter("benutzer", benutzer).getResultList();

        //        final String sql = "select id from personal_message pm where pm.unread = true \n" +
        //                SQLCreator.and("pm.empfaenger_id", SQLCreator.benutzerID(benutzer));
        //        return createQuery(sql).findList();
        //        final List<PersonalMessage> unreadMsg = createWhereClause().eq("unread", true).eq("empfaenger.login", benutzer.getLogin()).eq("empfaenger.mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        return unreadMsg;
    }

    public List<PersonalMessage> loadMessagesForEmpfaenger(final Benutzer benutzer) {
        return entityManager.createQuery("from PersonalMessage  pm where pm.empfaenger=:benutzer", PersonalMessage.class).setParameter("benutzer", benutzer).getResultList();

        //        final String mandantengruppe = benutzer.getMandantengruppe().getMandantengruppe();
        //        final List<PersonalMessage> msgList = createWhereClause().eq("empfaenger.login", benutzer.getLogin()).eq("empfaenger.mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        return msgList;

        //        final String sql = "select id from personal_message pm where pm.empfaenger_id = " + SQLCreator.benutzerID(benutzer) + " \n";
        //        return createQuery(sql).findList();
    }

    public List<PersonalMessage> loadMessagesForSender(final Benutzer benutzer) {
        return entityManager.createQuery("from PersonalMessage  pm where pm.sender=:benutzer", PersonalMessage.class).setParameter("benutzer", benutzer).getResultList();
        //        final String mandantengruppe = benutzer.getMandantengruppe().getMandantengruppe();
        //        final List<PersonalMessage> msgList = createWhereClause().eq("sender.login", benutzer.getLogin()).eq("sender.mandantengruppe.mandantengruppe", mandantengruppe).findList();
        //        return msgList;
        //        final String sql = "select id from personal_message pm where pm.sender_id = " + SQLCreator.benutzerID(benutzer) + " \n";
        //        return createQuery(sql).findList();
    }

    //    public void sendSupportMsgRegistration(final Benutzer sender, final String assigne){
    //        final PersonalMessage pmsru = new PersonalMessage();
    //        pmsru.setCreated(new Date());
    //        final String mandantengruppe = sender.getMandantengruppe().getMandantengruppe();
    //        pmsru.setSubject("Registration : " + mandantengruppe);
    //        pmsru.setUnread(true);
    //        pmsru.setSender(sender);
    //        pmsru.setEmpfaenger(new BenutzerDAO(entityManager).loadBenutzer(assigne, "RapidPMPortal"));
    //        persist(pmsru);
    //
    //    }
    //
    //    public void sendSupportMsgNewPasswdRequest(final Benutzer sender, final String assigne){
    //        final PersonalMessage pmsru = new PersonalMessage();
    //        pmsru.setCreated(new Date());
    //        final String mandantengruppe = sender.getMandantengruppe().getMandantengruppe();
    //        pmsru.setSubject("New Passwdord : " + mandantengruppe);
    //        pmsru.setUnread(true);
    //        pmsru.setSender(sender);
    //        pmsru.setEmpfaenger(new BenutzerDAO(entityManager).loadBenutzer(assigne, "RapidPMPortal"));
    //        persist(pmsru);
    //    }
    //
    //    public void sendSupportMsgKontaktanfrage(final Benutzer sender, final String assigne){
    //        final PersonalMessage pmsru = new PersonalMessage();
    //        pmsru.setCreated(new Date());
    //        final String mandantengruppe = sender.getMandantengruppe().getMandantengruppe();
    //        pmsru.setSubject("Kontaktanfrage : " + mandantengruppe);
    //        pmsru.setUnread(true);
    //        pmsru.setSender(sender);
    //        pmsru.setEmpfaenger(new BenutzerDAO(entityManager).loadBenutzer(assigne, "RapidPMPortal"));
    //        persist(pmsru);
    //    }


}
