package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.issueadd.IssueAddWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public class AddButtonClickListener implements Button.ClickListener {

    private final Tree issueTree;
    private final IssueOverviewScreen screen;

    public AddButtonClickListener(final IssueOverviewScreen screen, final Tree issueTree) {
        this.screen = screen;
        this.issueTree = issueTree;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        UI.getCurrent().addWindow(new IssueAddWindow(screen, issueTree));
    }
}
