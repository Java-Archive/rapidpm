package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableFiller;

import static org.rapidpm.Constants.*;

import java.util.Date;

public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button("Speichern");
    private TextField kundeField;
    private TextField projektField;
    private DateField datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeField;

    private ProjektBean projektBean;
    private RessourceGroupsBean ressourceGroupsBean;
    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private MyTreeTable treeTable = new MyTreeTable();
    private MyTable uebersichtTable = new MyTable();

    //private static final String TABLELAYOUT_WIDTH = "900px";
    private static final String COLUMN_WIDTH = "350px";
    private static final String ABSOLUTE_WIDTH = "700px";

    private HorizontalLayout felderLayout = new HorizontalLayout();
    private FormLayout unterschriftLayout = new FormLayout();
    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public AufwandProjInitScreen(MainRoot root) {
        this.projektBean = root.getPlanningUnitsBean();
        this.ressourceGroupsBean = root.getRessourceGroupsBean();

        erstelleUnterschriftLayout();
        erstelleFelderLayout();

        final TreeTableFiller treeTableFiller = new TreeTableFiller(this, projektBean, ressourceGroupsBean, treeTable, dataSource);
        treeTableFiller.fill();

        final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(uebersichtTable, projektBean, ressourceGroupsBean);
        overviewTableFiller.fill();

        fillFields();

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

    public void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator(ressourceGroupsBean,projektBean);
        timesCalculator.compute();
        manntageField.setReadOnly(false);
        summeField.setReadOnly(false);
        manntageField.setValue(timesCalculator.getMannTageGerundet().toString());
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
    }

    private void erstelleFelderLayout() {
        final FormLayout layoutLinks = new FormLayout();
        final FormLayout layoutRechts = new FormLayout();

        kundeField = new TextField("Kunde: ");
        projektField = new TextField("Projekt: ");
        datumField = new DateField("Datum: ", new Date());
        datumField.setDateFormat(DATE_FORMAT.toPattern());
        projektLeiterField = new TextField("Projektleiter: ");
        unterschriftField = new TextField("Unterschrift: ");
        manntageField = new TextField("MT: ");
        summeField = new TextField("Summe (d:hh:mm): ");
        // Horizontallayout (700px) beinhaltet 2 VerticalLayouts(jew. 350px)
        // beinhalten jeweils x horizontallayouts (sizefull)
        felderLayout.setWidth(ABSOLUTE_WIDTH);

        layoutLinks.addComponent(kundeField);
        layoutLinks.addComponent(projektField);
        layoutLinks.addComponent(datumField);
        layoutLinks.setMargin(false,true,false,false);

        layoutRechts.addComponent(manntageField);
        layoutRechts.addComponent(summeField);
        layoutRechts.setMargin(false,false,false,true);

        felderLayout.addComponent(layoutLinks);
        felderLayout.addComponent(layoutRechts);
        felderLayout.setMargin(true, false, true, false);

    }

    private void erstelleUnterschriftLayout() {
        projektLeiterField = new TextField("Projektleiter:");
        unterschriftField = new TextField("Unterschrift PM:");
        unterschriftLayout.setWidth("350px");

        unterschriftLayout.addComponent(projektLeiterField);
        unterschriftLayout.addComponent(unterschriftField);

        unterschriftLayout.setMargin(true, false, true, false);
    }

    public void setComponents() {
        addComponent(felderLayout);
        addComponent(unterschriftLayout);
        addComponent(table1layout);
        addComponent(table2layout);
        addComponent(formLayout);
    }


    public TextField getKundeField() {
        return kundeField;
    }

    public void setKundeField(TextField kundeField) {
        this.kundeField = kundeField;
    }

    public TextField getProjektField() {
        return projektField;
    }

    public void setProjektField(TextField projektField) {
        this.projektField = projektField;
    }

    public DateField getDatumField() {
        return datumField;
    }

    public void setDatumField(DateField datumField) {
        this.datumField = datumField;
    }

    public TextField getProjektLeiterField() {
        return projektLeiterField;
    }

    public void setProjektLeiterField(TextField projektLeiterField) {
        this.projektLeiterField = projektLeiterField;
    }

    public TextField getUnterschriftField() {
        return unterschriftField;
    }

    public void setUnterschriftField(TextField unterschriftField) {
        this.unterschriftField = unterschriftField;
    }

    public TextField getManntageField() {
        return manntageField;
    }

    public void setManntageField(TextField manntageField) {
        this.manntageField = manntageField;
    }

    public TextField getSummeField() {
        return summeField;
    }

    public void setSummeField(TextField summeField) {
        this.summeField = summeField;
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
