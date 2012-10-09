package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;

import java.util.ResourceBundle;
/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 10:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ChosenProjectPanel extends EditablePanel {

    private static Logger logger = Logger.getLogger(ChosenProjectPanel.class);

    private FormLayout formLayout = new FormLayout();
    private ProjektFieldGroup fieldGroup;

    private Label noSelectionLabel;

    public ChosenProjectPanel(final MainUI ui, final ResourceBundle messagesBundle) {
        super(messagesBundle);
        noSelectionLabel = new Label(messagesBundle.getString("pm_noselection"));

        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();
                activate(false);
            }
        });

        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    fieldGroup.commit();
                    activate(false);
                    ui.setWorkingArea(new ProjectAdministrationScreen(ui));
                } catch (FieldGroup.CommitException e) {
                    logger.warn("Commit Failed", e);
                }
            }
        });
        setComponents();
        doInternationalization();

        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
        buttonsLayout.setVisible(false);

    }

    @Override
    public void setComponents() {
        addComponent(noSelectionLabel);
        addComponent(formLayout);
        addComponent(buttonsLayout);
    }

    @Override
    public void doInternationalization() {
        setCaption(messagesBundle.getString("pm_editproject"));
        saveButton.setCaption(messagesBundle.getString("save"));
        cancelButton.setCaption(messagesBundle.getString("cancel"));
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
        formLayout.removeAllComponents();
        formLayout.addComponent(fieldGroup.getField(PlannedProject.NAME));
    }

    @Override
    public void activate(boolean b){
        for(Object propertyId : fieldGroup.getBoundPropertyIds()){
            if(!propertyId.equals(PlannedProject.ID)){
                final Field field = fieldGroup.getField(propertyId);
                field.setReadOnly(!b);
            }
        }
        buttonsLayout.setVisible(b);
    }
}
