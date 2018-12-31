package org.rapidpm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 29.09.11
 * Time: 15:43
 */
public class RandomGeneratorTest {

    private RandomGenerator rnd;

    @BeforeEach
    public void setUp() throws Exception {
        rnd = RandomGenerator.getInstance();
    }

    @Test
    public void testNextInt() throws Exception {
        int i = rnd.nextInt();
        i = rnd.nextInt(10);
        assertTrue(i >= 0 && i < 10);
        i = rnd.nextInt(10, 20);
        assertTrue(i >= 10 && i <= 20);
    }

    @Test
    public void testNextBoolean() throws Exception {
        final boolean b = rnd.nextBoolean();
    }

    @Test
    public void testNextChar() throws Exception {
        char c = rnd.nextChar();
        assertTrue(c >= Character.MIN_VALUE && c <= Character.MAX_VALUE);
        c = rnd.nextChar('c', 'g');
        assertTrue(c >= 'c' && c <= 'g');
    }

    @Test
    public void testNextLetter() throws Exception {
        final char l = Character.toLowerCase(rnd.nextLetter());
        assertTrue(l >= 'a' && l <= 'z');
    }

    @Test
    public void testNextDigit() throws Exception {
        final int d = rnd.nextDigit();
        assertTrue(d >= 0 && d <= 9);
    }

    @Test
    public void testNextString() throws Exception {
        String str = rnd.nextString(5);
        assertEquals(5, str.length());
        str = rnd.nextString(6, 9);
        assertTrue(str.length() >= 6 && str.length() <= 9);
        str = rnd.nextLetterString(4, 8);
        assertTrue(str.length() >= 4 && str.length() <= 8);

        str = rnd.nextString(5, 10, new RandomGenerator.CharFunctor() {
            @Override
            public char getChar() {
                return rnd.nextElement('a', 'b', 'c', 'ä', '$');
            }
        });
        assertTrue(str.length() >= 5 && str.length() <= 10);
    }

    @Test
    public void testNextWord() throws Exception {
        String word = rnd.nextWord(5);
        assertTrue(word.length() > 0 && word.length() <= 5);
        word = rnd.nextWord(3, 8);
        assertTrue(word.length() >= 3 && word.length() <= 8);
    }

    @Test
    public void testNextSentence() throws Exception {
        String s = rnd.nextSentence(5, 3, 8);
        String[] words = s.split(" ");
        assertEquals(5, words.length);
        for (final String word : words) {
            assertTrue(word.length() >= 3 && word.length() <= 8);
        }
        s = rnd.nextSentence(5, 10, 3, 8);
        final int length = s.split(" ").length;
        assertTrue(length >= 5 && length <= 10);
    }

    @Test
    public void testNextElement() throws Exception {
        final List<String> list = Arrays.asList("a", "4", "5.0");
        final String a = rnd.nextElement(list);
        assertTrue(list.contains(a));
    }

    @Test
    public void testNextElementCollection() throws Exception {
        final Collection<String> list = Arrays.asList("a", "4", "5.0");
        final String a = rnd.nextElement(list);
        assertTrue(list.contains(a));
    }

    @Test
    public void testNextDate() throws Exception {
        Date date = rnd.nextDate();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(1988, 12, 7);
        final Date time = calendar.getTime();
        final Date now = new Date();
        date = rnd.nextDate(time, now);
        assertTrue(date.after(time));
        assertTrue(date.before(now));
    }
}
