package org.rapidpm.orm.prj.stammdaten.address;

import org.rapidpm.orm.EntityFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 10:06
 */
public class AdresseFactory extends EntityFactory<Adresse> {
    private static final Logger logger = Logger.getLogger(AdresseFactory.class);

    public AdresseFactory() {
        super(Adresse.class);
    }

    @Override
    public Adresse createRandomEntity() {
        final Adresse adresse = new Adresse();
        adresse.setGrosskundenPLZ(RND.nextBoolean());
        adresse.setHausnummer(String.valueOf(RND.nextInt(1, 100)));
        adresse.setKlassifizierung(new AdressKlassifizierungFactory().createRandomEntity());
        adresse.setNotiz(RND.nextSentence(5, 15, 3, 7));
        adresse.setOrtsname(combineStringWithNextIndex(RND.nextWord(6, 15)));
        adresse.setPlz(String.valueOf(RND.nextInt(10000, 99999)));
        adresse.setPostfachListe(new PostfachFactory().createRandomPostfaecher(3));
        adresse.setState(new StateFactory().createRandomEntity());
        adresse.setStrasse(combineStringWithNextIndex(RND.nextWord(5, 15)));
        return adresse;
    }

    @Test
    public void testCreate() throws Exception {
        logger.debug("wafdawdwdad");
        final Adresse randomEntity = createRandomEntity();

        assertNotNull(randomEntity.getGrosskundenPLZ());

        final int hausnummer = Integer.valueOf(randomEntity.getHausnummer());
        assertTrue(hausnummer >= 1 && hausnummer <= 100);

        final String klassifizierung = randomEntity.getKlassifizierung().getKlassifizierung();
        assertTrue(AdressKlassifizierungFactory.getKlassifizierungen().contains(klassifizierung));

        assertFalse(randomEntity.getNotiz().isEmpty());

        assertFalse(randomEntity.getOrtsname().isEmpty());

//        randomEntity.
//        assertTrue();
        System.out.println(randomEntity);
    }
}
