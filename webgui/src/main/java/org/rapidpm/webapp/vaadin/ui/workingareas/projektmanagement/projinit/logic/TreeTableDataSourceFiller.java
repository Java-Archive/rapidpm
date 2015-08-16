package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinSession;
import org.apache.log4j.Logger;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;

import java.util.*;

import static org.rapidpm.Constants.MINS_HOUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {

    private static final Logger logger = Logger.getLogger(TreeTableDataSourceFiller.class);

    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Integer> ressourceGroupMinutesMap = new HashMap<>();
    private final Map<RessourceGroup, Double> ressourceGroupCostsMap = new HashMap<>();
    private ResourceBundle messages;
    private HierarchicalContainer dataSource;
    private TreeTableValue cellValue;
    private Screen screen;
    private PlannedProject currentProject;

    public TreeTableDataSourceFiller(final Screen screen, final ResourceBundle bundle,
                                     final HierarchicalContainer dSource, TreeTableValue cellValue) {
        this.screen = screen;
        this.messages = bundle;
        dataSource = dSource;
        this.cellValue = cellValue;

        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final VaadinSession session = screen.getUi().getSession();
        currentProject = session.getAttribute(PlannedProject.class);
        final RessourceGroupDAO ressourceGroupDAO = DaoFactorySingleton.getInstance().getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.findAll();

        dataSource.removeAllItems();
        final String aufgabe = messages.getString("aufgabe");
        dataSource.addContainerProperty(aufgabe, String.class, null);
        switch (cellValue){
            case COSTS:
                for (final RessourceGroup ressourceGroup : ressourceGroups) {
                    dataSource.addContainerProperty(ressourceGroup.getName(), Double.class, "");
                }
                break;
            case TIMES:
                for (final RessourceGroup ressourceGroup : ressourceGroups) {
                    dataSource.addContainerProperty(ressourceGroup.getName(), String.class, "");
                }
                break;
        }

    }

    public void fill() {
        currentProject = DaoFactorySingleton.getInstance().getPlannedProjectDAO().findByID(currentProject.getId(), true);
        final List<PlanningUnit> topLevelPlanningUnits = currentProject.getTopLevelPlanningUnits();
        final List<PlanningUnit> allPlanningUnitsOfProject = new ArrayList<>();
        allPlanningUnitsOfProject.addAll(topLevelPlanningUnits);
        for (PlanningUnit topLevelPlanningUnit : topLevelPlanningUnits) {
            allPlanningUnitsOfProject.addAll(DaoFactorySingleton.getInstance().getPlanningUnitDAO().loadChildren(topLevelPlanningUnit, true));
        }
        fillPlanningUnitBasedTreeTableWithValue(allPlanningUnitsOfProject, cellValue);
    }

    private void fillPlanningUnitBasedTreeTableWithValue(List<PlanningUnit> planningUnits, TreeTableValue cellValue) {
        fillRows(planningUnits, cellValue);
        buildTree(planningUnits);
    }

    private void buildTree(List<PlanningUnit> planningUnits) {
        for (PlanningUnit planningUnit : planningUnits) {
            calculatePlanningUnit(planningUnit);
        }
    }

    private void fillRows(List<PlanningUnit> planningUnits, TreeTableValue cellValue) {
        for (PlanningUnit planningUnit : planningUnits) {
            planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
                final Property<Object> resourceGroupColumn = planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName());
                switch (cellValue) {
                    case COSTS:
                        final int minutes = planningUnitElement.getPlannedMinutes();
                        final double hoursFromMinutes = (double) minutes / MINS_HOUR;
                        final Float externalEurosPerHour = planningUnitElement.getRessourceGroup().getExternalEurosPerHour();
                        resourceGroupColumn.setValue(hoursFromMinutes * externalEurosPerHour);
                        break;
                    case TIMES:
                        final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem(planningUnitElement, currentProject.getHoursPerWorkingDay());
                        resourceGroupColumn.setValue(daysHoursMinutesItem.toString());
                        break;
                }
            }
        }
    }

    public void calculatePlanningUnit(PlanningUnit fullPlanningUnit) {
        fullPlanningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(fullPlanningUnit.getId(), true);
        final String aufgabe = messages.getString("aufgabe");
        final String planningUnitItemName = dataSource.getItem(fullPlanningUnit.getPlanningUnitName()).getItemProperty(aufgabe).getValue().toString();
        if(fullPlanningUnit.getKindPlanningUnits() != null && !fullPlanningUnit.getKindPlanningUnits().isEmpty()){
            for (PlanningUnit childPlanningUnit : fullPlanningUnit.getKindPlanningUnits()) {
                System.out.println("Trying to set Parent: " + planningUnitItemName + " --> " + childPlanningUnit);
                dataSource.setParent(childPlanningUnit.getPlanningUnitName(), planningUnitItemName);
                calculatePlanningUnit(childPlanningUnit);
            }
        }
    }

}
