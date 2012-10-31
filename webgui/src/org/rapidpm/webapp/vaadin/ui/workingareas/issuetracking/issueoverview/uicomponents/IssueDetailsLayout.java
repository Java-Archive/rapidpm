package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.*;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.system.security.Benutzer;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditableVLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.DummyProjectData;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 25.09.12
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class IssueDetailsLayout extends ComponentEditableVLayout implements Internationalizationable{
    private static Logger logger = Logger.getLogger(IssueDetailsLayout.class);

    private TextField headerSummaryField;
    private Label headerTextField;
    private VerticalLayout headerLayout;

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
    private ComboBox riskSelect;
    private ListSelect componentListSelect;

    private TextArea descriptionTextArea;
    private TabSheet tabSheet;
    private VerticalLayout tabComments;
    private VerticalLayout tabTestcases;

    private VerticalLayout formLayout;

    private IssueBase issue;


    public IssueDetailsLayout(IssueOverviewScreen screen) {
        super(screen);
        doInternationalization();
    }


    @Override
    protected AbstractOrderedLayout buildForm() {
        final List<IssueType> typeList = GraphDaoFactory.getIssueTypeDAO().loadAllEntities();
        final List<IssueStatus> statusList = GraphDaoFactory.getIssueStatusDAO().loadAllEntities();
        final List<IssuePriority> priorityList =  GraphDaoFactory.getIssuePriorityDAO().loadAllEntities();
        final List<IssueComponent> componentsList = GraphDaoFactory.getIssueComponentDAO().loadAllEntities();
        final List<Benutzer> userList =  screen.getBaseDaoFactoryBean().getBenutzerDAO().loadAllEntities();
        formLayout = new VerticalLayout();

        FormLayout dateLayout = new FormLayout();
        FormLayout detailLayout = new FormLayout();
        HorizontalLayout horLayout = new HorizontalLayout();

        dateLayout.setWidth("100%");
        detailLayout.setWidth("100%");
        horLayout.setWidth("100%");

        headerTextField = new Label();
        headerTextField.setWidth("100%");

        headerSummaryField = new TextField();
        headerSummaryField.setInputPrompt("Enter name");
        headerSummaryField.setWidth("100%");
        headerSummaryField.setReadOnly(true);

        headerLayout = new VerticalLayout();
        headerLayout.addComponent(headerTextField);
        headerLayout.addComponent(headerSummaryField);
        headerLayout.setExpandRatio(headerSummaryField, 1.0F);

        formLayout.addComponent(headerLayout);

        typeSelect = new ComboBox();
        typeSelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        typeSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        Item item;
        for (IssueType type : typeList) {
            item = typeSelect.addItem(type);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(type.getTypeName());
            typeSelect.setItemIcon(type, new ThemeResource("images/" + type.getTypeFileName()));
        }
        typeSelect.select(typeList.get(0));
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
        statusSelect.select(statusList.get(0));
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
        prioritySelect.select(priorityList.get(0));
        prioritySelect.setTextInputAllowed(false);
        prioritySelect.setNullSelectionAllowed(false);
        prioritySelect.setReadOnly(true);
        detailLayout.addComponent(prioritySelect);

        reporterLabel = new Label();
        reporterLabel.setValue(screen.getUi().getCurrentUser().getLogin());
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

        componentListSelect = new ListSelect();
        componentListSelect.addContainerProperty(DummyProjectData.PROPERTY_CAPTION, String.class, null);
        componentListSelect.setItemCaptionPropertyId(DummyProjectData.PROPERTY_CAPTION);
        for (IssueComponent component : componentsList) {
            item = componentListSelect.addItem(component);
            item.getItemProperty(DummyProjectData.PROPERTY_CAPTION).setValue(component.getComponentName());
        }
        componentListSelect.setNullSelectionAllowed(true);
        componentListSelect.setMultiSelect(true);
        componentListSelect.setRows(5);
        componentListSelect.setReadOnly(true);
        detailLayout.addComponent(componentListSelect);


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
        storyPointSelect.select(DummyProjectData.getStoryPointArray().get(0));
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

        riskSelect = new ComboBox();
        for (Integer version : DummyProjectData.getRiskArray()) {
            riskSelect.addItem(version);
        }
        riskSelect.select(DummyProjectData.getRiskArray().get(0));
        riskSelect.setTextInputAllowed(false);
        riskSelect.setNullSelectionAllowed(true);
        riskSelect.setReadOnly(true);
        dateLayout.addComponent(riskSelect);

        horLayout.addComponent(detailLayout);
        horLayout.addComponent(dateLayout);
        formLayout.addComponent(horLayout);

        descriptionTextArea = new TextArea();
        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setReadOnly(true);
        formLayout.addComponent(descriptionTextArea);

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabComments = new VerticalLayout();
        tabComments.setMargin(true);
        tabSheet.addTab(tabComments);

        tabTestcases = new VerticalLayout();
        tabTestcases.setMargin(true);
        tabSheet.addTab(tabTestcases);

        formLayout.addComponent(tabSheet);

        return formLayout;
    }

    @Override
    public void doInternationalization() {
        typeSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_type"));
        statusSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_status"));
        prioritySelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_priority"));
        assigneeSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_assignee"));
        reporterLabel.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_reporter"));
        componentListSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_components"));
        plannedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_planned"));
        resolvedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_resolved"));
        closedDateField.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_closed"));
        storyPointSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_storypoints"));
        versionSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_version"));
        riskSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_risk"));
        descriptionTextArea.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_description"));
        tabSheet.getTab(tabComments).setCaption(screen.getMessagesBundle().getString("issuetracking_issue_comments"));
        tabSheet.getTab(tabTestcases).setCaption(screen.getMessagesBundle().getString("issuetracking_issue_testcases"));
    }

    public void setDetailsFromIssue(IssueBase issue) {
        if (issue == null)
            throw new NullPointerException("Details can't be set from null.");
        this.issue = issue;
        setLayoutReadOnly(false);

        headerTextField.setValue(issue.getText());
        headerSummaryField.setValue(issue.getSummary());
        typeSelect.select(issue.getType());
        statusSelect.select(issue.getStatus());
        prioritySelect.select(issue.getPriority());
        assigneeSelect.setValue(issue.getAssignee());
        reporterLabel.setValue(issue.getReporter().getLogin());
        plannedDateField.setValue(issue.getDueDate_planned());
        resolvedDateField.setValue(issue.getDueDate_resolved());
        closedDateField.setValue(issue.getDueDate_closed());
        storyPointSelect.select(issue.getStoryPoints());
        versionSelect.select(issue.getVersion());
        riskSelect.select(issue.getRisk());
        descriptionTextArea.setValue(issue.getStory());

        for (Object item : componentListSelect.getItemIds())
            componentListSelect.unselect(item);

        for (IssueComponent component : issue.getComponents()) {
            componentListSelect.select(component);
        }

        tabComments.removeAllComponents();
        for (IssueComment comment : issue.getComments()) {
            tabComments.addComponent(new Label(comment.getText()));
        }

        tabTestcases.removeAllComponents();
        for (TestCase testcase : issue.getTestcases()) {
            tabTestcases.addComponent(new Label(testcase.getText()));
        }

        setLayoutReadOnly(true);
    }

    public IssueBase setIssueProperties(boolean newIssue) {
        if (newIssue) {
            this.issue = new IssueBase(screen.getCurrentProject().getId());
            issue.setReporter(screen.getUi().getCurrentUser());
            if (logger.isInfoEnabled())
                    logger.info("Adding new issue");
        } else
            if (logger.isInfoEnabled())
                logger.info("Updating issue");

        if (issue == null) {
            logger.error("Issue to save was null");
            return null;
            //throw new NullPointerException("No Issue to save.");
        }


        if (headerSummaryField.getValue() == "") {
            headerSummaryField.setComponentError(new UserError("Issue must have a name!"));
            logger.warn("Issue must have a name");
            return null;
        }

        issue.setSummary(headerSummaryField.getValue());
        issue.setStatus((IssueStatus) statusSelect.getValue());
        issue.setPriority((IssuePriority) prioritySelect.getValue());
        issue.setType((IssueType) typeSelect.getValue());
        issue.setAssignee((Benutzer) assigneeSelect.getValue());
        issue.setDueDate_planned(plannedDateField.getValue());
        issue.setDueDate_resolved(resolvedDateField.getValue());
        issue.setDueDate_closed(closedDateField.getValue());
        issue.setStoryPoints((Integer) storyPointSelect.getValue());
        issue.setVersion((String) versionSelect.getValue());
        issue.setRisk((Integer) riskSelect.getValue());
        issue.setStory(descriptionTextArea.getValue());

        issue = GraphDaoFactory.getIssueBaseDAO(screen.getCurrentProject().getId()).persist(issue);

        for (IssueComponent component : (Set<IssueComponent>) componentListSelect.getValue())
            issue.addComponent(component);
        return issue;
    }

    public void setRequiredIndicator(boolean set) {
        headerSummaryField.setRequired(set);
    }


    public IssueBase getCurrentIssue() {
        return issue;
    }
}
