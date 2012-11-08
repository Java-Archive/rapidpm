package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell;

import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 09:44
 * To change this template use File | Settings | File Templates.
 */
public class CommentsDataContainer extends AbstractIssueDataContainer {


    private final static String TEXT = "text";
    private final static String CREATOR = "creator";
    private final static String COMMENT = "comment";

    public CommentsDataContainer(final IssueOverviewScreen screen) {
        super(screen);
    }

    @Override
    protected List<Object> setVisibleColumns() {
        List<Object> visibleColumns = new ArrayList<>();

        return visibleColumns;
    }

    @Override
    public void fillContainer(IssueBase issue) {

    }

    @Override
    public IssueBase getConnIssueFromItemId(Object itemId) {
        return null;
    }
}
