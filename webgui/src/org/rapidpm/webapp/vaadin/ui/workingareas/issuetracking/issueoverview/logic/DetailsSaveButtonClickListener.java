package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import org.apache.log4j.Logger;
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

    public DetailsSaveButtonClickListener(final IssueDetailsLayout detailsLayout) {
        this.detailsLayout = detailsLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        detailsLayout.setLayoutReadOnly(true);
        detailsLayout.setDetailsFromIssue(detailsLayout.setIssueProperties(false));
    }
}
