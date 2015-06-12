package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeSortDropHandler;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Marco Ebbinghaus
 * Date: 17.10.12
 * Time: 12:50
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitsTree extends Tree{
    
    private HierarchicalContainer container;
    private TreeValueChangeListener listener;



    public PlanningUnitsTree(final ProjektplanungScreen screen, final PlanningUnit selectedPlanningUnit){
        container = new HierarchicalContainer();
        if (selectedPlanningUnit != null) {
            setItemCaptionMode(ItemCaptionMode.ID);
            //setItemCaptionPropertyId(PlanningUnit.NAME);
            setImmediate(true);
            container.addItem(selectedPlanningUnit);
            buildTree(selectedPlanningUnit.getKindPlanningUnits(), selectedPlanningUnit);
            expandItemsRecursively(selectedPlanningUnit);
            listener = new TreeValueChangeListener(screen);
            addValueChangeListener(listener);
            setContainerDataSource(container);
            final Iterator iterator = rootItemIds().iterator();
            while (iterator.hasNext()){
                expandItemsRecursively(iterator.next());
            }
            setDragMode(TreeDragMode.NODE);
            setDropHandler(new TreeSortDropHandler(this, screen));
        }
    }

    private void buildTree(final List<PlanningUnit> planningUnits, PlanningUnit parentUnit) {
        for (PlanningUnit planningUnit : planningUnits) {
            planningUnit = DaoFactorySingleton.getInstance().getPlanningUnitDAO().findByID(planningUnit.getId(), true);
            container.addItem(planningUnit);
            container.setParent(planningUnit, parentUnit);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), planningUnit);
            }
        }
    }

    public TreeValueChangeListener getListener() {
        return listener;
    }
}
