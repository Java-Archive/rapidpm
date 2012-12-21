package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.AddButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.DeleteButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeActivateOnValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.ModelSingleton;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreeLayout extends VerticalLayout implements Internationalizationable{
    private static Logger logger = Logger.getLogger(IssueTreeLayout.class);

    private final IssueOverviewScreen screen;

    private Button addButton;
    private Button deleteButton;
    private Button expandButton;

    private Tree issueTree;

    private HorizontalLayout buttonLayout;

    public IssueTreeLayout(final IssueOverviewScreen screen, final IssueTabSheet issueTabSheet) {
        super();
        this.screen = screen;

        setComponents(issueTabSheet);
        doInternationalization();
    }

    private void setComponents(final IssueTabSheet issueTabSheet) {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        addButton = new Button();
        addButton.setEnabled(true);
        buttonLayout.addComponent(addButton);

        deleteButton = new Button();
        deleteButton.setEnabled(false);
        buttonLayout.addComponent(deleteButton);

        expandButton = new Button();
        expandButton.setEnabled(true);

        addComponent(buttonLayout);
        addComponent(expandButton);

        issueTree = new Tree("IssueTree");
        issueTree.setNullSelectionAllowed(false);
        issueTree.setContainerDataSource(ModelSingleton.getInstance(screen.getCurrentProject()));
        issueTree.setImmediate(true);
        if (issueTabSheet != null) {
            issueTree.addValueChangeListener(new TreeValueChangeListener(issueTabSheet, issueTree));
        }
        issueTree.addValueChangeListener(new TreeActivateOnValueChangeListener(new Button[]{deleteButton}));



        issueTree.setItemCaptionPropertyId(TreeIssueBaseContainer.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        if (issueTree.getItemIds().toArray().length > 0)
            issueTree.select(issueTree.getItemIds().toArray()[0]);

        issueTree.setDragMode(Tree.TreeDragMode.NODE);
        issueTree.setDropHandler(new TreeSortDropHandler(issueTree));
        addComponent(issueTree);

        addButton.addClickListener(new AddButtonClickListener(screen, issueTree));
        deleteButton.addClickListener(new DeleteButtonClickListener(screen, issueTree));
        expandButton.addClickListener(new Button.ClickListener() {
            private boolean expanded = true;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (expanded) {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.collapseItemsRecursively(id);
                    expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_expand"));
                }
                else {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.expandItemsRecursively(id);
                    expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
                }
                expanded = !expanded;
            }
        });
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(screen.getMessagesBundle().getString("add"));
        deleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
        expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
    }

    public void setSelectedItemByIssue(IssueBase issue) {
        for (Object itemId : issueTree.getItemIds()) {
            IssueBase treeIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
            if (treeIssue.equals(issue)){
                issueTree.select(itemId);
                break;
            }
        }
    }



    private static class TreeSortDropHandler implements DropHandler {
        private final Tree tree;

        /**
         * Tree must use {@link com.vaadin.data.util.HierarchicalContainer}.
         *
         * @param tree
         */

        public TreeSortDropHandler(final Tree tree) {
            this.tree = tree;
        }

        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        public void drop(DragAndDropEvent event) {
            // Wrapper for the object that is dragged
            Transferable t = event.getTransferable();

            // Make sure the drag source is the same tree
            if (t.getSourceComponent() != tree)
                return;

            Tree.TreeTargetDetails target = (Tree.TreeTargetDetails)
                    event.getTargetDetails();

            // Get ids of the dragged item and the target item
            Object sourceItemId = t.getData("itemId");
            Object targetItemId = target.getItemIdInto();

            // On which side of the target the item was dropped
            VerticalDropLocation location = target.getDropLocation();

            HierarchicalContainer container = (HierarchicalContainer)
                    tree.getContainerDataSource();

            // Drop right on an item -> make it a child
            if (location == VerticalDropLocation.MIDDLE) {
                container.setChildrenAllowed(targetItemId, true);
                tree.setParent(sourceItemId, targetItemId);
            }

                // Drop at the top of a subtree -> make it previous
            else if (location == VerticalDropLocation.TOP) {
                Object parentId = container.getParent(targetItemId);
                container.setParent(sourceItemId, parentId);
                container.moveAfterSibling(sourceItemId, targetItemId);
                container.moveAfterSibling(targetItemId, sourceItemId);
            }

            // Drop below another item -> make it next
            else if (location == VerticalDropLocation.BOTTOM) {
                Object parentId = container.getParent(targetItemId);
                container.setParent(sourceItemId, parentId);
                container.moveAfterSibling(sourceItemId, targetItemId);
            }

//            Object parentId = container.getParent(targetItemId);
//            IssueBase issue = (IssueBase)container.getContainerProperty(parentId,
//                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
//            issue.addSubIssue((IssueBase)container.getContainerProperty(targetItemId,
//                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue());
        }



//        public AcceptCriterion getAcceptCriterion() {
//            // Alternatively, could use the following criteria to eliminate some
//            // checks in drop():
//            // new And(IsDataBound.get(), new DragSourceIs(tree));
//            return AcceptAll.get();
//        }
//
//        public void drop(DragAndDropEvent dropEvent) {
//            // Called whenever a drop occurs on the component
//
//            // Make sure the drag source is the same tree
//            Transferable t = dropEvent.getTransferable();
//
//            // see the comment in getAcceptCriterion()
//            if (t.getSourceComponent() != tree
//                    || !(t instanceof DataBoundTransferable)) {
//                return;
//            }
//
//            Tree.TreeTargetDetails dropData = ((Tree.TreeTargetDetails) dropEvent
//                    .getTargetDetails());
//
//            Object sourceItemId = ((DataBoundTransferable) t).getItemId();
//            // FIXME: Why "over", should be "targetItemId" or just
//            // "getItemId"
//            Object targetItemId = dropData.getItemIdOver();
//
//            // Location describes on which part of the node the drop took
//            // place
//            VerticalDropLocation location = dropData.getDropLocation();
//
//            moveNode(sourceItemId, targetItemId, location);
//
//        }
//
//        /**
//         * Move a node within a tree onto, above or below another node depending
//         * on the drop location.
//         *
//         * @param sourceItemId
//         *            id of the item to move
//         * @param targetItemId
//         *            id of the item onto which the source node should be moved
//         * @param location
//         *            VerticalDropLocation indicating where the source node was
//         *            dropped relative to the target node
//         */
//        private void moveNode(Object sourceItemId, Object targetItemId,
//                              VerticalDropLocation location) {
//            HierarchicalContainer container = (HierarchicalContainer) tree
//                    .getContainerDataSource();
//
//            // Sorting goes as
//            // - If dropped ON a node, we append it as a child
//            // - If dropped on the TOP part of a node, we move/add it before
//            // the node
//            // - If dropped on the BOTTOM part of a node, we move/add it
//            // after the node
//
//            if (location == VerticalDropLocation.MIDDLE) {
//                container.setChildrenAllowed(targetItemId, true);
//                if (container.setParent(sourceItemId, targetItemId)
//                        && container.hasChildren(targetItemId)) {
//                    // move first in the container
//                    container.moveAfterSibling(sourceItemId, null);
//                }
//            } else if (location == VerticalDropLocation.TOP) {
//                Object parentId = container.getParent(targetItemId);
//                if (container.setParent(sourceItemId, parentId)) {
//                    // reorder only the two items, moving source above target
//                    container.moveAfterSibling(sourceItemId, targetItemId);
//                    container.moveAfterSibling(targetItemId, sourceItemId);
//                }
//            } else if (location == VerticalDropLocation.BOTTOM) {
//                Object parentId = container.getParent(targetItemId);
//                if (container.setParent(sourceItemId, parentId)) {
//                    container.moveAfterSibling(sourceItemId, targetItemId);
//                }
//            }
//
//            Object parentId = container.getParent(targetItemId);
//            IssueBase issue = (IssueBase)container.getContainerProperty(parentId,
//                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
//            issue.addSubIssue((IssueBase)container.getContainerProperty(targetItemId,
//                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue());
//
//        }

   }
}
