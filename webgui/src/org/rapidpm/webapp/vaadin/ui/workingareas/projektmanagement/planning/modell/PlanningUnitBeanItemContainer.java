package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco Ebbinghaus
 * Date: 20.09.12
 * Time: 09:13
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class PlanningUnitBeanItemContainer extends BeanItemContainer<PlanningUnit> implements Container.Hierarchical {

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

    /**
     * Moves a node (an Item) in the container immediately after a sibling node.
     * The two nodes must have the same parent in the container.
     *
     * @param itemId
     *            the identifier of the moved node (Item)
     * @param siblingId
     *            the identifier of the reference node (Item), after which the
     *            other node will be located
     */
    public void moveAfterSibling(Object itemId, Object siblingId) {
        final Object parent2 = getParent(itemId);
        final LinkedList<Object> childrenList;
        final LinkedList<Object> roots = new LinkedList<>();
        if (parent2 == null) {
            for (final PlanningUnit planningUnit : getItemIds()) {
                if (isRoot(planningUnit)) {
                    roots.add(planningUnit);
                }
            }
            childrenList = roots;
        } else {
            childrenList = new LinkedList<>(getChildren(parent2));
        }
        if (siblingId == null) {
            childrenList.remove(itemId);
            childrenList.addFirst(itemId);

        } else {
            int oldIndex = childrenList.indexOf(itemId);
            int indexOfSibling = childrenList.indexOf(siblingId);
            if (indexOfSibling != -1 && oldIndex != -1) {
                int newIndex;
                if (oldIndex > indexOfSibling) {
                    newIndex = indexOfSibling + 1;
                } else {
                    newIndex = indexOfSibling;
                }
                childrenList.remove(oldIndex);
                childrenList.add(newIndex, itemId);
            } else {
                throw new IllegalArgumentException(
                        "Given identifiers no not have the same parent.");
            }
        }
        fireItemSetChange();

    }


}
