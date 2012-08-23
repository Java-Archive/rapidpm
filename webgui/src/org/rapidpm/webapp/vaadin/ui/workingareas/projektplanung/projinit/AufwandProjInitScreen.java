package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic.ProjInitComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic.TableItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic.TreeTableContainerFiller;

public class AufwandProjInitScreen extends Screen {

    private Button saveButton = new Button("Speichern");
    private TextField kundeField;
    private TextField projektField;
    private TextField datumField;
    private TextField projektLeiterField;
    private TextField unterschriftField;
    private TextField manntageField;
    private TextField summeInMinField;
    private TextField summeKundentermineInStdField;

    private ProjektBean projektBean;
    private TreeTableContainerFiller containerFiller;
    private HierarchicalContainer dataSource;
    private TreeTable treeTable = new TreeTable();
    private Table uebersichtTable = new Table();

    private static final String TABLELAYOUT_WIDTH = "900px";
    private static final String COLUMN_WIDTH = "350px";
    private static final String ABSOLUTE_WIDTH = "700px";

    private HorizontalLayout felderLayout = new HorizontalLayout();
    private VerticalLayout unterschriftLayout = new VerticalLayout();
    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public AufwandProjInitScreen(ProjektBean cont) {
        this.projektBean = cont;
        containerFiller = new TreeTableContainerFiller(projektBean);
        containerFiller.fill();
        dataSource = containerFiller.getHierarchicalContainer();
        final ProjInitComputer computer = new ProjInitComputer(this);

        erstelleUnterschriftLayout();
        erstelleFelderLayout();
        fillTable();

        uebersichtTable.setPageLength(4);


        treeTable.setNullSelectionAllowed(false);
        treeTable.setSelectable(true);
        treeTable.setSizeFull();
        uebersichtTable.setSizeFull();
        treeTable.addListener(new TableItemClickListener(this));


        treeTable.setContainerDataSource(dataSource);
        table1layout.addComponent(uebersichtTable);

        table2layout.setWidth(TABLELAYOUT_WIDTH);
        table1layout.setWidth(TABLELAYOUT_WIDTH);
        table2layout.addComponent(treeTable);
        table1layout.setMargin(true, false, true, false);
        table2layout.setMargin(true, false, true, false);

        computer.compute();
        //computer.setValuesInScreen();

        lowerFormLayout.addComponent(saveButton);

        formLayout.addComponent(upperFormLayout);
        formLayout.addComponent(lowerFormLayout);
        formLayout.setVisible(false);
        setComponents();

    }

    private void fillTable() {
        uebersichtTable.addContainerProperty("Angabe", String.class, null);
        for(RessourceGroup ressourceGroup : projektBean.getProjekt().getRessourceGroups()){
            uebersichtTable.addContainerProperty(ressourceGroup.getName(), String.class, null);
        }
    }

