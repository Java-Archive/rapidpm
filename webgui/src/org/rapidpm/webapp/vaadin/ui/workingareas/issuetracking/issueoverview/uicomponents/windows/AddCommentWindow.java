package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComment;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.CommentsDataContainer;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 08.11.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class AddCommentWindow extends Window implements Internationalizationable {
    private static Logger logger = Logger.getLogger(AddRelationWindow.class);

    private final IssueOverviewScreen screen;
    private final CommentsDataContainer commentContainer;
    private final AddCommentWindow self;

    private TextArea commentText;
    private Button saveButton;
    private Button cancelButton;




    public AddCommentWindow(final IssueOverviewScreen screen, final AbstractIssueDataContainer commentContainer){
        super();
        self = this;
        this.screen = screen;
        this.commentContainer = (CommentsDataContainer) commentContainer;
        this.setModal(true);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout baseLayout = new VerticalLayout();
        baseLayout.setSizeFull();
        baseLayout.setSpacing(true);

        commentText = new TextArea();
        commentText.setWidth("100%");
        baseLayout.addComponent(commentText);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");

        saveButton = new Button();
        saveButton.addClickListener(new SaveButtonClickListener());
        cancelButton = new Button();
        cancelButton.addClickListener(new CancelButtonClickListener());

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);

        baseLayout.addComponent(buttonLayout);
        addComponent(baseLayout);
    }

    @Override
    public void doInternationalization() {
        this.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_addcommentwindow"));
        commentText.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_comments"));
        saveButton.setCaption(screen.getMessagesBundle().getString("add"));
        cancelButton.setCaption(screen.getMessagesBundle().getString("cancel"));
    }


    private class SaveButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            commentText.setRequired(false);

            final String commentTextValue = commentText.getValue();

            if (commentTextValue != null && commentTextValue != "") {
                final IssueComment newComment = new IssueComment();
                newComment.setText(commentTextValue);
                newComment.setCreator(screen.getUi().getCurrentUser());
                newComment.setCreated(new Date());
                if (!commentContainer.addComment(newComment)) {
                    //TODO Show Errormessage to User
                    logger.error("Adding comment failed");
                }
                self.close();
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Text for comment is needed");
                commentText.setRequired(true);
                commentText.setRequiredError("Text for comment is needed");
            }
        }
    }

    private class CancelButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
