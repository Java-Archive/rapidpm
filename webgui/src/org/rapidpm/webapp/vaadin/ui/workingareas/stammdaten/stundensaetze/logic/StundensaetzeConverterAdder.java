package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;

import static org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.StundensaetzeTableCreator.*;

public class StundensaetzeConverterAdder implements ConverterAdder {

    @Override
    public void addConvertersTo(final Table tabelle) {

        tabelle.setConverter(NESTED_BEAN_NAME+"."+BRUTTOGEHALT, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(final Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(NESTED_BEAN_NAME+"."+FACTURIZABLE, new StringToNumberConverter() {
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

        tabelle.setConverter(NESTED_BEAN_NAME+"."+EXTERNAL_EUROS_PER_HOUR,
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
