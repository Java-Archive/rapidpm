/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.organisationseinheit;
/**
 * Created by IntelliJ IDEA.
 * User: svenruppert
 * Date: Nov 19, 2010
 * Time: 2:25:59 PM
 * To change this template use File | Settings | File Templates.
 */

import org.rapidpm.orm.prj.BaseDAOTest;
import org.rapidpm.orm.prj.stammdaten.address.Adresse;
import org.rapidpm.orm.prj.stammdaten.web.WebDomain;
import org.rapidpm.ormviews.ViewOrgEinheitHptTaetigkeitsfeld;
import org.rapidpm.ormviews.ViewOrgEinheitWebDomain;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class OrganisationseinheitDAOTest extends BaseDAOTest {

    @Test
    public void testLoadViewOrgEinheitWebDomain() throws Exception {
        final List<ViewOrgEinheitWebDomain> views = daoFactory.getOrganisationseinheitDAO().loadViewOrgEinheitWebDomain("Hochschulsuchmaschine");
        assertNotNull(views);
        assertFalse(views.isEmpty());
        System.out.println("views.size() = " + views.size());
        //TODO Veteilung auf Filter

        for (final ViewOrgEinheitWebDomain view : views) {
            assertNotNull(view);
            System.out.println("view = " + view);
        }
    }

    @Test
    public void testLoadViewOrgEinheitHptTaetigkeitsfeld() throws Exception {
        final List<ViewOrgEinheitHptTaetigkeitsfeld> views = daoFactory.getOrganisationseinheitDAO().loadViewOrgEinheitHptTaetigkeitsfeld("Hochschulsuchmaschine");
        assertNotNull(views);
        assertFalse(views.isEmpty());
        System.out.println("views.size() = " + views.size());
        for (final ViewOrgEinheitHptTaetigkeitsfeld view : views) {
            assertNotNull(view);
            System.out.println("view = " + view);
        }
    }

    @Test
    public void testLoadOrganisationseinheitenForMandantengruppe() throws Exception {
        final OrganisationseinheitDAO organisationseinheitDAO = daoFactory.getOrganisationseinheitDAO();

        final List<Organisationseinheit> meotec = organisationseinheitDAO.loadOrganisationseinheitenForMandantengruppe("meotec");


        for (final Organisationseinheit organisationseinheit : meotec) {
            final List<WebDomain> webdomains = organisationseinheit.getWebdomains();
            final List<TaetigkeitsfeldAssoc> taetigkeitsfeldAssocs = organisationseinheit.getTaetigkeitsfeldAssocs();
            if (webdomains.isEmpty() || taetigkeitsfeldAssocs.isEmpty()) {

            } else {
                final String webDomain = webdomains.get(0).getDomainName();
                final String namen = taetigkeitsfeldAssocs.get(0).getTaetigkeitsfeld().getNamen();
                System.out.println(webDomain + ";" + namen);

            }
        }


        assertNotNull(meotec);
        assertFalse(meotec.isEmpty());
    }

    @Test
    public void testLoadAllEntitiesFetchAllEager() throws Exception {
        final List<Organisationseinheit> organisationseinheits = daoFactory.getOrganisationseinheitDAO().loadAllEntities(true);
        daoFactory.getEntityManager().close();
        for (final Organisationseinheit organisationseinheit : organisationseinheits) {
            final List<Adresse> adressen = organisationseinheit.getAdressen();
            for (final Adresse adresse : adressen) {
                System.out.println("adresse.getStrasse() = " + adresse.getStrasse());
            }
        }

    }

    @Test
    public void testLoadOrganisationseinheitenFor() throws Exception {
        final OrganisationseinheitDAO organisationseinheitDAO = daoFactory.getOrganisationseinheitDAO();
        final List<Organisationseinheit> organisationseinheitList = organisationseinheitDAO.loadOrganisationseinheitenFor("KIO Oberberg", "Kunststoffverarbeitung");
        assertNotNull(organisationseinheitList);
        assertFalse(organisationseinheitList.isEmpty());
        for (final Organisationseinheit organisationseinheit : organisationseinheitList) {
            System.out.println("organisationseinheit.getOrganisationsName() = " + organisationseinheit.getOrganisationsName());
        }

    }

    @Test
    public void testLoadOrganisationseinheitForID() throws Exception {
        final OrganisationseinheitDAO organisationseinheitDAO = daoFactory.getOrganisationseinheitDAO();

        final List<Organisationseinheit> organisationseinheitList = organisationseinheitDAO.loadAllEntities();
        final List<Long> oids = new ArrayList<Long>();
        for (final Organisationseinheit organisationseinheit : organisationseinheitList) {
            oids.add(organisationseinheit.getId());
        }
        System.out.println("oids.size() = " + oids.size());
        for (final Long oid : oids) {
            final Organisationseinheit organisationseinheit = organisationseinheitDAO.loadOrganisationseinheitForID(oid);
            assertNotNull(organisationseinheit);
            final String organisationsName = organisationseinheit.getOrganisationsName();
            assertNotNull(organisationsName);
        }

        List<Organisationseinheit> organisationseinheits = Collections.emptyList();
        int i = 0;
        while (i < 100) {
            final long start = System.nanoTime();
            organisationseinheits = organisationseinheitDAO.loadWithOIDList(oids);
            final long stop = System.nanoTime();
            System.out.println("diff t [ms] " + ((stop - start) / 1000 / 1000));
            i++;
        }
        System.out.println("organisationseinheits.size() = " + organisationseinheits.size());

    }


}
