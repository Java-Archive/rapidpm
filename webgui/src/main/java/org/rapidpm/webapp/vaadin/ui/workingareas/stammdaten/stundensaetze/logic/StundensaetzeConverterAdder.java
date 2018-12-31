package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;

import static org.rapidpm.persistence.prj.stammdaten.organisationseinheit.intern.personal.RessourceGroup.*;

public class StundensaetzeConverterAdder implements ConverterAdder {

    @Override
    public void addConvertersTo(final Grid tabelle) {

//        tabelle.setConverter(BRUTTOGEHALT, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(final Locale locale) {
//                return NumberFormat.getCurrencyInstance(locale);
//            }
//        });
//
//        tabelle.setConverter(FACTURIZABLE, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(Locale locale) {
//                return NumberFormat.getPercentInstance(locale);
//            }
//        });
//
//        tabelle.setConverter(EUROS_PER_HOUR, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(Locale locale) {
//                return NumberFormat.getCurrencyInstance(locale);
//            }
//        });
//
//        tabelle.setConverter(EXTERNAL_EUROS_PER_HOUR,
//                new StringToFloatConverter() {
//                    @Override
//                    protected NumberFormat getFormat(Locale locale) {
//                        return NumberFormat.getCurrencyInstance(locale);
//                    }
//                });
//
//        tabelle.setConverter(OPERATIVE_EUROS_PER_HOUR,
//                new StringToFloatConverter() {
//                    @Override
//                    protected NumberFormat getFormat(Locale locale) {
//                        return NumberFormat.getCurrencyInstance(locale);
//                    }
//                });
//
//        tabelle.setConverter(BRUTTO_PER_MONTH, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(Locale locale) {
//                return NumberFormat.getCurrencyInstance(locale);
//            }
//        });
//
//        tabelle.setConverter(SUM_PER_MONTH, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(Locale locale) {
//                return NumberFormat.getCurrencyInstance(locale);
//            }
//        });
//
//        tabelle.setConverter(SUM_PER_DAY, new StringToFloatConverter() {
//            @Override
//            protected NumberFormat getFormat(Locale locale) {
//                return NumberFormat.getCurrencyInstance(locale);
//            }
//        });
    }

}
