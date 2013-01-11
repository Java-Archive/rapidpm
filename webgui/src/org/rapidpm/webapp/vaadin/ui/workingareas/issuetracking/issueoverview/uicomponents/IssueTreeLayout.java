package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.apache.log4j.Logger;
import org.rapidpm.persistence.prj.projectmanagement.execution.issuetracking.type.IssueBase;
import org.rapidpm.webapp.vaadin.ui.RapidPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainerSingleton;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.model.TreeIssueBaseContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.AddIssueWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents.windows.EditSubissuesWindow;


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
        Button map = new Button("Map PU to IB");
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

        issueTree = new Tree(screen.getUi().getCurrentProject().getProjektName());
        issueTree.setNullSelectionAllowed(false);
        issueTree.setContainerDataSource(TreeIssueBaseContainerSingleton.getInstance(screen.getUi().getCurrentProject()
        ));
        issueTree.setImmediate(true);
        if (issueTabSheet != null) {
            issueTree.addValueChangeListener(new TreeValueChangeListener(issueTabSheet, issueTree));
        }
        issueTree.addValueChangeListener(new TreeActivateOnValueChangeListener(new Button[]{deleteButton}));

        issueTree.setItemCaptionPropertyId(TreeIssueBaseContainer.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        if (issueTree.getItemIds().toArray().length > 0)
            issueTree.select(issueTree.getItemIds().toArray()[0]);

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
                    for (Object id : issueTree.rootItemIds())
                        issueTree.collapseItemsRecursively(id);
                    expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_expand"));
                } else {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.expandItemsRecursively(id);
                    expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
                }
                expanded = !expanded;
            }
        });

        subissueButton.addClickListener(new Button.ClickListener() {
//            private boolean enabled = false;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().addWindow(new EditSubissuesWindow(screen));
            }
        });

        //TODO nur Testweise
        map.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                TreeIssueBaseContainer container;
                container = TreeIssueBaseContainerSingleton.getInstance(screen.getUi().getCurrentProject());
                if (container.getItemIds().isEmpty()) {
                    new MappingPlanningUnitToIssueBase(screen.getUi().getCurrentProject()).startMapping();
                    container.refresh();
                } else {
                    logger.error("Tree has already elements");
                    Notification.show("Tree has already elements", Notification.Type.ERROR_MESSAGE);
                }

            }
        });
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(screen.getMessagesBundle().getString("add"));
        deleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
        expandButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_collapse"));
        subissueButton.setCaption(screen.getMessagesBundle().getString("issuetracking_issue_dragdrop_edit"));
    }

    public void setSelectedItemByIssue(IssueBase issue) {
        for (Object itemId : issueTree.getItemIds()) {
            IssueBase treeIssue = (IssueBase)issueTree.getContainerDataSource().getContainerProperty(itemId,
                    TreeIssueBaseContainer.PROPERTY_ISSUEBASE).getValue();
            if (treeIssue.equals(issue)){
                issueTree.select(itemId);
                break;
            }
        }
    }

}
