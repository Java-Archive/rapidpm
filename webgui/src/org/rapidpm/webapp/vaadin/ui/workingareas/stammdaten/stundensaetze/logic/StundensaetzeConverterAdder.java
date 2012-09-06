package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;

public class StundensaetzeConverterAdder implements ConverterAdder {

    public static final String BRUTTO_GEHALT = "bruttoGehalt";
    public static final String FACTURIZABLE = "facturizable";
    public static final String EUROS_PER_HOUR = "eurosPerHour";
    public static final String EXTERNAL_EUROS_PER_HOUR = "externalEurosPerHour";
    public static final String OPERATIVE_EUROS_PER_HOUR = "operativeEurosPerHour";
    public static final String BRUTTO_PER_MONTH = "bruttoPerMonth";
    public static final String SUM_PER_MONTH = "sumPerMonth";
    public static final String SUM_PER_DAY = "sumPerDay";

    @Override
    public void addConvertersTo(final Table tabelle) {

        tabelle.setConverter(BRUTTO_GEHALT, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(final Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(FACTURIZABLE, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getPercentInstance(locale);
            }
        });

        tabelle.setConverter(EUROS_PER_HOUR, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(EXTERNAL_EUROS_PER_HOUR,
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getCurrencyInstance(locale);
                    }
                });

        tabelle.setConverter(OPERATIVE_EUROS_PER_HOUR,
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getCurrencyInstance(locale);
                    }
                });

        tabelle.setConverter(BRUTTO_PER_MONTH, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(SUM_PER_MONTH, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(SUM_PER_DAY, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });
    }

}
