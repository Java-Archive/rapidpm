/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.data.tree;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Beschreibt einen Knoten innerhalb eines generischen Baums.
 *
 * @author Alexander Vos
 * @see Tree
 * @see org.rapidpm.data.tree.impl.AbstractNode
 */
public interface Node extends Iterable<Node>, Serializable {
  /**
   * Gibt den Elternknoten zurück.
   *
   * @return Der Elternknoten.
   *
   * @see #setParent(Node)
   * @see #hasParent()
   */
  Node getParent();

  /**
   * Setzt den Elternknoten (kann <code>null</code> sein).
   *
   * @param parent Der Elternknoten.
   *
   * @see #getParent()
   * @see #hasParent()
   */
  void setParent(Node parent);

  /**
   * Gibt an, ob dieser Knoten einen Elternknoten besitzt.
   *
   * @return <code>true</code>, wenn dieser Knoten einen Elternknoten besitzt; sonst <code>false</code>.
   *
   * @see #getParent()
   * @see #setParent(Node)
   * @see #isRoot()
   */
  boolean hasParent();

  /**
   * Gibt an, ob dieser Knoten der Wurzelknoten ist (er keinen Elternknoten besitzt).
   *
   * @return <code>true</code>, wenn dieser Knoten der Wurzelknoten ist; sonst <code>false</code>.
   *
   * @see #hasParent()
   */
  boolean isRoot();

  /**
   * Gibt den Nachbarknoten mit dem angegebenen Delta-Wert zurück.
   *
   * @param delta Position des Nachbarknotens.
   *
   * @return Der Nachbarknoten oder <code>null</code>, wenn der Nachbarknoten nicht existiert.
   *
   * @see #getPreviousSibling()
   * @see #getNextSibling()
   */
  Node getSibling(int delta);

  /**
   * Gibt den vorherigen Nachbarknoten zurück.
   *
   * @return Der Nachbarknoten oder <code>null</code>, wenn der Nachbarknoten nicht existiert.
   *
   * @see #getSibling(int)
   * @see #getNextSibling()
   */
  Node getPreviousSibling();

  /**
   * Gibt den nächsten Nachbarknoten zurück.
   *
   * @return Der Nachbarknoten oder <code>null</code>, wenn der Nachbarknoten nicht existiert.
   *
   * @see #getSibling(int)
   * @see #getNextSibling()
   */
  Node getNextSibling();

  /**
   * Gibt eine Liste aller direkten Kindknoten dieses Knotens zurück.
   *
   * @return Liste aller direkten Kindknoten.
   *
   * @see #hasChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #isLeaf()
   */
  List<Node> getChildren();

  /**
   * Gibt an, ob dieser Knoten direkte Kindknoten besitzt.
   *
   * @return <code>true</code>, wenn dieser Knoten direkte Kindknoten besitzt; sonst <code>false</code>.
   *
   * @see #getChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   */
  boolean hasChildren();

  /**
   * Gibt an, ob dieser Knoten ein Blatt ist (er keine Kindknoten besitzt)
   *
   * @return <code>true</code>, wenn dieser Knoten ein Blatt ist; sonst <code>false</code>.
   *
   * @see #hasChildren()
   */
  boolean isLeaf();

  /**
   * Gibt die Anzahl der direkten Kindknoten zurück.
   *
   * @return Anzahl der direkten Kindknoten.
   *
   * @see #getChildren()
   * @see #hasChildren()
   * @see #getChildAt(int)
   */
  int getChildCount();

  /**
   * Gibt den direkten Kindknoten an der angegebenen Indexposition zurück.
   *
   * @param index Index des Kindknotens.
   *
   * @return Der Kindknoten oder <code>null</code>, wenn dieser nicht existiert.
   *
   * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt
   *                                   (<tt>index &lt; 0 || index &gt;= getChildCount()</tt>).
   * @see #getChildren()
   * @see #hasChildren()
   * @see #getChildCount()
   * @see #getFirstChild()
   * @see #getLastChild()
   * @see #isLeaf()
   * @see #getChildIndex(Node)
   */
  Node getChildAt(int index);

  /**
   * Gibt den Index des angegebenen direkten Kindknotens zurück.
   *
   * @param child Kindknoten.
   *
   * @return Index des Kindknotens oder -1, wenn der Knoten kein direkter Kindknoten ist.
   *
   * @see #getChildAt(int)
   */
  int getChildIndex(Node child);

  /**
   * Gibt den ersten direkten Kindknoten zurück.
   *
   * @return Der Kindknoten oder <code>null</code>, wenn dieser nicht existiert.
   *
   * @see #getChildren()
   * @see #hasChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #getLastChild()
   * @see #isLeaf()
   * @see #getChildIndex(Node)
   */
  Node getFirstChild();

