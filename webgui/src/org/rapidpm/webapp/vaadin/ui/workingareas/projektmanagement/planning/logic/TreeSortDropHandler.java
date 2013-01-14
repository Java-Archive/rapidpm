package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic;

import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Tree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.PlanningUnitBeanItemContainer;

public class TreeSortDropHandler implements DropHandler {
        private final Tree tree;

        /**
         * Tree must use {@link com.vaadin.data.util.HierarchicalContainer}.
         *
         * @param tree
         */
        public TreeSortDropHandler(final Tree tree,final  PlanningUnitBeanItemContainer container) {
            this.tree = tree;
        }

        public AcceptCriterion getAcceptCriterion() {
            // Alternatively, could use the following criteria to eliminate some
            // checks in drop():
            // new And(IsDataBound.get(), new DragSourceIs(tree));
            return AcceptAll.get();
        }

        public void drop(final DragAndDropEvent dropEvent) {
            // Called whenever a drop occurs on the component

            // Make sure the drag source is the same tree
            final Transferable t = dropEvent.getTransferable();

            // see the comment in getAcceptCriterion()
            if (t.getSourceComponent() != tree
                    || !(t instanceof DataBoundTransferable)) {
                return;
            }

            final Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent
                    .getTargetDetails());

            final Object sourceItemId = ((DataBoundTransferable) t).getItemId();
            // FIXME: Why "over", should be "targetItemId" or just
            // "getItemId"
            final Object targetItemId = dropData.getItemIdOver();

            // Location describes on which part of the node the drop took
            // place
            final VerticalDropLocation location = dropData.getDropLocation();

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
        private void moveNode(final Object sourceItemId, final Object targetItemId,
                              final VerticalDropLocation location) {
            final PlanningUnitBeanItemContainer container = (PlanningUnitBeanItemContainer) tree
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
                final Object parentId = container.getParent(targetItemId);
                if (container.setParent(sourceItemId, parentId)) {
                    // reorder only the two items, moving source above target
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                }
            } else if (location == VerticalDropLocation.BOTTOM) {
                final Object parentId = container.getParent(targetItemId);
                if (container.setParent(sourceItemId, parentId)) {
                    container.moveAfterSibling(sourceItemId, targetItemId);
                }
            }
        }
    }
