package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;

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

    public ProjectsListsValueChangeListener(ProjectsPanel projectsPanel, ChosenProjectPanel formPanel) {
        this.chosenProjectPanel = formPanel;
        this.projectsPanel = projectsPanel;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        final Button deleteButton = projectsPanel.getDeleteProjectButton();
        final FormLayout formLayout = chosenProjectPanel.getFormLayout();
        final Projekt chosenProjekt = (Projekt) event.getProperty().getValue();
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
