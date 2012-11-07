package org.rapidpm.persistence.security;

import org.rapidpm.RandomGenerator;
import org.rapidpm.persistence.EntityFactory;
import org.rapidpm.persistence.security.berechtigungen.BerechtigungEntityFactory;
import org.rapidpm.persistence.system.security.Benutzer;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:45
 */
public class BenutzerEntityFactory extends EntityFactory<Benutzer> {
    public static final long MILLISECONDS_PER_YEAR = 31536000000L;

    public BenutzerEntityFactory() {
        super(Benutzer.class);
    }

    @Override
    public Benutzer createRandomEntity() {
        final RandomGenerator rnd = RandomGenerator.getInstance();

//        final Person person = new PersonEntityFactory().createRandomEntity();

        final Benutzer benutzer = new Benutzer();
        benutzer.setHidden(false);
//        benutzer.setLogin(PersonenNameEntityFactory.namenslisteToString(person.getNamen()).toLowerCase().replace(' ', '.'));
        benutzer.setLogin("test.Login_" + System.nanoTime());
        benutzer.setPasswd(rnd.nextLetterString(6, 12)); // QUEST sicheres Passwort?
        final Date now = new Date();
        benutzer.setLastLogin(now);
        benutzer.setFailedLogins(0);
        benutzer.setActive(RND.nextBoolean());
        benutzer.setValidFrom(rnd.nextDate(new Date(now.getTime() - MILLISECONDS_PER_YEAR), now));
        benutzer.setValidUntil(rnd.nextDate(now, new Date(now.getTime() + MILLISECONDS_PER_YEAR)));
        benutzer.setMandantengruppe(new MandantengruppeEntityFactory().createRandomEntity());
        benutzer.setBenutzerGruppe(new BenutzerGruppeEntityFactory().createRandomEntity());
        benutzer.setBenutzerWebapplikation(new BenutzerWebapplikationEntityFactory().createRandomEntity());
//        benutzer.setPerson(person);
//        benutzer.setBerechtigungen(new BerechtigungEntityFactory().createRandomEntityList(1));
        return benutzer;
    }

    @Test
    public void testCreateRandomEntity() throws Exception {
        final BenutzerEntityFactory benutzerEntityFactory = new BenutzerEntityFactory();
        final Benutzer benutzer = benutzerEntityFactory.createRandomEntity();
        assertNotNull(benutzer);
    }
}
