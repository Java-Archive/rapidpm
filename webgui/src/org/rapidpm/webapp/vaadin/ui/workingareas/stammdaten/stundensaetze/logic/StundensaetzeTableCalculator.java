package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.Table;

import java.util.Collection;

import static org.rapidpm.Constants.*;

public class StundensaetzeTableCalculator {
    public static final double MONTH_COUNT_YEAR = 12.0;
    public static final double STD_WORKING_DAYS_PER_MONTH = 20.0;
    public static final String SUM_PER_MONTH = "sumPerMonth";
    public static final String PLAN_ANZAHL = "planAnzahl";
    public static final String BRUTTO_PER_MONTH = "bruttoPerMonth";
    public static final String BRUTTO_GEHALT = "bruttoGehalt";
    public static final String OPERATIVE_EUROS_PER_HOUR = "operativeEurosPerHour";
    public static final String EUROS_PER_HOUR = "eurosPerHour";
    public static final String EXTERNAL_EUROS_PER_HOUR = "externalEurosPerHour";
    public static final String FACTURIZABLE = "facturizable";
    public static final String HOURS_PER_YEAR = "hoursPerYear";
    public static final String WEEKS_PER_YEAR = "weeksPerYear";
    public static final String HOURS_PER_WEEK = "hoursPerWeek";
    public static final String GESAMTSUMMEN = "Gesamtsummen:";
    public static final String RESSOURCE = "ressource";
    public static final String SUM_PER_DAY = "sumPerDay";

    private Collection<?> itemIds;
    private Table table;

    private Double sumPerMonthTotal = 0.0;
    private Double sumPerDayTotal = 0.0;

    public StundensaetzeTableCalculator(final Table tabelle) {
        table = tabelle;
    }

    public void computeColumns() {
        itemIds = table.getItemIds();
        for (final Object itemId : itemIds) {
            final Item item = table.getItem(itemId);
            computeHoursPerYear(item);
            computeEurosPerHour(item);
            computeOperativeEurosPerHour(item);
            computeBruttoPerMonth(item);
            computeSumPerMonth(item);
            computeSumPerDay(item);
        }
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        table.setColumnFooter(SUM_PER_MONTH, format.format(sumPerMonthTotal)+ EUR);
        table.setColumnFooter(SUM_PER_DAY, format.format(sumPerDayTotal) + EUR);
        table.setColumnFooter(RESSOURCE, GESAMTSUMMEN);
    }

    private void computeHoursPerYear(final Item item) {
        final Integer hoursPerWeek = Integer.parseInt(getCellContent(item, HOURS_PER_WEEK));
        final Integer weeksPerYear = Integer.parseInt(getCellContent(item, WEEKS_PER_YEAR));
        final Integer hoursPerYear = hoursPerWeek * weeksPerYear;
        final Property<?> itemProperty = item.getItemProperty(HOURS_PER_YEAR);
        itemProperty.setValue(hoursPerYear);
    }

    private void computeEurosPerHour(final Item item) {
        final Double bruttoGehalt = Double.parseDouble(getCellContent(item, BRUTTO_GEHALT));
        final Double hoursPerYear = Double.parseDouble(getCellContent(item, HOURS_PER_YEAR));
        final Double facturierbar = Double.parseDouble(getCellContent(item, FACTURIZABLE));
        final Double eurosPerHour = bruttoGehalt/ (hoursPerYear * facturierbar);
        final Property<?> itemProperty = item.getItemProperty(EUROS_PER_HOUR);
        itemProperty.setValue(eurosPerHour);
    }

    private void computeOperativeEurosPerHour(final Item item) {
        final Double externalEurosPerHour = Double.parseDouble(getCellContent(item, EXTERNAL_EUROS_PER_HOUR));
        final Double eurosPerHour = Double.parseDouble(getCellContent(item, EUROS_PER_HOUR));
        final Double operativeEurosPerHour = externalEurosPerHour - eurosPerHour;
        final Property<?> itemProperty = item.getItemProperty(OPERATIVE_EUROS_PER_HOUR);
        itemProperty.setValue(operativeEurosPerHour);
    }

    private void computeBruttoPerMonth(final Item item) {
        final Double bruttoGehalt = Double.parseDouble(getCellContent(item, BRUTTO_GEHALT));
        final Double bruttoPerMonth = bruttoGehalt / MONTH_COUNT_YEAR;
        final Property<?> itemProperty = item.getItemProperty(BRUTTO_PER_MONTH);
        itemProperty.setValue(bruttoPerMonth);
    }

    private void computeSumPerMonth(final Item item) {
        final Double bruttoPerMonth = Double.parseDouble(getCellContent(item, BRUTTO_PER_MONTH));
        final Double planAnzahl = Double.parseDouble(getCellContent(item, PLAN_ANZAHL));
        final Double sumPerMonth = bruttoPerMonth * planAnzahl;
        final Property<?> itemProperty = item.getItemProperty(SUM_PER_MONTH);
        itemProperty.setValue(sumPerMonth);

        sumPerMonthTotal += sumPerMonth;
    }

    private void computeSumPerDay(final Item item) {
        final Double sumPerMonth = Double.parseDouble(getCellContent(item, SUM_PER_MONTH));
        final Double sumPerDay = sumPerMonth / STD_WORKING_DAYS_PER_MONTH;
        final Property<?> itemProperty = item.getItemProperty(SUM_PER_DAY);
        itemProperty.setValue(sumPerDay);

        sumPerDayTotal += sumPerDay;

    }

    private String getCellContent(final Item item, final String propertyId) {
        Property<?> itemProperty = null;
        String value = null;
        try{
           itemProperty = item.getItemProperty(propertyId);
           value = itemProperty.getValue().toString();
        } catch(NullPointerException npe) {
            value = "0";
        }finally {
            return value;
        }
    }

}
