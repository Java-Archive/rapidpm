package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Tree;

public class TreeSortDropHandler implements DropHandler {
    private final Tree tree;

    /**
     * Tree must use {@link com.vaadin.data.util.HierarchicalContainer}.
     *
     * @param tree
     */
    public TreeSortDropHandler(Tree tree) {
        this.tree = tree;
    }

    public AcceptCriterion getAcceptCriterion() {
        // Alternatively, could use the following criteria to eliminate some
        // checks in drop():
        // new And(IsDataBound.get(), new DragSourceIs(tree));
        return AcceptAll.get();
    }

    public void drop(DragAndDropEvent dropEvent) {
        // Called whenever a drop occurs on the component

        // Make sure the drag source is the same tree
        Transferable t = dropEvent.getTransferable();

        // see the comment in getAcceptCriterion()
        if (t.getSourceComponent() != tree
                || !(t instanceof DataBoundTransferable)) {
            return;
        }

        com.vaadin.ui.Tree.TreeTargetDetails dropData = ((com.vaadin.ui.Tree.TreeTargetDetails) dropEvent
                .getTargetDetails());

        Object sourceItemId = ((DataBoundTransferable) t).getItemId();
        // FIXME: Why "over", should be "targetItemId" or just
        // "getItemId"
        Object targetItemId = dropData.getItemIdOver();

        // Location describes on which part of the node the drop took
        // place
        VerticalDropLocation location = dropData.getDropLocation();

        moveNode(sourceItemId, targetItemId, location);

    }

    /**
     * Move a node within a tree onto, above or below another node depending
     * on the drop location.
     *
     * @param sourceItemId
     *            id of the item to move
     * @param targetItemId
     *            id of the item onto which the source node should be moved
     * @param location
     *            VerticalDropLocation indicating where the source node was
     *            dropped relative to the target node
     */
    private void moveNode(Object sourceItemId, Object targetItemId,
                          VerticalDropLocation location) {
        HierarchicalContainer container = (HierarchicalContainer) tree
                .getContainerDataSource();

        // Sorting goes as
        // - If dropped ON a node, we append it as a child
        // - If dropped on the TOP part of a node, we move/add it before
        // the node
        // - If dropped on the BOTTOM part of a node, we move/add it
        // after the node

        if (location == VerticalDropLocation.MIDDLE) {
            if (container.setParent(sourceItemId, targetItemId)
                    && container.hasChildren(targetItemId)) {
                // move first in the container
                container.moveAfterSibling(sourceItemId, null);
            }
        } else if (location == VerticalDropLocation.TOP) {
            Object parentId = container.getParent(targetItemId);
            if (container.setParent(sourceItemId, parentId)) {
                // reorder only the two items, moving source above target
                container.moveAfterSibling(sourceItemId, targetItemId);
                container.moveAfterSibling(targetItemId, sourceItemId);
            }
        } else if (location == VerticalDropLocation.BOTTOM) {
            Object parentId = container.getParent(targetItemId);
            if (container.setParent(sourceItemId, parentId)) {
                container.moveAfterSibling(sourceItemId, targetItemId);
            }
        }
    }
    }
