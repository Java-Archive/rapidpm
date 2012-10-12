/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@neoscio.de
 */

package org.rapidpm.persistence;

import org.junit.Test;
import org.rapidpm.Constants;
import org.rapidpm.persistence.prj.stammdaten.person.Person;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 08.12.11
 * Time: 18:11
 */
public class DaoFactoryTest {

    @Test
    public void testGetOIDFromEntity() throws Exception {
        final Person entity = new Person();
        entity.setId(23L);
        final DAO.EntityUtils entityUtils = new DAO.EntityUtils();
        final Long oid = entityUtils.getOIDFromEntity(entity);
        assertEquals(new Long(23L), oid);
        assertEquals(new Long(-1L), entityUtils.getOIDFromEntity("keine Entity"));
    }

    @Test
    public void testContainsOID() throws Exception {
        final List<Person> entityList = new ArrayList<>();
        entityList.add(new Person()); // null-ID
        for (long id = 5; id < 10; id++) {
            final Person entity = new Person();
            entity.setId(id);
            entityList.add(entity);
        }
        final DAO.EntityUtils entityUtils = new DAO.EntityUtils();
        assertTrue(entityUtils.containsOID(entityList, 7L));
        assertFalse(entityUtils.containsOID(entityList, 15L));
    }

    @Test
    public void testConnectEntity() throws Exception {
        final Person person = new Person();
        final ArrayList<Benutzer> benutzerList = new ArrayList<>();
        person.setBenutzer(benutzerList);
        final Benutzer b1 = new Benutzer();
        b1.setId(1L);
        final Benutzer b2 = new Benutzer();
        b2.setId(2L);
        final Benutzer b3 = new Benutzer();
        b3.setId(3L);
        final BenutzerDAO benutzerDAO = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST).getBenutzerDAO();
        benutzerDAO.connectEntity(b1, person.getBenutzer());
        benutzerDAO.connectEntity(b2, person.getBenutzer());
        benutzerDAO.connectEntity(b3, person.getBenutzer());
    }

    @Test(expected = DAO.AlreadyConnectedException.class)
    public void testConnectEntityFail() throws Exception {
        final Person person = new Person();
        final ArrayList<Benutzer> benutzerList = new ArrayList<>();
        person.setBenutzer(benutzerList);
        final Benutzer b1 = new Benutzer();
        b1.setId(1L);
        final Benutzer b2 = new Benutzer();
        b2.setId(2L);
        final Benutzer b3 = new Benutzer();
        b3.setId(3L);
        final BenutzerDAO benutzerDAO = new DaoFactory(Constants.PERSISTENCE_UNIT_NAME_TEST).getBenutzerDAO();
        benutzerDAO.connectEntity(b1, person.getBenutzer());
        benutzerDAO.connectEntity(b2, person.getBenutzer());
        benutzerDAO.connectEntity(b1, person.getBenutzer()); // fail
        benutzerDAO.connectEntity(b3, person.getBenutzer());
    }
}
