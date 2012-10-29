package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.AddButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.DeleteButtonClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeActivateOnValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeItemClickValueChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerIssueBase;


/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 20.09.12
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class IssueTreeLayout extends VerticalLayout implements Internationalizationable{

    private final IssueOverviewScreen screen;

    private Button addButton;
    private Button deleteButton;
    private Button expandButton;

    private Tree issueTree;

    private HorizontalLayout buttonLayout;

    public IssueTreeLayout(final IssueOverviewScreen screen, final IssueTabSheet issueTabSheet) {
        this.screen = screen;
        this.setSizeFull();

        setComponents(issueTabSheet);
        doInternationalization();
    }

    private void setComponents(final IssueTabSheet issueTabSheet) {
        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);

        addButton = new Button();
        addButton.setEnabled(true);
        buttonLayout.addComponent(addButton);

        deleteButton = new Button();
        deleteButton.setEnabled(false);
        buttonLayout.addComponent(deleteButton);

        expandButton = new Button();
        expandButton.setEnabled(true);

//        if (addButton.getWidth() > deleteButton.getWidth()) deleteButton.setWidth(addButton.getWidth(), Unit.PIXELS);
//        else addButton.setWidth(deleteButton.getWidth(), Unit.PIXELS);

        addComponent(buttonLayout);
        addComponent(expandButton);

        issueTree = new Tree("IssueTree", new TreeContainerIssueBase(screen.getCurrentProject()));
        issueTree.setImmediate(true);
        if (issueTabSheet != null) {
            TreeItemClickValueChangeListener listener = new TreeItemClickValueChangeListener(issueTabSheet, issueTree);
            issueTree.addItemClickListener(listener);
            issueTree.addValueChangeListener(listener);
            issueTree.addValueChangeListener(new TreeActivateOnValueChangeListener(new Button[]{deleteButton,
                    addButton}));
        }
        issueTree.setItemCaptionPropertyId(TreeContainerIssueBase.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        addComponent(issueTree);

        addButton.addClickListener(new AddButtonClickListener(screen, issueTree));
        deleteButton.addClickListener(new DeleteButtonClickListener(screen, issueTree));
        expandButton.addClickListener(new Button.ClickListener() {
            private boolean expanded = true;

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (expanded) {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.collapseItemsRecursively(id);
                    expandButton.setCaption("Alle Tickets ausklappen");
                }
                else {
                    for (Object id : issueTree.rootItemIds())
                        issueTree.expandItemsRecursively(id);
                    expandButton.setCaption("Alle Tickets einklappen");
                }
                expanded = !expanded;
            }
        });
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(screen.getMessagesBundle().getString("add"));
        deleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
        expandButton.setCaption("Issues einklappen");
    }
}
