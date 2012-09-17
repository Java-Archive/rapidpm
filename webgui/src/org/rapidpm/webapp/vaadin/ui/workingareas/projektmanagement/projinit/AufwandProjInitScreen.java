package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.ExpandTableCheckBox;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.UndoButton;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroupsBean;

import java.util.Date;

import static org.rapidpm.Constants.DATE_FORMAT;

public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private CheckBox expandCheckBox;
    private TextField kundeField;
    private TextField projektField;
    private DateField datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeField;

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

    public AufwandProjInitScreen(MainUI ui) {
        super(ui);

        erstelleUnterschriftLayout();
        erstelleFelderLayout();

        expandCheckBox = new ExpandTableCheckBox(treeTable, dataSource);
        undoButton = new UndoButton(this, treeTable, dataSource, projektBean, oldRessourceGroupsBean);
        undoButton.setVisible(false);

        final TreeTableFiller treeTableFiller = new TreeTableFiller(messagesBundle, this, projektBean,
                oldRessourceGroupsBean, treeTable, dataSource);
        treeTableFiller.fill();

        final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(messagesBundle, uebersichtTable,
                projektBean, oldRessourceGroupsBean);
        overviewTableFiller.fill();

        fillFields();

        uebersichtTable.setPageLength(4);
        uebersichtTable.setConnectedTable(treeTable);
        uebersichtTable.setSizeFull();
        treeTable.setConnectedTable(uebersichtTable);
        treeTable.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
        treeTable.setSizeFull();

        table1layout.addComponent(uebersichtTable);
        table1layout.setSizeFull();
        table1layout.setMargin(true);

        table2layout.addComponent(expandCheckBox);
        table2layout.addComponent(undoButton);
        table2layout.addComponent(treeTable);
        table2layout.setSizeFull();
        table2layout.setMargin(true);


        lowerFormLayout.addComponent(saveButton);

        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);
        formLayout.setVisible(false);
        setComponents();

        doInternationalization();

    }

    protected void doInternationalization() {
        expandCheckBox.setCaption(messagesBundle.getString("costsinit_expand"));
        saveButton.setCaption(messagesBundle.getString("save"));
        undoButton.setCaption(messagesBundle.getString("costsinit_removesortorder"));
        kundeField.setCaption(messagesBundle.getString("initscreen_customer"));
        projektField.setCaption(messagesBundle.getString("initscreen_project"));
        datumField.setCaption(messagesBundle.getString("costsinit_date"));
        manntageField.setCaption(messagesBundle.getString("costsinit_manday"));
        summeField.setCaption(messagesBundle.getString("costsinit_sumInDDHHMM"));
        projektLeiterField.setCaption(messagesBundle.getString("initscreen_projectleader"));
        unterschriftField.setCaption(messagesBundle.getString("initscreen_signature"));
    }

    public void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator(messagesBundle, oldRessourceGroupsBean,projektBean);
        timesCalculator.calculate();
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

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new DateField("Datum: ", new Date());
        datumField.setDateFormat(DATE_FORMAT.toPattern());
        manntageField = new TextField();
        summeField = new TextField();
        // Horizontallayout (700px) beinhaltet 2 VerticalLayouts(jew. 350px)
        // beinhalten jeweils x horizontallayouts (sizefull)
        felderLayout.setWidth(ABSOLUTE_WIDTH);

        layoutLinks.addComponent(kundeField);
        layoutLinks.addComponent(projektField);
        layoutLinks.addComponent(datumField);

        layoutRechts.addComponent(manntageField);
        layoutRechts.addComponent(summeField);

        felderLayout.addComponent(layoutLinks);
        felderLayout.addComponent(layoutRechts);

    }

    private void erstelleUnterschriftLayout() {
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setWidth("350px");

        unterschriftLayout.addComponent(projektLeiterField);
        unterschriftLayout.addComponent(unterschriftField);
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

    public OldRessourceGroupsBean getRessourceGroupsBean() {
        return oldRessourceGroupsBean;
    }
}
