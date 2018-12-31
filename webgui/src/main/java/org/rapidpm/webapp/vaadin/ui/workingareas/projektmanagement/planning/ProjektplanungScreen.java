package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTree;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all.PlanningUnitsTreePanelLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.parents.PlanningUnitSelect;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.PlanningCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Route(value = "projectplanning", layout = MainAppLayout.class)
@Caption("Projektplanung")
@Icon(VaadinIcon.PAPERPLANE)
public class ProjektplanungScreen extends AbstractView {

    public static final Logger logger = Logger.getLogger(ProjektplanungScreen.class);
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
    private HorizontalLayout addParentPlanningUnitLayout = new HorizontalLayout();
    private TextField addParentPlanningUnitField = new TextField();
    private Button addParentButton = new Button();
    private PlanningUnit tempPlanningUnit = new PlanningUnit();
    private DaoFactory daoFactory = DaoFactorySingelton.getInstance();
    private Tabs tabSheet = new Tabs();

    //Buttons im TreePanelLayout
    private Button addButton = new Button("+");
    private Button deleteButton = new Button("-");

    private Button addParentsButton = new Button();
    private Button addDescriptionOrTestCaseButton = new Button();

    public static final long PLATZHALTER_ID = 666l;


    public ProjektplanungScreen() {
        addParentPlanningUnitField.focus();

        try {
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if (plannedProjects == null || plannedProjects.isEmpty()) {
                throw new NoProjectsException();
            }

            final PlanningCalculator calculator = new PlanningCalculator(VaadinSession.getCurrent().getAttribute(ResourceBundle.class));
            calculator.calculate();

//            addDescriptionOrTestCaseButton.addClickListener(new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent event) {
//                    ui.addWindow(new AddDescriptionsOrTestCasesWindow((PlanningUnit)planningUnitsTree.getValue(), ui,
//                            messagesBundle));
//                }
//            });

//            leftColumn.setSizeFull();
//            centerColumn.setSizeFull();
//            rightColumn.setSizeFull();

            rightColumn.add(addDescriptionOrTestCaseButton);
            rightColumn.add(tabSheet);

            planningUnitPanel = new RapidPanel();
            treePanel = new RapidPanel();
            detailsPanel = new RapidPanel();
            ressourcesPanel = new RapidPanel();

            leftColumn.add(planningUnitPanel);
            leftColumn.add(treePanel);

            centerColumn.add(detailsPanel);
            centerColumn.add(ressourcesPanel);
            mainPanel = new RapidPanel();

//            ressourcesPanel.setSizeFull();
            borderLayout.setSizeFull();

            //rightColumn.add();

            buildPlanningUnitPanel();
            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e) {
            removeAll();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }

    }

    private void buildPlanningUnitPanel() {
        setAddParentButtonListener();
        setAddParentsButtonListener();
        addParentPlanningUnitLayout.add(addParentPlanningUnitField, addParentButton, addParentsButton);
        addParentButton.setSizeUndefined();
        addParentsButton.setSizeUndefined();
        addParentPlanningUnitLayout.setAlignSelf(FlexComponent.Alignment.START, addParentButton);
        addParentPlanningUnitLayout.setAlignSelf(FlexComponent.Alignment.START, addParentsButton);
        planningUnitSelect = new PlanningUnitSelect(null);
//        planningUnitSelect.addListenerComponent(deleteButton);
//        planningUnitSelect.addListenerComponent(addButton);
        addParentPlanningUnitField.setWidth("160px");
        final PlannedProject projectFromDB = planningUnitSelect.getProjectFromDB();
//        final List<?> ids = (List<?>) planningUnitSelect.getItemIds();
//        planningUnitSelect.addValueChangeListener(new Property.ValueChangeListener() {
//            @Override
//            public void valueChange(final Property.ValueChangeEvent valueChangeEvent) {
//                final PlanningUnit planningUnitFromSelect = (PlanningUnit) valueChangeEvent.getProperty().getValue();
//                PlanningUnit planningUnitFromDB = daoFactory.getPlanningUnitDAO().findByID
//                        (planningUnitFromSelect.getId());
//                if (planningUnitFromDB != null) {
//                    daoFactory.getEntityManager().refresh(planningUnitFromDB);
//                } else {
//                    planningUnitFromDB = planningUnitFromSelect;
//                }
//                treePanel.removeAllComponents();
//                detailsPanel.removeAllComponents();
//                treePanel.setText(planningUnitFromSelect.getPlanningUnitName());
//                fillTreePanel(planningUnitFromDB, projectFromDB);
//            }
//        });
//        if (ids != null && !ids.isEmpty()) {
//            final PlanningUnit firstPlanningUnit = daoFactory.getPlanningUnitDAO().findByID(((PlanningUnit) ids.get(0))
//                    .getId());
//            daoFactory.getEntityManager().refresh(firstPlanningUnit);
//            planningUnitSelect.setValue(firstPlanningUnit);
//        } else {
//            tempPlanningUnit.setId(666l);
//            tempPlanningUnit.setPlanningUnitName("Platzhalter");
//            tempPlanningUnit.setTestcases(new ArrayList<TextElement>());
//            tempPlanningUnit.setDescriptions(new ArrayList<TextElement>());
//            tempPlanningUnit.setKindPlanningUnits(new HashSet<PlanningUnit>());
//            planningUnitSelect.addItem(tempPlanningUnit);
//            planningUnitSelect.setValue(tempPlanningUnit);
//        }
//        planningUnitPanel.setText(projectFromDB.getProjektName());
//        planningUnitPanel.add(addParentPlanningUnitLayout);
//        planningUnitPanel.add(planningUnitSelect);
    }

