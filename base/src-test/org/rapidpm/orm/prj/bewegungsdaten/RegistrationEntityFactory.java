package org.rapidpm.orm.prj.bewegungsdaten;

import org.rapidpm.orm.security.BenutzerWebapplikationEntityFactory;
import org.rapidpm.orm.security.MandantengruppeEntityFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 11:48
 */
public class RegistrationEntityFactory extends org.rapidpm.orm.EntityFactory<Registration> {

    public RegistrationEntityFactory() {
        super(Registration.class);
    }

    @Override
    public Registration createRandomEntity() {
        final org.rapidpm.util.RandomNameFactory nameFactory = org.rapidpm.util.RandomNameFactory.getInstance();

        final Registration registration = new Registration();
        final String vorname = nameFactory.getRandomVorname();
        final String nachname = nameFactory.getRandomNachname();
        final String login = (vorname + '.' + nachname).toLowerCase();
        registration.setLogin(login);
        registration.setVorname(vorname);
        registration.setNachname(nachname);
        registration.setUnternehmen(RND.nextLetterString(5, 10));
        registration.setPosition(RND.nextLetterString(5, 10));
        registration.setEmail(login + "@mail.de");
        registration.setPlz(String.valueOf(RND.nextInt(1000, 99999)));
        registration.setOrt(RND.nextLetterString(8, 16));
        registration.setLaendercode("DE"); // TODO
        registration.setVorwahl("0" + RND.nextInt(1000, 9999));
        registration.setNummer(String.valueOf(RND.nextInt(100, 999)));
        registration.setDurchwahl(String.valueOf(RND.nextInt(100, 999)));
//        registration.setTitel(new TitelEntityFactory().createRandomEntity().getTitel());
        registration.setStrasse(RND.nextLetterString(8, 24));
        registration.setHausnr(String.valueOf(RND.nextInt(1, 100)));
        registration.setRegistrationStatus(new RegistrationStatusEntityFactory().createRandomEntity());
        // TODO registration.setTaetigkeitsfeld(new TaetigkeitsfeldEntityFactory().createRandomEntity());
        // TODO registration.setAnrede(new AnredeEntityFactory().createRandomEntity());
        registration.setMandantengruppe(new MandantengruppeEntityFactory().createRandomEntity());
        registration.setBenutzerWebapplikation(new BenutzerWebapplikationEntityFactory().createRandomEntity());
        return registration;
    }
}
