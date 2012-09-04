package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;
import org.apache.log4j.Logger;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import javax.inject.Inject;
import java.text.NumberFormat;
import java.util.Locale;

import static org.rapidpm.Constants.AUFGABE_SPALTE;

public class CostsConverterAdder implements ConverterAdder {

    @Inject
    private transient Logger logger;

    public CostsConverterAdder() {
    }

    @Override
    public void addConvertersTo(final Table tabelle) {
        for (final Object spaltenId : tabelle.getContainerPropertyIds()) {
            if (!spaltenId.equals(AUFGABE_SPALTE)) {
                tabelle.setConverter(spaltenId,
                        new StringToNumberConverter() {
                            @Override
                            protected NumberFormat getFormat(final Locale locale) {
                                return NumberFormat.getCurrencyInstance(locale);
                            }
                        });
            }
        }


    }

}
