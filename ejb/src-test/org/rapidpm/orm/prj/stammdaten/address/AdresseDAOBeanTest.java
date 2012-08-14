/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.orm.prj.stammdaten.address;

import org.rapidpm.orm.prj.BaseDAOBeanTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 15.12.11
 * Time: 13:09
 */
public class AdresseDAOBeanTest extends BaseDAOBeanTest<AdresseDAOBean> {
    public AdresseDAOBeanTest() {
        super(AdresseDAOBean.class);
    }

    @Test
    public void testSaveOrUpdateTX() throws Exception {
        final AdresseDAOBean.FlatAdresse adresse = new AdresseDAOBean.FlatAdresse();
        adresse.setStrasse("Galmeistraße");
        adresse.setHausnummer("23");
        adresse.setNotiz("Keine Notiz");
        adresse.setOrtsname("Iserlohn");
        adresse.setAdressKlassifizierungOID(daoFactory.getAddressKlassifizierungDAO().loadKlassifizierungPrivat().getId());
        adresse.setPlz("58097");
        adresse.setGrosskundenplz(false);
        adresse.setStateOID(daoFactory.getStateDAO().loadStateForShortname("NRW").getId());

        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final AdresseDAOBean.AdresseResult result = daoBean.saveOrUpdateTX("sessionID", -1L, adresse);
                assertTrue(result.getValid());
                printLog(result);
            }
        }.execute();
    }
}
