package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.vaadin.data.Property;
//import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
//import org.rapidpm.Constants;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
//import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsEditableLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTreePanelLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.PlanningUnitSelect;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.PlanningUnitBeanItemContainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 05.04.12
 * Time: 09:43
 */
public class ProjektplanungScreen extends Screen {
    private HorizontalLayout borderLayout = new HorizontalLayout();
    private RapidPanel leftColumn = new RapidPanel();
    private RapidPanel centerColumn = new RapidPanel();
    private RapidPanel rightColumn = new RapidPanel();
    private RapidPanel mainPanel;
    private RapidPanel ressourcesPanel;
    private RapidPanel planningUnitPanel;
    private RapidPanel treePanel;
    private RapidPanel detailsPanel;
    private PlanningUnitSelect planningUnitSelect;
    private PlanningUnitsTree planningUnitsTree;
    private PlanningUnitsTreePanelLayout planningUnitsTreePanelLayout;
    private PlanningDetailsEditableLayout planningDetailsEditableLayout;
    private PlanningUnitBeanItemContainer container;
    private PlanningUnit tempPlanningUnit = new PlanningUnit();
    private DaoFactory daoFactory = DaoFactorySingelton.getInstance();


    public ProjektplanungScreen(MainUI ui) {
        super(ui);

        final PlannedProject projectFromSession = ui.getSession().getAttribute(PlannedProject.class);
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();

        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }

            final PlanningCalculator calculator = new PlanningCalculator(messagesBundle, ui);
            calculator.calculate();
//        daoFactory.new Transaction() {
//            @Override
//            public void doTask() {
//                daoFactory.getEntityManager().refresh(projectFromDB);
//            }
//        }.execute();
            //daoFactory.getEntityManager().refresh(projectFromDB);

            leftColumn.setSizeFull();
            centerColumn.setSizeFull();
            rightColumn.setSizeFull();

            planningUnitPanel = new RapidPanel();
            treePanel = new RapidPanel();
            detailsPanel = new RapidPanel();

            leftColumn.addComponent(planningUnitPanel);
            leftColumn.addComponent(treePanel);

            centerColumn.addComponent(detailsPanel);
            //centerColumn.addComponent(ressourcesPanel);
            mainPanel = new RapidPanel();
            ressourcesPanel = new RapidPanel();
            ressourcesPanel.setSizeFull();

            borderLayout.setSizeFull();

            //rightColumn.addComponent();

            buildPlanningUnitPanel();
            detailsPanel.getClass();
            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
            addComponent(noProjectsScreen);
        }

    }

    private void buildPlanningUnitPanel() {
        planningUnitSelect = new PlanningUnitSelect(ui);
        final PlannedProject projectFromDB = planningUnitSelect.getProjectFromDB();
        final List<?> ids = (List<?>) planningUnitSelect.getItemIds();
        planningUnitSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
                final PlanningUnit planningUnitFromSelect = (PlanningUnit) valueChangeEvent.getProperty().getValue();
                PlanningUnit planningUnitFromDB = daoFactory.getPlanningUnitDAO().findByID
                        (planningUnitFromSelect.getId());
                if(planningUnitFromDB != null){
                    daoFactory.getEntityManager().refresh(planningUnitFromDB);
                }else{
                    planningUnitFromDB = planningUnitFromSelect;
                }
                treePanel.removeAllComponents();
                detailsPanel.removeAllComponents();
                treePanel.setCaption(planningUnitFromSelect.getPlanningUnitName());
                fillTreePanel(planningUnitFromDB, projectFromDB);
            }
        });
        if (ids != null && !ids.isEmpty()) {
            final PlanningUnit firstPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(((PlanningUnit)ids.get(0))
                    .getId());
            daoFactory.getEntityManager().refresh(firstPlanningUnit);
            planningUnitSelect.setValue(firstPlanningUnit);
        } else {
            tempPlanningUnit.setId(666l);
            tempPlanningUnit.setPlanningUnitName("Platzhalter");
            tempPlanningUnit.setTestcases(new ArrayList<String>());
            tempPlanningUnit.setDescription("Bitte dem Projekt über den \"+\"-Button neue Planungseinheiten " +
                    "hinzufügen.");
            tempPlanningUnit.setKindPlanningUnits(new HashSet<PlanningUnit>());
            planningUnitSelect.addItem(tempPlanningUnit);
            planningUnitSelect.setValue(tempPlanningUnit);
        }
        planningUnitPanel.setCaption(projectFromDB.getProjektName());
        planningUnitPanel.addComponent(planningUnitSelect);
    }

    @Override
    public void doInternationalization() {
        detailsPanel.setCaption(messagesBundle.getString("details"));
    }

    public void fillTreePanel(final PlanningUnit selectedPlanningUnit, final PlannedProject projekt) {
        planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit, projekt);
        planningUnitsTree.select(selectedPlanningUnit);
        planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(projekt, ProjektplanungScreen.this);
        treePanel.removeAllComponents();
        treePanel.addComponent(planningUnitsTreePanelLayout);
    }

    @Override
    public void setComponents() {
        activeVerticalFullScreenSize(true);
        borderLayout.addComponent(leftColumn);
        borderLayout.addComponent(centerColumn);
        borderLayout.addComponent(rightColumn);
        borderLayout.setExpandRatio(leftColumn, 1);
        borderLayout.setExpandRatio(centerColumn, 1);
        borderLayout.setExpandRatio(rightColumn, 1);
        addComponent(borderLayout);
    }

    public PlanningUnitsTree getPlanningUnitsTree() {
        return planningUnitsTree;
    }

    public RapidPanel getDetailsPanel() {
        return detailsPanel;
    }

    public RapidPanel getMainPanel() {
        return mainPanel;
    }

    public RapidPanel getRessourcesPanel() {
        return ressourcesPanel;
    }

    public PlanningUnit getTempPlanningUnit() {
        return tempPlanningUnit;
    }

    public PlanningUnitsTreePanelLayout getPlanningUnitsTreePanelLayout() {
        return planningUnitsTreePanelLayout;
    }

    public PlanningUnitSelect getPlanningUnitSelect() {
        return planningUnitSelect;
    }


}
