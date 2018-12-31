package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.flow.server.VaadinSession;
import org.rapidpm.persistence.DaoFactory;
import org.rapidpm.persistence.DaoFactorySingelton;
import org.rapidpm.persistence.prj.projectmanagement.planning.PlannedProject;
import org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup;
import org.rapidpm.webapp.vaadin.MainUI;
import org.rapidpm.webapp.vaadin.ui.workingareas.Screen;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.DECIMAL_FORMAT;
import static org.rapidpm.Constants.EUR;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 21:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class OverviewTableFiller {

    private static final String EXTERN = "extern";
    private static final String RELATIV = "relativ";
    private static final String ABSOLUT = "absolut";
    private static final String KOSTEN = "kosten";
    private static final int WIDTH = 200;

    private MyTable table;
    private MainUI ui;
    private List<RessourceGroup> ressourceGroups;
    private ResourceBundle messages;


    public OverviewTableFiller(final MyTable table) {
        this.messages = VaadinSession.getCurrent().getAttribute(ResourceBundle.class);
        this.table = table;

        final DaoFactory daoFactory = DaoFactorySingelton.getInstance();
        ressourceGroups = daoFactory.getRessourceGroupDAO().loadAllEntities();
        for(final RessourceGroup ressourceGroup : ressourceGroups){
            daoFactory.getEntityManager().refresh(ressourceGroup);
        }
    }

    public void fill() {
        final VaadinSession session = VaadinSession.getCurrent();
        final PlannedProject currentProject = session.getAttribute(PlannedProject.class);
//        table.removeAllItems();
        final String angabe = messages.getString("angabe");
//        table.addContainerProperty(angabe, String.class, null);
//        table.setColumnCollapsible(angabe, false);
//        table.setColumnWidth(angabe, WIDTH);
//        for (final RessourceGroup ressourceGroup : ressourceGroups) {
//            final String spaltenName = ressourceGroup.getName();
//            table.addContainerProperty(spaltenName, String.class, null);
//            table.setColumnExpandRatio(spaltenName,1);
//        }

        final TimesCalculator timesCalculator = new TimesCalculator();
        timesCalculator.calculate();

        final CostsCalculator costsCalculator = new CostsCalculator(messages);
        costsCalculator.calculate();

//        final Item externItem = table.addItem(EXTERN);
//        final Item relativItem = table.addItem(RELATIV);
//        final Item absolutItem = table.addItem(ABSOLUT);
//        final Item kostenItem = table.addItem(KOSTEN);
//
//        final Item item = table.getItem(EXTERN);
//        final Property<String> externItemAngabeProperty = item.getItemProperty(angabe);
//        externItemAngabeProperty.setValue(messages.getString("costsscreen_externalEuroPerHour"));
//        final Collection<?> itemPropertyIds = externItem.getItemPropertyIds();
//        for (final Object spalte : itemPropertyIds) {
//            if (!spalte.equals(angabe)) {
//                for (final RessourceGroup ressourceGroup : ressourceGroups) {
//                    final String spaltenName = spalte.toString();
//                    final String spaltenNameAusRessourceGroupsBean = ressourceGroup.getName();
//                    if (spaltenName.equals(spaltenNameAusRessourceGroupsBean)) {
////                        final Property<String> externItemSpalteProperty = externItem.getItemProperty(spalte);
////                        final String ressourceGroupsBeanValue = ressourceGroup.getExternalEurosPerHour().toString();
////                        externItemSpalteProperty.setValue(ressourceGroupsBeanValue + " " + EUR);
//                    }
//                }
//            }
//        }

//        final Property<String> absolutItemAngabeProperty = absolutItem.getItemProperty(angabe);
        final String costsinit_sumInDDHHMM = messages.getString("costsinit_sumInDDHHMM");
//        absolutItemAngabeProperty.setValue(costsinit_sumInDDHHMM);
//        for (final Object spalte : absolutItem.getItemPropertyIds()) {
//            if (!spalte.equals(angabe)) {
//                final Map<RessourceGroup, Integer> absoluteWerte = timesCalculator.getAbsoluteWerte();
//                for (final Map.Entry<RessourceGroup, Integer> absoluteWerteEntry : absoluteWerte.entrySet()) {
//                    final String spaltenName = spalte.toString();
//                    final RessourceGroup key = absoluteWerteEntry.getKey();
//                    final String spaltenNameAusMap = key.getName();
//                    if (spaltenNameAusMap.equals(spaltenName)) {
//                        final Property<String> absolutItemSpalteProperty = absolutItem.getItemProperty(spalte);
//                        final Integer minutesFromMap = absoluteWerte.get(key);
//                        final DaysHoursMinutesItem itemForMinutesFromMap = new DaysHoursMinutesItem(minutesFromMap,
//                                currentProject.getHoursPerWorkingDay());
//                        absolutItemSpalteProperty.setValue(itemForMinutesFromMap.toString());
//                    }
//                }
//            }
//        }

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
//        final Property<String> relativItemAngabeProperty = relativItem.getItemProperty(angabe);
//        final String costsinit_sumInPercent = messages.getString("costsinit_sumInPercent");
//        relativItemAngabeProperty.setValue(costsinit_sumInPercent);
//        for (final Object spalte : relativItem.getItemPropertyIds()) {
//            if (!spalte.equals(angabe)) {
//                final Map<RessourceGroup, Double> relativeWerte = timesCalculator.getRelativeWerte();
//                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
//                    final RessourceGroup key = relativeWerteEntry.getKey();
//                    final String spaltenNameAusMap = key.getName();
//                    final String spaltenName = spalte.toString();
//                    if (spaltenNameAusMap.equals(spaltenName)) {
//                        final Property<String> relativItemSpalteProperty = relativItem.getItemProperty(spalte);
//                        final String mapValue = format.format(relativeWerte.get(key));
//                        relativItemSpalteProperty.setValue(mapValue + " %");
//                    }
//                }
//            }
//        }

//        final Property<String> kostenItemAngabeProperty = kostenItem.getItemProperty(angabe);
//        final String costsscreen_sumInEuro = messages.getString("costsscreen_sumInEuro");
//        kostenItemAngabeProperty.setValue(costsscreen_sumInEuro);
//        for (final Object spalte : kostenItem.getItemPropertyIds()) {
//            if (!spalte.equals(angabe)) {
//                final Map<RessourceGroup, Double> kostenMap = costsCalculator.getRessourceGroupsCostsMap();
//                for (final Map.Entry<RessourceGroup, Double> kostenEntry : kostenMap.entrySet()) {
//                    final String spaltenName = spalte.toString();
//                    final RessourceGroup key = kostenEntry.getKey();
//                    final String spaltenNameAusMap = key.getName();
//                    if (spaltenNameAusMap.equals(spaltenName)) {
//                        final Property<String> kostenItemSpalteProperty = kostenItem.getItemProperty(spalte);
//                        final String mapValue = format.format(kostenMap.get(key));
//                        kostenItemSpalteProperty.setValue(mapValue + " " + EUR);
//                    }
//                }
//            }
//        }
    }
}
