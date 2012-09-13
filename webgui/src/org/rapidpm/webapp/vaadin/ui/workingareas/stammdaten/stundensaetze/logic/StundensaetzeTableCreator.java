package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.StundensaetzeScreen;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBeanContainer;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tablelisteners.StundensaetzeItemClickListener;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.uicomponents.ItemClickDependentComponent;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StundensaetzeTableCreator {
    public static final String NESTED_BEAN_NAME = "ressourceGroup";
    public static final String NAME = "name";
    public static final String BRUTTOGEHALT = "bruttoGehalt";
    public static final String HOURS_PER_WEEK = "hoursPerWeek";
    public static final String WEEKS_PER_YEAR = "weeksPerYear";
    public static final String FACTURIZABLE = "facturizable";
    public static final String EXTERNAL_EUROS_PER_HOUR = "externalEurosPerHour";
    public static final String PLAN_ANZAHL = "planAnzahl";
    public static final String HOURS_PER_YEAR = "hoursPerYear";
    public static final String EUROS_PER_HOUR = "eurosPerHour";
    public static final String OPERATIVE_EUROS_PER_HOUR = "operativeEurosPerHour";
    public static final String BRUTTO_PER_MONTH = "bruttoPerMonth";
    public static final String SUM_PER_MONTH = "sumPerMonth";
    public static final String SUM_PER_DAY = "sumPerDay";
    private Table tabelle;

    public StundensaetzeTableCreator(
            final StundensaetzeScreen screen, final List<RessourceGroupBean> containerBeans,
            final List<ItemClickDependentComponent> itemClickdependentComponents, final Button deleteButton,
            final Layout upperFormLayout, final Layout lowerFormLayout, final Layout formLayout,
            final Button saveButton) {
        final RessourceGroupBeanContainer dataSource = new RessourceGroupBeanContainer();
        final ResourceBundle messages = screen.getMessagesBundle();
        dataSource.fill(containerBeans);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+NAME);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+BRUTTOGEHALT);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+HOURS_PER_WEEK);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+WEEKS_PER_YEAR);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+FACTURIZABLE);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+EXTERNAL_EUROS_PER_HOUR);
        dataSource.addNestedContainerProperty(NESTED_BEAN_NAME+"."+PLAN_ANZAHL);
        tabelle = screen.getTabelle();
        tabelle.setSizeFull();
        tabelle.removeAllItems();
        tabelle.setLocale(Locale.GERMANY);
        tabelle.setColumnCollapsingAllowed(true);
        tabelle.setColumnReorderingAllowed(true);
        tabelle.setImmediate(true);
        tabelle.setSelectable(true);
        tabelle.setNullSelectionAllowed(false);
        tabelle.addListener(new StundensaetzeItemClickListener(screen,
                itemClickdependentComponents, deleteButton, upperFormLayout, lowerFormLayout, formLayout,
                saveButton, tabelle));
        tabelle.setReadOnly(false);

        tabelle.setContainerDataSource(dataSource);

        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+NAME, messages.getString(NAME));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+BRUTTOGEHALT, messages.getString(BRUTTOGEHALT));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+HOURS_PER_WEEK, messages.getString(HOURS_PER_WEEK));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+WEEKS_PER_YEAR, messages.getString(WEEKS_PER_YEAR));
        tabelle.setColumnHeader(HOURS_PER_YEAR, messages.getString(HOURS_PER_YEAR));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+FACTURIZABLE, messages.getString(FACTURIZABLE));
        tabelle.setColumnHeader(EUROS_PER_HOUR, messages.getString(EUROS_PER_HOUR));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+EXTERNAL_EUROS_PER_HOUR, messages.getString(EXTERNAL_EUROS_PER_HOUR));
        tabelle.setColumnHeader(OPERATIVE_EUROS_PER_HOUR, messages.getString(OPERATIVE_EUROS_PER_HOUR));
        tabelle.setColumnHeader(BRUTTO_PER_MONTH, messages.getString(BRUTTO_PER_MONTH));
        tabelle.setColumnHeader(NESTED_BEAN_NAME+"."+PLAN_ANZAHL, messages.getString(PLAN_ANZAHL));
        tabelle.setColumnHeader(SUM_PER_MONTH, messages.getString(SUM_PER_MONTH));
        tabelle.setColumnHeader(SUM_PER_DAY, messages.getString(SUM_PER_DAY));

        tabelle.setVisibleColumns(new Object[]{NESTED_BEAN_NAME+"."+NAME, NESTED_BEAN_NAME+"."+BRUTTOGEHALT,
                NESTED_BEAN_NAME+"."+HOURS_PER_WEEK, NESTED_BEAN_NAME+"."+WEEKS_PER_YEAR, HOURS_PER_YEAR,
                NESTED_BEAN_NAME+"."+FACTURIZABLE, EUROS_PER_HOUR, NESTED_BEAN_NAME+"."+EXTERNAL_EUROS_PER_HOUR,
                OPERATIVE_EUROS_PER_HOUR, BRUTTO_PER_MONTH, NESTED_BEAN_NAME+"."+PLAN_ANZAHL,
                SUM_PER_MONTH, SUM_PER_DAY});
        tabelle.setFooterVisible(true);

        final StundensaetzeConverterAdder convertersAdder = new StundensaetzeConverterAdder();
        convertersAdder.addConvertersTo(tabelle);

    }
}
