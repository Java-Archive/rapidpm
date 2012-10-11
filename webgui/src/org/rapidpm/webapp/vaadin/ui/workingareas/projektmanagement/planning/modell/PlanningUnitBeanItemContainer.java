package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.ArrayList;
import java.util.Collection;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 20.09.12
 * Time: 09:13
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitBeanItemContainer extends BeanItemContainer<PlanningUnit> implements Container.Hierarchical{

    public PlanningUnitBeanItemContainer() {
        super(PlanningUnit.class);
    }

    public boolean areChildrenAllowed(final Object planningUnit) {
        if (planningUnit instanceof PlanningUnit) {
            return true;
        }
        return false;
    }

    public Collection<?> getChildren(final Object planningUnit) {
        return ((PlanningUnit) planningUnit).getKindPlanningUnits();
    }

    public Object getParent(final Object planningUnit) {
        return ((PlanningUnit) planningUnit).getParent();
    }

    public boolean hasChildren(final Object planningUnit) {
        if (areChildrenAllowed(planningUnit)) {
            return !((PlanningUnit) planningUnit).getKindPlanningUnits().isEmpty();
        }
        return false;
    }

    public boolean isRoot(final Object planningUnit) {
        return (((PlanningUnit) planningUnit).getParent() == null);
    }

    public Collection<?> rootItemIds() {
        ArrayList<PlanningUnit> arrayList = new ArrayList<>();
        for (final PlanningUnit planningUnit : getItemIds()) {
            if (isRoot(planningUnit)) {
                arrayList.add(planningUnit);
            }
        }
        return arrayList;
    }

    public boolean setChildrenAllowed(final Object planningUnit, final boolean areChildrenAllowed){
        return true;
    }

    public boolean setParent(final Object planningUnit, final Object newParentId){
        ((PlanningUnit)planningUnit).setParent((PlanningUnit)newParentId);
        return true;
    }
}
