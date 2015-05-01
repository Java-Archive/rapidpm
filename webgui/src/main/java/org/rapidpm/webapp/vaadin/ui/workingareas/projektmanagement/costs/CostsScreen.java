package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.ui.*;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TreeTableHeaderClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.components.UndoButton;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.CostsCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.OverviewTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic.TreeTableFiller;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsException;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.noproject.NoProjectsScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.ExpandTableCheckBox;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTreeTable;

import java.util.Date;
import java.util.List;

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
    private TextField summeField;
    private TextField kostenField;

    //private Table einmaligeKostenTable = new Table();
    //private Table monatlicheKostenTable = new Table();

    private FormLayout unterschriftLayout = new FormLayout();
    private FormLayout felderLayout = new FormLayout();

    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private MyTreeTable treeTable = new MyTreeTable();
    private MyTable uebersichtTable = new MyTable();

    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public CostsScreen(MainUI ui) {
        super(ui);
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().findAll();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            undoButton = new UndoButton(this, treeTable, dataSource);
            undoButton.setVisible(false);

            expandCheckBox = new ExpandTableCheckBox(treeTable, dataSource);

            final TreeTableFiller treeTableFiller = new TreeTableFiller(messagesBundle, this, treeTable, dataSource);
            treeTableFiller.fill();

            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(this, uebersichtTable);
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
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen(ui);
            addComponent(noProjectsScreen);
        }

    }

    private void erstelleFelderLayout() {
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.addComponent(manntageField);
        felderLayout.addComponent(summeField);
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
        final TimesCalculator timesCalculator = new TimesCalculator(this);
        final CostsCalculator costsCalculator = new CostsCalculator(ui, messagesBundle);
        costsCalculator.calculate();
        timesCalculator.calculate();
        summeField = new TextField();
        summeField.setValue(timesCalculator.getGesamtSummeItem().toString());
        manntageField = new TextField();
        kostenField = new TextField();
        manntageField.setValue(timesCalculator.getMannTageGerundet());
        kostenField.setValue(costsCalculator.getTotalCostsGerundet() + EUR);
        kostenField.setReadOnly(true);
        manntageField.setReadOnly(true);
        summeField.setReadOnly(true);
    }

    @Override
    public void setComponents() {
        addComponent(felderLayout);
        addComponent(unterschriftLayout);
        addComponent(table1layout);
        addComponent(table2layout);
        addComponent(formLayout);
    }

    @Override
    public void doInternationalization() {
        expandCheckBox.setCaption(messagesBundle.getString("costsinit_expand"));
        saveButton.setCaption(messagesBundle.getString("save"));
        undoButton.setCaption(messagesBundle.getString("costsinit_removesortorder"));
        datumField.setCaption(messagesBundle.getString("costsinit_date"));
        manntageField.setCaption(messagesBundle.getString("costsinit_manday"));
        vertrieblerField.setCaption(messagesBundle.getString("costsscreen_responsible"));
        kostenField.setCaption(messagesBundle.getString("costsscreen_costs"));
        summeField.setCaption(messagesBundle.getString("costsinit_sumInDDHHMM"));
    }
}