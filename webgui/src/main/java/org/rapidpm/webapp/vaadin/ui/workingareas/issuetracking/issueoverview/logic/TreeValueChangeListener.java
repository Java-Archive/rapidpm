package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Property;
import com.vaadin.ui.Tree;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
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
        if (issueTabSheet == null)
            throw new NullPointerException("TabSheet is null.");
        if (issueTree == null)
            throw new NullPointerException("Tree is null.");

        this.issueTabSheet = issueTabSheet;
        this.issueTree = issueTree;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        if (event.getProperty().getValue() != null)
            changeDetails(event.getProperty().getValue());
        else {
            if (logger.isDebugEnabled())
                logger.debug("Value to change was null");
            issueTabSheet.setAllTabsEnabled(false);
        }
    }

    private void changeDetails(final Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");

        final IssueBase issueBase = (IssueBase)issueTree.getContainerDataSource().getContainerProperty(itemId,
                TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
        if (issueBase != null) {
            issueTabSheet.getDetailsLayout().setDetailsFromIssue(issueBase);

            if (!issueTree.hasChildren(itemId)) {
                issueTabSheet.setTableTabOnlyEnabled(false);
            } else {
                final List<IssueBase> issues = new ArrayList<>();
                issueTabSheet.setTableTabOnlyEnabled(true);
                for (final IssueBase childissue : issueBase.getSubIssues()) {
                    issues.add(childissue);
                }
                issueTabSheet.getTableLayout().setPropertiesFromIssueList(issues);
            }
        } else {
            logger.error("Chosen issue is null");
        }
    }

}