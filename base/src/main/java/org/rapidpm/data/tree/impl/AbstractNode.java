/*
 * Copyright (c) 2011 - RapidPM. All Rights Reserved.
 */

package org.rapidpm.data.tree.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.rapidpm.data.tree.Node;

import java.util.Iterator;
import java.util.List;

/**
 * Definiert einen abstrakten Knoten innerhalb eines generischen Baums.
 *
 * @author Alexander Vos
 * @see org.rapidpm.data.tree.Tree
 */
public abstract class AbstractNode implements Node {
    private static final Logger logger = Logger.getLogger(AbstractNode.class);

    /**
     * Elternknoten.
     */
    protected Node parent;
    /**
     * Liste der Kindknoten.
     */
    protected final List<Node> children = Lists.newArrayList();

    /**
     * Erstellt einen Kindknoten.
     *
     * @param parent Der Elternknoten (kann <code>null</code> sein).
     */
    public AbstractNode(final Node parent) {
        this.parent = parent;
    }

    /**
     * Erstellt einen Knoten.
     */
    public AbstractNode() {
        this.parent = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(final Node parent) {
        Preconditions.checkArgument(parent != this, "parent cannot point to this node");
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getSibling(final int delta) {
        if (delta == 0) {
            return this;
        }
        if (parent == null) {
            return null;
        }
        final int index = parent.getChildIndex(this);
        if (index < 0) {
            logger.error("parent (" + parent + ") doesn't contain this node: " + this);
            return null;
        }
        final int nthIndex = index + delta;
        if (nthIndex < 0 || nthIndex >= parent.getChildCount()) {
            return null;
        }
        return parent.getChildAt(nthIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getPreviousSibling() {
        return getSibling(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getNextSibling() {
        return getSibling(+1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Node> getChildren() {
        return children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getChildCount() {
        return children.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getChildAt(final int index) {
        return children.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getChildIndex(final Node child) {
        return children.indexOf(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getFirstChild() {
        return children.size() > 0 ? children.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node getLastChild() {
        final int size = children.size();
        return size > 0 ? children.get(size - 1) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChildAt(final int index, final Node child) {
        child.setParent(this);
        children.set(index, child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendChild(final Node child) {
        child.setParent(this);
        children.add(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertChild(final int index, final Node child) {
        child.setParent(this);
        children.add(index, child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertChildBefore(final Node before, final Node child) {
        int index = children.indexOf(before);
        if (index < 0) {
            index = 0;
        }
        child.setParent(this);
        children.add(index, child);
        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertChildAfter(final Node after, final Node child) {
        int index = children.indexOf(after);
        if (index < 0) {
            index = children.size() - 1;
        }
        child.setParent(this);
        children.add(index + 1, child);
        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node removeChildAt(final int index) {
        return children.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeChild(final Node child) {
        return children.remove(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replaceChild(final Node oldChild, final Node newChild) {
        final int index = children.indexOf(oldChild);
        if (index < 0) {
            return false;
        }
        setChildAt(index, newChild);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Node> iterator() {
        return children.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AbstractNode that = (AbstractNode) o;

        if (!children.equals(that.children)) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + children.hashCode();
        return result;
    }
}