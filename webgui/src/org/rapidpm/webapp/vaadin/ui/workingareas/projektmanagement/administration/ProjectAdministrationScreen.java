package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration;

import com.vaadin.ui.HorizontalLayout;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ChosenProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.CurrentProjectPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents.ProjectsPanel;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
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

        chosenProjectPanel = new ChosenProjectPanel(ui, messagesBundle);
        projectsPanel = new ProjectsPanel(ui, messagesBundle, chosenProjectPanel);
        currentProjectPanel = new CurrentProjectPanel(messagesBundle, this);

        buildScreen();
        doInternationalization();
        setComponents();
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
