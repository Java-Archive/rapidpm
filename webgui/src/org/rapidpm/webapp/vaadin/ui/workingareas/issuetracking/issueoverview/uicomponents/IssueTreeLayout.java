package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.*;
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
    private Button subissueButton;
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

        subissueButton = new Button();
        buttonLayout.addComponent(subissueButton);

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

        final TreeSubissueSortDropHandler dropHandler = new TreeSubissueSortDropHandler(issueTree);

        issueTree.setDragMode(Tree.TreeDragMode.NODE);
        issueTree.setDropHandler(dropHandler);
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
                } else {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.expandItemsRecursively(id);
                    expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
                }
                expanded = !expanded;
            }
        });

        subissueButton.addClickListener(new Button.ClickListener() {
            private boolean enabled = false;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (enabled) {
                    subissueButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_dragdrop_edit"));
                    dropHandler.setActivated(false);
                } else {
                    subissueButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_dragdrop_lock"));
                    dropHandler.setActivated(true);
                }
                enabled = !enabled;
            }
        });
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(screen.getMessagesBundle().getString("add"));
        deleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
        expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
        subissueButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_dragdrop_edit"));
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

}
