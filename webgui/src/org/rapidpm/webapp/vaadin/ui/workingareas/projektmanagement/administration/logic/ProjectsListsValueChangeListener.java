package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 10:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectsListsValueChangeListener implements Property.ValueChangeListener {

    private ChosenProjectPanel chosenProjectPanel;
    private ProjectsPanel projectsPanel;

    public ProjectsListsValueChangeListener(final ProjectsPanel projectsPanel, final ChosenProjectPanel formPanel) {
        this.chosenProjectPanel = formPanel;
        this.projectsPanel = projectsPanel;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent event) {
        final Button deleteButton = projectsPanel.getDeleteProjectButton();
        final FormLayout formLayout = chosenProjectPanel.getFormLayout();
        final PlannedProject chosenProjekt = (PlannedProject) event.getProperty().getValue();
        if(event.getProperty().getValue() != null){
            deleteButton.setVisible(true);
            formLayout.removeAllComponents();
            final ProjektFieldGroup fieldGroup = new ProjektFieldGroup(chosenProjekt);
            chosenProjectPanel.setFieldGroup(fieldGroup);
            chosenProjectPanel.buildForm();
            chosenProjectPanel.activate(false);
        }
    }
}
