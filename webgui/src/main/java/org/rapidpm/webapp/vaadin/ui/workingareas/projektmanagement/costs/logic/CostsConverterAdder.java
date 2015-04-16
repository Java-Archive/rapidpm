package org.rapidpm.webapp.vaadin.ui.workingareas.projektmanagement.costs.logic;

import com.vaadin.data.util.converter.StringToFloatConverter;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

//import org.rapidpm.logging.LoggerQualifier;


public class CostsConverterAdder implements ConverterAdder {

//    @Inject @LoggerQualifier
//    private transient Logger logger;

    private ResourceBundle messages;

    public CostsConverterAdder(final ResourceBundle bundle) {
        this.messages = bundle;
    }

    @Override
    public void addConvertersTo(final Table tabelle) {
        for (final Object spaltenId : tabelle.getContainerPropertyIds()) {
            final String aufgabe = messages.getString("aufgabe");
            if (!spaltenId.equals(aufgabe)) {
                tabelle.setConverter(spaltenId,
                        new StringToFloatConverter() {
                            @Override
                            protected NumberFormat getFormat(final Locale locale) {
                                return NumberFormat.getCurrencyInstance(new Locale("de","DE"));
                            }
                        });
            }
        }


    }

}
