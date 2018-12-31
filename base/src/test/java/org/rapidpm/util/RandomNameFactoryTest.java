package org.rapidpm.util;

import org.junit.jupiter.api.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 13:33
 */
public class RandomNameFactoryTest {
    @Test
    public void testNextVorname() throws Exception {
        final RandomNameFactory rnd = RandomNameFactory.getInstance();
        final String vorname = rnd.getRandomVorname();
        System.out.println(vorname);
    }

    @Test
    public void testNextNachname() throws Exception {
        final RandomNameFactory rnd = RandomNameFactory.getInstance();
        final String nachname = rnd.getRandomNachname();
        System.out.println(nachname);
    }

    @Test
    public void testNextName() throws Exception {
        final RandomNameFactory rnd = RandomNameFactory.getInstance();
        final String name = rnd.getRandomName();
        System.out.println(name);
    }
}