package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.transience.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeTableItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeTableItemSetChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.components.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.Locale;

public class StundensaetzeTableCreator {
    private Table tabelle;

    public StundensaetzeTableCreator(
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
        tabelle.addListener(new StundensaetzeTableItemClickListener(
                itemClickdependentComponents, deleteButton, upperFormLayout, lowerFormLayout, formLayout,
                saveButton, tabelle, betriebsFraField, betriebsStdField));
        tabelle.addListener(new StundensaetzeTableItemSetChangeListener(tabelle,
                betriebsStdField, betriebsFraField));
        tabelle.setReadOnly(false);

        tabelle.setContainerDataSource(dataSource);

        tabelle.setVisibleColumns(RessourceGroup.COLUMNS);
        tabelle.setColumnHeaders(RessourceGroup.VISIBLECOLUMNS);

        tabelle.setFooterVisible(true);

        final StundensaetzeConverterAdder convertersAdder = new StundensaetzeConverterAdder();
        convertersAdder.addConvertersTo(tabelle);

        final StundensaetzeTableComputer computer = new StundensaetzeTableComputer(tabelle);
        computer.computeColumns();

    }

    public Table getTabelle() {
        return tabelle;
    }

    public void setTabelle(Table tabelle) {
        this.tabelle = tabelle;
    }

}
