package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tablelisteners.CalculatorItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic.tablelisteners.CalculatorItemSetChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.Locale;

public class CalculatorTableCreator {
    private Table tabelle;

    public CalculatorTableCreator(
            RessourceGroupsBean ressourceGroupsBean, ArrayList<ItemClickDependentComponent> itemClickdependentComponents,
            Button deleteButton, Layout upperFormLayout, Layout lowerFormLayout, Layout formLayout, Button saveButton,
            TextField betriebsFraField, TextField betriebsStdField) {
        final RessourceGroupsBeanItemContainer dataSource = new RessourceGroupsBeanItemContainer();
        dataSource.fill(ressourceGroupsBean.getRessourceGroups());
        tabelle = new Table();
        tabelle.setLocale(Locale.GERMANY);
        tabelle.setColumnCollapsingAllowed(true);
        tabelle.setColumnReorderingAllowed(true);
        tabelle.setImmediate(true);
        tabelle.setSelectable(true);
        tabelle.setNullSelectionAllowed(false);
        tabelle.addListener(new CalculatorItemClickListener(
                itemClickdependentComponents, deleteButton, upperFormLayout, lowerFormLayout, formLayout,
                saveButton, tabelle, betriebsFraField, betriebsStdField));

        tabelle.addListener(new CalculatorItemSetChangeListener(tabelle,
                betriebsStdField, betriebsFraField));
        tabelle.setReadOnly(false);

        tabelle.setContainerDataSource(dataSource);

        tabelle.setVisibleColumns(RessourceGroup.COLUMNS);
        tabelle.setColumnHeaders(RessourceGroup.VISIBLECOLUMNS);

        tabelle.setFooterVisible(true);

        final CalculatorConverterAdder convertersAdder = new CalculatorConverterAdder();
        convertersAdder.addConvertersTo(tabelle);

        final CalculatorTableComputer computer = new CalculatorTableComputer(tabelle);
        computer.computeColumns();

    }

    public Table getTabelle() {
        return tabelle;
    }

    public void setTabelle(final Table tabelle) {
        this.tabelle = tabelle;
    }

}
