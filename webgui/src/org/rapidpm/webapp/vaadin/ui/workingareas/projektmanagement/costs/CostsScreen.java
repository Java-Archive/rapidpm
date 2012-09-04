package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.CostsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.util.Date;

import static org.rapidpm.Constants.*;


public class CostsScreen extends Screen {

    private Button saveButton = new Button("Speichern");
    private TextField vertrieblerField;
    private DateField datumField;
    private TextField manntageField;
    private TextField summeInMinField;
    private TextField kostenField;

    //private Table einmaligeKostenTable = new Table();
    //private Table monatlicheKostenTable = new Table();

    private FormLayout unterschriftLayout = new FormLayout();
    private FormLayout felderLayout = new FormLayout();

    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private MyTreeTable treeTable = new MyTreeTable();
    private MyTable uebersichtTable = new MyTable();

    private static final String COLUMN_WIDTH = "350px";
    private static final String ABSOLUTE_WIDTH = "700px";

    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public CostsScreen(MainRoot root) {
        this.projektBean = root.getPlanningUnitsBean();
        this.ressourceGroupsBean = root.getRessourceGroupsBean();

        erstelleUnterschriftLayout();
        erstelleFelderLayout();

        final TreeTableFiller treeTableFiller = new TreeTableFiller(projektBean, ressourceGroupsBean, treeTable, dataSource);
        treeTableFiller.fill();

        final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(uebersichtTable, projektBean, ressourceGroupsBean);
        overviewTableFiller.fill();

        uebersichtTable.setPageLength(4);
        uebersichtTable.setConnectedTable(treeTable);
        treeTable.setConnectedTable(uebersichtTable);
        table1layout.addComponent(uebersichtTable);

        table2layout.addComponent(treeTable);
        table1layout.setMargin(true, false, true, false);
        table2layout.setMargin(true, false, true, false);

        lowerFormLayout.addComponent(saveButton);

        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);
        formLayout.setVisible(false);
        setComponents();

    }

    private void erstelleFelderLayout() {
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.addComponent(manntageField);
        felderLayout.addComponent(summeInMinField);
        felderLayout.addComponent(kostenField);
        felderLayout.setMargin(true, false, true, false);
    }

    private void erstelleUnterschriftLayout() {
        // Unterschrift
        vertrieblerField = new TextField("Verantwortlicher Vertriebler:");
        datumField = new DateField("Datum:",new Date());
        datumField.setDateFormat(DATE_FORMAT.toPattern());
        unterschriftLayout.setWidth("560px");
        unterschriftLayout.addComponent(vertrieblerField);
        unterschriftLayout.addComponent(datumField);
        unterschriftLayout.setMargin(true, false, true, false);


    }

    private void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator(ressourceGroupsBean,projektBean);
        final CostsCalculator costsCalculator = new CostsCalculator(projektBean);
        costsCalculator.compute();
        timesCalculator.compute();
        summeInMinField = new TextField("Summe [d:hh:mm]");
        summeInMinField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField = new TextField("MT:");
        kostenField = new TextField("Kosten:");
        manntageField.setValue(timesCalculator.getMannTageGerundet().toString());
        kostenField.setValue(costsCalculator.getTotalCostsGerundet() + EUR);
        kostenField.setReadOnly(true);
        manntageField.setReadOnly(true);
        summeInMinField.setReadOnly(true);

    }

    public void setComponents() {
        addComponent(felderLayout);
        addComponent(unterschriftLayout);
        addComponent(table1layout);
        addComponent(table2layout);
        addComponent(formLayout);
    }


    public DateField getDatumField() {
        return datumField;
    }

    public void setDatumField(DateField datumField) {
        this.datumField = datumField;
    }


    public TextField getManntageField() {
        return manntageField;
    }

    public void setManntageField(TextField manntageField) {
        this.manntageField = manntageField;
    }

    public TextField getSummeInMinField() {
        return summeInMinField;
    }

    public void setSummeInMinField(TextField summeInMinField) {
        this.summeInMinField = summeInMinField;
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public void setFormLayout(VerticalLayout formLayout) {
        this.formLayout = formLayout;
    }

    public GridLayout getUpperFormLayout() {
        return upperFormLayout;
    }

    public void setUpperFormLayout(GridLayout upperFormLayout) {
        this.upperFormLayout = upperFormLayout;
    }

    public VerticalLayout getLowerFormLayout() {
        return lowerFormLayout;
    }

    public void setLowerFormLayout(VerticalLayout lowerFormLayout) {
        this.lowerFormLayout = lowerFormLayout;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public MyTreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(MyTreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public MyTable getUebersichtTable() {
        return uebersichtTable;
    }

    public void setUebersichtTable(MyTable uebersichtTable) {
        this.uebersichtTable = uebersichtTable;
    }

    public ProjektBean getProjektBean() {
        return projektBean;
    }

    public HierarchicalContainer getDataSource() {
        return dataSource;
    }

    public VerticalLayout getTable2layout() {
        return table2layout;
    }

    public void setTable2layout(VerticalLayout table2layout) {
        this.table2layout = table2layout;
    }

    public void setDataSource(HierarchicalContainer dataSource) {
        this.dataSource = dataSource;
    }

    public RessourceGroupsBean getRessourceGroupsBean() {
        return ressourceGroupsBean;
    }
}