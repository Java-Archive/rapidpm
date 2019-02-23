package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.AufgabeHashMap;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.HoursPerWorkingDayEditableLayout;

import java.text.DecimalFormat;
import java.util.*;

import static org.rapidpm.Constants.DECIMAL_FORMAT;

@Route(value = "aufwand", layout = MainAppLayout.class)
@Caption("Projektinitialisierung")
@Icon(VaadinIcon.INFO)
public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private Checkbox expandCheckBox;
    private TextField kundeField;
    private TextField projektField;
    private DatePicker datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeField;
    private Component hoursPerWorkingDayPanel;
    private HoursPerWorkingDayEditableLayout editableLayout;

//    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private TreeGrid<AufgabeHashMap> treeGrid = new TreeGrid<>();
    private Grid<HashMap<String, String>> overviewGrid = new Grid<>();
//    private AufwandProjInitScreenBean bean;
//    private DaoFactoryBean baseDaoFactoryBean;

    private static final String ABSOLUTE_WIDTH = "700px";
    private static final String TASK_COLUMN_WIDTH = "200px";

    private HorizontalLayout felderLayout = new HorizontalLayout();
    private FormLayout unterschriftLayout = new FormLayout();
    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private VerticalLayout upperFormLayout = new VerticalLayout();
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Integer> ressourceGroupsMinutesMap = new HashMap<>();

    public AufwandProjInitScreen() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            hoursPerWorkingDayPanel = new RapidPanel();
//            editableLayout = new HoursPerWorkingDayEditableLayout(this, hoursPerWorkingDayPanel);
//            hoursPerWorkingDayPanel.setContent(editableLayout);
//            hoursPerWorkingDayPanel.setStyleName(Reindeer.PANEL_LIGHT);
//            hoursPerWorkingDayPanel.setSizeUndefined();
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            ressourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();

//            expandCheckBox = new ExpandTableCheckBox(treeGrid, null);
//            undoButton = new UndoButton(this, treeGrid, null);
//            undoButton.setVisible(false);

            fillTreeGrid();
            fillOverviewGrid();

            overviewGrid.setHeightByRows(true);
            treeGrid.setHeightByRows(true);

            fillFields();

//            table1layout.add(overviewGrid);
//            table1layout.setSizeFull();
//            table1layout.setMargin(true);
//
//            table2layout.add(expandCheckBox);
//            table2layout.add(undoButton);
//            table2layout.add(treeGrid);
//            table2layout.setExpandRatio(expandCheckBox, 10);
//            table2layout.setExpandRatio(undoButton, 10);
//            table2layout.setExpandRatio(treeGrid, 80);
//            table2layout.setSizeFull();
//            table2layout.setMargin(true);


            lowerFormLayout.add(saveButton);

