package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Tree;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsPanel;
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

        if (!tree.hasChildren(event.getItemId())) {
            issueTabSheet.disableTableTab(true);

            List<IssueBase> issueList = new ArrayList<>();
            for (PlanningUnit planningUnit : DummyProjectData.getPlannungUnitGroups().get(0).getPlanningUnitList()) {
                issueList.add(planningUnit.getIssueBase());
            }

            IssueDetailsPanel det = issueTabSheet.getDetailsPanel();
            IssueBase issue = issueList.get(3);

            det.setPropertiesFromIssue(issue);
        }
        else {
            issueTabSheet.disableTableTab(false);
        }
    }
}
