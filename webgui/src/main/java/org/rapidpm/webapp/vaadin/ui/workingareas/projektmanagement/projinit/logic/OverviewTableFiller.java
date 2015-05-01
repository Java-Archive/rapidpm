package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
//import org.rapidpm.ejb3.EJBFactory;
//import org.rapidpm.persistence.DaoFactoryBean;
import com.vaadin.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingleton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroupDAO;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 21:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class OverviewTableFiller {

    private static final String RELATIV = "relativ";
    private static final String ABSOLUT = "absolut";
    public static final int WIDTH = 200;
    private final Screen screen;
    private PlannedProject currentProject;


    private MyTable table;
    private MainUI ui;
    private List<RessourceGroup> ressourceGroups;
    private ResourceBundle messages;
//    private OverviewTableFillerBean bean;

    public OverviewTableFiller(final Screen screen, final MyTable table) {
        this.screen = screen;
        messages = screen.getMessagesBundle();
        this.table = table;
        this.ui = screen.getUi();
        final DaoFactory daoFactory = DaoFactorySingleton.getInstance();
        final RessourceGroupDAO ressourceGroupDAO = daoFactory.getRessourceGroupDAO();
        ressourceGroups = ressourceGroupDAO.findAll();

}

    public void fill() {
        final VaadinSession session = screen.getUi().getSession();
        currentProject = session.getAttribute(PlannedProject.class);
        table.removeAllItems();
        final String angabe = messages.getString("angabe");
        table.addContainerProperty(angabe, String.class, null);
        table.setColumnCollapsible(angabe, false);
        table.setColumnWidth(angabe, WIDTH);
        for (final RessourceGroup ressourceGroup : ressourceGroups) {
            final String spaltenName = ressourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
            table.setColumnExpandRatio(spaltenName,1);
        }
        final TimesCalculator calculator = new TimesCalculator(screen);
        calculator.calculate();


        table.addItem(ABSOLUT);
        table.addItem(RELATIV);
        final Property<String> absolutItemAngabeProperty = table.getItem(ABSOLUT).getItemProperty(angabe);
        final String costsinit_sumInDDHHMM = messages.getString("costsinit_sumInDDHHMM");
        absolutItemAngabeProperty.setValue(costsinit_sumInDDHHMM);
        for (final Object spalte : table.getItem(ABSOLUT).getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<RessourceGroup, Integer> absoluteWerte = calculator.getAbsoluteWerte();
                for (final Map.Entry<RessourceGroup, Integer> absoluteWerteEntry : absoluteWerte
                        .entrySet()) {
                    final String spaltenNameAusMap = absoluteWerteEntry.getKey().getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<String> absolutItemSpalteProperty = table.getItem(ABSOLUT).getItemProperty(spalte);
                        final Integer minutesFromMap = absoluteWerte.get(absoluteWerteEntry.getKey());
                        final DaysHoursMinutesItem itemForMinutesFromMap = new DaysHoursMinutesItem(minutesFromMap,
                                currentProject.getHoursPerWorkingDay());
                        absolutItemSpalteProperty.setValue(itemForMinutesFromMap.toString());
                    }
                }
            }
        }
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final Item relativZeile = table.getItem(RELATIV);
        final Property<String> relativItemAngabeProperty = relativZeile.getItemProperty(angabe);
        final String costsinit_sumInPercent = messages.getString("costsinit_sumInPercent");
        relativItemAngabeProperty.setValue(costsinit_sumInPercent);
        for (final Object spalte : relativZeile.getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<RessourceGroup, Double> relativeWerte = calculator.getRelativeWerte();
                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    final String spaltenNameAusMap = relativeWerteEntry.getKey().getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final String relativeWerteMapValue = format.format(relativeWerte.get(relativeWerteEntry.getKey()));
                        relativZeile.getItemProperty(spalte).setValue(relativeWerteMapValue);
                    }
                }
            }
        }
    }
}
