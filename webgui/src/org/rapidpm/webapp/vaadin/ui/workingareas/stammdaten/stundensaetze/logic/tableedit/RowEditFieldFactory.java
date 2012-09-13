package org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.logic.tableedit;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;
import org.rapidpm.webapp.vaadin.ui.workingareas.stammdaten.stundensaetze.datenmodell.RessourceGroupBean;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 13.09.12
 * Time: 09:23
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class RowEditFieldFactory implements TableFieldFactory {

    private Item item;

    public RowEditFieldFactory(Item item){
        this.item = item;
    }

    @Override
    public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
        if((((RessourceGroupBean)itemId)).equals(((BeanItem<RessourceGroupBean>)item).getBean())){
            final String spaltenName = propertyId.toString();
            if(spaltenName.startsWith("ressourceGroup")){
                final TextField field = new TextField();
                final String cellValue = container.getItem(itemId).getItemProperty(propertyId).getValue().toString();
                field.setValue(cellValue);
                return field;
            }
        }
        return null;
    }
}
