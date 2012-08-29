package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.deprecated;

/**
 * Created by IntelliJ IDEA.
 * User: Alexander Vos
 * Date: 13.04.12
 * Time: 12:39
 */
public class Personal {

    public static final String[] VISIBLE_COLUMNS = {"wer", "was", "dauer", "anzahl", "summe"};
    public static final String[] COLUMN_NAMES = {"Wer", "Was", "Dauer / Stk", "Anzahl / Stk", "Summe h"};

    private String wer;
    private String was;
    private float dauer; // Dauer/Stk in h
    private int anzahl; // Anzahl/Stk

    public Personal(final String wer, final String was, final float dauer, final int anzahl) {
        this.wer = wer;
        this.was = was;
        this.dauer = dauer;
        this.anzahl = anzahl;
    }

    public String getWer() {
        return wer;
    }

    public String getWas() {
        return was;
    }

    public float getDauer() {
        return dauer;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public float getSumme() {
        return dauer * anzahl;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Personal");
        sb.append("{wer='").append(wer).append('\'');
        sb.append(", was='").append(was).append('\'');
        sb.append(", dauer=").append(dauer);
        sb.append(", anzahl=").append(anzahl);
        sb.append(", summe=").append(getSumme());
        sb.append('}');
        return sb.toString();
    }
}
