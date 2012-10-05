package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.PlanningUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class TreeItemClickListener implements ItemClickEvent.ItemClickListener {

    private final IssueTabSheet issueTabSheet;

    public TreeItemClickListener(final IssueTabSheet issueTabSheet) {
        this.issueTabSheet = issueTabSheet;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        final Tree tree = (Tree) event.getSource();
        Object itemId = event.getItemId();
        Item item;

        //item = tree.getItem(itemId);

//            List<IssueBase> issueList = new ArrayList<>();
//            for (PlanningUnit planningUnit : DummyProjectData.getPlannungUnitGroups().get(0).getPlanningUnitList()) {
//                issueList.add(planningUnit.getIssueBase());
//            }


        //issueList.get(3);

        PlanningUnit punit = (PlanningUnit)tree.getContainerProperty(itemId, TreeContainerPlanningUnits.PROPERTY_PLANNINGUNIT).getValue();
        issueTabSheet.getDetailsLayout().setPropertiesFromIssue(punit.getIssueBase());

        List<IssueBase> issues = new ArrayList<>();

        if (!tree.hasChildren(itemId)) {
            issueTabSheet.disableTableTab(true);


        }
        else {
            issueTabSheet.disableTableTab(false);
            for (PlanningUnit childUnit : punit.getKindPlanningUnits()) {
                issues.add(childUnit.getIssueBase());
            }
            issueTabSheet.getTableLayout().setPropertiesFromIssueList(issues);

        }
    }
}
