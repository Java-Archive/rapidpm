package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.HOURS_DAY;
import static org.rapidpm.Constants.STD_ANTEILE;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {
//    private TreeTableDataSourceFillerBean bean;
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;
    private CostsScreen screen;

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(final CostsScreen screen, final ResourceBundle bundle,
                                     final HierarchicalContainer dSource) {
        this.screen = screen;
        messages = bundle;
        dataSource = dSource;

//        bean = EJBFactory.getEjbInstance(TreeTableDataSourceFillerBean.class);
//        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        ressourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            daoFactory.getEntityManager().refresh(ressourceGroup);
        }
        dataSource.removeAllItems();
        dataSource.addContainerProperty(messages.getString("aufgabe"), String.class, null);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            dataSource.addContainerProperty(ressourceGroup.getName(), Double.class, "");
        }

    }

    public void fill() {
        computePlanningUnitsAndTotalsAbsolut();
    }

    private void computePlanningUnitsAndTotalsAbsolut() {
        final PlannedProject projectFromSession = screen.getUi().getCurrentProject();
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
        final PlannedProject projectFromDB = plannedProjectDAO.findByID(projectFromSession.getId());
        final List<PlanningUnit> planningUnits = projectFromDB.getPlanningUnits();
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitName);
            final List<PlanningUnit> planningUnitList = planningUnit.getKindPlanningUnits();
            if (planningUnitList == null || planningUnitList.isEmpty()) {
                for (final RessourceGroup spalte : ressourceGroups) {
                    final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
                    for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
                        final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                        if (ressourceGroup.equals(spalte)) {
                            planningUnitItem.getItemProperty(spalte.getName()).setValue(0.0);
                        }
                    }
                }
            } else {
                computePlanningUnits(planningUnitList, planningUnitName);
            }
        }
    }


    private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent) {
        for (final PlanningUnit planningUnit : planningUnits) {
            final String planningUnitName = planningUnit.getPlanningUnitName();
            final Item planningUnitItem = dataSource.addItem(planningUnitName);
            planningUnitItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitName);
            dataSource.setParent(planningUnitName, parent);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                for (final PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
                    final Double costs = getCosts(planningUnitElement);
                    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
                    planningUnitItem.getItemProperty(ressourceGroup.getName()).setValue(costs);
                }
                addiereZeileZurRessourceMap(planningUnit);
            } else {
                computePlanningUnits(planningUnit.getKindPlanningUnits(), planningUnitName);
            }
        }
        for (final RessourceGroup spalte : ressourceGroups) {
            final Item dataSourceItem = dataSource.getItem(parent);
            final Double newValue = ressourceGroupsCostsMap.get(spalte);
            final Property<Double> itemProperty = dataSourceItem.getItemProperty(spalte.getName());
            itemProperty.setValue(newValue);
        }
    }

    private void addiereZeileZurRessourceMap(final PlanningUnit planningUnit) {
        final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
        for (final PlanningUnitElement planningUnitElement : planningUnitElementList) {
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            if (!ressourceGroup.getName().equals(messages.getString("aufgabe"))) {
                Double costs = getCosts(planningUnitElement);
                if (ressourceGroupsCostsMap.containsKey(ressourceGroup)) {
                    costs += ressourceGroupsCostsMap.get(ressourceGroup);
                }
                ressourceGroupsCostsMap.put(ressourceGroup, costs);
            }
        }
    }

    private Double getCosts(final PlanningUnitElement planningUnitElement) {
        final int hoursFromDays = HOURS_DAY * planningUnitElement.getPlannedDays();
        final int hours = planningUnitElement.getPlannedHours();
        final double hoursFromMinutes = STD_ANTEILE * planningUnitElement.getPlannedMinutes();

        final Double totalHours = hoursFromDays + hours + hoursFromMinutes;

        final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
        final Double externalEurosPerHour = ressourceGroup.getExternalEurosPerHour();

        return totalHours * externalEurosPerHour;
    }

}
