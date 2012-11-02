package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings;

import com.vaadin.ui.GridLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueComponent;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueType;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents.AbstractSettingsComponent;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 02.11.12
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
public class IssueSettingsScreen  extends Screen {
    private static Logger logger = Logger.getLogger(IssueSettingsScreen.class);

    private GridLayout gridLayout;

    public IssueSettingsScreen(final MainUI ui) {
        super(ui);
        setComponents();
        doInternationalization();
    }


    @Override
    public void setComponents() {

        gridLayout = new GridLayout(2,3);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        //gridLayout.setMargin(true);

        gridLayout.addComponent(new AbstractSettingsComponent<IssueStatus>(this, "Status", IssueStatus.class));
        gridLayout.addComponent(new AbstractSettingsComponent<IssuePriority>(this, "Prioriry", IssuePriority.class));
        gridLayout.addComponent(new AbstractSettingsComponent<IssueType>(this, "Type", IssueType.class));
        gridLayout.addComponent(new AbstractSettingsComponent<IssueComponent>(this, "Component",
                IssueComponent.class));
        addComponent(gridLayout);
    }

    @Override
    public void doInternationalization() {

    }
}
