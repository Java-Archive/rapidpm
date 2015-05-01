package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration;

import com.vaadin.ui.HorizontalLayout;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectEditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.CurrentProjectEditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;

import java.util.Collections;
import java.util.List;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 08:54
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectAdministrationScreen extends Screen {

    private HorizontalLayout projectsPanelLayout = new HorizontalLayout();

    private ChosenProjectEditableRapidPanel chosenProjectEditablePanel;
    private ProjectsPanel projectsPanel;
    private CurrentProjectEditableRapidPanel currentProjectEditablePanel;

    public ProjectAdministrationScreen(final MainUI ui){
        super(ui);
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().findAll();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            Collections.sort(plannedProjects);
            chosenProjectEditablePanel = new ChosenProjectEditableRapidPanel(ui, messagesBundle);
            projectsPanel = new ProjectsPanel(ui, messagesBundle, chosenProjectEditablePanel);
            currentProjectEditablePanel = new CurrentProjectEditableRapidPanel(messagesBundle, this);

            buildScreen();
            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
            addComponent(noProjectsScreen);
        }
    }

    private void buildScreen() {
        chosenProjectEditablePanel.setSizeUndefined();
        chosenProjectEditablePanel.setWidth("100%");
        currentProjectEditablePanel.setSizeUndefined();
        currentProjectEditablePanel.setWidth("100%");
        projectsPanelLayout.setHeight("100%");
        projectsPanelLayout.setWidth("100%");
        projectsPanelLayout.addComponent(projectsPanel);
        projectsPanelLayout.addComponent(chosenProjectEditablePanel);
    }

    @Override
    public void setComponents() {
        activeVerticalFullScreenSize(false);
        addComponent(currentProjectEditablePanel);
        addComponent(projectsPanelLayout);
    }


    @Override
    public void doInternationalization() {
        projectsPanel.doInternationalization();
        currentProjectEditablePanel.doInternationalization();
    }
}
