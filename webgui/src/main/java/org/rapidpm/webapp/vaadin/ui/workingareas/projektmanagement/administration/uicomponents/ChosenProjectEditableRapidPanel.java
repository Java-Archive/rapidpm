package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;

import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 10:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ChosenProjectEditableRapidPanel extends EditableRapidPanel {

    private static Logger logger = Logger.getLogger(ChosenProjectEditableRapidPanel.class);

    private ProjektFieldGroup fieldGroup;

    private Label noSelectionLabel;
    private ListBox<PlannedProject> listboxComponent;

    public ChosenProjectEditableRapidPanel(final ResourceBundle messagesBundle) {
        super(messagesBundle);
        noSelectionLabel = new Label(messagesBundle.getString("project_noselection"));

        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            final Iterator<Component> componentIterator = formLayout.getChildren().iterator();
            while (componentIterator.hasNext()) {
                final Component component = componentIterator.next();
                if (component instanceof AbstractField) {
                    ((AbstractField) component).setReadOnly(true);
                }
            }
            buttonsLayout.setVisible(false);
            activate(false);
        });
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            fieldGroup.getFieldForProperty(PlannedProject.TOKEN).ifPresent(tokenField -> {
                try {
                    PlannedProjectDAO plannedProjectDAO = DaoFactorySingelton.getInstance().getPlannedProjectDAO();
                    final PlannedProject plannedProject = plannedProjectDAO.findByToken((String) tokenField.getValue());
                    fieldGroup.writeBean(plannedProject);
                    DaoFactorySingelton.getInstance().saveOrUpdateTX(plannedProject);
                    listboxComponent.setItems(plannedProjectDAO.loadAllEntities());
                    activate(false);
                } catch (Exception e) {
                    logger.warn("Commit Failed", e);
                }
            });
        });
        setComponents();
        doInternationalization();

        buttonsLayout.add(saveButton);
        buttonsLayout.add(cancelButton);
        buttonsLayout.setVisible(false);

    }

    @Override
    public void setComponents() {
        add(noSelectionLabel);
        add(formLayout);
        add(buttonsLayout);
    }

    @Override
    public void doInternationalization() {
//        setText(messagesBundle.getString("project_editproject"));
        saveButton.setText(messagesBundle.getString("save"));
        cancelButton.setText(messagesBundle.getString("cancel"));
    }

    public FormLayout getFormLayout() {
        return formLayout;
    }

    public ProjektFieldGroup getFieldGroup() {
        return fieldGroup;
    }

    public void setFieldGroup(ProjektFieldGroup fieldGroup) {
        this.fieldGroup = fieldGroup;
    }

    public void buildForm() {
        buildForm(false);
    }

    public void buildForm(boolean deselect) {
        if (deselect) {
            noSelectionLabel.setVisible(true);
            formLayout.removeAll();
            formLayout.setWidthFull();
        } else {
            noSelectionLabel.setVisible(false);
            formLayout.removeAll();
            formLayout.setWidthFull();
            final Optional<HasValue> tokenField = fieldGroup.getFieldForProperty(PlannedProject.TOKEN);
            tokenField.ifPresent(field -> {
                AbstractField abstractField = (AbstractField) field;
                abstractField.setReadOnly(true);
                formLayout.add(abstractField);
            });
            fieldGroup.getFieldForProperty(PlannedProject.NAME).ifPresent(field -> formLayout.add((AbstractField) field));
            fieldGroup.getFieldForProperty(PlannedProject.EXTERNALDAILYRATE).ifPresent(field -> formLayout.add((AbstractField) field));
            fieldGroup.getFieldForProperty(PlannedProject.HOURSPERWORKINGDAY).ifPresent(field -> formLayout.add((AbstractField) field));
        }
    }

    @Override
    public void activate(boolean b) {
        if (fieldGroup != null) {
            fieldGroup.getFields().forEach(field -> {
                fieldGroup.getFieldForProperty(PlannedProject.TOKEN).ifPresent(tokenField -> {
                    if (tokenField == field) {
                        field.setReadOnly(true);
                    } else {
                        field.setReadOnly(!b);
                    }
                });

            });
            buttonsLayout.setVisible(b);
        }
    }

    void setListboxComponent(ListBox<PlannedProject> listboxComponent) {
        this.listboxComponent = listboxComponent;
    }

}
