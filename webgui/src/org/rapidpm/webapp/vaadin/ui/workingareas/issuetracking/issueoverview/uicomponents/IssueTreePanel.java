package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnitGroup;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreePanel extends Panel{

    private static final String CAPTION_PROPERTY = "caption";
    private Tree issueTree;


    public IssueTreePanel(IssueTabSheet issueTabSheet) {
        this.setSizeFull();
        issueTree = new Tree("IssueTree", new TreeContainerPlanningUnits());
        issueTree.setImmediate(true);
        if (issueTabSheet != null)
            issueTree.addItemClickListener(new TreeItemClickListener(issueTabSheet));
        //fillTree();
        issueTree.setItemCaptionPropertyId(TreeContainerPlanningUnits.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        addComponent(issueTree);
    }

//    private void fillTree() {
//        issueTree.addContainerProperty(CAPTION_PROPERTY, String.class, null);
//        issueTree.setItemCaptionPropertyId(CAPTION_PROPERTY);
//
//        Item itemParent = null;
//        Item item = null;
//        for (PlanningUnitGroup planningUnitGroup : DummyProjectData.getPlannungUnitGroups()) {
//            itemParent = issueTree.addItem(planningUnitGroup);
//            itemParent.getItemProperty(CAPTION_PROPERTY).setValue(planningUnitGroup.getPlanningUnitGroupName());
//            if (planningUnitGroup.getPlanningUnitList().isEmpty()) {
//                issueTree.setChildrenAllowed(planningUnitGroup, false);
//            } else {
//                issueTree.setChildrenAllowed(planningUnitGroup, true);
//                for (PlanningUnit planningUnit : planningUnitGroup.getPlanningUnitList()) {
//                    item = issueTree.addItem(planningUnit);
//                    item.getItemProperty(CAPTION_PROPERTY).setValue(planningUnit.getPlanningUnitName());
//                    issueTree.setParent(planningUnit, planningUnitGroup);
//                    if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
//                        issueTree.setChildrenAllowed(planningUnit, false);
//                    } else {
//                        issueTree.setChildrenAllowed(planningUnit, true);
//                        iteratePlanningUnits(planningUnit);
//                    }
//                }
//            }
//            issueTree.expandItemsRecursively(planningUnitGroup);
//        }
//
//
//    }
//
//    private void iteratePlanningUnits(PlanningUnit parentPlanningUnit) {
//
//        Item item;
//        for (PlanningUnit planningUnit : parentPlanningUnit.getKindPlanningUnits()) {
//            item = issueTree.addItem(planningUnit);
//            item.getItemProperty(CAPTION_PROPERTY).setValue(planningUnit.getPlanningUnitName());
//            issueTree.setParent(planningUnit, parentPlanningUnit);
//            if (planningUnit.getKindPlanningUnits() == null || planningUnit.getKindPlanningUnits().isEmpty()) {
//                issueTree.setChildrenAllowed(planningUnit, false);
//            } else {
//                issueTree.setChildrenAllowed(planningUnit, true);
//                iteratePlanningUnits(planningUnit);
//            }
//        }
//    }
}
