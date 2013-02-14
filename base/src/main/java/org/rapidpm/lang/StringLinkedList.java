/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.lang;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Kapselung einer linked Stringlist.
 *
 * @author Alexander Vos
 */
public class StringLinkedList extends LinkedList<String> implements StringList {
    /**
     * Erstellt eine linked Stringlist.
     */
    public StringLinkedList() {
    }

    /**
     * Erstellt eine linked Stringlist.
     *
     * @param c Collection.
     */
    public StringLinkedList(final Collection<? extends String> c) {
        super(c);
    }
}