    private void erstelleFelderLayout() {
        final Label kundeLabel = new Label("Kunde:");
        final Label projektLabel = new Label("Projekt:");
        final Label datumLabel = new Label("Datum:");
        final Label manntageLabel = new Label("MT:");
        final Label summeInMinLabel = new Label("Summe in min:");
        final Label kundentermineLabel = new Label("Summe in min (Kundentermine):");
        final VerticalLayout linkeZeilen = new VerticalLayout();
        final VerticalLayout rechteZeilen = new VerticalLayout();
        final HorizontalLayout linkeZeile1 = new HorizontalLayout();
        final HorizontalLayout linkeZeile2 = new HorizontalLayout();
        final HorizontalLayout linkeZeile3 = new HorizontalLayout();
        final HorizontalLayout rechteZeile1 = new HorizontalLayout();
        final HorizontalLayout rechteZeile2 = new HorizontalLayout();
        final HorizontalLayout rechteZeile3 = new HorizontalLayout();

        kundeField = new TextField();
        projektField = new TextField();
        datumField = new TextField();
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        manntageField = new TextField();
        manntageField.setEnabled(false);
        summeInMinField = new TextField();
        summeInMinField.setEnabled(false);
        summeKundentermineInStdField = new TextField();
        summeKundentermineInStdField.setEnabled(false);
        // Horizontallayout (700px) beinhaltet 2 VerticalLayouts(jew. 350px)
        // beinhalten jeweils x horizontallayouts (sizefull)
        felderLayout.setWidth(ABSOLUTE_WIDTH);
        linkeZeilen.setWidth(COLUMN_WIDTH );
        rechteZeilen.setWidth(COLUMN_WIDTH );
        linkeZeile1.setSizeFull();
        linkeZeile2.setSizeFull();
        linkeZeile3.setSizeFull();
        rechteZeile1.setSizeFull();
        rechteZeile2.setSizeFull();
        rechteZeile3.setSizeFull();

        linkeZeile1.addComponent(kundeLabel);
        linkeZeile1.addComponent(kundeField);
        linkeZeile1.setComponentAlignment(kundeLabel, Alignment.MIDDLE_LEFT);
        linkeZeile1.setComponentAlignment(kundeField, Alignment.MIDDLE_LEFT);
        linkeZeile2.addComponent(projektLabel);
        linkeZeile2.addComponent(projektField);
        linkeZeile3.addComponent(datumLabel);
        linkeZeile3.addComponent(datumField);

        rechteZeile1.addComponent(manntageLabel);
        rechteZeile1.addComponent(manntageField);
        rechteZeile1.setComponentAlignment(manntageLabel, Alignment.MIDDLE_LEFT);
        rechteZeile1
                .setComponentAlignment(manntageField, Alignment.MIDDLE_LEFT);
        rechteZeile2.addComponent(summeInMinLabel);
        rechteZeile2.addComponent(summeInMinField);
        rechteZeile3.addComponent(kundentermineLabel);
        rechteZeile3.addComponent(summeKundentermineInStdField);

        linkeZeilen.addComponent(linkeZeile1);
        linkeZeilen.addComponent(linkeZeile2);
        linkeZeilen.addComponent(linkeZeile3);

        rechteZeilen.addComponent(rechteZeile1);
        rechteZeilen.addComponent(rechteZeile2);
        rechteZeilen.addComponent(rechteZeile3);

        felderLayout.addComponent(linkeZeilen);
        felderLayout.addComponent(rechteZeilen);
        felderLayout.setMargin(true, false, true, false);

    }

    private void erstelleUnterschriftLayout() {
        final Label projleiterLabel = new Label("Projektleiter:");
        final Label unterschriftLabel = new Label("Unterschrift PM:");
        final HorizontalLayout zeile1 = new HorizontalLayout();
        final HorizontalLayout zeile2 = new HorizontalLayout();
        projektLeiterField = new TextField();
        unterschriftField = new TextField();
        unterschriftLayout.setWidth("350px");

        zeile1.setSizeFull();
        zeile2.setSizeFull();

        zeile1.addComponent(projleiterLabel);
        zeile1.addComponent(projektLeiterField);
        zeile2.addComponent(unterschriftLabel);
        zeile2.addComponent(unterschriftField);

        unterschriftLayout.addComponent(zeile1);
        unterschriftLayout.addComponent(zeile2);
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

    public TextField getDatumField() {
        return datumField;
    }

    public void setDatumField(TextField datumField) {
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

    public TextField getSummeInMinField() {
        return summeInMinField;
    }

    public void setSummeInMinField(TextField summeInMinField) {
        this.summeInMinField = summeInMinField;
    }

    public TextField getSummeKundentermineInStdField() {
        return summeKundentermineInStdField;
    }

    public void setSummeKundentermineInStdField(
            TextField summeKundentermineInStdField) {
        this.summeKundentermineInStdField = summeKundentermineInStdField;
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

    public TreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(TreeTable treeTable) {
        this.treeTable = treeTable;
    }

    public Table getUebersichtTable() {
        return uebersichtTable;
    }

    public void setUebersichtTable(Table uebersichtTable) {
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


}
