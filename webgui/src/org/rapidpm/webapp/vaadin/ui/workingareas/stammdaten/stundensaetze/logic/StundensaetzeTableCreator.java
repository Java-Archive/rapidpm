package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.MainRoot;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBeanItemContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemSetChangeListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StundensaetzeTableCreator {
    private Table tabelle;

    public StundensaetzeTableCreator(
            final MainRoot root, final List<ItemClickDependentComponent>
            itemClickdependentComponents, final Button deleteButton, final Layout upperFormLayout,
            final Layout lowerFormLayout, final Layout formLayout, final Button saveButton,
            final TextField betriebsFraField, final TextField betriebsStdField) {
        final RessourceGroupsBeanItemContainer dataSource = new RessourceGroupsBeanItemContainer();
        final ArrayList<String> list = new ArrayList<>();
        final RessourceGroupsBean ressourceGroupsBean = root.getRessourceGroupsBean();
        final ResourceBundle messages = root.getResourceBundle();
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

        for(final String column : RessourceGroup.COLUMNS){
            list.add(messages.getString(column));
        }
        final String[] columns = new String[list.size()];
        list.toArray(columns);
        tabelle.setColumnHeaders(columns);

        tabelle.setFooterVisible(true);

        final StundensaetzeConverterAdder convertersAdder = new StundensaetzeConverterAdder();
        convertersAdder.addConvertersTo(tabelle);

        final StundensaetzeTableCalculator calculator = new StundensaetzeTableCalculator(tabelle);
        calculator.computeColumns();

    }

    public Table getTabelle() {
        return tabelle;
    }

    public void setTabelle(final Table tabelle) {
        this.tabelle = tabelle;
    }

}
