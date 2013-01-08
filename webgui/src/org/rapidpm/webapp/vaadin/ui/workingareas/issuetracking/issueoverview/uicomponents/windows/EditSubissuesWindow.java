package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeActivateOnValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeSubissueSortDropHandler;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainerSingleton;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 08.01.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class EditSubissuesWindow extends Window implements Internationalizationable {
    private static Logger logger = Logger.getLogger(EditSubissuesWindow.class);

    private final IssueOverviewScreen screen;
    private EditSubissuesWindow self;

    private Tree issueTree;
    private Button saveButton;
    private Button cancelButton;
    private Button expandButton;

    public EditSubissuesWindow(final IssueOverviewScreen screen) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");

        self = this;
        this.screen = screen;
        this.setModal(true);
        this.setResizable(false);
        this.setHeight("100%");
        this.setWidth("30%");
        initComponents();
        doInternationalization();
    }

    private void initComponents(){
        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        final HorizontalLayout horLayout = new HorizontalLayout();
        horLayout.setSizeFull();
        horLayout.setSpacing(true);

        saveButton = new Button();
        saveButton.setSizeFull();
        horLayout.addComponent(saveButton);

        cancelButton = new Button();
        cancelButton.setSizeFull();
        horLayout.addComponent(cancelButton);

        contentLayout.addComponent(horLayout);

        expandButton = new Button();
        expandButton.setSizeFull();
        contentLayout.addComponent(expandButton);

        final RapidPanel treePanel = new RapidPanel();
        treePanel.setStyleName(Reindeer.PANEL_LIGHT);

        issueTree = new Tree(screen.getUi().getCurrentProject().getProjektName());
        issueTree.setNullSelectionAllowed(false);
        issueTree.setContainerDataSource(new TreeIssueBaseContainer(screen.getUi().getCurrentProject()));
        issueTree.setImmediate(true);

        issueTree.setItemCaptionPropertyId(TreeIssueBaseContainer.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        if (issueTree.getItemIds().toArray().length > 0)
            issueTree.select(issueTree.getItemIds().toArray()[0]);

        final TreeSubissueSortDropHandler dropHandler = new TreeSubissueSortDropHandler(issueTree);

        issueTree.setDragMode(Tree.TreeDragMode.NODE);
        issueTree.setDropHandler(dropHandler);
        dropHandler.setActivated(true);

        treePanel.addComponent(issueTree);
        treePanel.setSizeFull();

        contentLayout.addComponent(treePanel);
        contentLayout.setExpandRatio(treePanel, 1F);

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

        this.setContent(contentLayout);
    }

    @Override
    public void doInternationalization() {
        this.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_dragdrop_edit"));
        saveButton.setCaption(screen.getMessagesBundle().getString("save"));
        cancelButton.setCaption(screen.getMessagesBundle().getString("cancel"));
        expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
    }

}
