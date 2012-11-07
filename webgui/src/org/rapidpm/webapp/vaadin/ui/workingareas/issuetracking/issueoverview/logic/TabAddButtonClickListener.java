package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 06.11.12
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class TabAddButtonClickListener implements Button.ClickListener {

    private final IssueOverviewScreen screen;
    private final TabSheet tabSheet;

    public TabAddButtonClickListener(final IssueOverviewScreen screen, final TabSheet tabSheet) {
        this.screen = screen;
        this.tabSheet = tabSheet;
    }
    @Override
    public void buttonClick(Button.ClickEvent event) {

    }
}
