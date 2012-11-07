package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import com.vaadin.data.util.IndexedContainer;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 09:03
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractIssueDataContainer extends IndexedContainer {

    private final IssueOverviewScreen screen;
    private List<Object> visibleColumns;

    public AbstractIssueDataContainer(final IssueOverviewScreen screen) {
        this.screen = screen;
        visibleColumns = setVisibleColumns();
    }

    protected abstract List<Object> setVisibleColumns();
    public abstract void fillContainer(final IssueBase issue);
    public abstract IssueBase getIssueFromItemId(Object itemId);

    public List<Object> getVisibleColumns() {
        return visibleColumns;
    }


}
