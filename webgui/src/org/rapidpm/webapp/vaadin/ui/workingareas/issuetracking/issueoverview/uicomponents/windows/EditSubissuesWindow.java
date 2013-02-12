package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeSubissueSortDropHandler;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainerSingleton;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 08.01.13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class EditSubissuesWindow extends Window implements Internationalizationable {
    private static Logger logger = Logger.getLogger(EditSubissuesWindow.class);

    private final IssueOverviewScreen screen;
    private final EditSubissuesWindow self;
    private final ResourceBundle messageBundle;

    private Tree subIssueTree;
    private Button saveButton;
    private Button cancelButton;
    private Button expandButton;

    public EditSubissuesWindow(final IssueOverviewScreen screen) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
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
        cancelButton.addClickListener(new CancelButtonClickListener());
        horLayout.addComponent(cancelButton);

        contentLayout.addComponent(horLayout);

        expandButton = new Button();
        expandButton.setSizeFull();
        contentLayout.addComponent(expandButton);

        final RapidPanel treePanel = new RapidPanel();
        treePanel.setStyleName(Reindeer.PANEL_LIGHT);

        subIssueTree = new Tree(screen.getUi().getCurrentProject().getProjektName());
        subIssueTree.setNullSelectionAllowed(false);
        subIssueTree.setContainerDataSource(new TreeIssueBaseContainer(screen.getUi().getCurrentProject()));
        subIssueTree.setImmediate(true);

        subIssueTree.setItemCaptionPropertyId(TreeIssueBaseContainer.PROPERTY_CAPTION);
        for (final Object id : subIssueTree.rootItemIds())
            subIssueTree.expandItemsRecursively(id);
        if (subIssueTree.getItemIds().toArray().length > 0) {
            subIssueTree.select(subIssueTree.getItemIds().toArray()[0]);
        } else {
            if (logger.isDebugEnabled())
                logger.debug("No items to show");
        }

        final TreeSubissueSortDropHandler dropHandler = new TreeSubissueSortDropHandler(subIssueTree);

        subIssueTree.setDragMode(Tree.TreeDragMode.NODE);
        subIssueTree.setDropHandler(dropHandler);
        dropHandler.setActivated(true);

        treePanel.addComponent(subIssueTree);
        treePanel.setSizeFull();

        contentLayout.addComponent(treePanel);
        contentLayout.setExpandRatio(treePanel, 1F);

        expandButton.addClickListener(new Button.ClickListener() {
            private boolean expanded = true;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (expanded) {
                    for (final Object id : subIssueTree.rootItemIds())
                        subIssueTree.collapseItemsRecursively(id);
                    expandButton.setCaption(messageBundle.getString("issuetracking_issue_expand"));
                } else {
                    for (final Object id : subIssueTree.rootItemIds())
                        subIssueTree.expandItemsRecursively(id);
                    expandButton.setCaption(messageBundle.getString("issuetracking_issue_collapse"));
                }
                expanded = !expanded;
            }
        });

        saveButton.addClickListener(new SaveButtonClickListener(dropHandler));

        this.setContent(contentLayout);
    }

    @Override
    public void doInternationalization() {
        this.setCaption(messageBundle.getString("issuetracking_issue_dragdrop_edit"));
        saveButton.setCaption(messageBundle.getString("save"));
        cancelButton.setCaption(messageBundle.getString("cancel"));
        expandButton.setCaption(messageBundle.getString("issuetracking_issue_collapse"));
    }

    private class SaveButtonClickListener implements Button.ClickListener {
        private final TreeSubissueSortDropHandler dropHandler;

        public SaveButtonClickListener(final TreeSubissueSortDropHandler dropHandler) {
            if (dropHandler == null)
                throw new NullPointerException("DropHandler is null");

            this.dropHandler = dropHandler;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            final IssueBaseDAO dao = DaoFactorySingelton.getInstance().getIssueBaseDAO();
            final Map<IssueBase, List<IssueBase>> map = dropHandler.getChangedIssues();
            if (!map.isEmpty()) {
                for (final IssueBase key : map.keySet()) {
                    for (final IssueBase value : map.get(key)) {
                        if (value != null) {
                            key.addSubIssue(value);
                        } else {
                            key.setAsRootIssue();
                        }
                    }
                    dao.persist(key);
                }
            }

            TreeIssueBaseContainerSingleton.getInstance(screen.getUi().getCurrentProject()).refresh();
            self.close();
        }
    }

    private class CancelButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }


}
