package org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.uicomponents;


import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.windows.KontaktWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.components.ComponentEditablePanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueedit.IssueEditWindow;
import org.rapidpm.webapp.vaadin.ui.workingareas.issuetracking.issueoverview.IssueOverviewScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Alvin Schiller
 * Date: 26.09.12
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class IssueTablePanel extends ComponentEditablePanel {

    private IssueOverviewScreen screen;

    private Button editButton;
    private Table issueTable;

    public IssueTablePanel(IssueOverviewScreen screen) {
        super(screen);
        this.screen = screen;
        this.setSizeFull();
    }


    @Override
    protected AbstractOrderedLayout buildForm() {
        FormLayout layout = new FormLayout();

        editButton = new Button("Ticket Editieren");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI.getCurrent().addWindow(new IssueEditWindow(screen));
            }
        });
        layout.addComponent(editButton);

        issueTable = new Table();
        issueTable.setColumnCollapsingAllowed(true);
        issueTable.setSizeFull();
        issueTable.setImmediate(true);
        issueTable.setSelectable(true);

        String[] headerNames = new String[] {"ID", "Bezeichnung", "Komponente"};
        for (String name : headerNames) {
            issueTable.addContainerProperty(name, String.class, "");
        }

        layout.addComponent(issueTable);

        return layout;
    }
}
