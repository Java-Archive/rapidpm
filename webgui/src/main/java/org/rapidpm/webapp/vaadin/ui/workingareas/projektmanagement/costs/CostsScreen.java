package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.CostsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;

import java.text.DecimalFormat;
import java.util.*;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

//import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;

@Route(value = "kosten", layout = MainAppLayout.class)
@Caption("Kosten")
@Icon(VaadinIcon.MONEY_DEPOSIT)
public class CostsScreen extends Screen {

    private static final String WIDTH = "200px";

    private Button saveButton = new Button();
    private Button undoButton;
    private Checkbox expandCheckBox;
    private TextField vertrieblerField;
    private DatePicker datumField;
    private TextField manntageField;
    private TextField summeField;
    private TextField kostenField;

    private Grid einmaligeKostenTable = new Grid();
    private Grid monatlicheKostenTable = new Grid();

    private FormLayout unterschriftLayout = new FormLayout();
    private FormLayout felderLayout = new FormLayout();

//    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private TreeGrid<AufgabeHashMap> treeGrid = new TreeGrid<>();
    private Grid<HashMap<String, String>> overviewGrid = new Grid<>();

//    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private VerticalLayout upperFormLayout = new VerticalLayout();
    private VerticalLayout lowerFormLayout = new VerticalLayout();
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private final DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);

    public CostsScreen() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            ressourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();

//            undoButton = new UndoButton(this, treeGrid, null);
//            undoButton.setVisible(false);

//            expandCheckBox = new ExpandTableCheckBox(treeGrid, null);

            fillTreeGrid();
            fillOverviewGrid();

            overviewGrid.setHeightByRows(true);
            treeGrid.setHeightByRows(true);
//            overviewGrid.setConnectedTable(treeGrid);
//            overviewGrid.setSizeFull();
//            treeGrid.setConnectedTable(overviewGrid);
//            treeGrid.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
//            treeGrid.setSizeFull();

//            table1layout.setMargin(true);
//            table1layout.add(overviewGrid);
//            table1layout.setSizeFull();

//            table2layout.setMargin(true);
//            table2layout.add(expandCheckBox);
//            table2layout.add(undoButton);
//            table2layout.add(treeGrid);
//            table2layout.setSizeFull();

            lowerFormLayout.add(saveButton);

