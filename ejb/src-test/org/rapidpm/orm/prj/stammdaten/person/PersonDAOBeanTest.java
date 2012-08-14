/*
 * Copyright (c) 2011. Diese Quelltexte sind Eigentum der RapidPM - www.rapidpm.org (RapidPM - www.rapidpm.org)
 * Bei Frage wenden Sie sich bitte an sven.ruppert@rapidpm.org
 */

package org.rapidpm.orm.prj.stammdaten.person;

import org.rapidpm.orm.prj.BaseDAOBeanTest;
import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 08.12.11
 * Time: 16:57
 */
public class PersonDAOBeanTest extends BaseDAOBeanTest<PersonDAOBean> {
    public PersonDAOBeanTest() {
        super(PersonDAOBean.class);
    }

    @Test
    public void testLoadPersonByBenutzer() throws Exception {
        BasicConfigurator.configure();

        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final PersonDAOBean.PersonResult result = daoBean.loadPersonByBenutzer("sessionID", -1L, 1L);
                assertNotNull(result);
            }
        }.execute();
    }

    @Test
    public void testSaveStammdaten() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final PersonDAOBean.FlatPersonStammdaten stammdaten = new PersonDAOBean.FlatPersonStammdaten();
                stammdaten.setId(3L); // Stammdaten aktualisieren
                stammdaten.setAnredeOID(daoFactory.getAnredeDAO().loadAnredeHerr().getId());
                stammdaten.setGeschlechtOID(daoFactory.getGeschlechtDAO().loadGeschlechtMaennlich().getId());
                stammdaten.setVornamen(Arrays.asList("Max"));
                stammdaten.setNachnamen(Arrays.asList("Mustermann"));
//                stammdaten.setGeburtsnamen(Arrays.asList("Mustergeburt"));
//                stammdaten.setTitel(Arrays.asList(""));
                final Calendar calendar = GregorianCalendar.getInstance();
                calendar.set(1973, Calendar.MARCH, 17);
                stammdaten.setGeburtsdatum(calendar.getTime());
                final PersonDAOBean.PersonResult result = daoBean.saveStammdaten("sessionID", -1L, stammdaten);
                printLog(result);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectBenutzer() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final PersonDAOBean.PersonResult result = daoBean.connectBenutzer("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }

    @Test
    public void testConnectAdresse() throws Exception {
        daoFactory.new Transaction() {
            @Override
            public void doTask() {
                final PersonDAOBean.PersonResult result = daoBean.connectAdresse("sessionID", -1L, 1L, 1L);
                assertTrue(result.getValid());
            }
        }.execute();
    }
}
