package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupContainer;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.*;

public class StundensaetzeTableCreator {
    private Grid tabelle;

    public StundensaetzeTableCreator(
            final StundensaetzeScreen screen, final List<RessourceGroup> containerBeans) {
        final RessourceGroupContainer dataSource = new RessourceGroupContainer();
        final ResourceBundle messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        dataSource.fill(containerBeans);
//        tabelle = screen.getTabelle();
//        tabelle.setSizeFull();
//        tabelle.removeAllItems();
//        tabelle.setLocale(Locale.GERMANY);
//        tabelle.setColumnCollapsingAllowed(true);
//        tabelle.setColumnReorderingAllowed(true);
//        tabelle.setImmediate(true);
//        tabelle.setSelectable(true);
//        tabelle.setNullSelectionAllowed(false);
//
//        tabelle.setReadOnly(false);
//
//        tabelle.setContainerDataSource(dataSource);
//
//        tabelle.setColumnHeader(NAME, messages.getString(NAME));
//        tabelle.setColumnHeader(BRUTTOGEHALT, messages.getString(BRUTTOGEHALT));
//        tabelle.setColumnHeader(HOURS_PER_WEEK, messages.getString(HOURS_PER_WEEK));
//        tabelle.setColumnHeader(WEEKS_PER_YEAR, messages.getString(WEEKS_PER_YEAR));
//        tabelle.setColumnHeader(HOURS_PER_YEAR, messages.getString(HOURS_PER_YEAR));
//        tabelle.setColumnHeader(FACTURIZABLE, messages.getString(FACTURIZABLE));
//        tabelle.setColumnHeader(EUROS_PER_HOUR, messages.getString(EUROS_PER_HOUR));
//        tabelle.setColumnHeader(EXTERNAL_EUROS_PER_HOUR, messages.getString(EXTERNAL_EUROS_PER_HOUR));
//        tabelle.setColumnHeader(OPERATIVE_EUROS_PER_HOUR, messages.getString(OPERATIVE_EUROS_PER_HOUR));
//        tabelle.setColumnHeader(BRUTTO_PER_MONTH, messages.getString(BRUTTO_PER_MONTH));
//        tabelle.setColumnHeader(PLAN_ANZAHL, messages.getString(PLAN_ANZAHL));
//        tabelle.setColumnHeader(SUM_PER_MONTH, messages.getString(SUM_PER_MONTH));
//        tabelle.setColumnHeader(SUM_PER_DAY, messages.getString(SUM_PER_DAY));
//
//        tabelle.setVisibleColumns(new Object[]{NAME, BRUTTOGEHALT,
//                HOURS_PER_WEEK, WEEKS_PER_YEAR, HOURS_PER_YEAR,
//                FACTURIZABLE, EUROS_PER_HOUR, EXTERNAL_EUROS_PER_HOUR,
//                OPERATIVE_EUROS_PER_HOUR, BRUTTO_PER_MONTH, PLAN_ANZAHL,
//                SUM_PER_MONTH, SUM_PER_DAY});
//        tabelle.setFooterVisible(true);

        final StundensaetzeConverterAdder convertersAdder = new StundensaetzeConverterAdder();
        convertersAdder.addConvertersTo(tabelle);

    }
}
