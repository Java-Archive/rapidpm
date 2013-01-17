package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainerSingleton;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.EditSubissuesWindow;

import java.util.ResourceBundle;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreeLayout extends VerticalLayout implements Internationalizationable{
    private static Logger logger = Logger.getLogger(IssueTreeLayout.class);

    private final IssueOverviewScreen screen;
    private final ResourceBundle messageBundle;

    private Button addButton;
    private Button deleteButton;
    private Button subissueButton;
    private Button expandButton;

    private Tree issueTree;

    public IssueTreeLayout(final IssueOverviewScreen screen, final IssueTabSheet issueTabSheet) {
        if (screen == null)
            throw new NullPointerException("Screen must not be null");
        if (issueTabSheet == null)
            throw new NullPointerException("TabSheet must not be null");

        this.screen = screen;
        this.messageBundle = screen.getMessagesBundle();
        this.setSizeFull();
        setComponents(issueTabSheet);
        doInternationalization();
    }

    private void setComponents(final IssueTabSheet issueTabSheet) {
        final VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(new MarginInfo(false, true, false, true));

        final HorizontalLayout horLayout = new HorizontalLayout();
        horLayout.setSizeFull();
        horLayout.setSpacing(true);

        //TODO nur Testweise
        final Button map = new Button("Map Planning to Issues");
        map.setSizeFull();
        buttonLayout.addComponent(map);

        addButton = new Button();
        addButton.setEnabled(true);
        addButton.setSizeFull();
        horLayout.addComponent(addButton);

        deleteButton = new Button();
        deleteButton.setEnabled(false);
        deleteButton.setSizeFull();
        horLayout.addComponent(deleteButton);
        buttonLayout.addComponent(horLayout);

        subissueButton = new Button();
        subissueButton.setSizeFull();
        buttonLayout.addComponent(subissueButton);

        expandButton = new Button();
        expandButton.setEnabled(true);
        expandButton.setSizeFull();
        buttonLayout.addComponent(expandButton);

        addComponent(buttonLayout);

        final RapidPanel treePanel = new RapidPanel();
        treePanel.setStyleName(Reindeer.PANEL_LIGHT);

        final PlannedProject project = screen.getUi().getCurrentProject();

        issueTree = new Tree(project.getProjektName());
        issueTree.setNullSelectionAllowed(false);
        issueTree.setContainerDataSource(TreeIssueBaseContainerSingleton.getInstance(project));
        issueTree.setImmediate(true);
        if (issueTabSheet != null) {
            issueTree.addValueChangeListener(new TreeValueChangeListener(issueTabSheet, issueTree));
        }
        issueTree.addValueChangeListener(new TreeActivateOnValueChangeListener(new Button[]{deleteButton}));

        issueTree.setItemCaptionPropertyId(TreeIssueBaseContainer.PROPERTY_CAPTION);
        for (final Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        if (issueTree.getItemIds().toArray().length > 0) {
            issueTree.select(issueTree.getItemIds().toArray()[0]);
        } else {
            if (logger.isDebugEnabled())
                logger.debug("No items to show");
        }

        treePanel.addComponent(issueTree);
        treePanel.setSizeFull();

        addComponent(treePanel);
        this.setExpandRatio(treePanel, 1F);

        addButton.addClickListener(new AddButtonClickListener(screen, issueTree));
        deleteButton.addClickListener(new DeleteButtonClickListener(screen, issueTree));
        expandButton.addClickListener(new Button.ClickListener() {
            private boolean expanded = true;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (expanded) {
                    for (final Object id : issueTree.rootItemIds())
                        issueTree.collapseItemsRecursively(id);
                    expandButton.setCaption(messageBundle.getString("issuetracking_issue_expand"));
                } else {
                    for (final Object id : issueTree.rootItemIds())
                        issueTree.expandItemsRecursively(id);
                    expandButton.setCaption(messageBundle.getString("issuetracking_issue_collapse"));
                }
                expanded = !expanded;
            }
        });

        subissueButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().addWindow(new EditSubissuesWindow(screen));
            }
        });

        //TODO nur Testweise
        map.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                final PlannedProject project = screen.getUi().getCurrentProject();
                final TreeIssueBaseContainer container;
                container = TreeIssueBaseContainerSingleton.getInstance(project);
                if (container.getItemIds().isEmpty()) {
                    final boolean mapped = new MappingPlanningUnitToIssueBase(project).startMapping();
                    if (mapped) {
                        container.refresh();
                    } else {
                        if (logger.isDebugEnabled())
                            logger.debug("No PlanningUnits present fpr mapping");

                        Notification.show(messageBundle.getString("issuetracking_error_noplanningunits"),
                                Notification.Type.HUMANIZED_MESSAGE);
                    }

                } else {
                    logger.error("Tree has already elements");
                    Notification.show(messageBundle.getString("issuetracking_error_treehaselements"),
                            Notification.Type.ERROR_MESSAGE);
                }

            }
        });
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(messageBundle.getString("add"));
        deleteButton.setCaption(messageBundle.getString("delete"));
        expandButton.setCaption(messageBundle.getString("issuetracking_issue_collapse"));
        subissueButton.setCaption(messageBundle.getString("issuetracking_issue_dragdrop_edit"));
    }

    public void setSelectedItemByIssue(IssueBase issue) {
        for (final Object itemId : issueTree.getItemIds()) {
            final IssueBase treeIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
            if (treeIssue.equals(issue)){
                issueTree.select(itemId);
                break;
            }
        }
    }

}
