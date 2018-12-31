package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.AbstractView;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;

import java.util.ResourceBundle;

@Route(value = "vertrieb", layout = MainAppLayout.class)
@Caption("Vertrieb")
@Icon(VaadinIcon.SAFE_LOCK)
public class VertriebScreen extends Screen {
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField summeMitAufschlagField;
    private TextField summeOhneAufschlagField;
    private TextField verhandelterPreisField;
    private TextArea bemerkungenArea;

    private Grid vertriebsTable;

    private FormLayout vertrieblerLayout = new FormLayout();
    private VerticalLayout tableLayout = new VerticalLayout();
    private FormLayout bottomLayout;

    public VertriebScreen() {
        erstelleVertrieblerLayout();
        erstelleStandardTableLayout(new Label("Uebersicht"));
        erstelleBottomLayout();
        setComponents();
        doInternationalization();
    }

    public void doInternationalization() {
        ResourceBundle messagesBundle = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        vertrieblerField.setValue(messagesBundle.getString("distri_responsible"));
        datumField.setValue(messagesBundle.getString("distri_date"));
        summeMitAufschlagField.setValue(messagesBundle.getString("distri_sumWithAddition"));
        summeOhneAufschlagField.setValue(messagesBundle.getString("distri_sumWithoutAddition"));
        verhandelterPreisField.setValue(messagesBundle.getString("distri_negotiatedPrice"));
        bemerkungenArea.setValue(messagesBundle.getString("distri_comments"));
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

        bottomLayout.add(summeMitAufschlagField);
        bottomLayout.add(summeOhneAufschlagField);
        bottomLayout.add(verhandelterPreisField);

        bottomLayout.add(bemerkungenArea);
    }

    private void erstelleStandardTableLayout(Label ueberschrift) {
        vertriebsTable = new Grid();
        tableLayout.add(ueberschrift);
        tableLayout.add(vertriebsTable);
    }

    private void erstelleVertrieblerLayout() {
        vertrieblerLayout.setWidth("600px");
        vertrieblerField = new TextField();
        datumField = new TextField();
        vertrieblerLayout.add(vertrieblerField);
        vertrieblerLayout.add(datumField);
    }

    public void setComponents() {
        add(vertrieblerLayout);
        add(tableLayout);
        add(bottomLayout);
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

    public Grid getVertriebsTable() {
        return vertriebsTable;
    }

    public void setVertriebsTable(Grid vertriebsTable) {
        this.vertriebsTable = vertriebsTable;
    }

    public VerticalLayout getTableLayout() {
        return tableLayout;
    }

    public void setTableLayout(VerticalLayout tableLayout) {
        this.tableLayout = tableLayout;
    }

}