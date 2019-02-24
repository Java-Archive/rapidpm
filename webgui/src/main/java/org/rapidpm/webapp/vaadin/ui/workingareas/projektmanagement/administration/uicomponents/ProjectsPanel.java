package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.uicomponents;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Componentssetable;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.ProjectAdministrationScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.exceptions.TryToDeleteCurrentProjectException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration.logic.ProjectsListsValueChangeListener;

import java.util.*;
import java.util.stream.Collectors;

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
//    private ProjectsPanelBean bean;


    private ListBox<PlannedProject> projectSelect;
    private Button addProjectButton = new Button();
    private Button deleteProjectButton = new Button();

    private HorizontalLayout buttonLayout = new HorizontalLayout();

    public ProjectsPanel(final ResourceBundle messages, final ChosenProjectEditableRapidPanel chosenProjectEditablePanel) {
//        setText(messages.getString("project_projects"));
        this.messagesBundle = messages;
        this.formPanel = chosenProjectEditablePanel;

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();

        final List<PlannedProject> projects = daoFactory.getPlannedProjectDAO().loadAllEntities();
        Collections.sort(projects);
//        setSizeFull();
        deleteProjectButton.setEnabled(false);
        fillListSelect(projects);

        buttonLayout.add(addProjectButton);
        buttonLayout.add(deleteProjectButton);

        deleteProjectButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            try {
                final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
                final PlannedProject projekt = projectSelect.getValue();
                final PlannedProject projektAusDB = daoFactory.getPlannedProjectDAO().findByID(projekt.getId());
                if (projectFromSession.equals(projektAusDB) && daoFactory.getPlannedProjectDAO().loadAllEntities()
                        .size() > 1) {
                    throw new TryToDeleteCurrentProjectException();
                }
                final Set<PlanningUnit> parentPlanningUnits = projektAusDB.getPlanningUnits();
                if (parentPlanningUnits != null && !parentPlanningUnits.isEmpty()) {
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
                } else {
                    tryToDeleteProject(daoFactory, projektAusDB);
                }
            } catch (final TryToDeleteCurrentProjectException e) {
                Notification.show(messages.getString("project_deletecurrent"));
            }
        });
        addProjectButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            final AddProjectWindow addWindow = new AddProjectWindow(messagesBundle);
            addWindow.show();
            addWindow.addOpenedChangeListener((ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>) event1 -> {
                final List<PlannedProject> reloadedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
                Collections.sort(reloadedProjects);
                projectSelect.setItems(reloadedProjects);
            });
        });

        doInternationalization();

        setComponents();
    }

    private void tryToDeleteProject(final DaoFactory daoFactory,
                                    final PlannedProject projektAusDB) {
        daoFactory.removeTX(projektAusDB);
        List<PlannedProject> plannedProjects = projectSelect.getDataProvider().fetch(new Query<>()).collect(Collectors.toList());
        plannedProjects.remove(projektAusDB);
        projectSelect.setItems(plannedProjects);
        formPanel.buildForm(true);
        deleteProjectButton.setEnabled(false);
    }

    private void fillListSelect(final List<PlannedProject> projects) {
        projectSelect = new ListBox<>();
        projectSelect.setItems(projects);
        projectSelect.setRenderer(new TextRenderer<>((ItemLabelGenerator<PlannedProject>) PlannedProject::getProjektName));
        projectSelect.addValueChangeListener(new ProjectsListsValueChangeListener(this, formPanel));
        projectSelect.setSizeFull();
        formPanel.setListboxComponent(projectSelect);
    }

    @Override
    public void doInternationalization() {
        addProjectButton.setText(messagesBundle.getString("add"));
        deleteProjectButton.setText(messagesBundle.getString("delete"));
    }

    @Override
    public void setComponents() {
        add(projectSelect);
        add(buttonLayout);
    }

    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }

    public ResourceBundle getMessagesBundle() {
        return messagesBundle;
    }
}
