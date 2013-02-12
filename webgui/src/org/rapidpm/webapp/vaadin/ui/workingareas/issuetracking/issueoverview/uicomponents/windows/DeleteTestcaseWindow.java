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
 * Date: 09.11.12
 * Time: 08:22
 * To change this template use File | Settings | File Templates.
 */
public class DeleteTestcaseWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(DeleteTestcaseWindow.class);

    private final IssueOverviewScreen screen;
    private final Table testcaseTable;
    private final DeleteTestcaseWindow self;
    private final ResourceBundle messageBundle;

    private Label questionLabel;
    private Button yesButton;
    private Button noButton;

    private final Object removeItemId;


    public DeleteTestcaseWindow(final IssueOverviewScreen screen, final Table testcaseTable) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (testcaseTable == null)
            throw new NullPointerException("Table must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.testcaseTable = testcaseTable;
        removeItemId = testcaseTable.getValue();
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
        setCaption(messageBundle.getString("issuetracking_issue_deletetestcasewindow"));

        questionLabel.setCaption(messageBundle.getString("issuetracking_issue_deletequestion"));
        final Collection<?> ids = testcaseTable.getItem(removeItemId).getItemPropertyIds();
        String labelValue = "-      ";
        Object value;
        for (final Object propId : ids) {
            value = testcaseTable.getItem(removeItemId).getItemProperty(propId).getValue();
            if (value != null)
                labelValue += value.toString() + "   -   ";
        }
        questionLabel.setValue(labelValue);

        yesButton.setCaption(messageBundle.getString("yes"));
        noButton.setCaption(messageBundle.getString("no"));
    }

    private class YesButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            testcaseTable.removeItem(removeItemId);
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
