package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class DetailsSaveButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(DetailsSaveButtonClickListener.class);

    private final IssueOverviewScreen screen;
    private final IssueDetailsLayout detailsLayout;

    public DetailsSaveButtonClickListener(final IssueOverviewScreen screen, final IssueDetailsLayout detailsLayout) {
        this.screen = screen;
        this.detailsLayout = detailsLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final IssueBase issueBase = detailsLayout.getCurrentIssue();
        final IssueBaseDAO dao = DaoFactorySingelton.getInstance().getIssueBaseDAO(issueBase.getProjectid());
        if (!dao.existInDatabase(issueBase.getId()))
            Notification.show("Issue has been deleted", Notification.Type.WARNING_MESSAGE);
        else {
            final IssueBase issue = detailsLayout.setIssueProperties(false);
            if (issue != null) {
                detailsLayout.setDetailsFromIssue(issue);
                detailsLayout.setLayoutReadOnly(true);
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Couldn't save Issue");
            }
        }
    }
}
