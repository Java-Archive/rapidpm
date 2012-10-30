package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.ui.Button;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 05.10.12
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
public class DetailsCancelButtonClickListener implements Button.ClickListener {

    private final IssueDetailsLayout detailsLayout;

    public DetailsCancelButtonClickListener(final IssueDetailsLayout detailsLayout) {
        this.detailsLayout = detailsLayout;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        detailsLayout.setLayoutReadOnly(true);
        if (detailsLayout.getCurrentIssue() != null)
        detailsLayout.setDetailsFromIssue(detailsLayout.getCurrentIssue());
    }
}
