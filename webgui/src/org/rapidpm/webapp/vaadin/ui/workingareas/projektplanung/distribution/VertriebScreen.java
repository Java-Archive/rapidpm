package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.distribution;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;

public class VertriebScreen extends Screen {
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField summeMitAufschlagField;
    private TextField summeOhneAufschlagField;
    private TextField verhandelterPreisField;
    private TextArea bemerkungenArea;

    private Table vertriebsTable;

    private VerticalLayout vertrieblerLayout = new VerticalLayout();
    private VerticalLayout tableLayout = new VerticalLayout();
    private GridLayout bottomLayout;

    public VertriebScreen() {
        erstelleVertrieblerLayout();
        erstelleStandardTableLayout(new Label("Uebersicht"), vertriebsTable,
                tableLayout);
        erstelleBottomLayout();
        setComponents();
    }

    private void erstelleBottomLayout() {
        bottomLayout = new GridLayout(2, 5);
        summeMitAufschlagField = new TextField();
        summeMitAufschlagField.setEnabled(false);
        summeOhneAufschlagField = new TextField();
        summeOhneAufschlagField.setEnabled(false);
        verhandelterPreisField = new TextField();
        bemerkungenArea = new TextArea();
        bemerkungenArea.setWidth("500px");
        bemerkungenArea.setHeight("300px");
        // GridLayout bottomGridLayout = new GridLayout(2,5);

        final Label summeMitAufschlagLabel = new Label("Summe mit Verhandlungsaufschlag: ");
        final Label summeOhneAufschlagLabel = new Label("Summe ohne Verhandlungsaufschlag: ");
        final Label horizontaleLinieLabel = new Label("--------------------------------");
        final Label verhandelterPreisLabel = new Label("verhandelter Preis: ");
        final Label bemerkungenLabel = new Label("Bemerkungen: ");

        bottomLayout.setMargin(true, false, true, false);

        bottomLayout.addComponent(summeMitAufschlagLabel);
        bottomLayout.addComponent(summeMitAufschlagField);
        bottomLayout.addComponent(summeOhneAufschlagLabel);
        bottomLayout.addComponent(summeOhneAufschlagField);
        bottomLayout.addComponent(horizontaleLinieLabel);
        bottomLayout.addComponent(new Label(""));
        bottomLayout.addComponent(verhandelterPreisLabel);
        bottomLayout.addComponent(verhandelterPreisField);
        bottomLayout.addComponent(bemerkungenLabel);

        bottomLayout.addComponent(bemerkungenArea);
        bottomLayout.setComponentAlignment(summeMitAufschlagLabel, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(summeMitAufschlagField,
                Alignment.MIDDLE_LEFT);
        bottomLayout.setComponentAlignment(summeOhneAufschlagLabel, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(summeOhneAufschlagField,
                Alignment.MIDDLE_LEFT);
        bottomLayout.setComponentAlignment(horizontaleLinieLabel, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(verhandelterPreisLabel, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(verhandelterPreisField,
                Alignment.MIDDLE_LEFT);
        bottomLayout.setComponentAlignment(bemerkungenLabel, Alignment.MIDDLE_RIGHT);
        bottomLayout.setComponentAlignment(bemerkungenArea,
                Alignment.MIDDLE_LEFT);

    }

    private void erstelleStandardTableLayout(Label ueberschrift, Table tabelle,
                                             Layout layout) {
        tabelle = new Table();
        layout.addComponent(ueberschrift);
        layout.addComponent(tabelle);
        layout.setMargin(true, false, true, false);
    }

    private void erstelleVertrieblerLayout() {
        final Label vertrieblerLabel = new Label("Verantwortlicher Vertriebler:");
        final Label datumLayout = new Label("Datum:");
        final HorizontalLayout zeile1 = new HorizontalLayout();
        final HorizontalLayout zeile2 = new HorizontalLayout();
        vertrieblerLayout.setWidth("600px");
        vertrieblerField = new TextField();
        datumField = new TextField();

        zeile1.addComponent(vertrieblerLabel);
        zeile1.addComponent(vertrieblerField);
        zeile1.setSizeFull();
        zeile2.addComponent(datumLayout);
        zeile2.addComponent(datumField);
        zeile2.setSizeFull();
        vertrieblerLayout.addComponent(zeile1);
        vertrieblerLayout.addComponent(zeile2);
        vertrieblerLayout.setMargin(true, false, true, false);
    }

    private void setComponents() {
        addComponent(vertrieblerLayout);
        addComponent(tableLayout);
        addComponent(bottomLayout);
    }


    public TextField getVertrieblerField() {
        return vertrieblerField;
    }

    public void setVertrieblerField(TextField vertrieblerField) {
        this.vertrieblerField = vertrieblerField;
    }

    public TextField getDatumField() {
        return datumField;
    }

    public void setDatumField(TextField datumField) {
        this.datumField = datumField;
    }

    public TextField getSummeMitAufschlagField() {
        return summeMitAufschlagField;
    }

    public void setSummeMitAufschlagField(TextField summeMitAufschlagField) {
        this.summeMitAufschlagField = summeMitAufschlagField;
    }

    public TextField getSummeOhneAufschlagField() {
        return summeOhneAufschlagField;
    }

    public void setSummeOhneAufschlagField(TextField summeOhneAufschlagField) {
        this.summeOhneAufschlagField = summeOhneAufschlagField;
    }

    public TextField getVerhandelterPreisField() {
        return verhandelterPreisField;
    }

    public void setVerhandelterPreisField(TextField verhandelterPreisField) {
        this.verhandelterPreisField = verhandelterPreisField;
    }

    public TextArea getBemerkungenArea() {
        return bemerkungenArea;
    }

    public void setBemerkungenArea(TextArea bemerkungenArea) {
        this.bemerkungenArea = bemerkungenArea;
    }

    public Table getVertriebsTable() {
        return vertriebsTable;
    }

    public void setVertriebsTable(Table vertriebsTable) {
        this.vertriebsTable = vertriebsTable;
    }

    public VerticalLayout getVertrieblerLayout() {
        return vertrieblerLayout;
    }

    public void setVertrieblerLayout(VerticalLayout vertrieblerLayout) {
        this.vertrieblerLayout = vertrieblerLayout;
    }

    public VerticalLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(VerticalLayout tableLayout) {
        this.tableLayout = tableLayout;
    }

}