    private void setAddParentsButtonListener() {
//        addParentsButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                final AddRootPlanningUnitsWindow addRootPlanningUnitsWindow = new AddRootPlanningUnitsWindow(ui, messagesBundle);
//                ui.addWindow(addRootPlanningUnitsWindow);
//            }
//        });
    }

    private void setAddParentButtonListener() {
//        addParentButton.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                try {
//                    final PlannedProject projekt = daoFactory.getPlannedProjectDAO().findByID(ui
//                            .getCurrentProject().getId());
//                    final PlanningUnit newPlanningUnit = new PlanningUnit();
//                    final String newPlanningUnitName = addParentPlanningUnitField.getValue();
//                    if (newPlanningUnitName.matches(Constants.EMPTY_OR_SPACES_ONLY_PATTERN)){
//                        throw new InvalidNameException();
//                    } else {
//                        newPlanningUnit.setPlanningUnitName(newPlanningUnitName);
//                    }
//                    if (newPlanningUnit.getParent() != null) {
//                        final String parentsPlanningUnitName = newPlanningUnit.getParent().getPlanningUnitName();
//                        if (newPlanningUnitName.equals(parentsPlanningUnitName)) {
//                            throw new SameNameException();
//                        }
//                    }
//                    final PlanningUnit foundPlanningUnit = daoFactory.getPlanningUnitDAO().loadPlanningUnitByName
//                            (newPlanningUnitName);
//                    if(foundPlanningUnit != null && foundPlanningUnit.getParent() == null){
//                        throw new SameNameException();
//                    }
//                    daoFactory.saveOrUpdateTX(newPlanningUnit);
//                    newPlanningUnit.setKindPlanningUnits(new HashSet<PlanningUnit>());
//                    if (newPlanningUnit.getParent() != null) {
//                        final PlanningUnit parentPlanningUnit = daoFactory.getPlanningUnitDAO().findByID
//                                (newPlanningUnit.getParent().getId());
//                        parentPlanningUnit.getKindPlanningUnits().add(newPlanningUnit);
//                        daoFactory.saveOrUpdateTX(parentPlanningUnit);
//                    }
//
//                    final List<RessourceGroup> ressourceGroups = daoFactory.getRessourceGroupDAO()
//                            .loadAllEntities();
//                    if (newPlanningUnit.getParent() != null) {
//                        final Set<PlanningUnit> geschwisterPlanningUnits = newPlanningUnit.getParent().getKindPlanningUnits();
//                        if (geschwisterPlanningUnits == null || geschwisterPlanningUnits.size() <= 1) {
//                            newPlanningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
//                            for (final PlanningUnitElement planningUnitElementFromParent : newPlanningUnit.getParent()
//                                    .getPlanningUnitElementList()) {
//                                final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
//                                planningUnitElement.setRessourceGroup(planningUnitElementFromParent.getRessourceGroup());
//                                planningUnitElement.setPlannedMinutes(planningUnitElementFromParent.getPlannedMinutes());
//                                daoFactory.saveOrUpdateTX(planningUnitElement);
//                                newPlanningUnit.getPlanningUnitElementList().add(planningUnitElement);
//                            }
//                        } else {
//                            createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
//                        }
//                    } else {
//                        createNewPlanningUnitElements(newPlanningUnit, ressourceGroups, daoFactory);
//                    }
//                    final Benutzer notAssignedUser = daoFactory.getBenutzerDAO().findByID(1l);
//                    newPlanningUnit.setResponsiblePerson(notAssignedUser);
//                    daoFactory.saveOrUpdateTX(newPlanningUnit);
//                    if (newPlanningUnit.getParent() == null) {
//                        projekt.getPlanningUnits().add(newPlanningUnit);
//                    }
//                    daoFactory.saveOrUpdateTX(projekt);
//                    daoFactory.getEntityManager().refresh(projekt);
//                    final MainUI ui = getUi();
//                    ui.setWorkingArea(new ProjektplanungScreen(ui));
//                } catch (final InvalidNameException e) {
//                    Notification.show(messagesBundle.getString("planning_invalidname"));
//                } catch (final SameNameException e) {
//                    Notification.show(messagesBundle.getString("planning_samename"));
//                }
//            }
//        });
    }

