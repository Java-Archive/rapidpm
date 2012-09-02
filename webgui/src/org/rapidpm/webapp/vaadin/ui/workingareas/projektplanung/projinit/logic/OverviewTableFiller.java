package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.projinit.logic;

import com.vaadin.data.Item;
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
        TimesComputer computer = new TimesComputer(ressourceGroupsBean, projektBean);
        computer.compute();
        
        
        table.addItem("absolut");
        table.addItem("relativ");

        table.getItem("absolut").getItemProperty("Angabe").setValue("Summe in h");
        for (Object spalte : table.getItem("absolut").getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                final Map<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = computer.getAbsoluteWerte();
                for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
                    if (absoluteWerteEntry.getKey().getName().equals(spalte.toString())) {
                        table.getItem("absolut").getItemProperty(spalte).setValue(absoluteWerte.get(absoluteWerteEntry.getKey()).toString());
                    }

                }

            }

        }

        final DecimalFormat format = new DecimalFormat("#0.00");
        final Item relativZeile = table.getItem("relativ");
        relativZeile.getItemProperty("Angabe").setValue("Summe in %");
        for (final Object spalte : relativZeile.getItemPropertyIds()) {
            if (!spalte.equals("Angabe")) {
                final HashMap<RessourceGroup,Double> relativeWerte = computer.getRelativeWerte();
                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    if (relativeWerteEntry.getKey().getName().equals(spalte.toString())) {
                        relativZeile.getItemProperty(spalte).setValue(format.format(relativeWerte.get(relativeWerteEntry.getKey())).toString());
                    }
                }
            }
        }
    }
}
