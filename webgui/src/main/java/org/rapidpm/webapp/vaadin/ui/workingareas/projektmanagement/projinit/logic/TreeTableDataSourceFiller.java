package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.VaadinSession;
import org.apache.log4j.Logger;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.*;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
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
    private ResourceBundle messages;
    private HierarchicalContainer dataSource;
    private AufwandProjInitScreen screen;
    private PlannedProject currentProject;

    public TreeTableDataSourceFiller(final AufwandProjInitScreen screen, final ResourceBundle bundle,
                                     final HierarchicalContainer dSource) {
        this.screen = screen;
        this.messages = bundle;
        dataSource = dSource;

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final VaadinSession session = screen.getUi().getSession();
        currentProject = session.getAttribute(PlannedProject.class);
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.loadAllEntities();

        dataSource.removeAllItems();
        final String aufgabe = messages.getString("aufgabe");
        dataSource.addContainerProperty(aufgabe, String.class, null);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            dataSource.addContainerProperty(ressourceGroup.getName(), String.class, "");
        }
    }

    public void fill() {
        calculatePlanningUnitsAndTotalsAbsolut();
    }

    private void calculatePlanningUnitsAndTotalsAbsolut() {
        final Set<PlanningUnit> planningUnits = currentProject.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            final Set<PlanningUnit> planningUnitList = planningUnit.getKindPlanningUnits();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                        if (planningUnitElement.getRessourceGroup().equals(spalte)) {
                            planningUnitElement.setPlannedMinutes(planningUnitElement.getPlannedMinutes());
                            final DaysHoursMinutesItem daysHoursMinutesItem = new DaysHoursMinutesItem
                                    (planningUnitElement, currentProject.getHoursPerWorkingDay());
                            final Property<String> itemProperty = planningUnitItem.getItemProperty(spalte.getName());
                            itemProperty.setValue(daysHoursMinutesItem.toString());
                        }
                    }
                }
            } else {
                calculatePlanningUnits(planningUnitList, planningUnitName);
            }
        }
    }


    private void calculatePlanningUnits(final Set<PlanningUnit> planningUnits, final String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            final String aufgabe = messages.getString("aufgabe");
            planningUnitItem.getItemProperty(aufgabe).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            final Set<PlanningUnit> kindPlanningUnits = planningUnit.getKindPlanningUnits();
            if (kindPlanningUnits == null || kindPlanningUnits.isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final DaysHoursMinutesItem item = new DaysHoursMinutesItem(planningUnitElement, currentProject.getHoursPerWorkingDay());
                    planningUnitItem.getItemProperty(planningUnitElement.getRessourceGroup().getName()).setValue(item.toString());
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                calculatePlanningUnits(kindPlanningUnits, planningUnitName);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final Integer minutes = ressourceGroupMinutesMap.get(spalte);
            final DaysHoursMinutesItem item = new DaysHoursMinutesItem(minutes, currentProject.getHoursPerWorkingDay());
            dataSource.getItem(parent).getItemProperty(spalte.getName()).setValue(item.toString());
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            final String aufgabe = messages.getString("aufgabe");
            int newMinutes;
            if (!ressourceGroup.getName().equals(aufgabe)) {
                if (ressourceGroupMinutesMap.containsKey(ressourceGroup)) {
                    final Integer oldMinutes = ressourceGroupMinutesMap.get(ressourceGroup);
                     newMinutes = planningUnitElement.getPlannedMinutes() + oldMinutes;
                    ressourceGroupMinutesMap.put(ressourceGroup, newMinutes);
                } else {
                    ressourceGroupMinutesMap.put(ressourceGroup, planningUnitElement.getPlannedMinutes());
                }
                final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
                daoFactory.saveOrUpdateTX(planningUnitElement);
            }
        }
    }
}
