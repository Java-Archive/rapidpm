package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.DaysHoursMinutesItem;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.TimesCalculator;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.planning.modell.ProjektBean;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.projinit.components.MyTable;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroup;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.OldRessourceGroupsBean;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.rapidpm.Constants.*;

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
    private OldRessourceGroupsBean oldRessourceGroupsBean;
    private ProjektBean projektBean;
    private ResourceBundle messages;


    public OverviewTableFiller(final ResourceBundle bundle, final MyTable table, final ProjektBean projektBean,
                               final OldRessourceGroupsBean oldRessourceGroupsBean) {
        this.messages = bundle;
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
        final List<OldRessourceGroup> oldRessourceGroups = oldRessourceGroupsBean.getOldRessourceGroups();
        for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroups) {
            final String spaltenName = oldRessourceGroup.getName();
            table.addContainerProperty(spaltenName, String.class, null);
            table.setColumnExpandRatio(spaltenName,1);
        }

        final TimesCalculator timesCalculator = new TimesCalculator(messages, oldRessourceGroupsBean, projektBean);
        timesCalculator.calculate();

        final CostsCalculator costsCalculator = new CostsCalculator(projektBean,messages);
        costsCalculator.calculate();

        final Item externItem = table.addItem(EXTERN);
        final Item relativItem = table.addItem(RELATIV);
        final Item absolutItem = table.addItem(ABSOLUT);
        final Item kostenItem = table.addItem(KOSTEN);

        final Item item = table.getItem(EXTERN);
        final Property<?> externItemAngabeProperty = item.getItemProperty(angabe);
        externItemAngabeProperty.setValue(messages.getString("costsscreen_externalEuroPerHour"));
        final Collection<?> itemPropertyIds = externItem.getItemPropertyIds();
        for (final Object spalte : itemPropertyIds) {
            if (!spalte.equals(angabe)) {
                for (final OldRessourceGroup oldRessourceGroup : oldRessourceGroups) {
                    final String spaltenName = spalte.toString();
                    final String spaltenNameAusRessourceGroupsBean = oldRessourceGroup.toString();
                    if (spaltenName.equals(spaltenNameAusRessourceGroupsBean)) {
                        final Property<?> externItemSpalteProperty = externItem.getItemProperty(spalte);
                        final String ressourceGroupsBeanValue = oldRessourceGroup.getExternalEurosPerHour().toString();
                        externItemSpalteProperty.setValue(ressourceGroupsBeanValue + " " + EUR);
                    }
                }
            }
        }

        final Property<?> absolutItemAngabeProperty = absolutItem.getItemProperty(angabe);
        absolutItemAngabeProperty.setValue(messages.getString("costsinit_sumInDDHHMM"));
        for (final Object spalte : absolutItem.getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<OldRessourceGroup, DaysHoursMinutesItem> absoluteWerte = timesCalculator.getAbsoluteWerte();
                for (final Map.Entry<OldRessourceGroup, DaysHoursMinutesItem> absoluteWerteEntry : absoluteWerte.entrySet()) {
                    final String spaltenName = spalte.toString();
                    final OldRessourceGroup key = absoluteWerteEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> absolutItemSpalteProperty = absolutItem.getItemProperty(spalte);
                        final String mapValue = absoluteWerte.get(key).toString();
                        absolutItemSpalteProperty.setValue(mapValue);
                    }
                }
            }
        }

        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        final Property<?> relativItemAngabeProperty = relativItem.getItemProperty(angabe);
        relativItemAngabeProperty.setValue(messages.getString("costsinit_sumInPercent"));
        for (final Object spalte : relativItem.getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<OldRessourceGroup, Double> relativeWerte = timesCalculator.getRelativeWerte();
                for (final Map.Entry<OldRessourceGroup, Double> relativeWerteEntry : relativeWerte.entrySet()) {
                    final OldRessourceGroup key = relativeWerteEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    final String spaltenName = spalte.toString();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> relativItemSpalteProperty = relativItem.getItemProperty(spalte);
                        final String mapValue = format.format(relativeWerte.get(key));
                        relativItemSpalteProperty.setValue(mapValue + " %");
                    }

                }
            }
        }

        final Property<?> kostenItemAngabeProperty = kostenItem.getItemProperty(angabe);
        kostenItemAngabeProperty.setValue(messages.getString("costsscreen_sumInEuro"));
        for (final Object spalte : kostenItem.getItemPropertyIds()) {
            if (!spalte.equals(angabe)) {
                final Map<OldRessourceGroup, Double> kostenMap = costsCalculator.getRessourceGroupsCostsMap();
                for (final Map.Entry<OldRessourceGroup, Double> kostenEntry : kostenMap.entrySet()) {
                    final String spaltenName = spalte.toString();
                    final OldRessourceGroup key = kostenEntry.getKey();
                    final String spaltenNameAusMap = key.getName();
                    if (spaltenNameAusMap.equals(spaltenName)) {
                        final Property<?> kostenItemSpalteProperty = kostenItem.getItemProperty(spalte);
                        final String mapValue = format.format(kostenMap.get(key));
                        kostenItemSpalteProperty.setValue(mapValue + " " + EUR);
                    }
                }
            }
        }
    }
}
