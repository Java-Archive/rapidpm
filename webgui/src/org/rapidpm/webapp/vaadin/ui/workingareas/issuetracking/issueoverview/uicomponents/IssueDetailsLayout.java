package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
//import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriority;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssuePriorityDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatus;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueStatusDAO;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.persistence.system.security.BenutzerDAO;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditableVLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;
//import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.components.details.PlanningDetailsFieldGroupBean;

//import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsLayout extends ComponentEditableVLayout implements Internationalizationable{

    private TextField headerTextField;
    private ComboBox typeSelect;
    private ComboBox statusSelect;
    private ComboBox prioritySelect;
    private ComboBox assigneeSelect;
    private Label reporterLabel;
    private DateField plannedDateField;
    private DateField resolvedDateField;
    private DateField closedDateField;
    private ComboBox storyPointSelect;
    private ComboBox versionSelect;

    private TextArea descriptionTextArea;
    private TabSheet tabSheet;
    private TabSheet.Tab tabComments;
    private TabSheet.Tab tabTestcases;
    private TabSheet.Tab tabStory;

    private VerticalLayout componentsLayout;

    private IssueBase issue;


    public IssueDetailsLayout(IssueOverviewScreen screen) {
        super(screen);
        doInternationalization();
    }


    @Override
    protected AbstractOrderedLayout buildForm() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        final IssueStatusDAO issueStatusDAO = daoFactory.getIssueStatusDAO();
        final List<IssueStatus> statusList = issueStatusDAO.loadAllEntities();
        final IssuePriorityDAO issuePriorityDAO = daoFactory.getIssuePriorityDAO();
        final List<IssuePriority> priorityList =  issuePriorityDAO.loadAllEntities();
        final BenutzerDAO benutzerDAO = daoFactory.getBenutzerDAO();
        final List<Benutzer> userList =  benutzerDAO.loadAllEntities();
        componentsLayout = new VerticalLayout();

        FormLayout dateLayout = new FormLayout();
        FormLayout detailLayout = new FormLayout();
        HorizontalLayout horLayout = new HorizontalLayout();



        headerTextField = new TextField();
        headerTextField.setWidth("100%");
        headerTextField.setReadOnly(true);
        componentsLayout.addComponent(headerTextField);

        typeSelect = new ComboBox();
        typeSelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        typeSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        Item item;
        for (String type : DummyProjectData.getTypeList()) {
            item = typeSelect.addItem(type);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(type);
        }
        typeSelect.setTextInputAllowed(false);
        typeSelect.setNullSelectionAllowed(false);
        typeSelect.setReadOnly(true);
        detailLayout.addComponent(typeSelect);

        statusSelect = new ComboBox();
        statusSelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        statusSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        for (IssueStatus status : statusList) {
            item = statusSelect.addItem(status);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(status.getStatusName());
            statusSelect.setItemIcon(status, new ThemeResource("images/" + status.getStatusFileName()));
        }
        statusSelect.setTextInputAllowed(false);
        statusSelect.setNullSelectionAllowed(false);
        statusSelect.setReadOnly(true);
        detailLayout.addComponent(statusSelect);

        prioritySelect = new ComboBox();
        prioritySelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        prioritySelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        for (IssuePriority priority : priorityList) {
            item = prioritySelect.addItem(priority);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(priority.getPriorityName());
            prioritySelect.setItemIcon(priority, new ThemeResource("images/" + priority.getPriorityFileName()));
        }
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        detailLayout.addComponent(prioritySelect);

        reporterLabel = new Label("sven.ruppert");
        reporterLabel.setValue("sven.ruppert");
        detailLayout.addComponent(reporterLabel);

        assigneeSelect = new ComboBox();
        assigneeSelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        assigneeSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        for (Benutzer user : userList) {
            item = assigneeSelect.addItem(user);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(user.getLogin());
        }
        assigneeSelect.select(userList.get(0));
        assigneeSelect.setTextInputAllowed(false);
        assigneeSelect.setNullSelectionAllowed(false);
        assigneeSelect.setReadOnly(true);
        detailLayout.addComponent(assigneeSelect);

        plannedDateField = new DateField();
        plannedDateField.setResolution(Resolution.DAY);
        plannedDateField.setValue(new Date());
        plannedDateField.setReadOnly(true);
        dateLayout.addComponent(plannedDateField);

        resolvedDateField = new DateField();
        resolvedDateField.setResolution(Resolution.DAY);
        resolvedDateField.setValue(new Date());
        resolvedDateField.setReadOnly(true);
        dateLayout.addComponent(resolvedDateField);

        closedDateField = new DateField();
        closedDateField.setResolution(Resolution.DAY);
        closedDateField.setValue(new Date());
        closedDateField.setReadOnly(true);
        dateLayout.addComponent(closedDateField);

        storyPointSelect = new ComboBox();
        for (Integer storyPoint : DummyProjectData.getStoryPointArray()) {
            storyPointSelect.addItem(storyPoint);
        }
        storyPointSelect.setTextInputAllowed(false);
        storyPointSelect.setNullSelectionAllowed(false);
        storyPointSelect.setReadOnly(true);
        dateLayout.addComponent(storyPointSelect);

        versionSelect = new ComboBox();
        for (String version : DummyProjectData.getVersionArray()) {
            versionSelect.addItem(version);
        }
        versionSelect.setTextInputAllowed(false);
        versionSelect.setNullSelectionAllowed(true);
        versionSelect.setReadOnly(true);
        dateLayout.addComponent(versionSelect);

        horLayout.addComponent(detailLayout);
        horLayout.addComponent(dateLayout);
        componentsLayout.addComponent(horLayout);

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setReadOnly(true);
        componentsLayout.addComponent(descriptionTextArea);

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        VerticalLayout commentsLayout = new VerticalLayout();
        commentsLayout.setMargin(true);
        commentsLayout.addComponent(new Label("Fill with Comments"));
        commentsLayout.addComponent(new Label("Fill with Comments"));
        commentsLayout.addComponent(new Label("Fill with Comments"));
        commentsLayout.addComponent(new Label("Fill with Comments"));
        tabComments = tabSheet.addTab(commentsLayout);

        VerticalLayout testcaseLayout = new VerticalLayout();
        testcaseLayout.setMargin(true);
        testcaseLayout.addComponent(new Label("Fill with TestCases"));
        tabTestcases = tabSheet.addTab(testcaseLayout);

        VerticalLayout storyLayout = new VerticalLayout();
        storyLayout.setMargin(true);
        storyLayout.addComponent(new Label("Fill with Story"));
        tabStory = tabSheet.addTab(storyLayout);

        componentsLayout.addComponent(tabSheet);

        return componentsLayout;
    }

    @Override
    public void doInternationalization() {
        typeSelect.setCaption(screen.getMessagesBundle().getString("issue_type"));
        statusSelect.setCaption(screen.getMessagesBundle().getString("issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issue_reporter"));
        plannedDateField.setCaption(screen.getMessagesBundle().getString("issue_planned"));
        resolvedDateField.setCaption(screen.getMessagesBundle().getString("issue_resolved"));
        closedDateField.setCaption(screen.getMessagesBundle().getString("issue_closed"));
        storyPointSelect.setCaption(screen.getMessagesBundle().getString("issue_storypoints"));
        versionSelect.setCaption(screen.getMessagesBundle().getString("issue_version"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issue_description"));
        tabComments.setCaption(screen.getMessagesBundle().getString("issue_comments"));
        tabTestcases.setCaption(screen.getMessagesBundle().getString("issue_testcases"));
        tabStory.setCaption(screen.getMessagesBundle().getString("issue_story"));
    }

    public void setPropertiesFromIssue(IssueBase issue) {
        this.issue = issue;
        setLayoutReadOnly(false);

        headerTextField.setValue(issue.getSummary() + "Hier steht der Name");
        statusSelect.select(issue.getStatus());
        prioritySelect.select(issue.getPriority());
        assigneeSelect.setValue(issue.getAssignee());
        //reporterLabel.setValue(issue.getReporter().getLogin());
        plannedDateField.setValue(issue.getDueDate_planned());
        resolvedDateField.setValue(issue.getDueDate_resolved());
        closedDateField.setValue(issue.getDueDate_closed());
        storyPointSelect.select(issue.getStoryPoints());
        descriptionTextArea.setValue(issue.getText());

        setLayoutReadOnly(true);
    }

    public IssueBase setIssueProperties(boolean newIssue) {
        if (newIssue)
            this.issue = new IssueBase();

        issue.setSummary(headerTextField.getValue());
        issue.setStatus((IssueStatus) statusSelect.getValue());
        issue.setPriority((IssuePriority) prioritySelect.getValue());
        issue.setAssignee((Benutzer) assigneeSelect.getValue());
        //reporterLabel.setValue(issue.getReporter().getLogin());
        issue.setDueDate_planned(plannedDateField.getValue());
        issue.setDueDate_resolved(resolvedDateField.getValue());
        issue.setDueDate_closed(closedDateField.getValue());
        issue.setStoryPoints((Integer) storyPointSelect.getValue());
        issue.setText(descriptionTextArea.getValue());

        return issue;
    }

}
