package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 12.09.12
 * Time: 12:03
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class TableEditFieldFactory implements TableFieldFactory {

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
            if(propertyId.toString().startsWith("ressourceGroup")){
                final TextField field = new TextField();
                final String cellValue = container.getItem(itemId).getItemProperty(propertyId).getValue().toString();
                field.setValue(cellValue);
                return field;
            }
        return null;
    }
}