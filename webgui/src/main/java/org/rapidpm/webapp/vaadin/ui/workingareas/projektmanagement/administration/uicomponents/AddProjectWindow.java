package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.Constants;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidWindow;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 15:45
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class AddProjectWindow extends RapidWindow{

    private static final Logger logger = Logger.getLogger(AddProjectWindow.class);

    private MainUI ui;

    private FormLayout formLayout = new FormLayout();
    private HorizontalLayout horizontalButtonLayout = new HorizontalLayout();
    private Button saveButton = new Button();
    private Button cancelButton = new Button();
    private ProjektFieldGroup fieldGroup;
    private ResourceBundle messages;
    private Checkbox makeCurrentProjectCheckBox;

    public AddProjectWindow(final ResourceBundle messages) {
        this.messages = messages;
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        final PlannedProject projekt = new PlannedProject();
        fieldGroup = new ProjektFieldGroup(projekt, messages);
        makeCurrentProjectCheckBox = new Checkbox(messages.getString("makeCurrentProject"));
        final List<PlannedProject> projectList = daoFactory.getPlannedProjectDAO().loadAllEntities();
        if(projectList == null || projectList.isEmpty()){
            makeCurrentProjectCheckBox.setValue(true);
            makeCurrentProjectCheckBox.setEnabled(false);
        }

        fillFormLayout();
        add(formLayout);

        horizontalButtonLayout.add(saveButton);
        horizontalButtonLayout.add(cancelButton);

        add(horizontalButtonLayout);
        addListeners();
        doInternationalization();

    }

    private void fillFormLayout() {
        final TextField idField = new TextField(messages.getString("users_id"));
        idField.setValue("autom.");
        idField.setRequired(true);
        idField.setEnabled(false);
        formLayout.add(idField);
        fieldGroup.getFields().forEach(field -> {
            AbstractField abstractField = (AbstractField) field;
            abstractField.setReadOnly(false);
            fieldGroup.getFieldForProperty(PlannedProject.HOURSPERWORKINGDAY).ifPresent(hoursPerWorkingDayField -> {
                if(field == hoursPerWorkingDayField){
                    abstractField.setValue(Integer.valueOf(Constants.DEFAULT_HOURS_PER_WORKINGDAY));
                    //als Standard 8 auswählen
                    final Iterator iterator = ((ComboBox)field).getDataProvider().fetch(new Query()).iterator();
                    for(int i=0; i<7; i++){
                        iterator.next();
                    }
                    ((ComboBox)field).setValue(iterator.next());
                }
                formLayout.add(abstractField);
            });

        });
        formLayout.add(makeCurrentProjectCheckBox);
    }

    private void doInternationalization() {
//        this.setText(messages.getString("project_addproject"));
        saveButton.setText(messages.getString("save"));
        cancelButton.setText(messages.getString("cancel"));
    }

    private void addListeners() {
        saveButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            boolean allFilled = true;
            final Iterator<Component> it = AddProjectWindow.this.formLayout.getChildren().iterator();
            while (it.hasNext()) {
                final Component component = it.next();
                if (component instanceof AbstractField) {
                    if (((AbstractField) component).getValue() == null
                            || ((AbstractField) component).getValue().equals(""))
                        allFilled = false;
                }
            }
            if (allFilled) {
                try {
                    final PlannedProject newProject = new PlannedProject();
                    fieldGroup.writeBean(newProject);
                    final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                    daoFactory.saveOrUpdateTX(newProject);
                    if(makeCurrentProjectCheckBox.getValue()){
                        VaadinSession.getCurrent().setAttribute(PlannedProject.class, newProject);
                    }
                    AddProjectWindow.this.close();
                } catch (final ValidationException e) {
                    logger.warn(e);
                }

            } else {
                final Label lbl = new Label();
                lbl.setText(messages.getString("stdsatz_fillInAllFields"));
                add(lbl);
            }
        });

        cancelButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> AddProjectWindow.this.close());

    }

    public void show() {
        super.open();
    }
}
