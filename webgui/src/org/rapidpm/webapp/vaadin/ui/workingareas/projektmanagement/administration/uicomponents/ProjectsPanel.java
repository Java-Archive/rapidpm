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
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.exceptions.TryToDeleteCurrentProjectException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic.ProjectsListsValueChangeListener;
import org.vaadin.dialogs.ConfirmDialog;

import javax.persistence.EntityManager;
import java.util.*;

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
    private VerticalLayout singleLayout = new VerticalLayout();
//    private ProjectsPanelBean bean;


    private ListSelect projectSelect;
    private Button addProjectButton = new Button();
    private Button deleteProjectButton = new Button();

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public ProjectsPanel(final MainUI theUi, final ResourceBundle messages, final ChosenProjectPanel chosenProjectPanel){
        super(messages.getString("project_projects"));
        this.messagesBundle = messages;
        this.formPanel = chosenProjectPanel;
        this.ui = theUi;

//        bean = EJBFactory.getEjbInstance(ProjectsPanelBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();

        deleteProjectButton.setVisible(false);
        setSizeFull();
        fillListSelect(projects);

        buttonLayout.addComponent(addProjectButton);
        buttonLayout.addComponent(deleteProjectButton);

        deleteProjectButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
                    final PlannedProject projekt = (PlannedProject)projectSelect.getValue();
                    final PlannedProject projektAusDB = daoFactory.getPlannedProjectDAO().findByID(projekt.getId());
                    if(projectFromSession.equals(projektAusDB) && daoFactory.getPlannedProjectDAO().loadAllEntities()
                            .size() > 1){
                        throw new TryToDeleteCurrentProjectException();
                    }
                    final Set<PlanningUnit> parentPlanningUnits = projektAusDB.getPlanningUnits();
                    if(parentPlanningUnits != null && !parentPlanningUnits.isEmpty()){
                        ConfirmDialog.show(ui, messagesBundle.getString("confirm"),
                                messagesBundle.getString("project_confirmdelete"), messagesBundle.getString("ok"),
                                messagesBundle.getString("cancel"),
                                new ConfirmDialog.Listener() {
                            @Override
                            public void onClose(ConfirmDialog dialog) {
                                if(dialog.isConfirmed()){
                                    tryToDeleteProject(parentPlanningUnits, daoFactory, projektAusDB);
                                }
                            }
                        });
                    }  else {
                        tryToDeleteProject(parentPlanningUnits, daoFactory, projektAusDB);
                    }
                } catch (final TryToDeleteCurrentProjectException e){
                    Notification.show(messages.getString("project_deletecurrent"));
                }

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

    private void tryToDeleteProject(Set<PlanningUnit> parentPlanningUnits, DaoFactory daoFactory, PlannedProject projektAusDB) {
        for(final PlanningUnit planningUnit : parentPlanningUnits){
            for (final PlanningUnit kindPlanningUnit : planningUnit.getKindPlanningUnits()) {
                kindPlanningUnit.setParent(null);
                daoFactory.saveOrUpdateTX(kindPlanningUnit);
            }
        }
        projektAusDB.setPlanningUnits(new HashSet<PlanningUnit>());
        daoFactory.saveOrUpdateTX(projektAusDB);
        final List<PlanningUnitElement> planningUnitElements = new ArrayList<>();
        for(final PlanningUnit planningUnit : parentPlanningUnits){
            planningUnitElements.addAll(planningUnit.getPlanningUnitElementList());
            planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
            daoFactory.saveOrUpdateTX(planningUnit);
        }
        for(final PlanningUnitElement planningUnitElement : planningUnitElements){
            daoFactory.removeTX(planningUnitElement);
        }
        for (final PlanningUnit planningUnit : parentPlanningUnits){
            daoFactory.removeTX(planningUnit);
        }
        daoFactory.removeTX(projektAusDB);
        ui.setWorkingArea(new ProjectAdministrationScreen(ui));
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
        singleLayout.addComponent(projectSelect);
        singleLayout.addComponent(buttonLayout);
        setContent(singleLayout);
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
}
