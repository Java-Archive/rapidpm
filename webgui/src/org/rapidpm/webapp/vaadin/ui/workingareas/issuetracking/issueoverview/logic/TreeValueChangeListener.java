package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic;

import com.vaadin.data.Property;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueDetailsPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.IssueTabSheet;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class TreeValueChangeListener implements Property.ValueChangeListener {

    private IssueTabSheet issueTabSheet;

    public TreeValueChangeListener(IssueTabSheet issueTabSheet) {
        this.issueTabSheet = issueTabSheet;
    }

    @Override
    public void valueChange(Property.ValueChangeEvent event) {
        Object value = event.getProperty().getValue();
        if (value != null) {
//            detailsPanel.getDescriptionTextArea().setReadOnly(false);
//            detailsPanel.getDescriptionTextArea().setValue(value);
//            detailsPanel.getDescriptionTextArea().setReadOnly(true);
//            detailsPanel.getHeaderLabel().setValue(value);
        }
    }
}
