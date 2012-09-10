package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssuePrioritiesEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.IssueStatusEnum;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.COMMIT_EXCEPTION_MESSAGE;
import static org.rapidpm.Constants.DATE_FORMAT;

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
public class PlanningDetailsMyFormLayout extends MyFormLayout {

    private static final Logger logger = Logger.getLogger(PlanningDetailsMyFormLayout.class);
    private static final String ICON = "icon";

    private ComboBox statusComboBox;
    private ComboBox priorityComboBox;
    private ComboBox reporterComboBox;
    private ComboBox assigneeComboBox;
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;
    private ResourceBundle messages;

    public PlanningDetailsMyFormLayout(final IssueBase issueBase, final ProjektplanungScreen screen, final Panel screenPanel) {
        super(issueBase, screen, screenPanel);
        messages = screen.getMessagesBundle();

        buildStatusBox(issueBase);
        buildPriorityBox(issueBase);
        buildReporterBox(issueBase);
        buildAssigneeBox(issueBase);
        buildPlannedDateField(issueBase);
        buildResolvedDateField(issueBase);
        buildClosedDateField(issueBase);
        buildForm();

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
                statusComboBox.select(IssueStatusEnum.valueOf(issueStatusName));
                priorityComboBox.select(IssuePrioritiesEnum.valueOf(issuePriorityName));
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
                try{
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

                    while (componentIterator.hasNext()) {
                        final Component component = componentIterator.next();
                        if (component instanceof Field) {
                            component.setReadOnly(true);
                        }
                    }
                    buttonLayout.setVisible(false);
                }catch (NullPointerException e){
                    logger.info(COMMIT_EXCEPTION_MESSAGE);
                }catch(Exception e){
                    logger.warn("Exception", e);
                }
            }
        });


    }

    private void buildClosedDateField(IssueBase issueBase) {
        closedDateField = new DateField(messages.getString("planning_closed"), issueBase.getDueDate_closed());
        closedDateField.setDateFormat(DATE_FORMAT.toPattern());
        closedDateField.setReadOnly(true);
    }

    private void buildResolvedDateField(IssueBase issueBase) {
        resolvedDateField = new DateField(messages.getString("planning_resolved"), issueBase.getDueDate_resolved());
        resolvedDateField.setDateFormat(DATE_FORMAT.toPattern());
        resolvedDateField.setReadOnly(true);
    }

    private void buildPlannedDateField(IssueBase issueBase) {
        plannedDateField = new DateField(messages.getString("planning_planned"), issueBase.getDueDate_planned());
        plannedDateField.setDateFormat(DATE_FORMAT.toPattern());
        plannedDateField.setReadOnly(true);
    }

    private void buildPriorityBox(IssueBase issueBase) {
        priorityComboBox = new ComboBox(messages.getString("planning_priority"), new BeanItemContainer<>(IssuePrioritiesEnum
                .class,
                Arrays.asList(IssuePrioritiesEnum.values())));
        priorityComboBox.setItemIconPropertyId(ICON);
        priorityComboBox.setNullSelectionAllowed(false);
        priorityComboBox.select(IssuePrioritiesEnum.valueOf(issueBase.getIssuePriority().getPriorityName()));
        priorityComboBox.setReadOnly(true);
    }

    private void buildStatusBox(IssueBase issueBase) {
        statusComboBox = new ComboBox(messages.getString("planning_state"), new BeanItemContainer<>(IssueStatusEnum.class,
                Arrays.asList(IssueStatusEnum.values())));
        statusComboBox.setItemIconPropertyId(ICON);
        statusComboBox.setNullSelectionAllowed(false);
        statusComboBox.select(IssueStatusEnum.valueOf(issueBase.getIssueStatus().getStatusName()));
        statusComboBox.setReadOnly(true);
    }

    private void buildReporterBox(IssueBase issueBase) {
        reporterComboBox = new ComboBox(messages.getString("planning_reporter"), new BeanItemContainer<>(String.class, 
                Arrays.asList(ProjektBean.issueUsers)));
        reporterComboBox.setNullSelectionAllowed(false);
        reporterComboBox.select(issueBase.getReporter().getLogin());
        reporterComboBox.setReadOnly(true);
    }

    private void buildAssigneeBox(IssueBase issueBase) {
        assigneeComboBox = new ComboBox(messages.getString("planning_assignee"), new BeanItemContainer<>(String.class,
                Arrays.asList(ProjektBean.issueUsers)));
        assigneeComboBox.setNullSelectionAllowed(false);
        assigneeComboBox.select(issueBase.getAssignee().getLogin());
        assigneeComboBox.setReadOnly(true);
    }

    @Override
    protected void buildForm() {
        componentsLayout.addComponent(statusComboBox);
        componentsLayout.addComponent(priorityComboBox);
        componentsLayout.addComponent(reporterComboBox);
        componentsLayout.addComponent(assigneeComboBox);
        componentsLayout.addComponent(plannedDateField);
        componentsLayout.addComponent(resolvedDateField);
        componentsLayout.addComponent(closedDateField);
    }


}
