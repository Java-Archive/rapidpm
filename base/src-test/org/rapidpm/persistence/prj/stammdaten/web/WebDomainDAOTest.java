package org.rapidpm.persistence.prj.stammdaten.web; /**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 25.03.11
 * Time: 12:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.junit.Test;
import org.rapidpm.persistence.DAOTest;
import org.rapidpm.persistence.system.security.berechtigungen.Berechtigung;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class WebDomainDAOTest extends DAOTest {
    private static final Logger logger = Logger.getLogger(WebDomainDAOTest.class);

    @Test
    public void testLoadWebDomainsFor() throws Exception {
        final WebDomainDAO dao = daoFactory.getWebDomainDAO();

        final List<Berechtigung> berechtigungList = daoFactory.getBerechtigungDAO().loadAllEntities();
        for (final Berechtigung berechtigung : berechtigungList) {
            System.out.println("berechtigung = " + berechtigung);
            final Set<String> webdomainSet = new HashSet<String>();
            final List<WebDomain> webDomains = dao.loadWebDomainsFor(berechtigung.getName());
            for (final WebDomain webDomain : webDomains) {
                final String domainName = webDomain.getDomainName();
                if (domainName != null && !domainName.isEmpty()) {
                    System.out.println("webDomain = " + webDomain);
                    assertNotNull(webdomainSet);
                    assertNotNull(domainName);
                    if (domainName.equals("www.actionsports.de")) {
                        System.out.println("domainName = " + domainName);
                    } else {

                    }
                    assertFalse(webdomainSet.contains(domainName));
                    webdomainSet.add(webDomain.getDomainName());
                } else {
                    System.out.println("webDomain = " + webDomain);
                }
            }

        }

    }

    @Test
    public void testLoadWebDomainsForSuchmodulAndTaetigkeitsfeldNameSQL() throws Exception {

        for (int i = 0; i < 4; i++) {
            final long start = System.nanoTime();
            final List<WebDomain> webDomains = daoFactory.getWebDomainDAO().loadWebDomainsFor("Hochschulsuchmaschine", "Universität");
            final long stop = System.nanoTime();
            System.out.println("diff [ms] = " + (stop - start) / 1000 / 1000);
            assertNotNull(webDomains);
            assertFalse(webDomains.isEmpty());
//            System.out.println("webDomains.size() = " + webDomains.size());
        }
        System.out.println(" = ");
        for (int i = 0; i < 4; i++) {
            final long start = System.nanoTime();
            final List<WebDomain> webDomains = daoFactory.getWebDomainDAO().loadWebDomainsForSQL("Hochschulsuchmaschine", "Universität");
            final long stop = System.nanoTime();
            System.out.println("diff [ms] = " + (stop - start) / 1000 / 1000);
            assertNotNull(webDomains);
            assertFalse(webDomains.isEmpty());
//            System.out.println("webDomains.size() = " + webDomains.size());
        }
    }


}
