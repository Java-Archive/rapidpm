package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupsBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesComputer;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;

import java.text.DecimalFormat;
import java.util.Map;

import static org.rapidpm.Constants.ANGABE_SPALTE;
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
    private static final String SUM_IN_PERCENT = "Summe [%]";
    private static final String SUM_IN_DAYSHOURSMINS = "Summe [d:hh:mm]";


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
        table.addContainerProperty(ANGABE_SPALTE, String.class, null);
        table.setColumnCollapsible(ANGABE_SPALTE, false);
        table.setColumnWidth(ANGABE_SPALTE, 250);
        for (final RessourceGroup ressourceGroup : ressourceGroupsBean.getRessourceGroups()) {
            final String spaltenName = ressourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
        }
        TimesComputer computer = new TimesComputer(ressourceGroupsBean, projektBean);
        computer.compute();
        
        
        table.addItem(ABSOLUT);
        table.addItem(RELATIV);

        final Property<?> absolutItemAngabeProperty = table.getItem(ABSOLUT).getItemProperty(ANGABE_SPALTE);
        absolutItemAngabeProperty.setValue(SUM_IN_DAYSHOURSMINS);
        for (final Object spalte : table.getItem(ABSOLUT).getItemPropertyIds()) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                final Map<RessourceGroup, DaysHoursMinutesItem> absoluteWerte = computer.getAbsoluteWerte();
                for (Map.Entry<RessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
                    final String spaltenNameAusMap = absoluteWerteEntry.getKey().getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final String absoluteWerteMapValue = absoluteWerte.get(absoluteWerteEntry.getKey()).toString();
                        table.getItem(ABSOLUT).getItemProperty(spalte).setValue(absoluteWerteMapValue);
                    }

                }

            }

        }

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final Item relativZeile = table.getItem(RELATIV);
        final Property<?> relativItemAngabeProperty = relativZeile.getItemProperty(ANGABE_SPALTE);
        relativItemAngabeProperty.setValue(SUM_IN_PERCENT);
        for (final Object spalte : relativZeile.getItemPropertyIds()) {
            if (!spalte.equals(ANGABE_SPALTE)) {
                final Map<RessourceGroup,Double> relativeWerte = computer.getRelativeWerte();
                for (final Map.Entry<RessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    final String spaltenNameAusMap = relativeWerteEntry.getKey().getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final String relativeWerteMapValue = format.format(relativeWerte.get(relativeWerteEntry.getKey())).toString();
                        relativZeile.getItemProperty(spalte).setValue(relativeWerteMapValue);
                    }
                }
            }
        }
    }
}
