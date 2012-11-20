package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.planningunits.all;

import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.ProjektplanungScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.logic.TreeValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.PlanningUnitBeanItemContainer;

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
    
    private PlanningUnitBeanItemContainer container;



    public PlanningUnitsTree(final ProjektplanungScreen screen, final PlanningUnit selectedPlanningUnit,
                             final PlannedProject projekt){
        container = new PlanningUnitBeanItemContainer();
        if (selectedPlanningUnit != null) {
            setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
            setItemCaptionPropertyId(PlanningUnit.NAME);
            setImmediate(true);
            container.addBean(selectedPlanningUnit);
            buildTree(selectedPlanningUnit.getKindPlanningUnits(), selectedPlanningUnit);
            expandItemsRecursively(selectedPlanningUnit);
            addValueChangeListener(new TreeValueChangeListener(screen, projekt));
            setContainerDataSource(container);
            final Iterator iterator = rootItemIds().iterator();
            while (iterator.hasNext()){
                expandItemsRecursively(iterator.next());
            }
        }
    }

    private void buildTree(final Set<PlanningUnit> planningUnits, final PlanningUnit parentUnit) {
        for (final PlanningUnit planningUnit : planningUnits) {
            container.addBean(planningUnit);
            container.setParent(planningUnit, parentUnit);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
            } else {
                buildTree(planningUnit.getKindPlanningUnits(), planningUnit);
            }
        }
    }
}
