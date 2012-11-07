package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
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

    public TabAddButtonClickListener(final IssueOverviewScreen screen, final IssueBase issue) {
        this.screen = screen;
        this.issue = issue;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        UI.getCurrent().addWindow(new AddRelationWindow(screen, issue));
    }
}
