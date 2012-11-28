package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeIssueBaseContainer;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.10.12
 * Time: 08:17
 * To change this template use File | Settings | File Templates.
 */
public class DeleteIssueWindow extends Window implements Internationalizationable {
    private static Logger logger = Logger.getLogger(DeleteIssueWindow.class);

    private final IssueOverviewScreen screen;
    private Label deleteLabel;
    private Button yesButton;
    private Button noButton;
    private CheckBox deleteRecursive;
    private HorizontalLayout buttonLayout;

    private final DeleteIssueWindow self = this;
    private final Tree issueTree;

    public DeleteIssueWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        super();
        if (screen == null)
            throw new NullPointerException("Screen must not be NULL!");
        if (issueTree == null)
            throw new NullPointerException("IssueTree must not be NULL!");
        this.screen = screen;
        this.issueTree = issueTree;
        this.setModal(true);
        this.setResizable(false);
        setComponentens();
        doInternationalization();
    }

    private void setComponentens() {
        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setSizeFull();

        deleteLabel = new Label();
        deleteLabel.setWidth("100%");
        contentLayout.addComponent(deleteLabel);

        deleteRecursive = new CheckBox();
        deleteRecursive.setWidth("100%");
        contentLayout.addComponent(deleteRecursive);

        buttonLayout = new HorizontalLayout();

        //buttonLayout.setMargin(true);
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
        setCaption(screen.getMessagesBundle().getString("issuetracking_issue_deletewindow"));

        deleteLabel.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_delete_question"));
        deleteLabel.setValue(issueTree.getItemCaption(issueTree.getValue()));

        deleteRecursive.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_deleterecursive"));
        yesButton.setCaption(screen.getMessagesBundle().getString("yes"));
        noButton.setCaption(screen.getMessagesBundle().getString("no"));
    }



    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object item = issueTree.getValue();
            issueTree.setValue(issueTree.getParent(item));
            boolean success = ((TreeIssueBaseContainer)issueTree.getContainerDataSource()).removeItem(item,
                    deleteRecursive.getValue());
            if (!success)
                Notification.show("Fehler beim Löschen!");
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
