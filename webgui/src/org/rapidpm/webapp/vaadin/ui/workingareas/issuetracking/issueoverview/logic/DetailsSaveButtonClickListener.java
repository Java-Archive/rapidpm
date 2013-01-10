package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.MissingAttributeException;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.NameAlreadyInUseException;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.exceptions.NoNameException;
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

    private final IssueDetailsLayout detailsLayout;
    private final Screen screen;

    public DetailsSaveButtonClickListener(final Screen screen, final IssueDetailsLayout detailsLayout) {
        if (screen == null)
            throw new NullPointerException("Screen must bot be null");
        if (detailsLayout == null)
            throw new NullPointerException("DetailsLayout must bot be null");

        this.screen = screen;
        this.detailsLayout = detailsLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final IssueBase issueBase = detailsLayout.getCurrentIssue();
        final IssueBaseDAO dao = DaoFactorySingelton.getInstance().getIssueBaseDAO(issueBase.getProjectid());
        if (!dao.existInDatabase(issueBase.getId())) {
            Notification.show(screen.getMessagesBundle().getString("issuetracking_exception_issuedeleted"),
                    Notification.Type.WARNING_MESSAGE);
        } else {
            try {
                IssueBase issue = detailsLayout.setIssueProperties(false);
                if (issue != null) {
                    try {
                        issue = DaoFactorySingelton.getInstance().getIssueBaseDAO(issue.getProjectid()).persist(issue);
                    } catch (IllegalArgumentException e) {
                        throw new NameAlreadyInUseException();
                    }
                    detailsLayout.setDetailsFromIssue(issue);
                    detailsLayout.setLayoutReadOnly(true);
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Couldn't save Issue");
                }
            } catch (MissingAttributeException e) {
                Notification.show(e.getLocalizedMessage(), Notification.Type.WARNING_MESSAGE);
            } catch (NoNameException e) {
                Notification.show(screen.getMessagesBundle().getString("issuetracking_exception_noname"),
                        Notification.Type.WARNING_MESSAGE);
            } catch (NameAlreadyInUseException e) {
                Notification.show(screen.getMessagesBundle().getString("issuetracking_exception_nameinuse"),
                        Notification.Type.WARNING_MESSAGE);
            }
        }
    }
}
