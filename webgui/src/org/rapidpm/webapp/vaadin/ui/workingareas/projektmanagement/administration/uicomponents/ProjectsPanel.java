package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic.ProjectsListsValueChangeListener;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectsPanel extends Panel implements Internationalizationable, Componentssetable {

    private ResourceBundle messagesBundle;
    private ChosenProjectPanel formPanel;
    private final MainUI ui;
//    private ProjectsPanelBean bean;


    private ListSelect projectSelect;
    private Button addProjectButton = new Button();
    private Button deleteProjectButton = new Button();

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public ProjectsPanel(final MainUI theUi, final ResourceBundle messages, final ChosenProjectPanel chosenProjectPanel){
        super(messages.getString("pm_projects"));
        this.messagesBundle = messages;
        this.formPanel = chosenProjectPanel;
        this.ui = theUi;

//        bean = EJBFactory.getEjbInstance(ProjectsPanelBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        refreshEntities(daoFactory);

        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();

        deleteProjectButton.setVisible(false);
        setSizeFull();
        fillListSelect(projects);

        buttonLayout.addComponent(addProjectButton);
        buttonLayout.addComponent(deleteProjectButton);

        deleteProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final PlannedProject projekt = (PlannedProject)projectSelect.getValue();
                projects.remove(projekt);
                daoFactory.remove(projekt);
                ui.setWorkingArea(new ProjectAdministrationScreen(ui));
            }
        });

        addProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final AddProjectWindow addWindow = new AddProjectWindow(ui, messagesBundle);
                addWindow.show();
            }
        });

        doInternationalization();
        setComponents();
    }

    private void fillListSelect(final List<PlannedProject> projects) {
        projectSelect = new ListSelect("Projekte", new BeanItemContainer<>(PlannedProject.class,
                projects));
        projectSelect.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        projectSelect.setItemCaptionPropertyId(PlannedProject.NAME);
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

    private void refreshEntities(final DaoFactory baseDaoFactoryBean) {
        final EntityManager entityManager = baseDaoFactoryBean.getEntityManager();
        for(final PlannedProject plannedProject : baseDaoFactoryBean.getPlannedProjectDAO().loadAllEntities()){
            entityManager.refresh(plannedProject);
        }
        for(final PlanningUnit planningUnit : baseDaoFactoryBean.getPlanningUnitDAO().loadAllEntities()){
            entityManager.refresh(planningUnit);
        }
        for(final PlanningUnitElement planningUnitElement : baseDaoFactoryBean.getPlanningUnitElementDAO().loadAllEntities()){
            entityManager.refresh(planningUnitElement);
        }
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
    }
}
