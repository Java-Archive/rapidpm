package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import com.vaadin.data.util.HierarchicalContainer;
import org.apache.log4j.Logger;
//import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;

import java.text.Collator;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.10.12
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class TreeIssueBaseContainer extends HierarchicalContainer {
    private static Logger logger = Logger.getLogger(TreeIssueBaseContainer.class);

    public final static String PROPERTY_CAPTION = "caption";
    public final static String PROPERTY_ISSUEBASE = "issueBase";
    private final PlannedProject currentProject;
    private final DaoFactory daoFactory;
    private final ComparatorIssues comparator;

    public TreeIssueBaseContainer(final PlannedProject currentProject) {
        if (currentProject == null)
            throw new NullPointerException("Project must not be null");

        this.daoFactory = DaoFactorySingelton.getInstance();
        this.currentProject = currentProject;
        this.comparator = new ComparatorIssues();
        this.addContainerProperty(PROPERTY_CAPTION, String.class, null);
        this.addContainerProperty(PROPERTY_ISSUEBASE, IssueBase.class, null);
        filltree();
    }

    private void filltree() {
        final List<IssueBase> topLevelIssues = daoFactory.getIssueBaseDAO(currentProject.getId()).loadTopLevelEntities();
        Collections.sort(topLevelIssues, comparator);
        for (final IssueBase issue : topLevelIssues) {
            final Object itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(issue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(issue);
            this.setParent(itemId, null);
            final List<IssueBase> subIssueList = issue.getSubIssues();
            Collections.sort(subIssueList, comparator);
            if (subIssueList == null || subIssueList.isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(subIssueList, itemId);
            }
        }
    }

    private void iterateSubIssues(final List<IssueBase> parentSubIssueList, final Object parentItemId) {
        for (final IssueBase subIssue : parentSubIssueList) {
            final Object itemId = addItem();
            this.getContainerProperty(itemId, PROPERTY_CAPTION).setValue(subIssue.name());
            this.getContainerProperty(itemId, PROPERTY_ISSUEBASE).setValue(subIssue);
            this.setParent(itemId, parentItemId);
            final List<IssueBase> subIssueList = subIssue.getSubIssues();
            Collections.sort(subIssueList, comparator);
            if (subIssueList == null || subIssueList.isEmpty()) {
                this.setChildrenAllowed(itemId, false);
            } else {
                this.setChildrenAllowed(itemId, true);
                iterateSubIssues(subIssueList, itemId);
            }
        }
    }


    @Override
    public boolean removeItem(final Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");

        boolean success = false;
        final Object parentItem = this.getParent(itemId);
        final IssueBase issue = (IssueBase)this.getContainerProperty(itemId, PROPERTY_ISSUEBASE)
                .getValue();

        if (this.hasChildren(itemId)) {
            final List<Object> children = new ArrayList<>(this.getChildren(itemId));
            for (final Object childItem : children) {
                setParent(childItem, parentItem);
            }
        }

        if (daoFactory.getIssueBaseDAO(currentProject.getId()).delete(issue))
            if (super.removeItem(itemId)) {
                success = true;
                if (!this.hasChildren(parentItem))
                    this.setChildrenAllowed(parentItem, false);
            }

        return success;
    }



    @Override
    public boolean removeItemRecursively(final Object itemId) {
        boolean success;
        //TODO Bei Fehler ausgangszustand wiederherstellen.
        success = removeRecusively(itemId);
        return success;
    }


    private boolean removeRecusively(final Object itemId) {
        if (itemId == null)
            throw new NullPointerException("ItemId must not be null");

        if (this.hasChildren(itemId)) {
            final Object[] children = this.getChildren(itemId).toArray();
            for (final Object child : children) {
                boolean success = removeRecusively(child);
                if (!success)
                    return false;
            }
        }
        return removeItem(itemId);
    }

    public void refresh() {
        removeAllItems();
        filltree();
    }

//     public boolean containsIssue(IssueBase issue) {
//        for (Object itemId : this.getItemIds()) {
//            if (issue.equals(this.getContainerProperty(itemId, PROPERTY_ISSUEBASE)))
//                return true;
//        }
//        return false;
//     }

    private class ComparatorIssues implements Comparator<IssueBase> {

        @Override
        public int compare(final IssueBase o1, final IssueBase o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    }

}
