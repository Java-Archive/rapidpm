package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class TreeItemClickValueChangeListener implements ItemClickEvent.ItemClickListener, Tree.ValueChangeListener {

    private final IssueTabSheet issueTabSheet;
    private final Tree issueTree;

    public TreeItemClickValueChangeListener(final IssueTabSheet issueTabSheet, final Tree issueTree) {
        this.issueTabSheet = issueTabSheet;
        this.issueTree = issueTree;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        changeDetails(event.getItemId());
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if (event.getProperty().getValue() != null)
            changeDetails(event.getProperty().getValue());
    }

    private void changeDetails(Object itemId) {
        PlanningUnit punit = (PlanningUnit)issueTree.getContainerProperty(itemId,
                TreeContainerPlanningUnits.PROPERTY_PLANNINGUNIT).getValue();
        //issueTabSheet.getDetailsLayout().setPropertiesFromIssue(punit.getIssueBase());

        if (!issueTree.hasChildren(itemId)) {
            issueTabSheet.disableTableTab(true);
        }
        else {
            List<IssueBase> issues = new ArrayList<>();
            issueTabSheet.disableTableTab(false);
//            for (PlanningUnit childUnit : punit.getKindPlanningUnits()) {
//                issues.add(childUnit.getIssueBase());
//            }
            issueTabSheet.getTableLayout().setPropertiesFromIssueList(issues);
        }
    }

}