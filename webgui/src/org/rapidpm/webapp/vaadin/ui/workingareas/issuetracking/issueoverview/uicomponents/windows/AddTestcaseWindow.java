package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueTestCase;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TestCasesDataContainer;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 09.11.12
 * Time: 08:14
 * To change this template use File | Settings | File Templates.
 */
public class AddTestcaseWindow extends RapidWindow implements Internationalizationable {
    private static Logger logger = Logger.getLogger(AddTestcaseWindow.class);

    private final IssueOverviewScreen screen;
    private final TestCasesDataContainer testcaseContainer;
    private final AddTestcaseWindow self;
    private final ResourceBundle messageBundle;

    private TextArea testcaseText;
    private Button saveButton;
    private Button cancelButton;




    public AddTestcaseWindow(final IssueOverviewScreen screen, final AbstractIssueDataContainer testcaseContainer){
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (testcaseContainer == null)
            throw new NullPointerException("Container must not be null");

        self = this;
        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.testcaseContainer = (TestCasesDataContainer) testcaseContainer;
        this.setModal(true);
        this.setResizable(false);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout baseLayout = new VerticalLayout();
        baseLayout.setSizeFull();
        baseLayout.setSpacing(true);

        testcaseText = new TextArea();
        testcaseText.setWidth("100%");
        baseLayout.addComponent(testcaseText);

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
        this.setCaption(messageBundle.getString("issuetracking_issue_addtestcasewindow"));
        testcaseText.setCaption(messageBundle.getString("issuetracking_issue_testcases"));
        saveButton.setCaption(messageBundle.getString("add"));
        cancelButton.setCaption(messageBundle.getString("cancel"));
    }


    private class SaveButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            testcaseText.setRequired(false);

            final String testcaseTextValue = testcaseText.getValue();

            if (testcaseTextValue != null && testcaseTextValue != "") {
                final IssueTestCase newTestcase = new IssueTestCase();
                newTestcase.setText(testcaseTextValue);
                testcaseContainer.addTestcase(newTestcase);
                self.close();
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Text for testcase is needed");
                testcaseText.setRequired(true);
                testcaseText.setRequiredError(messageBundle.getString("issuetracking_issue_details_testcasetext"));
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
