package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.10.12
 * Time: 08:17
 * To change this template use File | Settings | File Templates.
 */
public class DeleteIssueWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(DeleteIssueWindow.class);

    private final IssueOverviewScreen screen;
    private final ResourceBundle messageBundle;
    private Label deleteLabel;
    private Button yesButton;
    private Button noButton;
    private CheckBox deleteRecursive;
    private HorizontalLayout buttonLayout;

    private final DeleteIssueWindow self = this;
    private final Tree issueTree;

    public DeleteIssueWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        if (screen == null)
            throw new NullPointerException("Screen must not be NULL!");
        if (issueTree == null)
            throw new NullPointerException("IssueTree must not be NULL!");

        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.issueTree = issueTree;
        this.setModal(true);
        this.setResizable(false);
        setComponentens();
        doInternationalization();
    }

    private void setComponentens() {
        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setSizeFull();

        deleteLabel = new Label();
        deleteLabel.setWidth("100%");
        contentLayout.addComponent(deleteLabel);

        deleteRecursive = new CheckBox();
        deleteRecursive.setWidth("100%");
        contentLayout.addComponent(deleteRecursive);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        yesButton = new Button();
        yesButton.setWidth("100%");
        yesButton.addClickListener(new YesButtonClickListener());
        buttonLayout.addComponent(yesButton);
        buttonLayout.setExpandRatio(yesButton, 1.0f);

        noButton = new Button();
        noButton.setWidth("100%");
        noButton.addClickListener(new NoButtonClickListener());
        buttonLayout.addComponent(noButton);
        buttonLayout.setExpandRatio(noButton, 1.0f);

        contentLayout.addComponent(buttonLayout);
        addComponent(contentLayout);
    }


    @Override
    public void doInternationalization() {
        setCaption(messageBundle.getString("issuetracking_issue_deletewindow"));

        deleteLabel.setCaption(messageBundle.getString("issuetracking_issue_deletequestion"));
        deleteLabel.setValue(issueTree.getItemCaption(issueTree.getValue()));

        deleteRecursive.setCaption(messageBundle.getString("issuetracking_issue_deleterecursive"));
        yesButton.setCaption(messageBundle.getString("yes"));
        noButton.setCaption(messageBundle.getString("no"));
    }



    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            boolean success = false;
            final Object item = issueTree.getValue();
            final TreeIssueBaseContainer container = ((TreeIssueBaseContainer)issueTree.getContainerDataSource());
            final Object parent = issueTree.getParent(item);
            if (parent == null && !issueTree.getItemIds().isEmpty()) {
                issueTree.setValue(issueTree.getItemIds().toArray()[0]);
            } else {
                issueTree.setValue(parent);
            }


            if (container.containsId(item)) {
                if (deleteRecursive.getValue()) {
                    success = container.removeItemRecursively(item);
                } else {
                    success = issueTree.removeItem(item);
                }
            }
            if (!success)
                Notification.show(messageBundle.getString("issuetracking_issue_deleteerror"));
            self.close();
        }
    }

    private class NoButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
