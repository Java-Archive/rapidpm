package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.ejb3.EJBFactory;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components.UndoButton;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.CostsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.ExpandTableCheckBox;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

import java.util.Date;

import static org.rapidpm.Constants.DATE_FORMAT;
import static org.rapidpm.Constants.EUR;

//import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;


public class CostsScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private CheckBox expandCheckBox;
    private TextField vertrieblerField;
    private DateField datumField;
    private TextField manntageField;
    private TextField summeInMinField;
    private TextField kostenField;

    //private Table einmaligeKostenTable = new Table();
    //private Table monatlicheKostenTable = new Table();

    private FormLayout unterschriftLayout = new FormLayout();
    private FormLayout felderLayout = new FormLayout();

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

    public CostsScreen(MainUI ui) {
        super(ui);

        erstelleUnterschriftLayout();
        erstelleFelderLayout();

        undoButton = new UndoButton(this, treeTable, dataSource, projektBean);
        undoButton.setVisible(false);

        expandCheckBox = new ExpandTableCheckBox(treeTable, dataSource);

        final TreeTableFiller treeTableFiller = new TreeTableFiller(messagesBundle, this, projektBean,
                treeTable, dataSource);
        treeTableFiller.fill();

        final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(messagesBundle, uebersichtTable,
                projektBean, projektmanagementScreensBean);
        overviewTableFiller.fill();

        uebersichtTable.setPageLength(4);
        uebersichtTable.setConnectedTable(treeTable);
        uebersichtTable.setSizeFull();
        treeTable.setConnectedTable(uebersichtTable);
        treeTable.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
        treeTable.setSizeFull();

        table1layout.setMargin(true);
        table1layout.addComponent(uebersichtTable);
        table1layout.setSizeFull();

        table2layout.setMargin(true);
        table2layout.addComponent(expandCheckBox);
        table2layout.addComponent(undoButton);
        table2layout.addComponent(treeTable);
        table2layout.setSizeFull();

        lowerFormLayout.addComponent(saveButton);

        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);
        formLayout.setVisible(false);

        doInternationalization();
        setComponents();

    }

    private void erstelleFelderLayout() {
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.addComponent(manntageField);
        felderLayout.addComponent(summeInMinField);
        felderLayout.addComponent(kostenField);
    }

    private void erstelleUnterschriftLayout() {
        // Unterschrift
        vertrieblerField = new TextField();
        datumField = new DateField("Datum:", new Date());
        datumField.setDateFormat(DATE_FORMAT.toPattern());
        unterschriftLayout.setWidth("560px");
        unterschriftLayout.addComponent(vertrieblerField);
        unterschriftLayout.addComponent(datumField);


    }

    private void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator(messagesBundle, projektmanagementScreensBean, projektBean);
        final CostsCalculator costsCalculator = new CostsCalculator(projektBean, messagesBundle);
        costsCalculator.calculate();
        timesCalculator.calculate();
        summeInMinField = new TextField();
        summeInMinField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField = new TextField();
        kostenField = new TextField();
        manntageField.setValue(timesCalculator.getMannTageGerundet());
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

    @Override
    protected void doInternationalization() {
        expandCheckBox.setCaption(messagesBundle.getString("costsinit_expand"));
        saveButton.setCaption(messagesBundle.getString("save"));
        undoButton.setCaption(messagesBundle.getString("costsinit_removesortorder"));
        datumField.setCaption(messagesBundle.getString("costsinit_date"));
        manntageField.setCaption(messagesBundle.getString("costsinit_manday"));
        vertrieblerField.setCaption(messagesBundle.getString("costsscreen_responsible"));
        kostenField.setCaption(messagesBundle.getString("costsscreen_costs"));
        summeInMinField.setCaption(messagesBundle.getString("costsinit_sumInDDHHMM"));
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
}