package org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.model.UserWorkLog;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.TableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.logic.jira.JiraSoapClient;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.export.ExportPanel;
import org.rapidpm.webapp.vaadin.ui.workingareas.zeitmanagement.auswertung.uicomponents.filter.FilterPanel;


public class ZeitauswertungScreen extends Screen {

    public static final String[] HEADERS = new String[]{"Autor","Bearbeiter","Zeit","Issue","Kommentar",
            "Zusammenfassung","Erstellt am", "Aktualisiert am"};

    public static final String[] VISIBLE_COLUMNS = new String[]{UserWorkLog.AUTHOR, UserWorkLog.ASSIGNEE,
    UserWorkLog.ISSUEKEY, UserWorkLog.TIMESPENTSTRING, UserWorkLog.TIMESPENTINMINS, UserWorkLog.COMMENT, UserWorkLog.SUMMARY, UserWorkLog.CREATED, UserWorkLog.UPDATED};

    private Table tabelle;
    private JiraSoapClient jiraSoapClient;
    private TextField queryField;
    private Button queryButton;
    private HorizontalLayout queryLayout;
    private ExportPanel exportPanel;
    private FilterPanel filterPanel;

    private HorizontalLayout panelLayout;


    public ZeitauswertungScreen(final MainUI ui) {
        super(ui);

        queryLayout = new HorizontalLayout();
        queryButton = new Button("Abfrage starten");
        queryField = new TextField();
        queryField.setValue("project = \"JTEL Developer\" AND updatedDate >= \"2012-10-01\"");
        queryLayout.addComponent(new Label("Abfrage: "));
        queryLayout.addComponent(queryField);
        queryLayout.addComponent(queryButton);
        queryLayout.setSizeFull();
        queryLayout.setExpandRatio(queryField, 0.9f);

        tabelle = new Table();

        exportPanel = new ExportPanel(ui, tabelle);

        exportPanel.activate(false);
        panelLayout = new HorizontalLayout();
        panelLayout.addComponent(exportPanel);



        queryField.setSizeFull();


        tabelle.setSizeFull();
        tabelle.setSelectable(true);
        tabelle.setColumnReorderingAllowed(true);
        tabelle.setColumnCollapsingAllowed(true);

        queryButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                jiraSoapClient = new JiraSoapClient(queryField.getValue());
                final TableFiller tableFiller = new TableFiller(jiraSoapClient.getUserWorkLogs(), tabelle);
                tabelle.setVisibleColumns(VISIBLE_COLUMNS);
                tabelle.setCaption("Abfrageergebnis");
                //tabelle.setColumnHeaders(HEADERS);
                tabelle.setPageLength(200);
                filterPanel = new FilterPanel(tabelle);
                panelLayout.addComponent(filterPanel);
                exportPanel.activate(true);
                filterPanel.activate(true);
            }
        });


        setComponents();
    }

    @Override
    public void setComponents() {
        addComponent(queryLayout);
        addComponent(panelLayout);
        addComponent(tabelle);
    }

    @Override
    public void doInternationalization() {
       //(Vorerst) keine I18n
    }

    public Table getTabelle() {
        return tabelle;
    }
}
