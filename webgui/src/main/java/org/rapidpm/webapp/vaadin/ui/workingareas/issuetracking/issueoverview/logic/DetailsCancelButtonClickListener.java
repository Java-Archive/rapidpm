package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBaseDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class DetailsCancelButtonClickListener implements Button.ClickListener {
    private static Logger logger = Logger.getLogger(DetailsCancelButtonClickListener.class);

    private final IssueDetailsLayout detailsLayout;
    private final Screen screen;
    private final ResourceBundle messageBundle;

    public DetailsCancelButtonClickListener(final Screen screen, final IssueDetailsLayout detailsLayout) {
        if (screen == null)
            throw new NullPointerException("Screen must bot be null");
        if (detailsLayout == null)
            throw new NullPointerException("DetailsLayout must bot be null");

        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.detailsLayout = detailsLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        final IssueBase issueBase = detailsLayout.getCurrentIssue();
        final IssueBaseDAO dao = DaoFactorySingleton.getInstance().getIssueBaseDAO();
        if (!dao.existInDatabase(issueBase.getId())) {
            Notification.show(messageBundle.getString("issuetracking_exception_issuedeleted"),
                    Notification.Type.WARNING_MESSAGE);
        } else {
            detailsLayout.setLayoutReadOnly(true);
            if (detailsLayout.getCurrentIssue() != null) {
                detailsLayout.setDetailsFromIssue(detailsLayout.getCurrentIssue());
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("No issue selected to show");
            }
        }
    }
}
