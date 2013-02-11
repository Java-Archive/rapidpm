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
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.exceptions.TryToDeleteCurrentProjectException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic.ProjectsListsValueChangeListener;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 18.09.12
 * Time: 09:12
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class ProjectsPanel extends RapidPanel implements Internationalizationable, Componentssetable {

    private ResourceBundle messagesBundle;
    private ChosenProjectEditableRapidPanel formPanel;
    private final MainUI ui;
//    private ProjectsPanelBean bean;


    private ListSelect projectSelect;
    private Button addProjectButton = new Button();
    private Button deleteProjectButton = new Button();

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public ProjectsPanel(final MainUI theUi, final ResourceBundle messages, final ChosenProjectEditableRapidPanel chosenProjectEditablePanel){
        setCaption(messages.getString("project_projects"));
        this.messagesBundle = messages;
        this.formPanel = chosenProjectEditablePanel;
        this.ui = theUi;

//        bean = EJBFactory.getEjbInstance(ProjectsPanelBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        Collections.sort(projects);

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
                        //TODO ConfirmDialog updaten oder selber schreiben
                        //ConfirmDialog.show(ui, messagesBundle.getString("confirm"),
                        //        messagesBundle.getString("project_confirmdelete"), messagesBundle.getString("ok"),
                        //        messagesBundle.getString("cancel"),
                        //        new ConfirmDialog.Listener() {
                        //    @Override
                        //    public void onClose(ConfirmDialog dialog) {
                        //        if(dialog.isConfirmed()){
                                    tryToDeleteProject(daoFactory, projektAusDB);
                        //        }
                        //    }
                        //});
                    }  else {
                        tryToDeleteProject(daoFactory, projektAusDB);
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

    private void tryToDeleteProject(final DaoFactory daoFactory,
                                    final PlannedProject projektAusDB) {
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
        addComponent(projectSelect);
        addComponent(buttonLayout);
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }
}
