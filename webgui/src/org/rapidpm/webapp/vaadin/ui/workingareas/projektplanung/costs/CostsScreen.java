package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic.CostsComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic.TimesComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;


public class CostsScreen extends Screen {

    private Button saveButton = new Button("Speichern");
    private TextField vertrieblerField;
    private TextField datumField;
    private TextField manntageField;
    private TextField summeInMinField;
    private TextField kostenField;

    //private Table einmaligeKostenTable = new Table();
    //private Table monatlicheKostenTable = new Table();

    private GridLayout unterschriftLayout = new GridLayout(2, 2);
    private GridLayout felderLayout = new GridLayout(2, 5);

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
        final Label drei = new Label("MT:");
        final Label vier = new Label("Summe [d:hh:mm]");
        final Label fuenf = new Label("Kosten:");
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.addComponent(drei);
        felderLayout.addComponent(manntageField);
        felderLayout.addComponent(vier);
        felderLayout.addComponent(summeInMinField);
        felderLayout.addComponent(fuenf);
        felderLayout.addComponent(kostenField);
        felderLayout.setComponentAlignment(drei, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(vier, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(fuenf, Alignment.MIDDLE_LEFT);
        felderLayout.setComponentAlignment(manntageField,
                Alignment.MIDDLE_RIGHT);
        felderLayout.setComponentAlignment(summeInMinField,
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
        final TimesComputer timesComputer = new TimesComputer(ressourceGroupsBean,projektBean);
        final CostsComputer costsComputer = new CostsComputer(projektBean);
        costsComputer.compute();
        timesComputer.compute();
        summeInMinField = new TextField();
        summeInMinField.setValue(timesComputer.getGesamtSummeItem().toString());
        manntageField = new TextField();
        kostenField = new TextField();
        manntageField.setValue(timesComputer.getMannTageGerundet().toString());
        kostenField.setValue(costsComputer.getTotalCostsGerundet() + Constants.EUR);
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


    public TextField getDatumField() {
        return datumField;
    }

    public void setDatumField(TextField datumField) {
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