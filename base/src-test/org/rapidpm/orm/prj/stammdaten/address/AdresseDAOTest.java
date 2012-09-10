/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.orm.prj.stammdaten.address;

import org.junit.Test;
import org.rapidpm.orm.prj.BaseDAOTest;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 10.11.11
 * Time: 14:39
 */
public class AdresseDAOTest extends BaseDAOTest {
    @Test
    public void testGetAdressenByPerson() throws Exception {
        final AdresseDAO dao = daoFactoryFactory.getAdresseDAO();
        final List<Adresse> adressen = dao.getAdressenForPerson(1L);
        assertNotNull(adressen);
        assertFalse(adressen.isEmpty());
    }
}
