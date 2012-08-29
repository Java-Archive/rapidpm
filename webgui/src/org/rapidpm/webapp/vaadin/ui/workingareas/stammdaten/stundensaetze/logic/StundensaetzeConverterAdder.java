package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;

import java.text.NumberFormat;
import java.util.Locale;

public class StundensaetzeConverterAdder implements ConverterAdder {

    @Override
    public void addConvertersTo(Table tabelle) {

        tabelle.setConverter("bruttoGehalt", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter("facturizable", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getPercentInstance(locale);
            }
        });

        tabelle.setConverter("eurosPerHour", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter("externalEurosPerHour",
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getCurrencyInstance(locale);
                    }
                });

        tabelle.setConverter("operativeEurosPerHour",
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getCurrencyInstance(locale);
                    }
                });

        tabelle.setConverter("bruttoPerMonth", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter("sumPerMonth", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter("sumPerDay", new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });
    }

}
