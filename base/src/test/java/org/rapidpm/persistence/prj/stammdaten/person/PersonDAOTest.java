package org.rapidpm.persistence.prj.stammdaten.person;

import org.junit.jupiter.api.Test;
import org.rapidpm.persistence.prj.BaseDAOTest;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.Mandantengruppe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PersonDAO Tester.
 *
 * @author Sven Ruppert
 * @version 1.0
 * @since <pre>05/04/2010</pre>
 */
public class PersonDAOTest extends BaseDAOTest {
    private static final String KIO_OBERBERG = "KIO Oberberg";

    /**
     * Method: loadPersonByBenutzer(final Benutzer benutzer)
     */
    @Test
    public void testLoadPersonByBenutzer() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: loadPersonByMandantengruppe(final String mandantengruppe)
     */
    @Test
    public void testLoadPersonByMandantengruppe() throws Exception {
        final PersonDAO personDAO = daoFactory.getPersonDAO();
        final List<Person> personList = personDAO.loadPersonByMandantengruppe(KIO_OBERBERG);
        assertNotNull(personList);
        assertFalse(personList.isEmpty());
        for (final Person person : personList) {
            boolean equals = false;
            final List<Benutzer> benutzerSet = person.getBenutzer();
            for (final Benutzer benutzer : benutzerSet) {
                final Mandantengruppe mandantengruppe = benutzer.getMandantengruppe();
                equals = mandantengruppe.getMandantengruppe().equals(KIO_OBERBERG);
                if (equals) {
                    break;
                }
                assertTrue(equals);
            }
        }
    }

    /**
     * loadPersonByBenutzer(final Long benutzerOID)
     */
    @Test
    public void testLoadPersonByBenutzerId() throws Exception {
        final PersonDAO dao = daoFactory.getPersonDAO();
        final Person person = dao.loadPersonByBenutzer(2L);
        assertNotNull(person);
    }

//    public static Test suite() {
//        return new JUnit4TestAdapter(PersonDAOTest.class);
//    }
}
