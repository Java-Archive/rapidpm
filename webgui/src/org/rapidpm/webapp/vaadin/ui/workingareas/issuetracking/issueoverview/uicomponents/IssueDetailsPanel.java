package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssuePrioritiesEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.Date;

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
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;

    private VerticalLayout componentsLayout;

    private TextArea descriptionTextArea;



    public IssueDetailsPanel(IssueOverviewScreen screen) {
        super(screen);
        this.screen = screen;
        this.setSizeFull();
        doInternationalization();
    }


    @Override
    protected AbstractOrderedLayout buildForm() {
        componentsLayout = new VerticalLayout();

        FormLayout dateLayout = new FormLayout();
        FormLayout detailLayout = new FormLayout();
        HorizontalLayout horLayout = new HorizontalLayout();

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
        detailLayout.addComponent(statusSelect);

        prioritySelect = new ComboBox();
        for (IssuePrioritiesEnum prioritiesEnum : IssuePrioritiesEnum.values()) {
            prioritySelect.addItem(prioritiesEnum);
            prioritySelect.setItemIcon(prioritiesEnum, prioritiesEnum.getIcon());
        }
        prioritySelect.select(IssuePrioritiesEnum.values()[0]);
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        detailLayout.addComponent(prioritySelect);

        reporterLabel = new Label("sven.ruppert");
        reporterLabel.setValue(" sven.ruppert");
        detailLayout.addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addItem("sven.ruppert");
        assigneeSelect.select("sven.ruppert");
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        assigneeSelect.setReadOnly(true);
        detailLayout.addComponent(assigneeSelect);

        plannedDateField = new DateField();
        plannedDateField.setResolution(Resolution.DAY);
        plannedDateField.setValue(new Date());
        plannedDateField.setReadOnly(true);
        dateLayout.addComponent(plannedDateField);

        resolvedDateField = new DateField();
        resolvedDateField.setResolution(Resolution.DAY);
        resolvedDateField.setValue(new Date());
        resolvedDateField.setReadOnly(true);
        dateLayout.addComponent(resolvedDateField);

        closedDateField = new DateField();
        closedDateField.setResolution(Resolution.DAY);
        closedDateField.setValue(new Date());
        closedDateField.setReadOnly(true);
        dateLayout.addComponent(closedDateField);

        horLayout.addComponent(detailLayout);
        horLayout.addComponent(dateLayout);
        componentsLayout.addComponent(horLayout);

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
        plannedDateField.setCaption(screen.getMessagesBundle().getString("issue_planned"));
        resolvedDateField.setCaption(screen.getMessagesBundle().getString("issue_resolved"));
        closedDateField.setCaption(screen.getMessagesBundle().getString("issue_closed"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issue_description"));
    }


    public TextArea getDescriptionTextArea() {
        return descriptionTextArea;
    }

    public Label getHeaderLabel() {
        return headerLabel;
    }

}
