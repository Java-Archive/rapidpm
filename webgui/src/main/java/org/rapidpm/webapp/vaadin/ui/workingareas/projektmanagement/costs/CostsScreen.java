package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs;

import com.github.appreciated.app.layout.annotations.Caption;
import com.github.appreciated.app.layout.annotations.Icon;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.MainAppLayout;
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

import java.awt.*;
import java.util.Date;
import java.util.List;

import static org.rapidpm.Constants.DATE_FORMAT;
import static org.rapidpm.Constants.EUR;

//import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;

@Route(value = "kosten", layout = MainAppLayout.class)
@Caption("Kosten")
@Icon(VaadinIcon.MONEY_DEPOSIT)
public class CostsScreen extends Screen {

    private Button saveButton = new Button();
    private Button undoButton;
    private Checkbox expandCheckBox;
    private TextField vertrieblerField;
    private DatePicker datumField;
    private TextField manntageField;
    private TextField summeField;
    private TextField kostenField;

    //private Table einmaligeKostenTable = new Table();
    //private Table monatlicheKostenTable = new Table();

    private FormLayout unterschriftLayout = new FormLayout();
    private FormLayout felderLayout = new FormLayout();

//    private HierarchicalContainer dataSource = new HierarchicalContainer();
    private MyTreeTable treeTable = new MyTreeTable();
    private MyTable uebersichtTable = new MyTable();

    private VerticalLayout table1layout = new VerticalLayout();
    private VerticalLayout table2layout = new VerticalLayout();
    private VerticalLayout formLayout = new VerticalLayout();
    private GridLayout upperFormLayout = new GridLayout(2, 10);
    private VerticalLayout lowerFormLayout = new VerticalLayout();

    public CostsScreen() {
        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        try{
            final List<PlannedProject> plannedProjects = daoFactory.getPlannedProjectDAO().loadAllEntities();
            if(plannedProjects == null || plannedProjects.isEmpty()){
                throw new NoProjectsException();
            }
            erstelleUnterschriftLayout();
            erstelleFelderLayout();

            undoButton = new UndoButton(this, treeTable, null);
            undoButton.setVisible(false);

            expandCheckBox = new ExpandTableCheckBox(treeTable, null);

//            final TreeTableFiller treeTableFiller = new TreeTableFiller(messagesBundle, this, treeTable, dataSource);
//            treeTableFiller.fill();
//
//            final OverviewTableFiller overviewTableFiller = new OverviewTableFiller(this, uebersichtTable);
//            overviewTableFiller.fill();

//            uebersichtTable.setPageLength(4);
            uebersichtTable.setConnectedTable(treeTable);
//            uebersichtTable.setSizeFull();
            treeTable.setConnectedTable(uebersichtTable);
//            treeTable.addHeaderClickListener(new TreeTableHeaderClickListener(undoButton));
//            treeTable.setSizeFull();

            table1layout.setMargin(true);
            table1layout.add(uebersichtTable);
            table1layout.setSizeFull();

            table2layout.setMargin(true);
            table2layout.add(expandCheckBox);
            table2layout.add(undoButton);
//            table2layout.add(treeTable);
            table2layout.setSizeFull();

            lowerFormLayout.add(saveButton);

//            formLayout.add(upperFormLayout);
            formLayout.add(lowerFormLayout);
            formLayout.setVisible(false);

            doInternationalization();
            setComponents();
        } catch (final NoProjectsException e){
            removeAllComponents();
            final NoProjectsScreen noProjectsScreen = new NoProjectsScreen();
            add(noProjectsScreen);
        }

    }

    private void erstelleFelderLayout() {
        // Textfelder
        fillFields();
        felderLayout.setWidth("350px");
        felderLayout.add(manntageField);
        felderLayout.add(summeField);
        felderLayout.add(kostenField);
    }

    private void erstelleUnterschriftLayout() {
        // Unterschrift
        vertrieblerField = new TextField();
        datumField = new DatePicker("Datum:");
//        datumField.setDateFormat(DATE_FORMAT.toPattern());
        unterschriftLayout.setWidth("560px");
        unterschriftLayout.add(vertrieblerField);
        unterschriftLayout.add(datumField);
    }

    private void fillFields() {
        final TimesCalculator timesCalculator = new TimesCalculator();
        final CostsCalculator costsCalculator = new CostsCalculator(messagesBundle);
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
        add(felderLayout);
        add(unterschriftLayout);
        add(table1layout);
        add(table2layout);
        add(formLayout);
    }

    @Override
    public void doInternationalization() {
        expandCheckBox.setLabel(messagesBundle.getString("costsinit_expand"));
        saveButton.setText(messagesBundle.getString("save"));
        undoButton.setText(messagesBundle.getString("costsinit_removesortorder"));
        datumField.setLabel(messagesBundle.getString("costsinit_date"));
        manntageField.setValue(messagesBundle.getString("costsinit_manday"));
        vertrieblerField.setValue(messagesBundle.getString("costsscreen_responsible"));
        kostenField.setValue(messagesBundle.getString("costsscreen_costs"));
        summeField.setValue(messagesBundle.getString("costsinit_sumInDDHHMM"));
    }
}