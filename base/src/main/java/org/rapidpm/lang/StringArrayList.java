/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.lang;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Kapselung einer Array-Stringlist.
 *
 * @author Alexander Vos
 */
public class StringArrayList extends ArrayList<String> implements StringList {
    /**
     * Erstellt eine Array-Stringlist.
     *
     * @param initialCapacity Anfängliche Kapazität.
     */
    public StringArrayList(final int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Erstellt eine Array-Stringlist.
     */
    public StringArrayList() {
    }

    /**
     * Erstellt eine Array-Stringlist.
     *
     * @param c Collection.
     */
    public StringArrayList(final Collection<? extends String> c) {
        super(c);
    }
}