//            formLayout.add(upperFormLayout);
            formLayout.add(lowerFormLayout);
            formLayout.setVisible(false);
            setComponents();

            doInternationalization();
        } catch (final NoProjectsException e){
        removeAll();
        final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
        add(noProjectsScreen);
    }

    }

    public void doInternationalization() {
        ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
//        expandCheckBox.setLabel(messagesBundle.getString("costsinit_expand"));
        saveButton.setText(messagesBundle.getString("save"));
//        undoButton.setText(messagesBundle.getString("costsinit_removesortorder"));
        kundeField.setLabel(messagesBundle.getString("initscreen_customer"));
        projektField.setLabel(messagesBundle.getString("initscreen_project"));
        datumField.setLabel(messagesBundle.getString("costsinit_date"));
        manntageField.setLabel(messagesBundle.getString("costsinit_manday"));
        summeField.setLabel(messagesBundle.getString("costsinit_sumInDDHHMM"));
        projektLeiterField.setLabel(messagesBundle.getString("initscreen_projectleader"));
        unterschriftField.setLabel(messagesBundle.getString("initscreen_signature"));
    }

    public void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator();
        timesCalculator.calculate();
        manntageField.setReadOnly(false);
        summeField.setReadOnly(false);
        manntageField.setValue(timesCalculator.getMannTageGerundet());
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
        final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projekt = plannedProjectDAO.findByID(projectFromSession.getId());
        projektField.setValue(projekt.getProjektName());
    }

    private void erstelleFelderLayout() {
        final FormLayout layoutLinks = new FormLayout();
        final FormLayout layoutRechts = new FormLayout();

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new DatePicker("Datum: ");
//        datumField.setDateFormat(DATE_FORMAT.toPattern());
        manntageField = new TextField();
        summeField = new TextField();
        felderLayout.setSizeUndefined();
        felderLayout.setWidth(ABSOLUTE_WIDTH);

        layoutLinks.add(kundeField);
        layoutLinks.add(projektField);
        layoutLinks.add(datumField);

        layoutRechts.add(manntageField);
        layoutRechts.add(summeField);

        felderLayout.add(layoutLinks);
        felderLayout.add(layoutRechts);

    }

    private void erstelleUnterschriftLayout() {
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setSizeUndefined();
        unterschriftLayout.setWidth("350px");

        unterschriftLayout.add(projektLeiterField);
        unterschriftLayout.add(unterschriftField);
    }

    public void setComponents() {
        overviewGrid.getElement().getStyle().set("margin-top", "10px");
        treeGrid.getElement().getStyle().set("margin-top", "10px");
        treeGrid.expandRecursively(treeGrid.getTreeData().getRootItems().stream(), 100);
        add(felderLayout);
        add(hoursPerWorkingDayPanel);
        add(unterschriftLayout);
        add(overviewGrid);
        add(treeGrid);
        add(formLayout);
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public VerticalLayout getUpperFormLayout() {
        return upperFormLayout;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    private void fillOverviewGrid() {

        final VaadinSession session = VaadinSession.getCurrent();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        final String angabe = messagesBundle.getString("angabe");
        Grid.Column<HashMap<String, String>> column = overviewGrid.addColumn(hashmap -> hashmap.get(angabe));
        applyColumnHeaderAndKey(column, angabe);
        column.setWidth(TASK_COLUMN_WIDTH);

        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            Grid.Column<HashMap<String, String>> resourceGroupColumn = overviewGrid.addColumn(hashmap -> hashmap.get(ressourceGroup.getName())).setTextAlign(ColumnTextAlign.END);;
            applyColumnHeaderAndKey(resourceGroupColumn, ressourceGroup.getName());
        }
        final TimesCalculator timesCalculator = new TimesCalculator();
        timesCalculator.calculate();

        List<HashMap<String, String>> overviewBeans = new ArrayList<>();

        final String costsinit_sumInPercent = messagesBundle.getString("costsinit_sumInPercent");
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final HashMap<String, String> overviewBeanSumInPercent = new HashMap<>();
        final Map<RessourceGroup, Double> relativeWerte = timesCalculator.getRelativeWerte();
        overviewBeanSumInPercent.put(angabe, costsinit_sumInPercent);
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> {
                final String prozentWert = format.format(relativeWerte.get(ressourceGroup));
                overviewBeanSumInPercent.put(ressourceGroup.getName(), prozentWert + " %");
            });
        }
        overviewBeans.add(overviewBeanSumInPercent);


        final String costsinit_sumInDDHHMM = messagesBundle.getString("costsinit_sumInDDHHMM");
        final HashMap<String, String> overviewBeanSumInDDHHMM = new HashMap<>();
        final Map<RessourceGroup, Integer> absoluteWerte = timesCalculator.getAbsoluteWerte();
        overviewBeanSumInDDHHMM.put(angabe, costsinit_sumInDDHHMM);
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> {
                final Integer minutesFromMap = absoluteWerte.get(ressourceGroup);
                final DaysHoursMinutesItem itemForMinutesFromMap = new DaysHoursMinutesItem(minutesFromMap, currentProject.getHoursPerWorkingDay());
                overviewBeanSumInDDHHMM.put(ressourceGroup.getName(), itemForMinutesFromMap.toString());
            });
        }
        overviewBeans.add(overviewBeanSumInDDHHMM);

        overviewGrid.setItems(overviewBeans);
    }

    private void fillTreeGrid() {
        final String aufgabe = messagesBundle.getString("aufgabe");
        Grid.Column<AufgabeHashMap> column = treeGrid.addHierarchyColumn(hashmap -> hashmap.get(aufgabe));
        applyColumnHeaderAndKey(column, aufgabe);
        column.setWidth(TASK_COLUMN_WIDTH);

        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            Grid.Column<AufgabeHashMap> resourceGroupColumn = treeGrid.addColumn(hashmap -> hashmap.get(ressourceGroup.getName())).setTextAlign(ColumnTextAlign.END);;
            applyColumnHeaderAndKey(resourceGroupColumn, ressourceGroup.getName());
        }

        final TimesCalculator timeCalculator = new TimesCalculator();
        timeCalculator.calculate();
        final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projectFromDB = plannedProjectDAO.findByID(projectFromSession.getId());
        final Set<PlanningUnit> planningUnits = projectFromDB.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            final AufgabeHashMap treeGridRowBean = new AufgabeHashMap();
            final String planningUnitName = planningUnit.getPlanningUnitName();
            treeGridRowBean.put(aufgabe, planningUnitName);
            if (planningUnit.getParent() == null) {
                treeGrid.getTreeData().addItem(null, treeGridRowBean);
            }
            final Set<PlanningUnit> planningUnitList = planningUnit.getKindPlanningUnits();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                    final Optional<PlanningUnitElement> matchingPlanningUnitElement = planningUnitElementList.stream()
                            .filter(planningUnitElement -> planningUnitElement.getRessourceGroup().getName().equals(spalte.getName())).findFirst();
                    matchingPlanningUnitElement.ifPresent(planningUnitElement -> {

//                        planningUnitElement.setPlannedMinutes(planningUnitElement.getPlannedMinutes()); ???
                        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement, projectFromSession.getHoursPerWorkingDay());
                        treeGridRowBean.put(spalte.getName(), daysHoursMinutesItem.toString());
//                        treeGrid.getTreeData().addItem(null, treeGridRowBean);
                    });
                }
            } else {
                calculatePlanningUnits(planningUnitList, treeGridRowBean);
            }
        }
        addFootersToTreeGrid(timeCalculator);
    }

    private void addFootersToTreeGrid(TimesCalculator timeCalculator) {
        final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        final Map<RessourceGroup, Integer> werteMap = timeCalculator.getAbsoluteWerte();
        for(final RessourceGroup ressourceGroup : werteMap.keySet()){
            final DaysHoursMinutesItem item = new DaysHoursMinutesItem(werteMap.get(ressourceGroup), projectFromSession.getHoursPerWorkingDay());
            treeGrid.getColumnByKey(ressourceGroup.getName()).setFooter(item.toString());
        }
    }

    private void calculatePlanningUnits(final Set<PlanningUnit> planningUnits, final AufgabeHashMap parent) {
        final PlannedProject projectFromSession = VaadinSession.getCurrent().getAttribute(PlannedProject.class);
        for (final PlanningUnit planningUnit : planningUnits) {
            final AufgabeHashMap row = new AufgabeHashMap();
            final String planningUnitName = planningUnit.getPlanningUnitName();
            row.put(messagesBundle.getString("aufgabe"), planningUnitName);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement, projectFromSession.getHoursPerWorkingDay());
                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                    row.put(ressourceGroup.getName(), item.toString());
                }
                treeGrid.getTreeData().addItem(parent, row);
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                treeGrid.getTreeData().addItem(parent, row);
                calculatePlanningUnits(planningUnit.getKindPlanningUnits(), row);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final Integer minutes = ressourceGroupsMinutesMap.get(spalte);
            final DaysHoursMinutesItem item = new DaysHoursMinutesItem(minutes, projectFromSession.getHoursPerWorkingDay());
            parent.put(spalte.getName(), item.toString());
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messagesBundle.getString("aufgabe");
            int newMinutes;
            if (!ressourceGroup.getName().equals(aufgabe)) {
                if (ressourceGroupsMinutesMap.containsKey(ressourceGroup)) {
                    final Integer oldMinutes = ressourceGroupsMinutesMap.get(ressourceGroup);
                    newMinutes = planningUnitElement.getPlannedMinutes() + oldMinutes;
                    ressourceGroupsMinutesMap.put(ressourceGroup, newMinutes);
                } else {
                    ressourceGroupsMinutesMap.put(ressourceGroup, planningUnitElement.getPlannedMinutes());
                }
//                final DaoFactory daoFactory = DaoFactorySingelton.getInstance(); ???
//                daoFactory.saveOrUpdateTX(planningUnitElement); ???
            }
        }
    }

    private void applyColumnHeaderAndKey(Grid.Column<? extends HashMap<String, String>> column, String key) {
        column.setHeader(key).setKey(key);
    }
}
