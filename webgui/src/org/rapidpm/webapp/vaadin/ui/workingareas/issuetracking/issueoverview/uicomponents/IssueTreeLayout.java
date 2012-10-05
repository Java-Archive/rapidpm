package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.Internationalizationable;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.logic.TreeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.modell.TreeContainerPlanningUnits;


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

        addButton = new Button();
        buttonLayout.addComponent(addButton);

        deleteButton = new Button();
        buttonLayout.addComponent(deleteButton);

        addComponent(buttonLayout);

        issueTree = new Tree("IssueTree", new TreeContainerPlanningUnits());
        issueTree.setImmediate(true);
        if (issueTabSheet != null)
            issueTree.addItemClickListener(new TreeItemClickListener(issueTabSheet));
        issueTree.setItemCaptionPropertyId(TreeContainerPlanningUnits.PROPERTY_CAPTION);
        for (Object id : issueTree.rootItemIds())
            issueTree.expandItemsRecursively(id);
        addComponent(issueTree);
    }

    @Override
    public void doInternationalization() {
        addButton.setCaption(screen.getMessagesBundle().getString("add"));
        deleteButton.setCaption(screen.getMessagesBundle().getString("delete"));
    }
}
