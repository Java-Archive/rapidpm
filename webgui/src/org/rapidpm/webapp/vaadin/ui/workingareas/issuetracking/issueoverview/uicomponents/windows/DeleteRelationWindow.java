package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 07.11.12
 * Time: 19:02
 * To change this template use File | Settings | File Templates.
 */
public class DeleteRelationWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(DeleteRelationWindow.class);

    private final IssueOverviewScreen screen;
    private final Table relationTable;
    private final DeleteRelationWindow self;
    private final ResourceBundle messageBundle;

    private Label questionLabel;
    private Button yesButton;
    private Button noButton;

    private final Object removeItemId;


    public DeleteRelationWindow(final IssueOverviewScreen screen, final Table relationTable) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (relationTable == null)
            throw new NullPointerException("Table must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.relationTable = relationTable;
        removeItemId = relationTable.getValue();
        this.setModal(true);
        this.setResizable(false);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);

        questionLabel = new Label();
        contentLayout.addComponent(questionLabel);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        yesButton = new Button();
        yesButton.addClickListener(new YesButtonClickListener());
        noButton = new Button();
        noButton.addClickListener(new NoButtonClickListener());

        buttonLayout.addComponent(yesButton);
        buttonLayout.addComponent(noButton);
        contentLayout.addComponent(buttonLayout);

        addComponent(contentLayout);
    }

    @Override
    public void doInternationalization() {
        setCaption(messageBundle.getString("issuetracking_issue_deleterelationswindow"));

        questionLabel.setCaption(messageBundle.getString("issuetracking_issue_deletequestion"));
        final Collection<?> ids = relationTable.getItem(removeItemId).getItemPropertyIds();
        String labelValue = "-      ";
        Object value;
        int i = 0;
        for (final Object propId : ids) {
            if (i < ids.size() - 2) {
                value = relationTable.getItem(removeItemId).getItemProperty(propId).getValue();
                if (value != null)
                    labelValue += value.toString() + "   -   ";
            } else
                break;
            i++;
        }
        questionLabel.setValue(labelValue);

        yesButton.setCaption(messageBundle.getString("yes"));
        noButton.setCaption(messageBundle.getString("no"));
    }

    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            relationTable.removeItem(removeItemId);
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
