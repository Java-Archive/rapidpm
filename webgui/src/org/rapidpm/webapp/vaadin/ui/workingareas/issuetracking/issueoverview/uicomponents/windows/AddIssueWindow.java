package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class AddIssueWindow extends Window {
    private static Logger logger = Logger.getLogger(AddIssueWindow.class);

    private final IssueOverviewScreen screen;
    private final Tree issueTree;
    private AddIssueWindow self;

    private IssueDetailsLayout addDetailsLayout;

    public AddIssueWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        super();
        self = this;
        this.screen = screen;
        this.issueTree = issueTree;
        setCaption(screen.getMessagesBundle().getString("issuetracking_issue_addwindow"));
        this.setModal(true);
        this.setResizable(false);
        this.setWidth("70%");
        addDetailsLayout = new IssueDetailsLayout(screen, false);
        addDetailsLayout.addSaveButtonClickListener(new AddIssueSaveClickListener());
        addDetailsLayout.addCancelButtonClickListener(new AddIssueCancelClickListener());
        this.addComponent(addDetailsLayout);
    }

    private class AddIssueSaveClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            final IssueBase childIssue = addDetailsLayout.setIssueProperties(true);
            if (childIssue != null) {
                final Object itemId = issueTree.addItem();
                final Object parentItemId = issueTree.getValue();

                issueTree.getContainerDataSource().getContainerProperty(itemId,
                        TreeIssueBaseContainer.PROPERTY_CAPTION).setValue(childIssue.name());
                issueTree.getContainerDataSource().getContainerProperty(itemId,
                        TreeIssueBaseContainer.PROPERTY_ISSUEBASE).setValue(childIssue);

                if (parentItemId != null)  {
                    final IssueBase parentIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty
                            (parentItemId, TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
                    parentIssue.addSubIssue(childIssue);

                }

                issueTree.setChildrenAllowed(parentItemId, true);
                issueTree.setParent(itemId, parentItemId);
                issueTree.setChildrenAllowed(itemId, false);
                issueTree.expandItem(parentItemId);
                issueTree.select(itemId);
                self.close();
            } else
                if (logger.isDebugEnabled())
                    logger.debug("childIssue is null");

        }
    }

    private class AddIssueCancelClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
