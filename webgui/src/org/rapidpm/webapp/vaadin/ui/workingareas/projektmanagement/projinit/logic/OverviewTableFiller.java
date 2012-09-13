package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroupsBean;

import java.text.DecimalFormat;
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


    private MyTable table;
    private OldRessourceGroupsBean oldRessourceGroupsBean;
    private ProjektBean projektBean;
    private ResourceBundle messages;

    public OverviewTableFiller(final ResourceBundle bundle, final MyTable table, final ProjektBean projektBean, 
                               final OldRessourceGroupsBean oldRessourceGroupsBean) {
        messages = bundle;
        this.table = table;
        this.oldRessourceGroupsBean = oldRessourceGroupsBean;
        this.projektBean = projektBean;
    }

    public void fill() {
        table.removeAllItems();
        final String angabe = messages.getString("angabe");
        table.addContainerProperty(angabe, String.class, null);
        table.setColumnCollapsible(angabe, false);
        table.setColumnWidth(angabe, WIDTH);
        for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroupsBean.getOldRessourceGroups()) {
            final String spaltenName = oldRessourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
            table.setColumnExpandRatio(spaltenName,1);
        }
        final TimesCalculator calculator = new TimesCalculator(messages, oldRessourceGroupsBean, projektBean);
        calculator.calculate();


        table.addItem(ABSOLUT);
        table.addItem(RELATIV);

        final Property<?> absolutItemAngabeProperty = table.getItem(ABSOLUT).getItemProperty(angabe);
        absolutItemAngabeProperty.setValue(messages.getString("costsinit_sumInDDHHMM"));
        for (final Object spalte : table.getItem(ABSOLUT).getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<OldRessourceGroup, DaysHoursMinutesItem> absoluteWerte = calculator.getAbsoluteWerte();
                for (final Map.Entry<OldRessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte
                        .entrySet()) {
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
        final Property<?> relativItemAngabeProperty = relativZeile.getItemProperty(angabe);
        relativItemAngabeProperty.setValue(messages.getString("costsinit_sumInPercent"));
        for (final Object spalte : relativZeile.getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<OldRessourceGroup, Double> relativeWerte = calculator.getRelativeWerte();
                for (final Map.Entry<OldRessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
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
