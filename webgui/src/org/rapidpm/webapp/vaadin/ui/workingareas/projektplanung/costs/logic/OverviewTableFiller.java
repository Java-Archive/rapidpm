package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.data.Item;
import org.rapidpm.webapp.vaadin.Constants;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.logic.TimesComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.datenmodell.ProjektBean;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 02.09.12
 * Time: 21:05
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class OverviewTableFiller {

    private MyTable table;
    private RessourceGroupsBean ressourceGroupsBean;
    private ProjektBean projektBean;

    public OverviewTableFiller(final MyTable table, final ProjektBean projektBean, final RessourceGroupsBean ressourceGroupsBean){
        this.table = table;
        this.ressourceGroupsBean = ressourceGroupsBean;
        this.projektBean = projektBean;
    }
    public void fill(){
        table.removeAllItems();
        table.addContainerProperty("Angabe", String.class, null);
        table.setColumnCollapsible("Angabe", false);
        table.setColumnWidth("Angabe", 250);
        for (final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups()) {
            final String spaltenName = ressourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
        }

        final TimesComputer timesComputer = new TimesComputer(ressourceGroupsBean, projektBean);
        timesComputer.compute();

        final CostsComputer costsComputer = new CostsComputer(projektBean);
        costsComputer.compute();

        final Item externItem = table.addItem("extern");
        final Item relativItem = table.addItem("relativ");
        final Item absolutItem = table.addItem("absolut");
        final Item kostenItem = table.addItem("kosten");

        table.getItem("extern").getItemProperty("Angabe").setValue("Extern € / h");
        for (final Object spalte : externItem.getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                for(final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups()){
                    if(spalte.toString().equals(ressourceGroup.toString())){
                        externItem.getItemProperty(spalte).setValue(ressourceGroup.getExternalEurosPerHour().toString()+" "+ Constants.EUR);
                    }
                }
            }
        }

        absolutItem.getItemProperty("Angabe").setValue("Summe [d:hh:mm]");
        for (final Object spalte : absolutItem.getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                final Map<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = timesComputer.getAbsoluteWerte();
                for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
                    if (absoluteWerteEntry.getKey().getName().equals(spalte.toString())) {
                        absolutItem.getItemProperty(spalte).setValue(absoluteWerte.get(absoluteWerteEntry.getKey()).toString());
                    }
                }
            }
        }

        final DecimalFormat format = new DecimalFormat("#0.00");
        relativItem.getItemProperty("Angabe").setValue("Summe [%]");
        for (final Object spalte : relativItem.getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                final HashMap<RessourceGroup,Double> relativeWerte = timesComputer.getRelativeWerte();
                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    if (relativeWerteEntry.getKey().getName().equals(spalte.toString())) {
                        relativItem.getItemProperty(spalte).setValue(format.format(relativeWerte.get(relativeWerteEntry.getKey())).toString()+" %");
                    }

                }
            }
        }

        kostenItem.getItemProperty("Angabe").setValue("Summe [€]");
        for (final Object spalte : kostenItem.getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                final Map<RessourceGroup, Double> kostenMap = costsComputer.getRessourceGroupsCostsMap();
                for (Map.Entry<RessourceGroup, Double> kostenEntry : kostenMap.entrySet()) {
                    if (kostenEntry.getKey().getName().equals(spalte.toString())) {
                        kostenItem.getItemProperty(spalte).setValue(format.format(kostenMap.get(kostenEntry.getKey())).toString()+" "+ Constants.EUR);
                    }
                }
            }
        }
    }
}
