package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration;

import com.vaadin.ui.HorizontalLayout;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.CurrentProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;

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

    private ChosenProjectPanel chosenProjectPanel;
    private ProjectsPanel projectsPanel;
    private CurrentProjectPanel currentProjectPanel;

    public ProjectAdministrationScreen(final MainUI ui){
        super(ui);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            chosenProjectPanel = new ChosenProjectPanel(ui, messagesBundle);
            projectsPanel = new ProjectsPanel(ui, messagesBundle, chosenProjectPanel);
            currentProjectPanel = new CurrentProjectPanel(messagesBundle, this);

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
        projectsPanelLayout.setSizeFull();
        projectsPanelLayout.addComponent(projectsPanel);
        projectsPanelLayout.addComponent(chosenProjectPanel);
        projectsPanelLayout.setExpandRatio(projectsPanel,1);
        projectsPanelLayout.setExpandRatio(chosenProjectPanel,1);
    }

    @Override
    public void setComponents() {
        addComponent(currentProjectPanel);
        addComponent(projectsPanelLayout);
    }


    @Override
    public void doInternationalization() {
        projectsPanel.doInternationalization();
        currentProjectPanel.doInternationalization();
    }
}
