package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.flow.component.treegrid.TreeGrid;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeSortDropHandler;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;

import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 17.10.12
 * Time: 12:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitsTree
    extends TreeGrid<PlanningUnit> {

  //    private HierarchicalContainer container;
  private TreeValueChangeListener listener;


  public PlanningUnitsTree(final ProjektplanungScreen screen, final PlanningUnit selectedPlanningUnit) {
    super(PlanningUnit.class);
    if (selectedPlanningUnit != null) {
      setItems(selectedPlanningUnit);
      setColumns(PlanningUnit.NAME);
      setHierarchyColumn(PlanningUnit.NAME);
      setSelectionMode(SelectionMode.SINGLE);
      buildTree(selectedPlanningUnit.getKindPlanningUnits(), selectedPlanningUnit);
      expand(selectedPlanningUnit);
      listener = new TreeValueChangeListener(screen);
      addItemClickListener(listener);
      for (PlanningUnit planningUnit : getTreeData().getRootItems()) {
        expand(planningUnit);
      }
//            setDragMode(TreeDragMode.NODE);
//            setDropHandler(new TreeSortDropHandler(this, screen));
    }
  }

  private void buildTree(final Set<PlanningUnit> planningUnits, final PlanningUnit parentUnit) {
    for (final PlanningUnit planningUnit : planningUnits) {
      getTreeData().addItem(parentUnit, planningUnit);
      if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits()
                                                                     .isEmpty()) {
      } else {
        buildTree(planningUnit.getKindPlanningUnits(), planningUnit);
      }
    }
  }

  public TreeValueChangeListener getListener() {
    return listener;
  }
}
