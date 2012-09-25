package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssuePrioritiesEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsPanel extends Panel implements Internationalizationable{

    final private String ICON = "icon";

    private IssueOverviewScreen screen;

    private Label headerLabel;
    private ComboBox statusSelect;
    private ComboBox prioritySelect;
    private ComboBox assigneeSelect;
    private Label reporterLabel;
    private HorizontalLayout selectLayoutH;

    private TextArea descriptionTextArea;



    public IssueDetailsPanel(IssueOverviewScreen screen) {
        this.screen = screen;
        this.setSizeFull();
        buildComponents();
        doInternationalization();
    }


    private void buildComponents() {
        statusSelect = new ComboBox();
        for (IssueStatusEnum statusEnum : IssueStatusEnum.values()) {
            statusSelect.addItem(statusEnum);
            statusSelect.setItemIcon(statusEnum, statusEnum.getIcon());
        }
        statusSelect.setTextInputAllowed(false);
        statusSelect.setNullSelectionAllowed(false);
        addComponent(statusSelect);

        prioritySelect = new ComboBox();
        for (IssuePrioritiesEnum prioritiesEnum : IssuePrioritiesEnum.values()) {
            prioritySelect.addItem(prioritiesEnum);
            prioritySelect.setItemIcon(prioritiesEnum, prioritiesEnum.getIcon());
        }
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        addComponent(prioritySelect);

        reporterLabel = new Label("sven.ruppert");
        reporterLabel.setValue("sven.ruppert");
        addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addItem("sven.ruppert");
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        addComponent(assigneeSelect);


        selectLayoutH = new HorizontalLayout();

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        addComponent(descriptionTextArea);
    }

    public TextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    @Override
    public void doInternationalization() {
        statusSelect.setCaption(screen.getMessagesBundle().getString("issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issue_reporter"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issue_description"));
    }
}
