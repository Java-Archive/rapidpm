package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectEditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.CurrentProjectEditableRapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 18.09.12
 * Time: 08:54
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
@Route(value = "admin", layout = MainAppLayout.class)
@Caption("Projekte verwalten...")
@Icon(VaadinIcon.DATABASE)
public class ProjectAdministrationScreen extends VerticalLayout {

    private HorizontalLayout projectsPanelLayout = new HorizontalLayout();

    private ChosenProjectEditableRapidPanel chosenProjectEditablePanel;
    private ProjectsPanel projectsPanel;
    private CurrentProjectEditableRapidPanel currentProjectEditablePanel;

    public ProjectAdministrationScreen(){
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            Collections.sort(plannedProjects);
            ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
            chosenProjectEditablePanel = new ChosenProjectEditableRapidPanel(messagesBundle);
            projectsPanel = new ProjectsPanel(null, messagesBundle, chosenProjectEditablePanel);
            currentProjectEditablePanel = new CurrentProjectEditableRapidPanel(messagesBundle);

            buildScreen();
            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAll();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }
    }

    private void buildScreen() {
//        chosenProjectEditablePanel.setSizeUndefined();
//        chosenProjectEditablePanel.setWidth("100%");
//        currentProjectEditablePanel.setSizeUndefined();
//        currentProjectEditablePanel.setWidth("100%");
        projectsPanelLayout.setHeight("100%");
        projectsPanelLayout.setWidth("100%");
        projectsPanelLayout.add(projectsPanel);
        projectsPanelLayout.add(chosenProjectEditablePanel);
    }

    public void setComponents() {
        add(currentProjectEditablePanel);
        add(projectsPanelLayout);
    }


    public void doInternationalization() {
        projectsPanel.doInternationalization();
        currentProjectEditablePanel.doInternationalization();
    }
}
