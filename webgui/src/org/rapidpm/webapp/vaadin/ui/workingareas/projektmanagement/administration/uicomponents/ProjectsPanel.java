package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic.ProjectsListsValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.Projekt;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.List;
import java.util.ResourceBundle;

import static org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents
        .ProjektFieldGroup.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectsPanel extends Panel implements Internationalizationable, Componentssetable {

    private ResourceBundle messagesBundle;
    private ProjektBean projektBean;
    private ChosenProjectPanel formPanel;
    private final MainUI ui;
    private ProjectAdministrationScreen screen;

    private ListSelect projectSelect;
    private Button addProjectButton = new Button();
    private Button deleteProjectButton = new Button();

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public ProjectsPanel(ProjectAdministrationScreen scr, MainUI theUi, ResourceBundle messagesBundle,
                         ProjektBean bean,
                         ChosenProjectPanel chosenProjectPanel){
        super(messagesBundle.getString("pm_projects"));
        this.messagesBundle = messagesBundle;
        this.projektBean = bean;
        this.formPanel = chosenProjectPanel;
        this.ui = theUi;
        this.screen = scr;

        deleteProjectButton.setVisible(false);
        setSizeFull();
        fillListSelect();

        buttonLayout.addComponent(addProjectButton);
        buttonLayout.addComponent(deleteProjectButton);

        deleteProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final Projekt projekt = (Projekt)projectSelect.getValue();
                final List<Projekt> projektList = projektBean.getProjekte();
                projektList.remove(projekt);
                ui.setWorkingArea(new ProjectAdministrationScreen(ui));
            }
        });

        addProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final AddProjectWindow addWindow = new AddProjectWindow(ui, screen);
                addWindow.show();
            }
        });

        doInternationalization();
        setComponents();
    }

    private void fillListSelect() {
        projectSelect = new ListSelect("Projekte", new BeanItemContainer<>(Projekt.class,
                projektBean.getProjekte()));
        projectSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        projectSelect.setItemCaptionPropertyId(PROJEKT_NAME);
        projectSelect.setImmediate(true);
        projectSelect.setMultiSelect(false);
        projectSelect.setNullSelectionAllowed(false);
        projectSelect.addValueChangeListener(new ProjectsListsValueChangeListener(this, formPanel));
        projectSelect.setSizeFull();
    }

    @Override
    public void doInternationalization() {
       addProjectButton.setCaption(messagesBundle.getString("add"));
       deleteProjectButton.setCaption(messagesBundle.getString("delete"));
    }

    @Override
    public void setComponents() {
        addComponent(projectSelect);
        addComponent(buttonLayout);
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
}
