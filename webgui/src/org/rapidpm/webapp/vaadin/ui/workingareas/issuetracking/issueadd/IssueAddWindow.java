package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueadd;

import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Window;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 11:19
 * To change this template use File | Settings | File Templates.
 */
public class IssueAddWindow extends Window {
    private final IssueOverviewScreen screen;

    private IssueDetailsLayout addDetailsLayout;

    public IssueAddWindow(IssueOverviewScreen screen) {
        this.screen = screen;
        setCaption(screen.getMessagesBundle().getString("issue_addwindow"));
        this.setModal(true);
        addDetailsLayout = new IssueDetailsLayout(screen);
        this.addComponent(addDetailsLayout);
    }
}