    public void doInternationalization() {
//        detailsPanel.setText(messagesBundle.getString("details"));
//        addParentPlanningUnitField.setText(messagesBundle.getString("planning_fastadd"));
//        addParentButton.setText("+");
//        addParentsButton.setText("++");
//        leftColumn.setText(messagesBundle.getString("planning_planningunits"));
//        centerColumn.setText(messagesBundle.getString("planning_detailsandressources"));
//        rightColumn.setText(messagesBundle.getString("planning_descriptionandtestcases"));
//        addDescriptionOrTestCaseButton.setText("+");
    }

    public void fillTreePanel(final PlanningUnit selectedPlanningUnit, final PlannedProject projekt) {
//        planningUnitsTree = new PlanningUnitsTree(this, selectedPlanningUnit);
//        planningUnitsTree.select(selectedPlanningUnit);
//        planningUnitsTreePanelLayout = new PlanningUnitsTreePanelLayout(projekt, ProjektplanungScreen.this);
//        treePanel.removeAllComponents();
//        treePanel.add(planningUnitsTreePanelLayout);
//        final TreeValueChangeListener listener = planningUnitsTree.getListener();
//        final Button renameButton = planningUnitsTreePanelLayout.getRenameButton();
//        listener.setDeleteButton(deleteButton);
//        listener.setRenameButton(renameButton);
    }

    public void setComponents() {
        borderLayout.add(leftColumn);
        borderLayout.add(centerColumn);
        borderLayout.add(rightColumn);
//        borderLayout.setExpandRatio(leftColumn, 30);
//        borderLayout.setExpandRatio(centerColumn, 30);
//        borderLayout.setExpandRatio(rightColumn, 40);
        add(borderLayout);
    }

    private void createNewPlanningUnitElements(final PlanningUnit planningUnit,
                                               final List<RessourceGroup> ressourceGroups,
                                               final DaoFactory daoFactory) {
        planningUnit.setPlanningUnitElementList(new ArrayList<PlanningUnitElement>());
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            final PlanningUnitElement planningUnitElement = new PlanningUnitElement();
            planningUnitElement.setPlannedMinutes(0);
            planningUnitElement.setRessourceGroup(ressourceGroup);
            daoFactory.saveOrUpdateTX(planningUnitElement);
            planningUnit.getPlanningUnitElementList().add(planningUnitElement);
        }
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

    public RapidPanel getRightColumn() {
        return rightColumn;
    }

    public Button getAddDescriptionOrTestCaseButton() {
        return addDescriptionOrTestCaseButton;
    }

    public Tabs getTabs() {
        return tabSheet;
    }

    public void setTabs(Tabs tabSheet) {
        this.tabSheet = tabSheet;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    @Override
    public String getViewName() {
        return getClass().getName();
    }

}
