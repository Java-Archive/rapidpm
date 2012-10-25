package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.HierarchicalContainer;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.10.12
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeContainerIssueBase extends HierarchicalContainer {

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
        for (IssueBase issuebase : GraphDaoFactory.getIssueBaseDAO(currentProject.getId()).loadTopLevelEntities()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(issuebase.getSummary());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(issuebase);
            if (issuebase.getSubIssues() == null || issuebase.getSubIssues().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(issuebase, itemId);
            }
        }
    }

    private void iterateSubIssues(IssueBase parentIssue, Object parentItemId) {

        Object itemId;
        for (IssueBase issueBase : parentIssue.getSubIssues()) {
            itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(issueBase.getSummary());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(issueBase);
            this.setParent(itemId, parentItemId);
            if (issueBase.getSubIssues() == null || issueBase.getSubIssues().isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(issueBase, itemId);
            }
        }
    }
}
