package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.logic;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.transience.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.enums.IssuePriorities;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.enums.IssueStati;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projektplanung.ProjektplanungScreen;
import org.rapidpm.transience.prj.projectmanagement.planning.ProjektBean;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 25.08.12
 * Time: 18:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

/**
 * Nicht als FieldGroup möglich, da für nicht-primitive Attribute kein entsprechendes Feld erstellt werden kann
 */
public class DetailsFormLayout extends VerticalLayout {

    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");

    private FormLayout componentsLayout = new FormLayout();
    private HorizontalLayout buttonLayout = new HorizontalLayout();

    private ComboBox statusComboBox;
    private ComboBox priorityComboBox;
    private ComboBox reporterComboBox;
    private ComboBox assigneeComboBox;
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;

    public DetailsFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen){

        buildStatusBox(issueBase);
        buildPriorityBox(issueBase);
        buildReporterBox(issueBase);
        buildAssigneeBox(issueBase);
        buildPlannedDateField(issueBase);
        buildResolvedDateField(issueBase);
        buildClosedDateField(issueBase);
        buildForm();

        screen.getDetailPanel().addListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent event) {
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();
                    if( component instanceof Field){
                        component.setReadOnly(false);
                    }
                }
                buttonLayout.setVisible(true);
            }
        });
        cancelButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String issueStatusName = issueBase.getIssueStatus().getStatusName();
                final String issuePriorityName = issueBase.getIssuePriority().getPriorityName();
                final String reporterLoginName = issueBase.getReporter().getLogin();
                final String assigneeLoginName = issueBase.getAssignee().getLogin();
                final Date closedDate = issueBase.getDueDate_closed();
                final Date resolvedDate = issueBase.getDueDate_resolved();
                final Date plannedDate = issueBase.getDueDate_planned();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                statusComboBox.select(IssueStati.valueOf(issueStatusName));
                priorityComboBox.select(IssuePriorities.valueOf(issuePriorityName));
                reporterComboBox.select(reporterLoginName);
                assigneeComboBox.select(assigneeLoginName);
                closedDateField.setValue(closedDate);
                resolvedDateField.setValue(resolvedDate);
                plannedDateField.setValue(plannedDate);
                while (componentIterator.hasNext()) {
                    final Component component = componentIterator.next();
                    if (component instanceof Field) {
                        component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });
        saveButton.addListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final String statusBoxValue = statusComboBox.getValue().toString();
                final String priorityBoxValue = priorityComboBox.getValue().toString();
                final String reporterBoxValue = reporterComboBox.getValue().toString();
                final String assigneeBoxValue = assigneeComboBox.getValue().toString();
                final long plannedDateFieldValueTime = plannedDateField.getValue().getTime();
                final long resolvedDateFieldValueTime = resolvedDateField.getValue().getTime();
                final long closedDateFieldValueTime = closedDateField.getValue().getTime();
                final String planningUnitGroupListSelection = screen.getProjektSelect().getValue().toString();
                final Iterator<Component> componentIterator = componentsLayout.getComponentIterator();
                issueBase.getIssueStatus().setStatusName(statusBoxValue);
                issueBase.getIssuePriority().setPriorityName(priorityBoxValue);
                issueBase.getReporter().setLogin(reporterBoxValue);
                issueBase.getAssignee().setLogin(assigneeBoxValue);
                issueBase.getDueDate_planned().setTime(plannedDateFieldValueTime);
                issueBase.getDueDate_resolved().setTime(resolvedDateFieldValueTime);
                issueBase.getDueDate_closed().setTime(closedDateFieldValueTime);
                screen.fillTreePanel(planningUnitGroupListSelection);

                while(componentIterator.hasNext()){
                    final Component component = componentIterator.next();
                    if( component instanceof Field){
                       component.setReadOnly(true);
                    }
                }
                buttonLayout.setVisible(false);
            }
        });

       buttonLayout.addComponent(saveButton);
       buttonLayout.addComponent(cancelButton);
       buttonLayout.setVisible(false);
       addComponent(componentsLayout);
       addComponent(buttonLayout);


    }

    private void buildClosedDateField(IssueBase issueBase) {
        closedDateField = new DateField("Closed", issueBase.getDueDate_closed());
        closedDateField.setDateFormat(Constants.DATE_FORMAT.toPattern());
        closedDateField.setReadOnly(true);
    }

    private void buildResolvedDateField(IssueBase issueBase) {
        resolvedDateField = new DateField("Resolved", issueBase.getDueDate_resolved());
        resolvedDateField.setDateFormat(Constants.DATE_FORMAT.toPattern());
        resolvedDateField.setReadOnly(true);
    }

    private void buildPlannedDateField(IssueBase issueBase) {
        plannedDateField = new DateField("Planned", issueBase.getDueDate_planned());
        plannedDateField.setDateFormat(Constants.DATE_FORMAT.toPattern());
        plannedDateField.setReadOnly(true);
    }

    private void buildPriorityBox(IssueBase issueBase) {
        priorityComboBox = new ComboBox("Priority", new BeanItemContainer<>(IssuePriorities.class, Arrays.asList(IssuePriorities.values())));
        priorityComboBox.setItemIconPropertyId("icon");
        priorityComboBox.setNullSelectionAllowed(false);
        priorityComboBox.select(IssuePriorities.valueOf(issueBase.getIssuePriority().getPriorityName()));
        priorityComboBox.setReadOnly(true);
    }

    private void buildStatusBox(IssueBase issueBase) {
        statusComboBox = new ComboBox("Status", new BeanItemContainer<>(IssueStati.class, Arrays.asList(IssueStati.values())));
        statusComboBox.setItemIconPropertyId("icon");
        statusComboBox.setNullSelectionAllowed(false);
        statusComboBox.select(IssueStati.valueOf(issueBase.getIssueStatus().getStatusName()));
        statusComboBox.setReadOnly(true);
    }

    private void buildReporterBox(IssueBase issueBase) {
        reporterComboBox = new ComboBox("Reporter", new BeanItemContainer<>(String.class, Arrays.asList(ProjektBean.issueUsers)));
        reporterComboBox.setNullSelectionAllowed(false);
        reporterComboBox.select(issueBase.getReporter().getLogin());
        reporterComboBox.setReadOnly(true);
    }

    private void buildAssigneeBox(IssueBase issueBase) {
        assigneeComboBox = new ComboBox("Assignee", new BeanItemContainer<>(String.class, Arrays.asList(ProjektBean.issueUsers)));
        assigneeComboBox.setNullSelectionAllowed(false);
        assigneeComboBox.select(issueBase.getAssignee().getLogin());
        assigneeComboBox.setReadOnly(true);
    }

    private void buildForm() {

        componentsLayout.addComponent(statusComboBox);
        componentsLayout.addComponent(priorityComboBox);
        componentsLayout.addComponent(reporterComboBox);
        componentsLayout.addComponent(assigneeComboBox);
        componentsLayout.addComponent(plannedDateField);
        componentsLayout.addComponent(resolvedDateField);
        componentsLayout.addComponent(closedDateField);
    }

    public FormLayout getComponentsLayout() {
        return componentsLayout;
    }

    public void setComponentsLayout(FormLayout componentsLayout) {
        this.componentsLayout = componentsLayout;
    }

    public HorizontalLayout getButtonLayout() {
        return buttonLayout;
    }

    public void setButtonLayout(HorizontalLayout buttonLayout) {
        this.buttonLayout = buttonLayout;
    }
}
