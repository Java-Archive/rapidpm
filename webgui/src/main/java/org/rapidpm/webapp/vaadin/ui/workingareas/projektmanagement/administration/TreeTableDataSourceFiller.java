package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.administration;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProjectDAO;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.CostsScreen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 31.08.12
 * Time: 16:29
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TreeTableDataSourceFiller {
  private final Map<RessourceGroup, Double> ressourceGroupsCostsMap = new HashMap<>();
  private List<RessourceGroup> ressourceGroups;
  private ResourceBundle messages;
  private CostsScreen screen;

  private HierarchicalContainer dataSource;

  public TreeTableDataSourceFiller(final CostsScreen screen, final ResourceBundle bundle,
                                   final HierarchicalContainer dSource) {
    this.screen = screen;
    messages = bundle;
    dataSource = dSource;
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    ressourceGroups = daoFactory.getRessourceGroupDAO().findAll();
    for (final RessourceGroup ressourceGroup : ressourceGroups) {
//            daoFactory.getEntityManager().refresh(ressourceGroup);
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
    final PlannedProject projectFromSession = screen.getUi().getSession().getAttribute(PlannedProject.class);
    final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
    final PlannedProjectDAO plannedProjectDAO = daoFactory.getPlannedProjectDAO();
    final PlannedProject projectFromDB = plannedProjectDAO.findByID(projectFromSession.getId(), true);
    final List<PlanningUnit> planningUnits = projectFromDB.getTopLevelPlanningUnits();
    for (PlanningUnit planningUnit : planningUnits) {
      planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
      final String planningUnitName = planningUnit.getPlanningUnitName();
      final Item planningUnitItem = dataSource.addItem(planningUnitName);
      planningUnitItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitName);
      final List<PlanningUnit> planningUnitList = planningUnit.getKindPlanningUnits();
      if (planningUnitList == null || planningUnitList.isEmpty()) {
        for (final RessourceGroup spalte : ressourceGroups) {
          final List<PlanningUnitElement> planningUnitElementList = planningUnit.getPlanningUnitElementList();
          for (PlanningUnitElement planningUnitElement : planningUnitElementList) {
            planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
            final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
            if (ressourceGroup.equals(spalte)) {
              planningUnitItem.getItemProperty(spalte.getName()).setValue(getCosts(planningUnitElement));
            }
          }
        }
      } else {
        computePlanningUnits(planningUnitList, planningUnitName);
      }
    }
  }


  private void computePlanningUnits(final List<PlanningUnit> planningUnits, final String parent) {
    for (PlanningUnit planningUnit : planningUnits) {
      final String planningUnitName = planningUnit.getPlanningUnitName();
      final Item planningUnitItem = dataSource.addItem(planningUnitName);
      planningUnitItem.getItemProperty(messages.getString("aufgabe")).setValue(planningUnitName);
      dataSource.setParent(planningUnitName, parent);
      planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
      if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
        for (PlanningUnitElement planningUnitElement : planningUnit.getPlanningUnitElementList()) {
          planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
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
    for (PlanningUnitElement planningUnitElement : planningUnitElementList) {
      planningUnitElement = DaoFactorySingleton.getInstance().getPlanningUnitElementDAO().findByID(planningUnitElement.getId(), true);
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

    final Double totalHours = planningUnitElement.getPlannedMinutes() / 60.0;

    final RessourceGroup ressourceGroup = planningUnitElement.getRessourceGroup();
    final Double externalEurosPerHour = ressourceGroup.getExternalEurosPerHour();

    return totalHours * externalEurosPerHour;
  }

}
