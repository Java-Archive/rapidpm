package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

public class VertriebScreen extends Screen {
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField summeMitAufschlagField;
    private TextField summeOhneAufschlagField;
    private TextField verhandelterPreisField;
    private TextArea bemerkungenArea;

    private Table vertriebsTable;

    private FormLayout vertrieblerLayout = new FormLayout();
    private VerticalLayout tableLayout = new VerticalLayout();
    private FormLayout bottomLayout;

    public VertriebScreen(MainUI ui) {
        super(ui);
        erstelleVertrieblerLayout();
        erstelleStandardTableLayout(new Label("Uebersicht"), vertriebsTable, tableLayout);
        erstelleBottomLayout();
        setComponents();
        doInternationalization();
    }

    @Override
    protected void doInternationalization() {
        vertrieblerField.setCaption(messagesBundle.getString("distri_responsible"));
        datumField.setCaption(messagesBundle.getString("distri_date"));
        summeMitAufschlagField.setCaption(messagesBundle.getString("distri_sumWithAddition"));
        summeOhneAufschlagField.setCaption(messagesBundle.getString("distri_sumWithoutAddition"));
        verhandelterPreisField.setCaption(messagesBundle.getString("distri_negotiatedPrice"));
        bemerkungenArea.setCaption(messagesBundle.getString("distri_comments"));
    }

    private void erstelleBottomLayout() {
        bottomLayout = new FormLayout();
        summeMitAufschlagField = new TextField();
        summeMitAufschlagField.setEnabled(false);
        summeOhneAufschlagField = new TextField();
        summeOhneAufschlagField.setEnabled(false);
        verhandelterPreisField = new TextField();
        bemerkungenArea = new TextArea();
        bemerkungenArea.setWidth("500px");
        bemerkungenArea.setHeight("300px");

        bottomLayout.setMargin(true);
        bottomLayout.addComponent(summeMitAufschlagField);
        bottomLayout.addComponent(summeOhneAufschlagField);
        bottomLayout.addComponent(verhandelterPreisField);

        bottomLayout.addComponent(bemerkungenArea);
    }

    private void erstelleStandardTableLayout(Label ueberschrift, Table tabelle, Layout layout) {
        tabelle = new Table();
        layout.addComponent(ueberschrift);
        layout.addComponent(tabelle);
    }

    private void erstelleVertrieblerLayout() {
        vertrieblerLayout.setWidth("600px");
        vertrieblerField = new TextField();
        datumField = new TextField();
        vertrieblerLayout.addComponent(vertrieblerField);
        vertrieblerLayout.addComponent(datumField);
        vertrieblerLayout.setMargin(true);
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

    public VerticalLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(VerticalLayout tableLayout) {
        this.tableLayout = tableLayout;
    }

}