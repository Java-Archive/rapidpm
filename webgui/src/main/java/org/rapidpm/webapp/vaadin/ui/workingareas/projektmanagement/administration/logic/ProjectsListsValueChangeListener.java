package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic;

import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectEditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjektFieldGroup;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 10:34
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectsListsValueChangeListener /*implements Property.ValueChangeListener*/ {

    private ChosenProjectEditableRapidPanel chosenProjectEditablePanel;
    private ProjectsPanel projectsPanel;

    public ProjectsListsValueChangeListener(final ProjectsPanel projectsPanel, final ChosenProjectEditableRapidPanel formPanel) {
        this.chosenProjectEditablePanel = formPanel;
        this.projectsPanel = projectsPanel;
    }

//    @Override
//    public void valueChange(final Property.ValueChangeEvent event) {
//        final Button deleteButton = projectsPanel.getDeleteProjectButton();
//        final FormLayout formLayout = chosenProjectEditablePanel.getFormLayout();
//        final PlannedProject chosenProjekt = (PlannedProject) event.getProperty().getValue();
//        if(event.getProperty().getValue() != null){
//            deleteButton.setVisible(true);
//            formLayout.removeAllComponents();
//            final ProjektFieldGroup fieldGroup = new ProjektFieldGroup(chosenProjekt, projectsPanel.getMessagesBundle());
//            chosenProjectEditablePanel.setFieldGroup(fieldGroup);
//            chosenProjectEditablePanel.buildForm();
//            chosenProjectEditablePanel.activate(false);
//        }
//    }
}
