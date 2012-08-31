package org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.costs.logic;

import com.vaadin.data.util.converter.StringToNumberConverter;
import com.vaadin.ui.Table;
import org.rapidpm.webapp.vaadin.ui.workingareas.projektplanung.interfaces.ConverterAdder;

import java.text.NumberFormat;
import java.util.Locale;

public class CostsConverterAdder implements ConverterAdder {

    @Override
    public void addConvertersTo(Table tabelle) {


        for(Object spaltenId : tabelle.getContainerPropertyIds()){
            if(!spaltenId.equals("Aufgabe")){
                tabelle.setConverter(spaltenId,
                        new StringToNumberConverter() {
                            @Override
                            protected NumberFormat getFormat(Locale locale) {
                                return NumberFormat.getCurrencyInstance(locale);
                            }
                        });
            }
        }


    }

}
