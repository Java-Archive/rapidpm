package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings;

import com.vaadin.ui.GridLayout;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issuesettings.uicomponents.SettingLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 02.11.12
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
public class IssueSettingsScreen  extends Screen {
    private static Logger logger = Logger.getLogger(IssueSettingsScreen.class);

    private GridLayout gridLayout;

    public IssueSettingsScreen(final MainUI ui) {
        super(ui);
        setSizeFull();
        setComponents();
        doInternationalization();
    }


    @Override
    public void setComponents() {
        gridLayout = new GridLayout(2,3);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);

        gridLayout.addComponent(new SettingLayout<IssueStatus>(this, getMessagesBundle().getString
                ("issuetracking_issue_status"), IssueStatus.class, false));
        gridLayout.addComponent(new SettingLayout<IssuePriority>(this, getMessagesBundle().getString
                ("issuetracking_issue_priority"), IssuePriority.class, false));
        gridLayout.addComponent(new SettingLayout<IssueType>(this, getMessagesBundle().getString
                ("issuetracking_issue_type"), IssueType.class, false));
        gridLayout.addComponent(new SettingLayout<IssueComponent>(this, getMessagesBundle().getString
                ("issuetracking_issue_components"), IssueComponent.class, true));
        gridLayout.addComponent(new SettingLayout<IssueVersion>(this, getMessagesBundle().getString
                ("issuetracking_issue_version"), IssueVersion.class, false));
        gridLayout.addComponent(new SettingLayout<IssueStoryPoint>(this, getMessagesBundle().getString
                ("issuetracking_issue_storypoints"), IssueStoryPoint.class, false));
        gridLayout.addComponent(new SettingLayout<IssueRelation>(this, getMessagesBundle().getString
                ("issuetracking_issue_relations"), IssueRelation.class, true));
        addComponent(gridLayout);
    }

    @Override
    public void doInternationalization() {

    }
}
