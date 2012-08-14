package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs;

import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic.UebersichtTableCreator;

public class CostsScreen extends Screen {
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField euroProKmField;
    private TextField stdSatzFuerReiseZeitField;
    private TextField mannTageField;
    private TextField summeInStdField;
    private TextField kostenField;

    private Table uebersichtTable;
    private Table detailsTable = new Table();
    private Table einmaligeKostenTable = new Table();
    private Table monatlicheKostenTable = new Table();

    private GridLayout unterschriftLayout = new GridLayout(2, 2);
    private GridLayout felderLayout = new GridLayout(2, 5);
    private VerticalLayout uebersichtTableLayout = new VerticalLayout();
    private VerticalLayout detailsTableLayout = new VerticalLayout();
    private HorizontalLayout einmaligRegelmaessigLayout = new HorizontalLayout();

    public CostsScreen() {

        erstelleUnterschriftLayout();
        erstelleFelderLayout();

        fillSummaryTable();
        erstelleUebersichtTableLayout();
        erstelleDetailsTableLayout();
        erstelleEinmaligRegelmaessigTablesLayout();
        setComponents();

    }

    private void erstelleUebersichtTableLayout() {
        uebersichtTableLayout.addComponent(new Label("Uebersicht"));
        uebersichtTableLayout.addComponent(uebersichtTable);
        uebersichtTableLayout.setMargin(true, false, true, false);
    }

    private void erstelleDetailsTableLayout() {
        detailsTableLayout.addComponent(new Label("Details"));
        detailsTableLayout.addComponent(detailsTable);
        detailsTableLayout.setMargin(true, false, true, false);
    }

    private void erstelleEinmaligRegelmaessigTablesLayout() {
        final VerticalLayout einmaligLayout = new VerticalLayout();
        final VerticalLayout monatlichLayout = new VerticalLayout();
        einmaligLayout.addComponent(new Label("einmalige Kosten"));
        einmaligLayout.addComponent(einmaligeKostenTable);
        einmaligLayout.setMargin(true, false, true, false);
        monatlichLayout.addComponent(new Label("monatliche Kosten"));
        monatlichLayout.addComponent(monatlicheKostenTable);
        monatlichLayout.setMargin(true, false, true, false);
        einmaligRegelmaessigLayout.addComponent(einmaligLayout);
        einmaligRegelmaessigLayout.addComponent(monatlichLayout);
        einmaligRegelmaessigLayout.setSpacing(true);
    }

    private void erstelleFelderLayout() {
        final Label eins = new Label("Euro pro km:");
        final Label zwei = new Label("Stundensatz f. Reisezeit:");
        final Label drei = new Label("MT:");
        final Label vier = new Label("Summe in h");
        final Label fuenf = new Label("Kosten:");
        // Textfelder
        fillFields();
        mannTageField.setEnabled(false);
        summeInStdField.setEnabled(false);
        kostenField.setEnabled(false);
        felderLayout.setWidth("350px");
        felderLayout.addComponent(eins);
        felderLayout.addComponent(euroProKmField);
        felderLayout.addComponent(zwei);
        felderLayout.addComponent(stdSatzFuerReiseZeitField);
        felderLayout.addComponent(drei);
        felderLayout.addComponent(mannTageField);
        felderLayout.addComponent(vier);
        felderLayout.addComponent(summeInStdField);
        felderLayout.addComponent(fuenf);
        felderLayout.addComponent(kostenField);
        felderLayout.setComponentAlignment(eins, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(zwei, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(drei, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(vier, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(fuenf, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(euroProKmField,
                Alignment.MIDDLE_RIGHT);
        felderLayout.setComponentAlignment(stdSatzFuerReiseZeitField,
                Alignment.MIDDLE_RIGHT);
        felderLayout.setComponentAlignment(mannTageField,
                Alignment.MIDDLE_RIGHT);
        felderLayout.setComponentAlignment(summeInStdField,
                Alignment.MIDDLE_RIGHT);
        felderLayout.setComponentAlignment(kostenField, Alignment.MIDDLE_RIGHT);
        felderLayout.setMargin(true, false, true, false);
    }

    private void erstelleUnterschriftLayout() {
        final Label vertrieblerLabel = new Label("Verantwortlicher Vertriebler:");
        final Label datumLabel = new Label("Datum:");
        // Unterschrift
        vertrieblerField = new TextField();
        datumField = new TextField();
        unterschriftLayout.setWidth("560px");
        unterschriftLayout.addComponent(vertrieblerLabel);
        unterschriftLayout.addComponent(vertrieblerField);
        unterschriftLayout.addComponent(datumLabel);
        unterschriftLayout.addComponent(datumField);
        unterschriftLayout.setComponentAlignment(vertrieblerLabel, Alignment.MIDDLE_RIGHT);
        unterschriftLayout.setComponentAlignment(datumLabel, Alignment.MIDDLE_RIGHT);
        unterschriftLayout.setComponentAlignment(datumField,
                Alignment.MIDDLE_LEFT);
        unterschriftLayout.setComponentAlignment(vertrieblerField,
                Alignment.MIDDLE_LEFT);
        unterschriftLayout.setMargin(true, false, true, false);


    }

    private void fillFields() {
        euroProKmField = new TextField();
        stdSatzFuerReiseZeitField = new TextField();
        summeInStdField = new TextField();
        mannTageField = new TextField();
        kostenField = new TextField();

        euroProKmField.setValue("0,30" + Constants.EUR);
        stdSatzFuerReiseZeitField.setValue("0,45" + Constants.EUR);
        summeInStdField.setValue("40:35:00");
        mannTageField.setValue("5,07");
        kostenField.setValue("3270,42" + Constants.EUR);

    }

    private void fillSummaryTable() {
        final UebersichtTableCreator creator = new UebersichtTableCreator();
        uebersichtTable = creator.getTabelle();
    }

    private void setComponents() {
        addComponent(unterschriftLayout);
        addComponent(felderLayout);
        addComponent(uebersichtTableLayout);
        addComponent(detailsTableLayout);
        addComponent(einmaligRegelmaessigLayout);
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

    public TextField getEuroProKmField() {
        return euroProKmField;
    }

    public void setEuroProKmField(TextField euroProKmField) {
        this.euroProKmField = euroProKmField;
    }

    public TextField getStdSatzFuerReiseZeitField() {
        return stdSatzFuerReiseZeitField;
    }

    public void setStdSatzFuerReiseZeitField(TextField stdSatzFuerReiseZeitField) {
        this.stdSatzFuerReiseZeitField = stdSatzFuerReiseZeitField;
    }

    public TextField getMannTageField() {
        return mannTageField;
    }

    public void setMannTageField(TextField mannTageField) {
        this.mannTageField = mannTageField;
    }

    public TextField getSummeInH() {
        return summeInStdField;
    }

    public void setSummeInH(TextField summeInH) {
        this.summeInStdField = summeInH;
    }

    public TextField getKostenField() {
        return kostenField;
    }

    public void setKostenField(TextField kostenField) {
        this.kostenField = kostenField;
    }

}
