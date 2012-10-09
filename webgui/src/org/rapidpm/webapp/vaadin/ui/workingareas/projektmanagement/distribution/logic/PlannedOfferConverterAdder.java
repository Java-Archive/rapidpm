package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.distribution.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;

import static org.rapidpm.persistence.prj.projectmanagement.planning.finance.PlannedOffer.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 09.10.12
 * Time: 09:06
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */

public class PlannedOfferConverterAdder implements ConverterAdder {

    @Override
    public void addConvertersTo(final Table tabelle) {

        tabelle.setConverter(PERCENT, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getPercentInstance(locale);
            }
        });

        tabelle.setConverter(COSTS, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(final Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(EUROS_PER_HOUR, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getCurrencyInstance(locale);
            }
        });

        tabelle.setConverter(COSTS,
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getCurrencyInstance(locale);
                    }
                });

        tabelle.setConverter(PERCENT_WITH,
                new StringToNumberConverter() {
                    @Override
                    protected NumberFormat getFormat(Locale locale) {
                        return NumberFormat.getPercentInstance(locale);
                    }
                });

        tabelle.setConverter(PERCENT_WITHOUT, new StringToNumberConverter() {
            @Override
            protected NumberFormat getFormat(Locale locale) {
                return NumberFormat.getPercentInstance(locale);
            }
        });
    }

}
