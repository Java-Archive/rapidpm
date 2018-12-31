package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.EditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

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

    private FormLayout formLayout = new FormLayout();
    private ProjektFieldGroup fieldGroup;

    private Label noSelectionLabel;

    public ChosenProjectEditableRapidPanel(final ResourceBundle messagesBundle) {
        super(messagesBundle);
        noSelectionLabel = new Label(messagesBundle.getString("project_noselection"));

//        cancelButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                fieldGroup.discard();
//                activate(false);
//            }
//        });
//
//        saveButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                try {
//                    fieldGroup.commit();
//                    activate(false);
//                    ui.setWorkingArea(new ProjectAdministrationScreen(ui));
//                } catch (FieldGroup.CommitException e) {
//                    logger.warn("Commit Failed", e);
//                }
//            }
//        });
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
        noSelectionLabel.setVisible(false);
//        formLayout.removeAllComponents();
//        final Field tokenField = fieldGroup.getField(PlannedProject.TOKEN);
//        tokenField.setEnabled(false);
//        formLayout.add(tokenField);
//        formLayout.add(fieldGroup.getField(PlannedProject.NAME));
//        formLayout.add(fieldGroup.getField(PlannedProject.EXTERNALDAILYRATE));
//        formLayout.add(fieldGroup.getField(PlannedProject.HOURSPERWORKINGDAY));
    }

    @Override
    public void activate(boolean b){
        if(fieldGroup != null){
//            for(Object propertyId : fieldGroup.getBoundPropertyIds()){
//                if(!propertyId.equals(PlannedProject.ID)){
//                    final Field field = fieldGroup.getField(propertyId);
//                    field.setReadOnly(!b);
//                }
//            }
            buttonsLayout.setVisible(b);
        }
    }
}
