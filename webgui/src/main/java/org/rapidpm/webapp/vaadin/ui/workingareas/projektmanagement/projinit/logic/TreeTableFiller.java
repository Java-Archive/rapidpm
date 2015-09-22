package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.VaadinSession;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnitElement;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.AufwandProjInitScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 31.08.12
 * Time: 15:52
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
//TODO TreeTableFiller-Klassen mergen!!!
public class TreeTableFiller {

  private static final int WIDTH = 200;

  private HierarchicalContainer dataSource;
  private MyTreeTable treeTable;
  private AufwandProjInitScreen screen;
  private TreeTableValue cellValue;
  private ResourceBundle messages;
  private PlannedProject currentProject;


  public TreeTableFiller(final ResourceBundle bundle, final AufwandProjInitScreen screen,
                         final MyTreeTable treeTable, final HierarchicalContainer dataSource, TreeTableValue cellValue) {
    this.messages = bundle;
    this.dataSource = dataSource;
    this.treeTable = treeTable;
    this.screen = screen;
    this.cellValue = cellValue;
  }

  public void fill() {
    final VaadinSession session = screen.getUi().getSession();
    currentProject = session.getAttribute(PlannedProject.class);
    final TreeTableDataSourceFiller treeTableDataSourceFiller = new TreeTableDataSourceFiller(screen, messages, dataSource, cellValue);
    treeTableDataSourceFiller.fill();
    for (final Object listener : treeTable.getListeners(ItemClickEvent.ItemClickListener.class)) {
      treeTable.removeItemClickListener((ItemClickEvent.ItemClickListener) listener);
    }
    treeTable.addItemClickListener(new TableItemClickListener(messages, screen));
    treeTable.setContainerDataSource(this.dataSource);
    final String aufgabeColumn = messages.getString("aufgabe");
    for (final Object propertyId : treeTable.getContainerPropertyIds()) {
      if (propertyId.equals(aufgabeColumn)) {
        treeTable.setColumnCollapsible(aufgabeColumn, false);
        treeTable.setColumnWidth(aufgabeColumn, WIDTH);
      } else {
        treeTable.setColumnExpandRatio(propertyId, 1);
      }
    }
    treeTable.setFooterVisible(true);
    fillFooter();
    treeTable.setValue(null);
  }

  private void fillFooter() {
    final Map<RessourceGroup, Integer> ressourceGroupTimesMap = new HashMap<>();
    final PlannedProject plannedProject = DaoFactorySingleton.getInstance().getPlannedProjectDAO().findByID(screen.getUi().getCurrentProject().getId(), true);
    final List<PlanningUnit> topLevelPlanningUnits = plannedProject.getTopLevelPlanningUnits();
    for (PlanningUnit topLevelPlanningUnit : topLevelPlanningUnits) {
      topLevelPlanningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(topLevelPlanningUnit.getId(), true);
      for (PlanningUnitElement planningUnitElement : topLevelPlanningUnit.getPlanningUnitElementList()) {
        if (!ressourceGroupTimesMap.containsKey(planningUnitElement.getRessourceGroup())) {
          ressourceGroupTimesMap.put(planningUnitElement.getRessourceGroup(), 0);
        }
        ressourceGroupTimesMap.put(planningUnitElement.getRessourceGroup(), ressourceGroupTimesMap.get(planningUnitElement.getRessourceGroup()) + planningUnitElement.getPlannedMinutes());
      }
    }
    for (final RessourceGroup ressourceGroup : ressourceGroupTimesMap.keySet()) {
      treeTable.setColumnFooter(ressourceGroup.getName(), new DaysHoursMinutesItem(ressourceGroupTimesMap.get(ressourceGroup), currentProject.getHoursPerWorkingDay()).toString());
    }
  }
}
