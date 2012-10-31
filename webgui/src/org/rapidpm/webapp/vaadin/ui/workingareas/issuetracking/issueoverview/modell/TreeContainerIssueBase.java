package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.HierarchicalContainer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.10.12
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeContainerIssueBase extends HierarchicalContainer {
    private static Logger logger = Logger.getLogger(TreeContainerIssueBase.class);

    public final static String PROPERTY_CAPTION = "caption";
    public final static String PROPERTY_ISSUEBASE = "issueBase";
    private final PlannedProject currentProject;

    public TreeContainerIssueBase(final PlannedProject currentProject) {
        this.currentProject = currentProject;
        this.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        this.addContainerProperty(PROPERTY_ISSUEBASE, IssueBase.class, null);
        filltree();
    }

    private void filltree() {
        Object itemId;
        for (IssueBase issue : GraphDaoFactory.getIssueBaseDAO(currentProject.getId()).loadTopLevelEntities()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(issue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(issue);
            if (issue.getSubIssues() == null || issue.getSubIssues().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(issue, itemId);
            }
        }
    }

    private void iterateSubIssues(IssueBase parentIssue, Object parentItemId) {

        Object itemId;
        for (IssueBase subIssue : parentIssue.getSubIssues()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(subIssue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(subIssue);
            this.setParent(itemId, parentItemId);
            if (subIssue.getSubIssues() == null || subIssue.getSubIssues().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(subIssue, itemId);
            }
        }
    }

    @Override
    public boolean removeItem(Object itemId) {
        boolean success = true;
        Object parentItem = this.getParent(itemId);
        IssueBase issue = (IssueBase)this.getContainerProperty(itemId, TreeContainerIssueBase.PROPERTY_ISSUEBASE)
                .getValue();

        if (this.hasChildren(itemId)) {
            List<Object> children = new ArrayList<>(this.getChildren(itemId));
            for (Object childItem : children) {
                setParent(childItem, parentItem);
            }
        }

        if (!this.hasChildren(parentItem))
            this.setChildrenAllowed(parentItem, false);

        if (!GraphDaoFactory.getIssueBaseDAO(currentProject.getId()).delete(issue))
            success = false;

        if (!super.removeItem(itemId))
            success = false;

        return success;
    }
}