//            formLayout.add(upperFormLayout);
            formLayout.add(lowerFormLayout);
            formLayout.setVisible(false);

            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }

    }

    private void fillOverviewGrid() {
        final VaadinSession session = VaadinSession.getCurrent();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
        final String angabe = messagesBundle.getString("angabe");
        Grid.Column<HashMap<String, String>> column = overviewGrid.addColumn(hashmap -> hashmap.get(angabe));
        applyColumnHeaderAndKey(column, angabe);
        column.setWidth(WIDTH);

        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            Grid.Column<HashMap<String, String>> resourceGroupColumn = overviewGrid.addColumn(hashmap -> hashmap.get(ressourceGroup.getName())).setTextAlign(ColumnTextAlign.END);;
            applyColumnHeaderAndKey(resourceGroupColumn, ressourceGroup.getName());
        }
        final TimesCalculator timesCalculator = new TimesCalculator();
        timesCalculator.calculate();

        final CostsCalculator costsCalculator = new CostsCalculator(messagesBundle);
        costsCalculator.calculate();

        List<HashMap<String, String>> overviewBeans = new ArrayList<>();

        final HashMap<String, String> overviewBean = new HashMap<>();
        overviewBean.put(angabe, messagesBundle.getString("costsscreen_externalEuroPerHour"));
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            final Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> overviewBean.put(ressourceGroup.getName(), ressourceGroup.getExternalEurosPerHour().toString()));
        }
        overviewBeans.add(overviewBean);

        final HashMap<String, String> overviewBeanSumTime = new HashMap<>();
        overviewBeanSumTime.put(angabe, messagesBundle.getString("costsinit_sumInDDHHMM"));
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            final Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> {
                final Map<RessourceGroup, Integer> absoluteWerte = timesCalculator.getAbsoluteWerte();
                final Integer minutesTotal = absoluteWerte.get(ressourceGroup);
                final DaysHoursMinutesItem itemForMinutesFromMap = new DaysHoursMinutesItem(minutesTotal, currentProject.getHoursPerWorkingDay());
                overviewBeanSumTime.put(ressourceGroup.getName(), itemForMinutesFromMap.toString());
            });
        }
        overviewBeans.add(overviewBeanSumTime);

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final HashMap<String, String> overviewBeanSumRelative = new HashMap<>();
        overviewBeanSumRelative.put(angabe, messagesBundle.getString("costsinit_sumInPercent"));
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> {
                final Map<RessourceGroup, Double> relativeZeiten = timesCalculator.getRelativeWerte();
                final Double relativeZeit = relativeZeiten.get(ressourceGroup);
                final String cellValue = format.format(relativeZeit);
                overviewBeanSumRelative.put(ressourceGroup.getName(), cellValue + " %");
            });
        }
        overviewBeans.add(overviewBeanSumRelative);

        final HashMap<String, String> overviewBeanCostsTotal = new HashMap<>();
        overviewBeanCostsTotal.put(angabe, messagesBundle.getString("costsscreen_sumInEuro"));
        for (Grid.Column<HashMap<String, String>> resourceGroupNameColumn : overviewGrid.getColumns()) {
            Optional<RessourceGroup> matchingResourceGroup = ressourceGroups.stream().filter(ressourceGroup -> ressourceGroup.getName().equals(resourceGroupNameColumn.getKey())).findFirst();
            matchingResourceGroup.ifPresent(ressourceGroup -> {
                final Map<RessourceGroup, Double> kostenMap = costsCalculator.getRessourceGroupsCostsMap();
                final Double costsTotal = kostenMap.get(ressourceGroup);
                final String cellValue = format.format(costsTotal);
                overviewBeanCostsTotal.put(ressourceGroup.getName(), cellValue + " " + EUR);
            });
        }
        overviewBeans.add(overviewBeanCostsTotal);
        overviewGrid.setItems(overviewBeans);
    }

    private void applyColumnHeaderAndKey(Grid.Column<? extends HashMap<String, String>> column, String key) {
        column.setHeader(key).setKey(key);
    }

    private void fillTreeGrid() {
        final String aufgabe = messagesBundle.getString("aufgabe");
        Grid.Column<AufgabeHashMap> column = treeGrid.addHierarchyColumn(hashmap -> hashmap.get(aufgabe));
        applyColumnHeaderAndKey(column, aufgabe);
        column.setWidth(WIDTH);

        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            Grid.Column<AufgabeHashMap> resourceGroupColumn = treeGrid.addColumn(hashmap -> hashmap.get(ressourceGroup.getName())).setTextAlign(ColumnTextAlign.END);;
            applyColumnHeaderAndKey(resourceGroupColumn, ressourceGroup.getName());
        }

        final CostsCalculator costsCalculator = new CostsCalculator(messagesBundle);
        costsCalculator.calculate();
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
                        treeGridRowBean.put(spalte.getName(), decimalFormat.format(getCosts(planningUnitElement)) + EUR);
                        treeGrid.getTreeData().addItem(null, treeGridRowBean);
                    });
                }
            } else {
                computePlanningUnits(planningUnitList, treeGridRowBean);
            }
        }
        addFootersToTreeGrid(costsCalculator);
    }

    private void addFootersToTreeGrid(CostsCalculator costsCalculator) {
        final Map<RessourceGroup, Double> werteMap = costsCalculator.getRessourceGroupsCostsMap();
        for(final RessourceGroup ressourceGroup : werteMap.keySet()){
            final String ressourceGroupKostenString = decimalFormat.format(werteMap.get(ressourceGroup))+EUR;
            treeGrid.getColumnByKey(ressourceGroup.getName()).setFooter(ressourceGroupKostenString);
        }
    }

    private void computePlanningUnits(Set<PlanningUnit> planningUnits, AufgabeHashMap parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final AufgabeHashMap row = new AufgabeHashMap();
            final String planningUnitName = planningUnit.getPlanningUnitName();
            row.put(messagesBundle.getString("aufgabe"), planningUnitName);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final Double costs = getCosts(planningUnitElement);
                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                    row.put(ressourceGroup.getName(), decimalFormat.format(costs) + EUR);
                }
                treeGrid.getTreeData().addItem(parent, row);
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                treeGrid.getTreeData().addItem(parent, row);
                computePlanningUnits(planningUnit.getKindPlanningUnits(), row);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final Double newValue = ressourceGroupsCostsMap.get(spalte);
            parent.put(spalte.getName(), decimalFormat.format(newValue) + EUR);
        }
    }

    private void erstelleFelderLayout() {
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.add(manntageField);
        felderLayout.add(summeField);
        felderLayout.add(kostenField);
    }

    private void erstelleUnterschriftLayout() {
        // Unterschrift
        vertrieblerField = new TextField();
        datumField = new DatePicker("Datum:");
//        datumField.setDateFormat(DATE_FORMAT.toPattern());
        unterschriftLayout.setWidth("560px");
        unterschriftLayout.add(vertrieblerField);
        unterschriftLayout.add(datumField);
    }

    private void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator();
        final CostsCalculator costsCalculator = new CostsCalculator(messagesBundle);
        costsCalculator.calculate();
        timesCalculator.calculate();
        summeField = new TextField();
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField = new TextField();
        kostenField = new TextField();
        manntageField.setValue(timesCalculator.getMannTageGerundet());
        kostenField.setValue(costsCalculator.getTotalCostsGerundet() + EUR);
        kostenField.setReadOnly(true);
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
    }

    @Override
    public void setComponents() {
        overviewGrid.getElement().getStyle().set("margin-top", "10px");
        treeGrid.getElement().getStyle().set("margin-top", "10px");
        treeGrid.expandRecursively(treeGrid.getTreeData().getRootItems().stream(), 100);
        add(felderLayout);
        add(unterschriftLayout);
        add(overviewGrid);
        add(treeGrid);
        add(formLayout);
    }

    @Override
    public void doInternationalization() {
//        expandCheckBox.setLabel(messagesBundle.getString("costsinit_expand"));
        saveButton.setText(messagesBundle.getString("save"));
//        undoButton.setText(messagesBundle.getString("costsinit_removesortorder"));
        datumField.setLabel(messagesBundle.getString("costsinit_date"));
        manntageField.setLabel(messagesBundle.getString("costsinit_manday"));
        vertrieblerField.setLabel(messagesBundle.getString("costsscreen_responsible"));
        kostenField.setLabel(messagesBundle.getString("costsscreen_costs"));
        summeField.setLabel(messagesBundle.getString("costsinit_sumInDDHHMM"));
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            if (!ressourceGroup.getName().equals(messagesBundle.getString("aufgabe"))) {
                Double costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(ressourceGroup)) {
                    costs += ressourceGroupsCostsMap.get(ressourceGroup);
                }
                ressourceGroupsCostsMap.put(ressourceGroup, costs);
            }
        }
    }

    private Double getCosts(final PlanningUnitElement planningUnitElement) {

        final Double totalHours = planningUnitElement.getPlannedMinutes() / 60.0;

        final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
        final Double externalEurosPerHour = ressourceGroup.getExternalEurosPerHour();

        return totalHours * externalEurosPerHour;
    }
}