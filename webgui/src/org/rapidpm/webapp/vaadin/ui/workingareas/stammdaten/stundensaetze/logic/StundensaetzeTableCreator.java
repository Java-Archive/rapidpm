package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.RessourceGroup;
import org.rapidpm.webapp.vaadin.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemSetChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.List;
import java.util.Locale;

public class StundensaetzeTableCreator {
    private Table tabelle;

    public StundensaetzeTableCreator(
            final RessourceGroupsBean ressourceGroupsBean, final List<ItemClickDependentComponent>
            itemClickdependentComponents, final Button deleteButton, final Layout upperFormLayout,
            final Layout lowerFormLayout, final Layout formLayout, final Button saveButton,
            final TextField betriebsFraField, final TextField betriebsStdField) {
        final RessourceGroupsBeanItemContainer dataSource = new RessourceGroupsBeanItemContainer();
        dataSource.fill(ressourceGroupsBean.getRessourceGroups());
        tabelle = new Table();
        tabelle.setLocale(Locale.GERMANY);
        tabelle.setColumnCollapsingAllowed(true);
        tabelle.setColumnReorderingAllowed(true);
        tabelle.setImmediate(true);
        tabelle.setSelectable(true);
        tabelle.setNullSelectionAllowed(false);
        tabelle.addListener(new StundensaetzeItemClickListener(
                itemClickdependentComponents, deleteButton, upperFormLayout, lowerFormLayout, formLayout,
                saveButton, tabelle, betriebsFraField, betriebsStdField));

        tabelle.addListener(new StundensaetzeItemSetChangeListener(tabelle, betriebsStdField, betriebsFraField));
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

    public void setTabelle(final Table tabelle) {
        this.tabelle = tabelle;
    }

}
