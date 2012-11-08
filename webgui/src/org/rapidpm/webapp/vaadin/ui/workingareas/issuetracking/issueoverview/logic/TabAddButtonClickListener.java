package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddRelationWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.IssueAddWindow;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 06.11.12
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class TabAddButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(TabAddButtonClickListener.class);

    private final IssueOverviewScreen screen;
    private final IssueBase issue;
    private final Table table;

    public TabAddButtonClickListener(final IssueOverviewScreen screen, final IssueBase issue, final Table table) {
        this.screen = screen;
        this.issue = issue;
        this.table = table;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        UI.getCurrent().addWindow(new AddRelationWindow(screen, issue, table));
    }
}
