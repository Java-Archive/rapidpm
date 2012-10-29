package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.issueadd;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerIssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class IssueAddWindow extends Window {
    private final IssueOverviewScreen screen;
    private final Tree issueTree;
    private IssueAddWindow self;

    private IssueDetailsLayout addDetailsLayout;

    public IssueAddWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        self = this;
        this.screen = screen;
        this.issueTree = issueTree;
        setCaption(screen.getMessagesBundle().getString("issue_addwindow"));
        this.setModal(true);
        addDetailsLayout = new IssueDetailsLayout(screen);
        addDetailsLayout.addSaveButtonClickListener(new AddIssueSaveClickListener());
        addDetailsLayout.addCancelButtonClickListener(new AddIssueCancelClickListener());
        this.addComponent(addDetailsLayout);
    }

    private class AddIssueSaveClickListener implements Button.ClickListener {


        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object itemId = issueTree.addItem();
            Object parentItemId = issueTree.getValue();
            IssueBase childIssue = addDetailsLayout.setIssueProperties(true);
            IssueBase parentIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty
                    (parentItemId, TreeContainerIssueBase.PROPERTY_ISSUEBASE).getValue();
            issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeContainerIssueBase.PROPERTY_CAPTION).setValue(childIssue.name());
            issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeContainerIssueBase.PROPERTY_ISSUEBASE).setValue(childIssue);
            parentIssue.addSubIssue(childIssue);
            issueTree.setChildrenAllowed(parentItemId, true);
            issueTree.setParent(itemId, parentItemId);
            issueTree.setChildrenAllowed(itemId, false);

            self.close();
        }
    }

    private class AddIssueCancelClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
