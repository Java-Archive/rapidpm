package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.Item;

import com.vaadin.external.com.ibm.icu.text.DecimalFormat;
import com.vaadin.ui.Table;

import static org.rapidpm.Constants.*;

public class StundensaetzeFieldsComputer {

    public static final String SUM_PER_DAY = "sumPerDay";
    private Table tabelle;
    private Double betriebsStunde;
    private Double betriebsWert;

    public StundensaetzeFieldsComputer(final Table tabelle) {
        this.tabelle = tabelle;
    }

    public void compute() {
        Double summeProTag = 0.0;

        for (final Object itemId : tabelle.getItemIds()) {
            final Item item = tabelle.getItem(itemId);
            final Double sumPerDay = (Double) item.getItemProperty(SUM_PER_DAY).getValue();
            summeProTag += sumPerDay;
        }
        betriebsStunde = summeProTag / WORKINGHOURS_DAY;
        betriebsWert = summeProTag / KONSTANTE;
    }

    public Double getBetriebsStunde() {
        return betriebsStunde;
    }

    public Double getBetriebsWert() {
        return betriebsWert;
    }

    public String getBetriebsStundeAsString() {
        final DecimalFormat f = new DecimalFormat(DECIMAL_FORMAT);
        return f.format(betriebsStunde) + EUR;
    }

    public String getBetriebsFraAsString() {
        final DecimalFormat f = new DecimalFormat(DECIMAL_FORMAT);
        return f.format(betriebsWert) + EUR;
    }

}
