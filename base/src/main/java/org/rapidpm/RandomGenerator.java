package org.rapidpm;


import java.security.SecureRandom;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 29.09.11
 * Time: 15:16
 */
public class RandomGenerator {
    private static final Random random = new SecureRandom();
    private static final RandomGenerator instance = new RandomGenerator();

    public static RandomGenerator getInstance() {
        return instance;
    }

    /**
     * Generiert einen zufälligen Integer-Wert.
     *
     * @return Zufälliger Integer-Wert.
     */
    public int nextInt() {
        return random.nextInt();
    }

    /**
     * Generiert einen zufälligen Integer-Wert zwischen 0 (inklusiv) und dem angegebenen Wert <tt>n</tt> (exklusiv).
     *
     * @param n Grenze des Zufallswertes (exklusiv).
     * @return Zufälliger Integer-Wert.
     * @throws IllegalArgumentException Wenn <tt>n</tt> nicht positiv ist.
     */
    public int nextInt(final int n) {
        return random.nextInt(n);
    }

    /**
     * Generiert einen zufälligen Integer-Wert zwischen zwei Zahlen (inklusiv).
     *
     * @param from Startwert(inklusiv).
     * @param to   Endwert(inklusiv).
     * @return Zufälliger Integer-Wert im angegebenen Bereich.
     * @throws IllegalArgumentException Wenn <tt>to + 1 - from</tt> nicht positiv ist.
     */
    public int nextInt(final int from, final int to) {
        return from + random.nextInt(to + 1 - from);
    }

    /**
     * Generiert einen zufälligen boolschen Wert.
     *
     * @return Zufälliger boolscher Wert.
     */
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public char nextChar() {
        final int rnd = nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
        return (char) rnd;
    }

    public char nextChar(final char from, final char to) {
        final int rnd = nextInt(from, to);
        return (char) rnd;
    }

    public char nextLetter() {
        return nextChar('A', 'Z');
    }

    public int nextDigit() {
        return nextInt(0, 9);
    }

    public String nextString(final int minLength, final int maxLength, final CharFunctor charFunctor) {
        if (charFunctor == null) {
            throw new NullPointerException("charFunctor");
        }
        final int length = nextInt(minLength, maxLength);
        if (length < 1) {
            throw new IllegalArgumentException("length muss >= 1 sein");
        }
        final char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = charFunctor.getChar();
        }
        return new String(chars);
    }

    public String nextString(final int minLength, final int maxLength) {
        final CharFunctor randomCharFunctor = new CharFunctor() {
            @Override
            public char getChar() {
                return nextChar();
            }
        };
        return nextString(minLength, maxLength, randomCharFunctor);
    }


    public String nextString(final int length) {
        return nextString(length, length);
    }

    public String nextLetterString(final int minLength, final int maxLength) {
        final CharFunctor letterCharFunctor = this::nextLetter;
        return nextString(minLength, maxLength, letterCharFunctor);
    }

    public String nextWord(final int minLength, final int maxLength) {
        final String str = nextLetterString(minLength, maxLength);
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public String nextWord(final int length) {
        return nextWord(length, length);
    }

    public String nextSentence(final int wordCount, final int minWordLength, final int maxWordLength) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            final String word = nextWord(minWordLength, maxWordLength);
            sb.append(word);
            if (i == wordCount - 1) { // last word
                sb.append('.');
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public String nextSentence(final int minWordCount, final int maxWordCount, final int minWordLength, final int maxWordLength) {
        final int wordCount = nextInt(minWordCount, maxWordCount);
        return nextSentence(wordCount, minWordLength, maxWordLength);
    }

    public static interface CharFunctor {
        char getChar();
    }

    @SafeVarargs
    public final <E> E nextElement(final E... elements) {
        final int i = nextInt(elements.length);
        return elements[i];
    }

    public <E> E nextElement(final List<E> list) {
        final int i = nextInt(list.size());
        return list.get(i);
    }

    public <E> E nextElement(final Collection<E> collection) {
        int i = nextInt(collection.size());
        Iterator<E> iterator = collection.iterator();
        while (i-- > 0) {
            iterator.next();
        }
        return iterator.next();
    }

    public Date nextDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        final Date timeBegin = calendar.getTime();
        calendar.set(2030, 0, 0);
        final Date timeEnd = calendar.getTime();
        return nextDate(timeBegin, timeEnd);
    }

    public Date nextDate(final Date from, final Date to) {
        final long fromTime = from.getTime();
        final long toTime = to.getTime();
        final long timeDiff = toTime - fromTime;
        final long time = fromTime + (long) (random.nextDouble() * timeDiff);

        return new Date(time);
    }

    private RandomGenerator() {
    }
}
