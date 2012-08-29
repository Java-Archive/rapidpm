package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.Item;
import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.Constants;

import java.util.Collection;

public class StundensaetzeTableComputer {
    private Collection<?> itemIds;
    private Table table;

    private Double sumPerMonthTotal = 0.0;
    private Double sumPerDayTotal = 0.0;

    public StundensaetzeTableComputer(Table tabelle) {
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
        final DecimalFormat format = new DecimalFormat("0.00");
        table.setColumnFooter("sumPerMonth", format.format(sumPerMonthTotal)
                + Constants.EUR);
        table.setColumnFooter("sumPerDay", format.format(sumPerDayTotal) + Constants.EUR);
        table.setColumnFooter("ressource", "Gesamtsummen:");
    }

    private void computeHoursPerYear(Object itemId) {
        final Item item = table.getItem(itemId);
        final Integer hoursPerYear = ((Integer) getCellContent(itemId, "hoursPerWeek"))
                * ((Integer) getCellContent(itemId, "weeksPerYear"));
        item.getItemProperty("hoursPerYear")
                .setValue(hoursPerYear);
    }

    private void computeEurosPerHour(Object itemId) {
        final Item item = table.getItem(itemId);
        final Double eurosPerHour = ((Double) getCellContent(itemId, "bruttoGehalt"))
                / ((Double.parseDouble(getCellContent(itemId, "hoursPerYear")
                .toString())) * (Double.parseDouble(getCellContent(
                itemId, "facturizable").toString())));
        item.getItemProperty("eurosPerHour")
                .setValue(eurosPerHour);
    }

    private void computeOperativeEurosPerHour(Object itemId) {
        final Item item = table.getItem(itemId);
        final Double operativeEurosPerHour = ((Double) getCellContent(itemId,
                "externalEurosPerHour"))
                - ((Double) getCellContent(itemId, "eurosPerHour"));
        item.getItemProperty("operativeEurosPerHour")
                .setValue(operativeEurosPerHour);
    }

    private void computeBruttoPerMonth(Object itemId) {
        final Double bruttoPerMonth = ((Double) getCellContent(itemId, "bruttoGehalt")) / 12.0;
        final Item item = table.getItem(itemId);
        item.getItemProperty("bruttoPerMonth")
                .setValue(bruttoPerMonth);
    }

    private void computeSumPerMonth(Object itemId) {
        final Double sumPerMonth = ((Double) getCellContent(itemId, "bruttoPerMonth"))
                * Double.parseDouble(getCellContent(itemId, "planAnzahl")
                .toString());
        final Item item = table.getItem(itemId);
        item.getItemProperty("sumPerMonth")
                .setValue(sumPerMonth);

        sumPerMonthTotal += sumPerMonth;
    }

    private void computeSumPerDay(Object itemId) {
        final Double sumPerDay = ((Double) getCellContent(itemId, "sumPerMonth")) / 20.0;
        final Item item = table.getItem(itemId);
        item.getItemProperty("sumPerDay").setValue(sumPerDay);

        sumPerDayTotal += sumPerDay;

    }

    private Object getCellContent(Object itemId, String propertyId) {
        return table.getItem(itemId).getItemProperty(propertyId).getValue();
    }

}
