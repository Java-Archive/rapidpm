package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;

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
    private TreeTableDataSourceFillerBean bean;
    private List<RessourceGroup> ressourceGroups;
    private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
    private ResourceBundle messages;

    private HierarchicalContainer dataSource;

    public TreeTableDataSourceFiller(final ResourceBundle bundle, final HierarchicalContainer dSource) {
        messages = bundle;
        dataSource = dSource;
        bean = EJBFactory.getEjbInstance(TreeTableDataSourceFillerBean.class);
        final DaoFactoryBean baseDaoFactoryBean = bean.getDaoFactoryBean();
        final EntityManager entityManager = baseDaoFactoryBean.getRessourceGroupDAO().getEntityManager();
        for(final RessourceGroup ressourceGroup : baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities()){
            entityManager.refresh(ressourceGroup);
        }
        ressourceGroups = baseDaoFactoryBean.getRessourceGroupDAO().loadAllEntities();

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
        //final Integer currentProjectIndex = projektBean.getCurrentProjectIndex();
        //final Projekt projekt = projektBean.getProjekte().get(currentProjectIndex);
        final PlannedProject projekt = bean.getDaoFactoryBean().getPlannedProjectDAO().loadAllEntities().get(0);
        final List<PlanningUnit> planningUnits = projekt.getPlanningUnits();
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


    private void computePlanningUnits(List<PlanningUnit> planningUnits, String parent) {
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
            final Property<?> itemProperty = dataSourceItem.getItemProperty(spalte.getName());
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
