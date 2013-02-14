/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.lang;

import java.io.Serializable;

/**
 * Generischer Zähler (mutable Integer).
 *
 * @author Alexander Vos
 */
public class Counter implements Comparable<Counter>, Serializable {

    private int count;

    /**
     * Erstellt einen Zähler.
     *
     * @param count Startwert.
     */
    public Counter(final int count) {
        this.count = count;
    }

    /**
     * Erstellt einen Zähler.
     */
    public Counter() {
        this(0);
    }

    /**
     * Gibt den aktuellen Zählerstand zurück.
     *
     * @return Zählerstand.
     * @see #setCount(int)
     * @see #increment()
     * @see #decrement()
     * @see #reset()
     */
    public int getCount() {
        return count;
    }

    /**
     * Setzt den Zählerstand auf den angegebenen Wert.
     *
     * @param count Zählerstand.
     * @see #getCount()
     * @see #increment()
     * @see #decrement()
     * @see #reset()
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     * Erhöht den Zählerstand um 1.
     *
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #increment(int)
     * @see #decrement()
     */
    public int increment() {
        return ++count;
    }

    /**
     * Erhöht den Zählerstand um den angegebenen Wert.
     *
     * @param steps Schritte zur Erhöhung des Zählerstands.
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #increment()
     * @see #decrement()
     */
    public int increment(final int steps) {
        count += steps;
        return count;
    }

    /**
     * Erhöht den Zählerstand um den angegebenen Zähler.
     *
     * @param counter Zähler.
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #increment()
     * @see #decrement()
     */
    public int increment(final Counter counter) {
        count += counter.count;
        return count;
    }

    /**
     * Verringert den Zählerstand um 1.
     *
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #decrement(int)
     * @see #increment()
     */
    public int decrement() {
        return --count;
    }

    /**
     * Verringert den Zählerstand um den angegebenen Wert.
     *
     * @param steps Schritte zur Verringerung des Zählerstands.
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #decrement()
     * @see #increment()
     */
    public int decrement(final int steps) {
        count -= steps;
        return count;
    }

    /**
     * Verringert den Zählerstand um den angegebenen Zähler.
     *
     * @param counter Zähler.
     * @return Zählerstand.
     * @see #getCount()
     * @see #setCount(int)
     * @see #decrement()
     * @see #increment()
     */
    public int decrement(final Counter counter) {
        count -= counter.count;
        return count;
    }

    /**
     * Kopiert diesen Zähler.
     *
     * @return Kopie dieses Zählers.
     */
    public Counter copy() {
        return new Counter(count);
    }

    /**
     * Sezt den Zählerstand auf 0 zurück.
     *
     * @see #getCount()
     * @see #setCount(int)
     * @see #increment()
     * @see #decrement()
     */
    public void reset() {
        count = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Counter counter = (Counter) o;
        return count == counter.count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Counter{" +
                "count=" + count +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Counter o) {
        return NumberUtils.compare(count, o.count);

    }
}