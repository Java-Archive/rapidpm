package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuedelete;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTreeLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 09.10.12
 * Time: 08:17
 * To change this template use File | Settings | File Templates.
 */
public class IssueDeleteWindow extends Window implements Internationalizationable{
    private final IssueOverviewScreen screen;
    private Label deleteLabel;
    private Button yesButton;
    private Button noButton;
    private HorizontalLayout buttonLayout;

    private final IssueDeleteWindow self = this;
    private final Tree issueTree;

    public IssueDeleteWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        if (screen == null)
            throw new NullPointerException("Screen must not be NULL!");
        if (issueTree == null)
            throw new NullPointerException("IssueTree must not be NULL!");
        this.screen = screen;
        this.issueTree = issueTree;
        this.setModal(true);
        setComponentens();
        doInternationalization();
    }

    private void setComponentens() {
        deleteLabel = new Label();
        deleteLabel.setWidth("100%");
        addComponent(deleteLabel);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setMargin(true);
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


        addComponent(buttonLayout);
    }


    @Override
    public void doInternationalization() {
        setCaption(screen.getMessagesBundle().getString("issue_deletewindow"));

        deleteLabel.setCaption(screen.getMessagesBundle().getString("issue_delete_question"));
        deleteLabel.setValue(issueTree.getItemCaption(issueTree.getValue()));

        yesButton.setCaption(screen.getMessagesBundle().getString("yes"));
        noButton.setCaption(screen.getMessagesBundle().getString("no"));
    }

    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object selectParentItem = issueTree.getParent(issueTree.getValue());
            issueTree.getContainerDataSource().removeItem(issueTree.getValue());
            issueTree.removeItem(issueTree.getValue());
            issueTree.setValue(selectParentItem);
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
