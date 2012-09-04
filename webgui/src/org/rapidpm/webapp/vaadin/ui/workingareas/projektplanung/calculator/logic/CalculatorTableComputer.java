package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.calculator.logic;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.Constants;

import java.util.Collection;

import static org.rapidpm.Constants.*;

public class CalculatorTableComputer {
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

    public CalculatorTableComputer(final Table tabelle) {
        table = tabelle;
    }

    public void computeColumns() {
        itemIds = table.getItemIds();
        for (final Object itemId : itemIds) {
            computeHoursPerYear(itemId);
            computeEurosPerHour(itemId);
            computeOperativeEurosPerHour(itemId);
            computeBruttoPerMonth(itemId);
            computeSumPerMonth(itemId);
            computeSumPerDay(itemId);
        }
        final DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
        table.setColumnFooter(SUM_PER_MONTH, format.format(sumPerMonthTotal)+ Constants.EUR);
        table.setColumnFooter(SUM_PER_DAY, format.format(sumPerDayTotal) + Constants.EUR);
        table.setColumnFooter(RESSOURCE, GESAMTSUMMEN);
    }

    private void computeHoursPerYear(final Object itemId) {
        final Item item = table.getItem(itemId);
        final Integer hoursPerWeek = (Integer) getCellContent(itemId, HOURS_PER_WEEK);
        final Integer weeksPerYear = (Integer) getCellContent(itemId, WEEKS_PER_YEAR);
        final Integer hoursPerYear = hoursPerWeek * weeksPerYear;
        final Property<?> itemProperty = item.getItemProperty(HOURS_PER_YEAR);
        itemProperty.setValue(hoursPerYear);
    }

    private void computeEurosPerHour(final Object itemId) {
        final Item item = table.getItem(itemId);
        final Double bruttoGehalt = (Double) getCellContent(itemId, BRUTTO_GEHALT);
        final double hoursPerYear = Double.parseDouble(getCellContent(itemId, HOURS_PER_YEAR).toString());
        final double facturierbar = Double.parseDouble(getCellContent(itemId, FACTURIZABLE).toString());
        final Double eurosPerHour = bruttoGehalt/ (hoursPerYear * facturierbar);
        item.getItemProperty(EUROS_PER_HOUR).setValue(eurosPerHour);
    }

    private void computeOperativeEurosPerHour(final Object itemId) {
        final Item item = table.getItem(itemId);
        final Double externalEurosPerHour = (Double) getCellContent(itemId, EXTERNAL_EUROS_PER_HOUR);
        final Double eurosPerHour = (Double) getCellContent(itemId, EUROS_PER_HOUR);
        final Double operativeEurosPerHour = externalEurosPerHour - eurosPerHour;
        item.getItemProperty(OPERATIVE_EUROS_PER_HOUR).setValue(operativeEurosPerHour);
    }

    private void computeBruttoPerMonth(final Object itemId) {
        final Double bruttoGehalt = (Double) getCellContent(itemId, BRUTTO_GEHALT);
        final Double bruttoPerMonth = bruttoGehalt / MONTH_COUNT_YEAR;
        final Item item = table.getItem(itemId);
        item.getItemProperty(BRUTTO_PER_MONTH).setValue(bruttoPerMonth);
    }

    private void computeSumPerMonth(final Object itemId) {
        final Double bruttoPerMonth = (Double) getCellContent(itemId, BRUTTO_PER_MONTH);
        final double planAnzahl = Double.parseDouble(getCellContent(itemId, PLAN_ANZAHL).toString());
        final Double sumPerMonth = bruttoPerMonth * planAnzahl;
        final Item item = table.getItem(itemId);
        final Property<?> itemProperty = item.getItemProperty(SUM_PER_MONTH);
        itemProperty.setValue(sumPerMonth);

        sumPerMonthTotal += sumPerMonth;
    }

    private void computeSumPerDay(final Object itemId) {
        final Double sumPerMonth = (Double) getCellContent(itemId, SUM_PER_MONTH);
        final Double sumPerDay = sumPerMonth / STD_WORKING_DAYS_PER_MONTH;
        final Item item = table.getItem(itemId);
        item.getItemProperty(SUM_PER_DAY).setValue(sumPerDay);

        sumPerDayTotal += sumPerDay;

    }

    private Object getCellContent(final Object itemId, final String propertyId) {
        final Item item = table.getItem(itemId);
        final Property<?> itemProperty = item.getItemProperty(propertyId);
        final Object value = itemProperty.getValue();
        return value;
    }

}
