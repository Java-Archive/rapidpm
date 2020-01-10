package org.rapidpm.persistence.prj.stammdaten.web;

import org.rapidpm.persistence.DAO;

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
 * @since 18.03.2010
 *        Time: 23:21:33
 */

public class WebDomainDAO extends DAO<Long, WebDomain> {

    public WebDomainDAO(final EntityManager entityManager) {
        super(entityManager, WebDomain.class);
    }

    public List<WebDomain> loadWebDomainsFor(final String suchmaschinenmodul, final String taetigkeitsfeld) {
        return entityManager.createQuery("select w from Organisationseinheit o inner join o.webDomains w " +
                "where o.id in (select o.id from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta " +
                "where ta.taetigkeitsfeld.namen=:taetigkeitsfeld and ta.mandantengruppe.mandantengruppe=:suchmaschinenmodul)", WebDomain.class)
                .setParameter("suchmaschinenmodul", suchmaschinenmodul).setParameter("taetigkeitsfeld", taetigkeitsfeld).getResultList();
    }

    public List<WebDomain> loadWebDomainsForSQL(final String suchmaschinenmodul, final String taetigkeitsfeld) {
        return entityManager.createNativeQuery("select * from webdomain w where w.id in (\n" +
                "          select ow.webdomains_id from organisationseinheit_webdomain ow where ow.organisationseinheit_id in (\n" +
                "          select otfa.organisationseinheit_id from organisationseinheit_taetigkeitsfeldassoc otfa where otfa.taetigkeitsfeldassocs_id in (\n" +
                "          select tfa.id from taetigkeitsfeldassoc tfa\n" +
                "  where tfa.mandantengruppe_id =\n" +
                "          (select id from mandantengruppe m\n" +
                "            where m.mandantengruppe= '" + suchmaschinenmodul + "')\n" +
                "          and\n" +
                "          tfa.klassifizierung_id =\n" +
                "                  (select id from taetigkeitsfeldklassifizierung tfk\n" +
                "                    where tfk.klassifizierung= 'Haupttätigkeit')\n" +
                "          and\n" +
                "          tfa.taetigkeitsfeld_id in\n" +
                "                  (select id from taetigkeitsfeld t where t.namen='" + taetigkeitsfeld + "')\n" +
                "        )\n" +
                "        )\n" +
                "        )", WebDomain.class)
                .getResultList();
    }


    public List<WebDomain> loadWebDomainsFor(final String suchmaschinenmodul, final Long taetigkeitsfeldOID) {
        return entityManager.createQuery("select w from Organisationseinheit o inner join o.webDomains w " +
                "where o.id in (select o.id from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta " +
                "where ta.taetigkeitsfeld.id=:taetigkeitsfeldOID and ta.mandantengruppe.mandantengruppe=:suchmaschinenmodul)",
                WebDomain.class)
                .setParameter("suchmaschinenmodul", suchmaschinenmodul)
                .setParameter("taetigkeitsfeldOID", taetigkeitsfeldOID)
                .getResultList();
    }

    public List<WebDomain> loadWebDomainsFor(final String suchmaschinenmodul) {
        return entityManager.createQuery("select w from Organisationseinheit o inner join o.webDomains w " +
                "where o.id in (select o.id from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta " +
                "where  ta.mandantengruppe.mandantengruppe=:suchmaschinenmodul)", WebDomain.class)
                .setParameter("suchmaschinenmodul", suchmaschinenmodul)
                .getResultList();
    }
    //REFAC DAO anpassen um mit der OID zu suchen
//    public List<WebDomain> loadWebDomainsFor(final Long suchmaschinenmodul){
//        return entityManager.createQuery("select w from Organisationseinheit o inner join o.webdomains w " +
//                "where o.id in (select o.id from Organisationseinheit o inner join o.taetigkeitsfeldAssocs ta " +
//                "where  ta.mandantengruppe.mandantengruppe=:suchmaschinenmodul)",WebDomain.class)
//                .setParameter("suchmaschinenmodul", suchmaschinenmodul)
//                .getResultList();
//    }

    public List<WebDomain> loadWebDomainsByName(final String domainname) {
        return entityManager.createQuery("select w from WebDomain w where w.domainName=:domainname", WebDomain.class).setParameter("domainname", domainname).getResultList();
    }


    //    public WebDomain loadWebDomain(final String domainName) {
    //        WebDomain result = null;
    //        try {
    //            result = (WebDomain) entityManager.createNamedQuery("LoadWebDomain")
    //                    .setParameter("domainName", domainName)
    //                    .getSingleResult();
    //        } catch (Exception e) {
    //            logger.warn("Die angegebene WebDmaoni konnte nicht geladen werden. : " + domainName);
    //        }
    //        return result;
    //    }


}
