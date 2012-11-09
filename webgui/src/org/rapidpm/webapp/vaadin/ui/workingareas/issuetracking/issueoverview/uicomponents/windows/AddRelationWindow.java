package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.GraphDaoFactory;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.IssueRelation;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.AbstractIssueDataContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.RelationsDataContainer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin
 * Date: 07.11.12
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class AddRelationWindow extends Window implements Internationalizationable {
    private static Logger logger = Logger.getLogger(AddRelationWindow.class);

    private final static String PROPERTY_NAME = "name";
    private final IssueOverviewScreen screen;
    private final RelationsDataContainer relationContainer;
    private final AddRelationWindow self;

    private ComboBox relationsSelect;
    private ComboBox issueSelect;
    private Button saveButton;
    private Button cancelButton;




    public AddRelationWindow(final IssueOverviewScreen screen, final AbstractIssueDataContainer relationContainer){
        super();
        self = this;
        this.screen = screen;
        this.relationContainer = (RelationsDataContainer)relationContainer;
        this.setModal(true);
        setComponents();
        doInternationalization();
    }

    private void setComponents() {
        final VerticalLayout baseLayout = new VerticalLayout();
        baseLayout.setSizeFull();
        baseLayout.setSpacing(true);

        final List<IssueRelation> relationList = GraphDaoFactory.getIssueRelationDAO().loadAllEntities();
        relationsSelect = new ComboBox();
        relationsSelect.setWidth("100%");
        relationsSelect.addContainerProperty(PROPERTY_NAME, String.class, null);
        relationsSelect.setItemCaptionPropertyId(PROPERTY_NAME);
        for (final IssueRelation relation : relationList) {
            Item item = relationsSelect.addItem(relation);
            item.getItemProperty(PROPERTY_NAME).setValue(relation.getRelationName());
        }
        relationsSelect.setNullSelectionAllowed(false);
        relationsSelect.setScrollToSelectedItem(true);
        relationsSelect.setFilteringMode(FilteringMode.STARTSWITH);
        baseLayout.addComponent(relationsSelect);

        final List<IssueBase> issueList = GraphDaoFactory.getIssueBaseDAO(relationContainer.getCurrentIssue().getProjectId())
        .loadAllEntities();
        issueSelect = new ComboBox();
        issueSelect.setWidth("100%");
        issueSelect.addContainerProperty(PROPERTY_NAME, String.class, null);
        issueSelect.setItemCaptionPropertyId(PROPERTY_NAME);
        for (final IssueBase connIssue : issueList) {
            Item item = issueSelect.addItem(connIssue);
            item.getItemProperty(PROPERTY_NAME).setValue(connIssue.name());
        }
        issueSelect.setNullSelectionAllowed(false);
        issueSelect.setScrollToSelectedItem(true);
        issueSelect.setFilteringMode(FilteringMode.STARTSWITH);
        baseLayout.addComponent(issueSelect);

        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("100%");

        saveButton = new Button();
        saveButton.addClickListener(new SaveButtonClickListener());
        cancelButton = new Button();
        cancelButton.addClickListener(new CancelButtonClickListener());

        buttonLayout.addComponent(saveButton);
        buttonLayout.addComponent(cancelButton);

        baseLayout.addComponent(buttonLayout);
        addComponent(baseLayout);
    }

    @Override
    public void doInternationalization() {
        this.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_addrelationswindow"));
        relationsSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_relations"));
        issueSelect.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_table"));
        saveButton.setCaption(screen.getMessagesBundle().getString("save"));
        cancelButton.setCaption(screen.getMessagesBundle().getString("cancel"));
    }


    private class SaveButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            relationsSelect.setRequired(false);
            issueSelect.setRequired(false);

            final Object relation = relationsSelect.getValue();
            final Object connIssue = issueSelect.getValue();

            if (relation != null && relation != "") {
                if (connIssue != null && connIssue != "")  {
                    if (!relationContainer.addRelation((IssueBase) connIssue, (IssueRelation)relation))
                        //TODO Show Errormessage to User
                        logger.error("Connecting issues failed");
                    self.close();
                } else {
                    if (logger.isDebugEnabled())
                        logger.debug("Issue to connect to is needed");
                    issueSelect.setRequired(true);
                    issueSelect.setRequiredError("Issue to connect to is needed");
                }
            } else {
                if (logger.isDebugEnabled())
                    logger.debug("Relations to connect issues is needed");
                relationsSelect.setRequired(true);
                relationsSelect.setRequiredError("Relations to connect issues is needed");
            }

        }
    }

    private class CancelButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            self.close();
        }
    }
}
