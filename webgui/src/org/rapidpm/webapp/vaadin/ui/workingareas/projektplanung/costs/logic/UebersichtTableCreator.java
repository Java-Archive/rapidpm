package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.datenmodell.UebersichtBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.datenmodell.UebersichtContainer;

import java.util.Locale;

public class UebersichtTableCreator {

    private Table tabelle;

    public UebersichtTableCreator() {
        tabelle = new Table();
        tabelle.setLocale(Locale.GERMANY);
        tabelle.setColumnCollapsingAllowed(true);
        tabelle.setColumnReorderingAllowed(true);
        tabelle.setImmediate(true);
        tabelle.setSelectable(true);
        tabelle.setNullSelectionAllowed(false);
        tabelle.setPageLength(5);
        tabelle.setContainerDataSource(UebersichtContainer.fill());
        tabelle.setVisibleColumns(UebersichtBean.COLUMNS);
        tabelle.setColumnHeaders(UebersichtBean.VISIBLECOLUMNS);

    }

    public Table getTabelle() {
        return tabelle;
    }
}
