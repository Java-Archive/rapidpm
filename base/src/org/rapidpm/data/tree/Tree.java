/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.data.tree;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Beschreibt eine generische Baumstruktur.
 *
 * @author Alexander Vos
 * @see org.rapidpm.data.tree.impl.TreeImpl
 * @see Node
 */
public interface Tree extends Iterable<Node>, Serializable {
    /**
     * Gibt den Wurzelknoten des Baums zurück.
     *
     * @return Wurzelknoten.
     * @see #setRoot(Node)
     */
    Node getRoot();

    /**
     * Setzt den Wurzelknoten des Baums.
     *
     * @param root Wurzelknoten.
     * @see #getRoot()
     */
    void setRoot(Node root);

    /**
     * Gibt an, ob der Baum leer ist.
     *
     * @return <code>true</code>, wenn der Baum leer ist; sonst <code>false</code>.
     */
    boolean isEmpty();

    /**
     * Gibt einen Iterator über alle Knoten in diesem Baum zurück (in natürlicher Reihenfolge).
     *
     * @return Iterator über alle Knoten in diesem Baum.
     */
    @Override
    Iterator<Node> iterator();
}