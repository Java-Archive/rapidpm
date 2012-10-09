package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.10.12
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeContainerPlanningUnits extends HierarchicalContainer {

    public final static String PROPERTY_CAPTION = "caption";
    public final static String PROPERTY_PLANNINGUNIT = "planningUnit";

    public TreeContainerPlanningUnits() {
        this.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        this.addContainerProperty(PROPERTY_PLANNINGUNIT, PlanningUnit.class, null);
        filltree();
    }

    private void filltree() {
        Object itemId;
        for (PlanningUnit planningUnit : DummyProjectData.getPlanningUnitList()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(planningUnit.getPlanningUnitName());
            this.getContainerProperty(itemId, PROPERTY_PLANNINGUNIT).setValue(planningUnit);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iteratePlanningUnits(planningUnit, itemId);
            }
        }
    }

    private void iteratePlanningUnits(PlanningUnit parentPlanningUnit, Object parentItemId) {

        Object itemId;
        for (PlanningUnit planningUnit : parentPlanningUnit.getKindPlanningUnits()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(planningUnit.getPlanningUnitName());
            this.getContainerProperty(itemId, PROPERTY_PLANNINGUNIT).setValue(planningUnit);
            this.setParent(itemId, parentItemId);
            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iteratePlanningUnits(planningUnit, itemId);
            }
        }
    }
}
