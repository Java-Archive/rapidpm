package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Tree;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeIssueBaseContainer;
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
public class TreeValueChangeListener implements Tree.ValueChangeListener {
    private static Logger logger = Logger.getLogger(TreeValueChangeListener.class);

    private final IssueTabSheet issueTabSheet;
    private final Tree issueTree;

    public TreeValueChangeListener(final IssueTabSheet issueTabSheet, final Tree issueTree) {
        this.issueTabSheet = issueTabSheet;
        this.issueTree = issueTree;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if (event.getProperty().getValue() != null)
            changeDetails(event.getProperty().getValue());
        else
            if (logger.isDebugEnabled())
                logger.debug("Property of values was null");
    }

    private void changeDetails(Object itemId) {
        IssueBase issueBase = (IssueBase)issueTree.getContainerDataSource().getContainerProperty(itemId,
                TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
        issueTabSheet.getDetailsLayout().setDetailsFromIssue(issueBase);

        if (!issueTree.hasChildren(itemId)) {
            issueTabSheet.disableTableTab(true);
        } else {
            List<IssueBase> issues = new ArrayList<>();
            issueTabSheet.disableTableTab(false);
            for (IssueBase childissue : issueBase.getSubIssues()) {
                issues.add(childissue);
            }
            issueTabSheet.getTableLayout().setPropertiesFromIssueList(issues);
        }
    }

}