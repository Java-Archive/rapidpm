package org.rapidpm.util;

import org.rapidpm.RandomGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.10.11
 * Time: 13:04
 */
public class RandomNameFactory {

    private static final RandomNameFactory instance = new RandomNameFactory();
    private static final RandomGenerator RND = RandomGenerator.getInstance();

    //    private static Set<String> titel = new HashSet<String>();
    private static final Set<String> vornamen = new HashSet<String>();
    private static final Set<String> nachnamen = new HashSet<String>();

    static {
        vornamen.add("Maximilian");
        vornamen.add("Leon");
        vornamen.add("Alexander");
        vornamen.add("Paul");
        vornamen.add("Luca");
        vornamen.add("Lukas");
        vornamen.add("Elias");
        vornamen.add("Felix");
        vornamen.add("Tim");
        vornamen.add("David");

        vornamen.add("Sofie");
        vornamen.add("Lea");
        vornamen.add("Anna");
        vornamen.add("Mia");
        vornamen.add("Maria");
        vornamen.add("Leonie");
        vornamen.add("Johanna");
        vornamen.add("Marie");
        vornamen.add("Hannah");
        vornamen.add("Lena");

        nachnamen.add("Müller");
        nachnamen.add("Schmidt");
        nachnamen.add("Schneider");
        nachnamen.add("Fischer");
        nachnamen.add("Meyer");
        nachnamen.add("Weber");
        nachnamen.add("Becker");
        nachnamen.add("Wagner");
        nachnamen.add("Schulz");
        nachnamen.add("Herrmann");
        nachnamen.add("Schäfer");
        nachnamen.add("Bauer");
        nachnamen.add("Koch");
        nachnamen.add("Richter");
        nachnamen.add("Klein");
        nachnamen.add("Wolf");
        nachnamen.add("Schröder");
        nachnamen.add("Neumann");
        nachnamen.add("Zimmermann");
        nachnamen.add("Krüger");
    }

    public static RandomNameFactory getInstance() {
        return instance;
    }

    public String getRandomVorname() {
        return RND.nextElement(vornamen);
    }

    public String getRandomNachname() {
        return RND.nextElement(nachnamen);
    }

    public String getRandomName() {
        return getRandomVorname() + ' ' + getRandomNachname();
    }

    private RandomNameFactory() {
    }
}
