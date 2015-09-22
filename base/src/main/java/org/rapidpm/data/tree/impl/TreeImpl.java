/*
 * Copyright (C) 2011 RapidPM - www.rapidpm.org
 *
 * This file is part of the bachelor thesis: Development of a Prototype for Trend Detection
 * in German Patents using Dynamic Stop Word Filters
 *
 * Bachelorarbeit: Entwicklung eines Prototypen zur Trenderkennung
 * in deutschen Patenten unter Einsatz dynamischer Stoppwortfilter
 *
 * Fachhochschule Südwestfalen, Iserlohn
 * Fachbereich Informatik und Naturwissenschaften
 * Studiengang Angewandte Informatik
 */

package org.rapidpm.data.tree.impl;

import com.google.common.collect.Lists;
import org.rapidpm.data.tree.Node;
import org.rapidpm.data.tree.Tree;

import java.util.Iterator;
import java.util.List;

/**
 * Implementiert eine generische Baumstruktur.
 *
 * @author Alexander Vos
 */
public class TreeImpl implements Tree {

  /**
   * Wurzelknoten.
   */
  protected Node root;

  /**
   * Erstellt einen generischen Baum.
   *
   * @param rootNode Wurzelknooten.
   */
  public TreeImpl(final Node rootNode) {
    rootNode.setParent(null);
    root = rootNode;
  }

  /**
   * Erstellt einen generischen Baum.
   */
  public TreeImpl() {
    root = null;
  }

  private static void recursiveTreeWalker(final List<Node> nodeList, final Node node) {
    nodeList.add(node);
    for (final Node childNode : node) {
      recursiveTreeWalker(nodeList, childNode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getRoot() {
    return root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRoot(final Node root) {
    root.setParent(null);
    this.root = root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<Node> iterator() {
    final List<Node> nodeList = Lists.newLinkedList();
    if (root != null) {
      recursiveTreeWalker(nodeList, root);
    }
    return nodeList.iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (root != null ? root.hashCode() : 0);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    final TreeImpl tree = (TreeImpl) o;

    if (root != null ? !root.equals(tree.root) : tree.root != null) return false;

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "TreeImpl{" +
        "root=" + root +
        '}';
  }
}