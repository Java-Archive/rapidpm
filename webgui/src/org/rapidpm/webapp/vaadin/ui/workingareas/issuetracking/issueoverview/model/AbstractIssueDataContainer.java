package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model;

import com.vaadin.data.util.IndexedContainer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 07.11.12
 * Time: 09:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIssueDataContainer extends IndexedContainer {
    protected static Logger logger = Logger.getLogger(AbstractIssueDataContainer.class);

    private final IssueOverviewScreen screen;
    private final List<Object> visibleColumns;
    private IssueBase currentIssue;

    public AbstractIssueDataContainer(final IssueOverviewScreen screen) {
        if (screen == null)
            throw new NullPointerException("Screen must not nbe null");

        this.screen = screen;
        visibleColumns = setVisibleColumns();
    }

    protected abstract List<Object> setVisibleColumns();
    public abstract void fillContainer(final IssueBase issue);

    public IssueBase getCurrentIssue() {
        return currentIssue;
    }

    protected void setCurrentIssue(final IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Issue to set must not be null");

        currentIssue = issue;
    }

    public boolean refresh() {
        boolean success = false;
        if (currentIssue != null) {
            fillContainer(currentIssue);
            success = true;
        } else
            logger.error("CurrentIssue is null. Cant refresh.");

        return success;
    }

    public List<Object> getVisibleColumns() {
        return visibleColumns;
    }
}
