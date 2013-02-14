package org.rapidpm.lang;

/**
 * Created by IntelliJ IDEA.
 * User: cernst
 * Date: 05.10.11
 * Time: 14:51
 */

/**
 * Ein zusammengehörendes (generisches) Wertepaar.
 *
 * @param <T1> Typ des ersten Wertes
 * @param <T2> Typ des zweiten Wertes
 * @author Christian Ernst
 */
public class Pair<T1, T2> {
    private T1 value1 = null;
    private T2 value2 = null;

    public static <T1, T2> Pair<T1, T2> create(final T1 value1, final T2 value2) {
        return new Pair<T1, T2>(value1, value2);
    }

    /**
     * Konstruktor.
     */
    public Pair() {
    }

    /**
     * Konstruktor mit Wertepaar.
     *
     * @param value1 Erster Wert
     * @param value2 Zweiter Wert.
     */
    public Pair(final T1 value1, final T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    /**
     * Gibt den ersten Wert zurück.
     *
     * @return ersten Wert
     */
    public T1 getValue1() {
        return value1;
    }

    /**
     * Setzt ersten Wert.
     *
     * @param value1 erster Wert
     */
    public void setValue1(final T1 value1) {
        this.value1 = value1;
    }

    /**
     * Setzt zweiten Wert.
     *
     * @return zweiter Wert
     */
    public T2 getValue2() {
        return value2;
    }

    /**
     * Gibt zweiten Wert zurück.
     *
     * @param value2 zweiter Wert
     */
    public void setValue2(final T2 value2) {
        this.value2 = value2;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Pair pair = (Pair) o;

        if (value1 != null ? !value1.equals(pair.value1) : pair.value1 != null) return false;
        if (value2 != null ? !value2.equals(pair.value2) : pair.value2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value1 != null ? value1.hashCode() : 0;
        result = 31 * result + (value2 != null ? value2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }
}