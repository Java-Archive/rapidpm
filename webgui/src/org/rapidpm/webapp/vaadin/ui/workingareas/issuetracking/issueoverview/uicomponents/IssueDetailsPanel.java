package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssuePrioritiesEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsPanel extends ComponentEditablePanel implements Internationalizationable{

    private IssueOverviewScreen screen;

    private Label headerLabel;
    private ComboBox statusSelect;
    private ComboBox prioritySelect;
    private ComboBox assigneeSelect;
    private Label reporterLabel;
    private FormLayout componentsLayout;

    private TextArea descriptionTextArea;



    public IssueDetailsPanel(IssueOverviewScreen screen) {
        super(screen);
        this.screen = screen;
        this.setSizeFull();
        doInternationalization();
    }


    @Override
    protected FormLayout buildForm() {
        componentsLayout = new FormLayout();

        headerLabel = new Label();

        statusSelect = new ComboBox();
        for (IssueStatusEnum statusEnum : IssueStatusEnum.values()) {
            statusSelect.addItem(statusEnum);
            statusSelect.setItemIcon(statusEnum, statusEnum.getIcon());
        }
        statusSelect.select(IssueStatusEnum.values()[0]);
        statusSelect.setTextInputAllowed(false);
        statusSelect.setNullSelectionAllowed(false);
        statusSelect.setReadOnly(true);
        componentsLayout.addComponent(statusSelect);

        prioritySelect = new ComboBox();
        for (IssuePrioritiesEnum prioritiesEnum : IssuePrioritiesEnum.values()) {
            prioritySelect.addItem(prioritiesEnum);
            prioritySelect.setItemIcon(prioritiesEnum, prioritiesEnum.getIcon());
        }
        prioritySelect.select(IssuePrioritiesEnum.values()[0]);
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        componentsLayout.addComponent(prioritySelect);

        reporterLabel = new Label("sven.ruppert");
        reporterLabel.setValue("sven.ruppert");
        componentsLayout.addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addItem("sven.ruppert");
        assigneeSelect.select("sven.ruppert");
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        assigneeSelect.setReadOnly(true);
        componentsLayout.addComponent(assigneeSelect);

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setReadOnly(true);
        componentsLayout.addComponent(descriptionTextArea);

        return componentsLayout;
    }


    @Override
    public void doInternationalization() {
        statusSelect.setCaption(screen.getMessagesBundle().getString("issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issue_reporter"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issue_description"));
    }


    public TextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public Label getHeaderLabel() {
        return headerLabel;
    }

}