  /**
   * Gibt den letzten direkten Kindknoten zurück.
   *
   * @return Der Kindknoten oder <code>null</code>, wenn dieser nicht existiert.
   *
   * @see #getChildren()
   * @see #hasChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #getLastChild()
   * @see #isLeaf()
   * @see #getChildIndex(Node)
   */
  Node getLastChild();

  /**
   * Überschreibt einen Kindknoten an der angegebenen Indexposition. Der Kindknoten enthält diesen Knoten als Elternknoten.
   *
   * @param index Index des Kindknotens.
   * @param child Kindknoten.
   *
   * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt
   *                                   (<tt>index &lt; 0 || index &gt;= getChildCount()</tt>).
   * @see #getChildCount()
   * @see #getChildren()
   * @see #appendChild(Node)
   * @see #insertChild(int, Node)
   */
  void setChildAt(int index, Node child);

  /**
   * Fügt den Kindknoten ans Ende dieses Knotens. Der Kindknoten enthält diesen Knoten als Elternknoten.
   *
   * @param child Kindknoten.
   *
   * @see #getChildCount()
   * @see #getChildren()
   * @see #setChildAt(int, Node)
   * @see #insertChild(int, Node)
   */
  void appendChild(Node child);

  /**
   * Fügt einen Kindknoten an die angegebenen Indexposition ein. Der Kindknoten enthält diesen Knoten als Elternknoten.
   *
   * @param index Index des Kindknotens.
   * @param child Kindknoten.
   *
   * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt
   *                                   (<tt>index &lt; 0 || index &gt;= getChildCount()</tt>).
   * @see #getChildCount()
   * @see #getChildren()
   * @see #setChildAt(int, Node)
   * @see #appendChild(Node)
   * @see #insertChildBefore(Node, Node)
   * @see #insertChildAfter(Node, Node)
   */
  void insertChild(int index, Node child);

  /**
   * Fügt einen Kindknoten vor dem angegebenen Knoten ein. Der Kindknoten enthält diesen Knoten als Elternknoten.
   * Wenn der Kindknoten nicht existiert, wird der Knoten am Anfang eingefügt.
   *
   * @param before Knoten, bevor der Kindknoten eingefügt werden soll.
   * @param child  Kindknoten.
   *
   * @return Index der eingefügten Position.
   *
   * @see #getChildCount()
   * @see #getChildren()
   * @see #setChildAt(int, Node)
   * @see #appendChild(Node)
   * @see #insertChild(int, Node)
   */
  int insertChildBefore(Node before, Node child);

  /**
   * Fügt einen Kindknoten nach dem angegebenen Knoten ein. Der Kindknoten enthält diesen Knoten als Elternknoten.
   * Wenn der Kindknoten nicht existiert, wird der Knoten am Ende eingefügt.
   *
   * @param after Knoten, nachdem der Kindknoten eingefügt werden soll.
   * @param child Kindknoten.
   *
   * @return Index der eingefügten Position.
   *
   * @see #getChildCount()
   * @see #getChildren()
   * @see #setChildAt(int, Node)
   * @see #appendChild(Node)
   * @see #insertChild(int, Node)
   */
  int insertChildAfter(Node after, Node child);

  /**
   * Entfernt den direkten Kindknoten mit dem angegebenen Index.
   *
   * @param index Index des Kindknotens.
   *
   * @return Der entfernte Knoten.
   *
   * @throws IndexOutOfBoundsException Wenn der Index außerhalb des gültigen Bereichs liegt
   *                                   (<tt>index &lt; 0 || index &gt;= getChildCount()</tt>).
   * @see #getChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #removeChild(Node)
   * @see #replaceChild(Node, Node)
   * @see #setChildAt(int, Node)
   */
  Node removeChildAt(int index);

  /**
   * Entfernt den angegebenen Kindknoten.
   *
   * @param child Der zu entfernende Kindknoten.
   *
   * @return <code>true</code>, wenn der Knoten entfernt wurde; sonst <code>false</code>.
   *
   * @see #getChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #removeChildAt(int)
   * @see #replaceChild(Node, Node)
   * @see #setChildAt(int, Node)
   */
  boolean removeChild(Node child);

  /**
   * Ersetzt den angegebenen Kindknoten.
   *
   * @param oldChild Der zu ersetzenden Kindknoten.
   * @param newChild Der neue Kindknoten.
   *
   * @return <code>true</code>, wenn der Knoten ersetzt wurde; sonst <code>false</code>.
   *
   * @see #getChildren()
   * @see #getChildCount()
   * @see #getChildAt(int)
   * @see #removeChildAt(int)
   * @see #setChildAt(int, Node)
   */
  boolean replaceChild(Node oldChild, Node newChild);

  /**
   * Gibt einen Iterator über alle direkten Kindknoten dieses Knotens zurück.
   *
   * @return Iterator über alle direkten Kindknoten.
   */
  @Override
  Iterator<Node> iterator();
}
