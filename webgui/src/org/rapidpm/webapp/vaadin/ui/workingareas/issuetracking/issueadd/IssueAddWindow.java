package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueadd;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlanningUnit;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class IssueAddWindow extends Window {
    private final IssueOverviewScreen screen;
    private final Tree issueTree;
    private IssueAddWindow self;

    private IssueDetailsLayout addDetailsLayout;

    public IssueAddWindow(final IssueOverviewScreen screen, final Tree issueTree) {
        self = this;
        this.screen = screen;
        this.issueTree = issueTree;
        setCaption(screen.getMessagesBundle().getString("issue_addwindow"));
        this.setModal(true);
        addDetailsLayout = new IssueDetailsLayout(screen);
        addDetailsLayout.addSaveButtonClickListener(new AddIssueSaveClickListener());
        addDetailsLayout.addCancelButtonClickListener(new AddIssueCancelClickListener());
        setContent(addDetailsLayout);
    }

    private class AddIssueSaveClickListener implements Button.ClickListener {


        @Override
        public void buttonClick(Button.ClickEvent event) {
            Object itemId = issueTree.addItem();
            Object parentItemId = issueTree.getValue();
            PlanningUnit planningUnit = new PlanningUnit();
            PlanningUnit parentPlanningunit = (PlanningUnit)issueTree.getContainerDataSource().getContainerProperty
                    (parentItemId, TreeContainerPlanningUnits.PROPERTY_PLANNINGUNIT).getValue();
            planningUnit.setPlanningUnitName("TEST UNIT WITH ISSUE");
            //planningUnit.setIssueBase(addDetailsLayout.setIssueProperties(true));
            issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeContainerPlanningUnits.PROPERTY_CAPTION).setValue(planningUnit.getPlanningUnitName());
            issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeContainerPlanningUnits.PROPERTY_PLANNINGUNIT).setValue(planningUnit);
            Set<PlanningUnit> childList = parentPlanningunit.getKindPlanningUnits();
            if (childList == null) childList = new HashSet<PlanningUnit>();
            childList.add(planningUnit);
            parentPlanningunit.setKindPlanningUnits(childList);
            issueTree.setChildrenAllowed(parentItemId, true);
            issueTree.setParent(itemId, parentItemId);
            issueTree.setChildrenAllowed(itemId, false);

            self.close();
        }
    }

    private class AddIssueCancelClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
